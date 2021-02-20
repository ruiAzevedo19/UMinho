-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Arquivo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Arquivo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Arquivo` DEFAULT CHARACTER SET utf8 ;
USE `Arquivo` ;

-- -----------------------------------------------------
-- Table `Arquivo`.`Editor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Editor` (
  `ID` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Empresa` (
  `ID` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cidade` VARCHAR(45) NOT NULL,
  `Rua` VARCHAR(45) NOT NULL,
  `QuantidadeRevistas` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Revista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Revista` (
  `ID` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Edicao` DATE NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `NrConsultas` INT NOT NULL DEFAULT 0,
  `Editor_ID` INT NOT NULL,
  `Empresa_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Revista_1_idx` (`Editor_ID` ASC),
  INDEX `fk_Revista_Empresa1_idx` (`Empresa_ID` ASC),
  CONSTRAINT `fk_Revista_1`
    FOREIGN KEY (`Editor_ID`)
    REFERENCES `Arquivo`.`Editor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Revista_Empresa1`
    FOREIGN KEY (`Empresa_ID`)
    REFERENCES `Arquivo`.`Empresa` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Artigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Artigo` (
  `ID` INT NOT NULL,
  `Revista_ID` INT NOT NULL,
  `Data` DATE NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Corpo` VARCHAR(2500) NOT NULL,
  `NrConsultas` INT NOT NULL DEFAULT 0,
  `Categoria` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Artigo_1_idx` (`Revista_ID` ASC),
  CONSTRAINT `fk_Artigo_1`
    FOREIGN KEY (`Revista_ID`)
    REFERENCES `Arquivo`.`Revista` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Anuncio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Anuncio` (
  `ID` INT NOT NULL,
  `Titulo` VARCHAR(45) NOT NULL,
  `Conteudo` VARCHAR(250) NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `NrConsultas` INT NOT NULL DEFAULT 0,
  `Contacto` VARCHAR(45) NULL,
  `Empresa_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Anuncio_1_idx` (`Empresa_ID` ASC),
  CONSTRAINT `fk_Anuncio_1`
    FOREIGN KEY (`Empresa_ID`)
    REFERENCES `Arquivo`.`Empresa` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`AnuncioRevista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`AnuncioRevista` (
  `Anuncio_ID` INT NOT NULL,
  `Revista_ID` INT NOT NULL,
  PRIMARY KEY (`Anuncio_ID`, `Revista_ID`),
  INDEX `fk_AnuncioJornal_2_idx` (`Revista_ID` ASC),
  CONSTRAINT `fk_AnuncioJornal_1`
    FOREIGN KEY (`Anuncio_ID`)
    REFERENCES `Arquivo`.`Anuncio` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AnuncioJornal_2`
    FOREIGN KEY (`Revista_ID`)
    REFERENCES `Arquivo`.`Revista` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Contactos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Contactos` (
  `Empresa_ID` INT NOT NULL,
  `Contacto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Contacto`, `Empresa_ID`),
  INDEX `fk_Contactos_Empresa1_idx` (`Empresa_ID` ASC),
  CONSTRAINT `fk_Contactos_Empresa1`
    FOREIGN KEY (`Empresa_ID`)
    REFERENCES `Arquivo`.`Empresa` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`Escritor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`Escritor` (
  `ID` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Arquivo`.`EscritorArtigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Arquivo`.`EscritorArtigo` (
  `Artigo_ID` INT NOT NULL,
  `Escritor_ID` INT NOT NULL,
  PRIMARY KEY (`Artigo_ID`, `Escritor_ID`),
  INDEX `fk_ArtigoEscritor_2_idx` (`Escritor_ID` ASC),
  CONSTRAINT `fk_ArtigoEscritor_1`
    FOREIGN KEY (`Artigo_ID`)
    REFERENCES `Arquivo`.`Artigo` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ArtigoEscritor_2`
    FOREIGN KEY (`Escritor_ID`)
    REFERENCES `Arquivo`.`Escritor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `Arquivo` ;

-- -----------------------------------------------------
--  routine1
-- -----------------------------------------------------

DELIMITER $$
USE `Arquivo`$$
$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
