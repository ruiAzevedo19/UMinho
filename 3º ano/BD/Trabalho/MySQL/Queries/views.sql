-- View da tabela Anuncio
CREATE VIEW viewAnuncios AS 
	SELECT Titulo , Categoria , Contacto , Conteudo FROM Anuncio;

-- View da tabela Artigo
CREATE VIEW viewArtigos AS
	SELECT  Titulo , Data , Categoria , NrConsultas FROM Artigo;
    
-- View da tabela Editor
CREATE VIEW viewEditor AS 
	SELECT Nome FROM Editor;
    
-- View da tabela Empresa
CREATE VIEW viewEmpresa AS
	SELECT Nome , Cidade , Rua , QuantidadeRevistas FROM Empresa;
    
-- View da tabela Escritor
CREATE VIEW viewEscritor AS
	SELECT Nome FROM Escritor;

-- View da tabela Revista
CREATE VIEW viewRevista AS 
	SELECT DISTINCT Nome , Categoria , SUM(NrConsultas) AS nTotal	FROM Revista 
		GROUP BY Nome , Categoria
			ORDER BY nTotal DESC;
            
-- View da tabela das empresas e do numero de vizualizações das suas revistas
CREATE VIEW viewEmpresaV AS
	SELECT Empresa.Nome , SUM(Revista.NrConsultas) AS nTotal FROM Empresa 
		INNER JOIN Revista ON Empresa.ID = Revista.Empresa_ID
			GROUP BY Empresa.Nome
				ORDER BY nTotal DESC;

-- Top 5 escritores
CREATE VIEW viewTop5Escritores AS
	SELECT Nome , COUNT(EscritorArtigo.Escritor_ID) AS NumeroArtigos FROM Escritor 
		INNER JOIN EscritorArtigo ON EscritorArtigo.Escritor_ID = Escritor.ID
			GROUP BY Nome
				ORDER BY NumeroArtigos DESC
					LIMIT 5;