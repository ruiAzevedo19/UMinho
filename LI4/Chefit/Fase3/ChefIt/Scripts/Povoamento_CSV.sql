
-- Povoamento da tabela Users ----------------------------------------------------------------------------------------------
bulk insert dbo.Users from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\user.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='ACP',
		KEEPIDENTITY
	);

SELECT * FROM Users;

DELETE FROM  Users;

-- Povoamento da tabela Receita --------------------------------------------------------------------------------------------
bulk insert dbo.Receitas from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\receita.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='ACP',
		KEEPIDENTITY
	);

SELECT * FROM Receitas;

DELETE FROM  Receitas;

-- Povoamento da tabela Ingredientes ---------------------------------------------------------------------------------------
bulk insert dbo.Ingredientes from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\ingredientes.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='ACP',
		KEEPIDENTITY
	);

SELECT * FROM Ingredientes;

DELETE FROM  Ingredientes;

-- Povoamento da tabela Passos ---------------------------------------------------------------------------------------------
bulk insert dbo.Passos from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\passo.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='ACP',
		KEEPIDENTITY
	);

SELECT * FROM Passos;

DELETE FROM  Passos;

-- Povoamento da tabela ReceitaIngredientes --------------------------------------------------------------------------------
bulk insert dbo.ReceitaIngredientes from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\receita_ingrediente.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='ACP',
		KEEPIDENTITY
	);

SELECT * FROM ReceitaIngredientes;

DELETE FROM  ReceitaIngredientes;

-- Povoamento da tabela ReceitaIngredientePasso ----------------------------------------------------------------------------
bulk insert dbo.ReceitaIngredientePassos from 'C:\Users\ruiaz\Desktop\UM\LI4\Chef.it\Fase3\ChefIt\Scripts\receita_ingrediente_passo.csv'
	with(
		rowterminator='\n',
		fieldterminator=';',
		CODEPAGE='65001',
		KEEPIDENTITY
	);

SELECT * FROM ReceitaIngredientePassos;

DELETE FROM  ReceitaIngredientePassos;
