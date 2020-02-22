-- Trigger que faz o update do número de revistas de uma empresa sempre que se adiciona uma revista
DELIMITER $$
CREATE TRIGGER quantidadeRevistasUpdate AFTER INSERT ON Revista
	FOR EACH ROW 
		BEGIN
				UPDATE Empresa SET QuantidadeRevistas = QuantidadeRevistas + 1
					WHERE Empresa.ID = NEW.Empresa_ID;
        END$$
        
