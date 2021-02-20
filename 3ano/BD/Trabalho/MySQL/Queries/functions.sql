-- Calcular o número total de consultas das revistas de uma empresa
DELIMITER $$ 
CREATE FUNCTION nrConsultasByEmpresaTotal(nomeEmpresa VARCHAR(45)) 
RETURNS INT DETERMINISTIC 
	BEGIN  
		DECLARE N INT;
		
		SELECT SUM(R.NrConsultas) INTO N FROM Revista AS R , Empresa AS E
			WHERE E.Nome = nomeEmpresa AND E.ID = R.Empresa_ID;
		RETURN N;
	END $$ 
    
DROP FUNCTION nrConsultasByEmpresaTotal;
SELECT nrConsultasByEmpresaTotal('Soberania do Povo Editora, S.A.');

-- -------------------------------------------------------------------------------------------------------------------------

