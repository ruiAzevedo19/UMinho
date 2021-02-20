-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ConfiguraFacil
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ConfiguraFacil
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ConfiguraFacil` DEFAULT CHARACTER SET utf8 ;
USE `ConfiguraFacil` ;

-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`modelo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`modelo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  `preco` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`type` (
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`user` (
  `nif` INT(9) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `contacto` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nif`),
  INDEX `fk_funcionario_1_idx` (`tipo` ASC),
  CONSTRAINT `fk_funcionario_1`
    FOREIGN KEY (`tipo`)
    REFERENCES `ConfiguraFacil`.`type` (`type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`categoria` (
  `tipo` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`tipo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`componente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`componente` (
  `id` INT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  `preco` DOUBLE NOT NULL,
  `tipo` VARCHAR(20) NOT NULL,
  `stock` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_componente_1_idx` (`tipo` ASC),
  CONSTRAINT `fk_componente_1`
    FOREIGN KEY (`tipo`)
    REFERENCES `ConfiguraFacil`.`categoria` (`tipo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`cliente` (
  `nif` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `morada` VARCHAR(45) NOT NULL,
  `contacto` VARCHAR(45) NULL,
  PRIMARY KEY (`nif`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`encomenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`encomenda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `modelo` INT NOT NULL,
  `cliente` INT NOT NULL,
  `preco` DOUBLE NULL,
  `vendedor` INT NOT NULL,
  `metodo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_encomenda_2_idx` (`modelo` ASC),
  INDEX `fk_encomenda_3_idx` (`cliente` ASC),
  INDEX `fk_encomenda_4_idx` (`vendedor` ASC),
  CONSTRAINT `fk_encomenda_2`
    FOREIGN KEY (`modelo`)
    REFERENCES `ConfiguraFacil`.`modelo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomenda_3`
    FOREIGN KEY (`cliente`)
    REFERENCES `ConfiguraFacil`.`cliente` (`nif`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomenda_4`
    FOREIGN KEY (`vendedor`)
    REFERENCES `ConfiguraFacil`.`user` (`nif`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`pacote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`pacote` (
  `id` INT NOT NULL,
  `designacao` VARCHAR(45) NOT NULL,
  `preco` DOUBLE NOT NULL,
  `desconto` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`pacoteComp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`pacoteComp` (
  `pacote` INT NOT NULL,
  `componente` INT NOT NULL,
  PRIMARY KEY (`pacote`, `componente`),
  INDEX `fk_pacoteComp_1_idx` (`componente` ASC),
  CONSTRAINT `fk_pacoteComp_1`
    FOREIGN KEY (`componente`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pacoteComp_2`
    FOREIGN KEY (`pacote`)
    REFERENCES `ConfiguraFacil`.`pacote` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`compCompativelModelo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`compCompativelModelo` (
  `componente` INT NOT NULL,
  `modelo` INT NOT NULL,
  PRIMARY KEY (`componente`, `modelo`),
  INDEX `fk_compCompativel_2_idx` (`modelo` ASC),
  CONSTRAINT `fk_compCompativel_1`
    FOREIGN KEY (`componente`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compCompativel_2`
    FOREIGN KEY (`modelo`)
    REFERENCES `ConfiguraFacil`.`modelo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`compIncompativel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`compIncompativel` (
  `comp1` INT NOT NULL,
  `comp2` INT NOT NULL,
  PRIMARY KEY (`comp1`, `comp2`),
  INDEX `fk_compIncompativel_2_idx` (`comp2` ASC),
  CONSTRAINT `fk_compIncompativel_1`
    FOREIGN KEY (`comp1`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compIncompativel_2`
    FOREIGN KEY (`comp2`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`compObrigatorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`compObrigatorio` (
  `comp1` INT NOT NULL,
  `comp2` INT NOT NULL,
  PRIMARY KEY (`comp1`, `comp2`),
  INDEX `fk_compIncompativel_2_idx` (`comp2` ASC),
  CONSTRAINT `fk_compIncompativel_10`
    FOREIGN KEY (`comp1`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compIncompativel_20`
    FOREIGN KEY (`comp2`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ConfiguraFacil`.`EncomendaComponente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`EncomendaComponente` (
  `encomenda` INT NOT NULL,
  `componente` INT NOT NULL,
  PRIMARY KEY (`encomenda`, `componente`),
  INDEX `asfaw_idx` (`componente` ASC),
  CONSTRAINT `adf`
    FOREIGN KEY (`encomenda`)
    REFERENCES `ConfiguraFacil`.`encomenda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `asfaw`
    FOREIGN KEY (`componente`)
    REFERENCES `ConfiguraFacil`.`componente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
