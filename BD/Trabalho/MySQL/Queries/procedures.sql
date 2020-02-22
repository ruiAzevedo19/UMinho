Use Arquivo;
-- -------------------------------------------------------------------------------------------------------------------------

--  (1) Permitir a consulta das informações de uma empresa.
DROP PROCEDURE  IF EXISTS getEmpresaInfo;
DELIMITER $$
CREATE PROCEDURE getEmpresaInfo(IN nomeEmpresa VARCHAR(45))
	BEGIN
		SELECT Nome , Cidade , Rua , QuantidadeRevistas FROM Empresa 
			WHERE Nome = nomeEmpresa;
	END$$

-- Teste --
CALL getEmpresaInfo('Soberania do Povo Editora, S.A.');

-- -------------------------------------------------------------------------------------------------------------------------
--  (2) Permitir a consulta das informações de um anuncio.
DROP PROCEDURE IF EXISTS getAnuncioInfo;
DELIMITER $$
CREATE PROCEDURE getAnuncioInfo(IN tituloAnuncio VARCHAR(45))
	BEGIN
		SELECT Titulo , Conteudo , Categoria , Contacto FROM Anuncio
			WHERE Titulo = tituloAnuncio;
	END$$

-- Teste --
CALL getAnuncioInfo('Empregado de mesa');

-- -------------------------------------------------------------------------------------------------------------------------

--  (3) Permitir a consulta das informações de um artigo.
DROP PROCEDURE IF EXISTS getArtigoInfo;
DELIMITER $$
CREATE PROCEDURE getArtigoInfo(IN nomeArtigo VARCHAR(45) , IN dataArtigo DATE)
	BEGIN
		SELECT Titulo , Data , Corpo FROM Artigo
			WHERE Titulo = nomeArtigo AND Data = dataArtigo;
	END$$

-- Teste --
CALL getArtigoInfo('Viva Melhor' , '2017-12-31');

-- -------------------------------------------------------------------------------------------------------------------------

-- (4) Listar todos as revistas de uma dada empresa
DROP PROCEDURE IF EXISTS getRevistaByEmpresa;
DELIMITER $$ 
CREATE PROCEDURE getRevistaByEmpresa(IN nomeEmpresa VARCHAR(45)) 
	BEGIN
		SELECT  DISTINCT R.Nome , R.Categoria  FROM Revista AS R
			INNER JOIN Empresa AS E ON E.ID = R.Empresa_ID
				WHERE E.Nome = nomeEmpresa;
	END$$
    
-- Teste --
CALL getRevistaByEmpresa('Soberania do Povo Editora, S.A.');

-- -------------------------------------------------------------------------------------------------------------------------

-- (5) Listar todos os artigos contidos numa revista.
DROP PROCEDURE IF EXISTS getArtigosByRevista; 
DELIMITER $$
CREATE PROCEDURE getArtigosByRevista (IN nomeRevista VARCHAR(45))
	BEGIN
		SELECT A.Titulo , A.Data , A.Corpo FROM Artigo AS A , Revista AS R
			WHERE R.Nome = nomeRevista AND A.Revista_ID = R.ID;
	END$$

-- Teste --
CALL getArtigosByRevista('Fofoquices com a Vizinha');

-- -------------------------------------------------------------------------------------------------------------------------

-- (6) Listar todos os anuncios contidos  numa revista
DROP PROCEDURE IF EXISTS getAnunciosByRevista;
DELIMITER $$ 
CREATE PROCEDURE getAnunciosByRevista (IN nomeRevista VARCHAR(45) , IN edicao VARCHAR(45))
	BEGIN
		SELECT A.Titulo , A.Categoria , A.NrConsultas , A.Contacto , A.Conteudo FROM Anuncio AS A 
			INNER JOIN AnuncioRevista AS AR ON A.ID = AR.Anuncio_ID
				INNER JOIN Revista AS R ON AR.Revista_ID = R.ID 
					WHERE R.Nome = nomeRevista AND R.Edicao = edicao;
    END$$

-- Teste --
CALL getAnunciosByRevista('Fofoquices com a Vizinha' , '2018-01-07');

-- -------------------------------------------------------------------------------------------------------------------------

-- (7) Listar todos os artigos escritos por um dado escritor.
DROP PROCEDURE IF EXISTS getArtigosByEscritor;
DELIMITER $$
CREATE PROCEDURE getArtigosByEscritor(IN nomeEscritor VARCHAR(45)) 
	BEGIN
		SELECT A.Titulo , A.Data , A.Categoria , A.Corpo FROM Artigo As A 
			INNER JOIN  EscritorArtigo AS EA ON A.ID = EA.Artigo_ID
				INNER JOIN Escritor AS E ON EA.Escritor_ID = E.ID WHERE E.Nome = nomeEscritor; 
    END$$

-- Teste --
CALL getArtigosByEscritor('Rogério Furão');

-- -------------------------------------------------------------------------------------------------------------------------

-- (8) Listar todas as revistas editadas por um dado editor.
DROP PROCEDURE IF EXISTS getRevistasByEditor;
DELIMITER $$
CREATE PROCEDURE getRevistasByEditor(IN nomeEditor VARCHAR(45))
	BEGIN
		SELECT R.Nome , R.Edicao FROM Revista AS R , Editor AS E
			WHERE R.Editor_ID = E.ID AND E.Nome = nomeEditor;
    END$$
    
-- Teste --
CALL getRevistasByEditor('José Carlos Malato')

-- -------------------------------------------------------------------------------------------------------------------------

-- (9) Identificar a quantidade de revistas visualizadas de uma empresa.
DROP PROCEDURE IF EXISTS nrConsultasByEmpresa;
DELIMITER $$ 
CREATE PROCEDURE nrConsultasByEmpresa(IN nomeEmpresa VARCHAR(45))
	BEGIN
		SELECT R.Nome , R.Edicao , R.NrConsultas FROM Revista As R , Empresa AS E
			WHERE E.Nome = nomeEmpresa AND E.ID = R.Empresa_ID
				ORDER BY R.NrConsultas DESC;
    END$$
 
-- Calcula a quantidade total de visualizações das revistas de uma dada empresa
DROP PROCEDURE IF EXISTS nrConsultasByEmpresaTotal;
DELIMITER $$ 
CREATE PROCEDURE nrConsultasByEmpresaTotal(IN nomeEmpresa VARCHAR(45)) 
	BEGIN 
		SELECT SUM(NrConsultas) FROM 
			(SELECT R.NrConsultas FROM Revista AS R , Empresa AS E
				WHERE E.Nome = nomeEmpresa AND E.ID = R.Empresa_ID) A;
    END$$ 

-- Teste --
CALL nrConsultasByEmpresaTotal('Edições Saída de Emergência, Lda');

-- -------------------------------------------------------------------------------------------------------------------------

-- (10) Listar todos os escritores que têm pelo menos um artigo escrito numa determinada revista.
DROP PROCEDURE IF EXISTS getEscritoresByRevista;
DELIMITER $$ 
CREATE PROCEDURE getEscritoresByRevista(IN nomeRevista VARCHAR(45), IN edicaoRevista DATE)
	BEGIN
		SELECT DISTINCT Escritor.Nome FROM Escritor 
			INNER JOIN EscritorArtigo ON Escritor.ID = EscritorArtigo.Escritor_ID
				INNER JOIN Artigo ON EscritorArtigo.Artigo_ID = Artigo.ID
					INNER JOIN Revista ON Artigo.Revista_ID = Revista.ID
						WHERE Revista.Nome = nomeRevista AND Revista.Edicao = edicaoRevista;
    END$$

-- Teste --
CALL getEscritoresByRevista('Fofoquices com a Vizinha' , '2018-01-07');

-- -------------------------------------------------------------------------------------------------------------------------

-- (11) Listar todas as revistas de uma dada empresa numa determinada data ou intervalo de datas.
DROP PROCEDURE IF EXISTS getRevistasByEmpresaByData;
DELIMITER $$
CREATE PROCEDURE getRevistasByEmpresaByData(IN nomeEmpresa VARCHAR(45) , IN dataInicio DATE , IN dataFim DATE)
	BEGIN
		SELECT * FROM Revista
			INNER JOIN Empresa ON Revista.Empresa_ID = Empresa.ID
				WHERE Empresa.Nome = nomeEmpresa AND (Revista.Edicao BETWEEN dataInicio AND dataFim);
    END$$

-- Teste --
CALL getRevistasByEmpresaByData('Edições Saída de Emergência, Lda', '2018-01-08','2018-07-06');

-- -------------------------------------------------------------------------------------------------------------------------

-- (12) Listar todos os escritores que já contribuíram para a escrita de revistas de uma dada empresa.
DROP PROCEDURE IF EXISTS getEscritoresByEmpresa;
DELIMITER $$ 
CREATE PROCEDURE getEscritoresByEmpresa(IN nomeEmpresa VARCHAR(45))
	BEGIN
		SELECT DISTINCT Escritor.Nome FROM Escritor 
			INNER JOIN EscritorArtigo ON EscritorArtigo.Escritor_ID = Escritor.ID 
				INNER JOIN Artigo ON Artigo.ID = EscritorArtigo.Artigo_ID
					INNER JOIN Revista ON Revista.ID = Artigo.Revista_ID
						INNER JOIN Empresa ON Empresa.ID = Revista.Empresa_ID
							WHERE Empresa.Nome = nomeEmpresa;
    END$$ 
    
-- Teste --
CALL getEscritoresByEmpresa('Edições Saída de Emergência, Lda');

-- -------------------------------------------------------------------------------------------------------------------------

-- (13)Listar todas as revistas em que aparecerem os anúncios de uma determinada empresa.
DROP PROCEDURE IF EXISTS getRevistasByAnuncioByEmpresa;
DELIMITER $$
CREATE PROCEDURE getRevistasByAnuncioByEmpresa(IN nomeEmpresa VARCHAR(45))
	BEGIN
		SELECT Revista.Nome , Revista.Edicao FROM Revista 
			INNER JOIN AnuncioRevista ON AnuncioRevista.Revista_ID = Revista.ID
				INNER JOIN Anuncio ON Anuncio.ID = AnuncioRevista.Anuncio_ID
					INNER JOIN Empresa ON Empresa.ID = Anuncio.Empresa_ID
						WHERE Empresa.Nome = nomeEmpresa;
    END$$
    
-- Teste --
CALL getRevistasByAnuncioByEmpresa('Dicas e Pistas, Lda');

-- -------------------------------------------------------------------------------------------------------------------------

-- (15) Listar as N revistas com maior número de consultas
DROP PROCEDURE IF EXISTS topRevistas;
DELIMITER $$ 
CREATE PROCEDURE topRevistas(IN N INT )
	BEGIN 
		SELECT Nome , Edicao , Categoria , NrConsultas FROM Revista
			ORDER BY nrConsultas DESC
				LIMIT N;
	END$$
    
-- Teste --
CALL topRevistas(10);

-- -------------------------------------------------------------------------------------------------------------------------

-- (16) Listar as  categorias por frequências relativas de consultas
DROP PROCEDURE IF EXISTS topCategoriasFR;
DELIMITER $$
CREATE PROCEDURE topCategoriasFR()
	BEGIN
		DECLARE nTotal INT DEFAULT 1;
       
        SELECT SUM(nrConsultas) INTO nTotal FROM Revista;

		SELECT Categoria , (SUM(NrConsultas) / nTotal) * 100 AS nTotalC FROM Revista
			GROUP BY Categoria
					ORDER BY nTotalC DESC;
    END$$

-- Teste --
CALL topCategoriasFR2();

-- -------------------------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------------------------

-- Listar os contactos de uma dada empresa
DROP PROCEDURE IF EXISTS contactosByEmpresa;
DELIMITER $$ 
CREATE PROCEDURE contactosByEmpresa(IN nomeEmpresa VARCHAR(45))
	BEGIN
		SELECT Contacto FROM Contactos 
			INNER JOIN Empresa ON Empresa.ID = Contactos.Empresa_ID AND Empresa.Nome = nomeEmpresa;
    END$$
    
-- Teste --
CALL contactosByEmpresa('Soberania do Povo Editora, S.A.');

-- -------------------------------------------------------------------------------------------------------------------------
