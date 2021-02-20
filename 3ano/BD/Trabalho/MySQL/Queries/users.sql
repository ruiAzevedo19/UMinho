-- -------------------------------------------------------------------------------------------------------------------------
-- Criação do perfil de administrador

-- Criação do utilizador
CREATE USER 'admin'@'localhost'
	IDENTIFIED BY 'admin';
    
-- Permissões
GRANT ALL ON Arquivo.* TO 'admin'@'localhost';

-- Tabela de users
SELECT * FROM mysql.user;

-- Nomes dos utilizadres e dos sistemas em que estão definidos.
SELECT User, Host FROM mysql.user
	ORDER BY User;
    
-- Caracterização de um utilizador
DELIMITER $$
CREATE PROCEDURE  userInfo(IN nomeUser VARCHAR(45))
	BEGIN
		SELECT * FROM mysql.user
			WHERE User = nomeUser;
    END$$

-- Consulta dos privilegios dos utilizadores
SHOW GRANTS FOR 'app'@'localhost';

-- -------------------------------------------------------------------------------------------------------------------------
-- Criação do perfil de app

-- Criação do utilizador
CREATE USER 'app'@'localhost'
	IDENTIFIED BY 'app';
    
-- Permissões 
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Anuncio TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Artigo TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Contactos TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Editor TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Empresa TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Escritor TO 'app'@'localhost';
GRANT SELECT , INSERT , UPDATE  ON Arquivo.Revista TO 'app'@'localhost';

-- -------------------------------------------------------------------------------------------------------------------------
