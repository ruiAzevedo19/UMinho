-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir uma empresa
DELIMITER $$
CREATE PROCEDURE insertEmpresa(IN nomeE VARCHAR(45) , IN cidadeE VARCHAR(45) , IN ruaE VARCHAR(45))
	BEGIN
		DECLARE teste INT;
        DECLARE idE INT;
        
        SET idE = 1 + (SELECT COUNT(ID) FROM Empresa);
        SET teste = (SELECT COUNT(Nome) FROM Empresa WHERE Nome = nomeE);
        
        START TRANSACTION;
        
        IF( teste = 0 ) THEN
			INSERT INTO Empresa 
				(ID, Nome, Cidade, Rua)
				VALUES
				(idE, nomeE, cidadeE, ruaE);
                SELECT 'Empresa registada com sucesso!!!';
				COMMIT;
        ELSE 
				SELECT 'Empresa já existe!!!';
                ROLLBACK;
        END IF;
    END$$


-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir editor

DELIMITER $$
CREATE PROCEDURE insertEditor(IN nomeE VARCHAR(45) )
	BEGIN
		DECLARE teste INT;
        DECLARE idE INT;
        
        SET idE = 1 + (SELECT COUNT(ID) FROM Editor);
        SET teste = (SELECT COUNT(Nome) FROM Editor WHERE Nome = nomeE);
        
        START TRANSACTION;
        
        IF( teste = 0 ) THEN
			INSERT INTO Editor 
				(ID, Nome)
				VALUES
				(idE, nomeE);
                SELECT 'Editor registado com sucesso!!!';
				COMMIT;
        ELSE 
				SELECT 'Editor já existe!!!';
                ROLLBACK;
        END IF;
    END$$

-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir escritor

DELIMITER $$
CREATE PROCEDURE insertEscritor(IN nomeE VARCHAR(45) )
	BEGIN
		DECLARE teste INT;
        DECLARE idE INT;
        
        SET idE = 1 + (SELECT COUNT(ID) FROM Escritor);
        SET teste = (SELECT COUNT(Nome) FROM Escritor WHERE Nome = nomeE);
        
        START TRANSACTION;
        
        IF( teste = 0 ) 
			THEN
				INSERT INTO Escritor 
				(ID, Nome)
				VALUES
				(idE, nomeE);
                SELECT 'Escritor registado com sucesso!!!';
				COMMIT;
		ELSE
				SELECT 'Escritor já existe!!!';
                ROLLBACK;
        END IF;
    END$$
    
    DROP PROCEDURE insertEscritor;

CALL insertEscritor('Rafa');
SELECT * FROM Escritor;
-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir Anuncio

DELIMITER $$ 
CREATE PROCEDURE insertAnuncio(IN TituloA VARCHAR(45) , IN ConteudoA VARCHAR(2500) , IN CategoriaA VARCHAR(45), 
															  IN ContactoA VARCHAR(45), IN nomeEmpresa VARCHAR(45) , IN revistaNome VARCHAR(45))
	BEGIN    
		DECLARE teste INT;
        DECLARE idA INT;
        DECLARE testeR INT;
        DECLARE testeE BOOL ;
        DECLARE IDE INT;
        
        SET idA 	   = 1 + (SELECT COUNT(ID) FROM Anuncio);
        SET teste   =  (SELECT COUNT(ID) FROM Escritor WHERE ID = idA);
        SET testeE = (SELECT COUNT(Nome) FROM Empresa WHERE Nome = nomeEmpresa);
        SET testeR = (SELECT ID FROM Revista WHERE Nome = revistaNome);
		
        START TRANSACTION;
        
        IF( teste = 0 && testeE > 0 && testeR IS NOT NULL) 
			THEN 
                SET IDE = (SELECT ID FROM Empresa WHERE Nome = nomeEmpresa);
                INSERT INTO Anuncio  
					(ID,Titulo,Conteudo,Categoria,Contacto, Empresa_ID)
                    VALUES
                    (idA, TituloA,ConteudoA,CategoriaA,ContactoA,IDE);
				INSERT INTO AnuncioRevista
					(Anuncio_ID , Revista_ID)
                    VALUES
                    (idA , testeR);
				SELECT 'Anúncio registado com sucesso';
				COMMIT;
			ELSE 
				SELECT 'Empresa ou revista associada não existe';
				ROLLBACK;
			END IF;
    END$$ 

-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir revista

DELIMITER $$
CREATE PROCEDURE inserirRevista(IN nomeEmpresa VARCHAR(45), IN nomeA VARCHAR(45), IN edicaoA DATE, 
															  IN categoriaA VARCHAR(45), IN nomeEditor VARCHAR(45))
	BEGIN 
		DECLARE testeNomeE INT; 
        DECLARE testeEditorE INT;
        DECLARE idA INT;
        
        SET testeNomeE = (SELECT ID FROM Empresa WHERE Nome = nomeEmpresa);
        SET testeEditorE = (SELECT ID FROM Editor WHERE Nome = nomeEditor);
        SET idA = (SELECT COUNT(ID) FROM Revista WHERE Nome = nomeEmpresa);
        
        START TRANSACTION;
        
        IF( testeNomeE IS NOT NULL && testeEditorE IS  NOT NULL) 
			THEN 
				INSERT INTO Revista
					(ID,Empresa_ID,Nome,Edicao,Categoria,Editor_ID)
					VALUES
                    (idA,testeNomeE,nomeA,edicaoA,categoriaA,testeEditorE);
				SELECT 'Revista adicionado com sucesso';
                COMMIT;
			ELSE 
				SELECT 'Empresa ou editor não existem';
                ROLLBACK;
			END IF;
    END$$    
-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir contacto de uma empresa

DELIMITER $$
CREATE PROCEDURE inserirContacto(IN nomeEmpresa VARCHAR(45) , IN contactoE VARCHAR(45)) 
	BEGIN
		DECLARE idE INT;
        
        SET idE = (SELECT ID FROM Empresa WHERE Nome = nomeEmpresa);
        
        START TRANSACTION;
        
        IF ( idE IS NOT NULL)
			THEN
				INSERT INTO Contactos
					(Empresa_ID , Contacto)
                    VALUES
                    (idE , contactoE);
				SELECT 'Contacto adicionado com sucesso'; 
                COMMIT;
			ELSE 
				SELECT 'Empresa não existe';
                ROLLBACK;
		END IF;
	END $$
    
-- -------------------------------------------------------------------------------------------------------------------------
-- Inserir artigo

DELIMITER $$ 
CREATE PROCEDURE inserirArtigo(IN nomeRevistaA VARCHAR(45) , IN dataA DATE , IN tituloA VARCHAR(45), 
															IN corpoA VARCHAR(45), IN categoriaA VARCHAR(45), IN escritorA VARCHAR(45))
	BEGIN 
		DECLARE idR INT;
        DECLARE idE INT;
        DECLARE idA INT;
        
        SET idR = (SELECT ID FROM Revista WHERE Nome = nomeRevistaA);
        SET idE = (SELECT ID FROM Escritor WHERE Nome = escritorA);
        SET idA = 1 + (SELECT MAX(ID) FROM Artigo);
        
        START TRANSACTION;
        
        IF(idR IS NOT NULL && idE IS NOT NULL)
			THEN
				INSERT INTO Artigo
					(ID, Revista_ID, Data, Titulo, Corpo, Categoria)
                    VALUES
                    (idA, idR, dataA, tituloA, corpoA, categoriaA);
				INSERT INTO EscritorArtigo
					(Escritor_ID, Artigo_ID)
                    VALUES
                    (idE, idA);
				SELECT 'Artigo adicionado com sucesso!!!';
                COMMIT;
			ELSE	
				SELECT 'Revista ou escritor não existem!!!';
                ROLLBACK;
		END IF;
    END$$
                                                            
                                                            