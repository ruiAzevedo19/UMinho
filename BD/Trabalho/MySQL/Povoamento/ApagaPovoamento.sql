USE Arquivo;

DELETE from EscritorArtigo where Escritor_ID<9999;
DELETE from Escritor where ID<9999;
DELETE from Artigo where ID<9999;
DELETE from Contactos where Empresa_ID<9999;
DELETE from AnuncioRevista where Anuncio_ID<9999;
DELETE from Anuncio where ID<9999;
DELETE from Revista where ID<9999;
DELETE from Editor where ID<9999;
DELETE from Empresa where ID<9999;


DROP PROCEDURE IF EXISTS Fill_AnuncioRevista;
DROP PROCEDURE IF EXISTS Fill_EscritorArtigo;

