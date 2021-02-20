% SICStus PROLOG: Declaracoes iniciais
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).

:- use_module(library(lists)).

% SICStus PROLOG: Definicoes iniciais
:- op( 900, xfy,'::' ).
:- dynamic '-'/1.
:- dynamic (excecao/1).
:- dynamic (utente/5).
:- dynamic (servico/4).
:- dynamic (consulta/7).
:- dynamic (prestador/5).
:- dynamic (nulo/1).
:- dynamic (::)/2.
:- dynamic (uncertaintyName/1).
:- dynamic ( uncertaintyAdress/1).
:- dynamic (uncertaintyInstitution/1).
:- dynamic (uncertaintyCost/1).
%--------------------BASE DE CONHECIMENTO---------------------------------------------------------------

%-----------CONHECIMENTO POSITIVO--------------------------------
% utente: #IdUt, Nome, Idade, Sexo, Cidade ↝ { 𝕍, 𝔽 }
utente(1,joana,24,'F','Braga').
utente(2,mauricia,22,'F','Porto').
utente(3,rui,22,'M','Coimbra').
utente(4,etienne,25,'M','Lisboa').
utente(5,rafael,32,'M','Aveiro').
utente(6,pedro,36,'M','Guimaraes').

% servico: #IdServ,Especialidade,Instituição, Cidade ↝ { 𝕍, 𝔽 }
servico(1,'Cardiologia','Hospital Privado de Braga','Braga').
servico(2,'Cardiologia','Clinica Cuf Alvaldade','Lisboa').
servico(3,'Oncologia','Hospital da Luz','Lisboa').
servico(4,'Endoscopia','Hospital da Arrabida','Porto').
servico(5,'Endoscopia','Hospital do Alto Ave','Guimaraes').
servico(6,'Dermatologia','Hospital Sao Bernardo','Setubal').
servico(7,'Dermatologia','Hospital Privado de Braga','Braga').
servico(8,'Ginecologia','Hospital Espirito Santo','Evora').
servico(9,'Clinica Geral','Hospital do Alto Ave','Guimaraes').
servico(10,'Neurologia','Hospital Santa Maria','Lisboa').
servico(11,'Oftamologia','Hospital Santa Maria','Lisboa').

% consulta:#IdConsult,#IdUt,#IdPrestador,#IdServ,Descrição,Custo,Data ↝ { 𝕍, 𝔽 }
consulta(1,1,12,9,'Consulta de rotina',15.00,(2018,3,11)).
consulta(2,1,9,10,'bla bla bla',149.99,(2015,9,1)).
consulta(3,1,12,9,'bla bla bla',12.50,(2015,9,1)).
consulta(4,2,8,8,'bla bla bla',25.00,(2019,2,13)).
consulta(5,2,3,2,'bla bla bla',60.00,(2017,3,28)).
consulta(6,3,1,3,'bla bla bla',33.95,(2010,4,1)).
consulta(7,3,10,11,'bla bla bla',25.00,(2010,4,2)).
consulta(8,4,11,9,'bla bla bla',25.00,(2019,1,1)).
consulta(9,4,3,2,'bla bla bla',90.00,(2011,6,12)).
consulta(10,4,2,1,'bla bla bla',120.00,(2012,6,12)).
consulta(11,5,6,7,'bla bla bla',32.33,(2015,9,1)).
consulta(12,5,9,10,'bla bla bla',149.99,(2015,9,1)).
consulta(13,6,1,3,'bla bla bla',25.00,(2018,12,1)).
consulta(14,6,1,3,'bla bla bla',25.00,(2018,12,1)).
consulta(15,6,1,3,'bla bla bla',25.00,(2018,12,1)).
consulta(16,6,1,3,'bla bla bla',25.00,(2018,12,1)).
consulta(17,6,1,3,'bla bla bla',25.00,(2018,12,1)).

% prestador: #IdPrestador,#IdServ,Nome,Idade,Sexo ↝ { 𝕍, 𝔽 }
prestador(1,3,carlos,35,'M').
prestador(2,1,maria,58,'F').
prestador(3,2,rui,68,'M').
prestador(4,4,carla,60,'F').
prestador(5,5,isabel,55,'F').
prestador(6,7,ines,49,'F').
prestador(7,6,bruna,56,'F').
prestador(8,8,tiago,33,'M').
prestador(9,10,antonio,46,'M').
prestador(10,11,cesar,63,'M').
prestador(11,9,nuno,50,'M').
prestador(12,9,marcos,44,'M').


%-----------CONHECIMENTO NEGATIVO--------------------------------

% utente: #IdUt, Nome, Idade, Sexo, Cidade ↝ { 𝕍, 𝔽 }
-utente(7,orlando,33,'M','Barcelona').
-utente(8,belo,45,'M','Dubai').
-utente(9,nestor,29,'M','Madrid').
-utente(10,ribeiro,30,'M','Luanda').

% servico: #IdServ,Especialidade,Instituição, Cidade ↝ { 𝕍, 𝔽 }
-servico(12,'Psicologia','Hspital Egas Moniz','Braga').
-servico(13,'Osteopatia','Hospital CUF','Lisboa').
-servico(14,'Ortopedia','Hospital de Santa Marta','Porto').

% consulta:#IdConsult,#IdUt,#IdPrestador,#IdServ,Descrição,Custo,Data ↝ { 𝕍, 𝔽 }
-consulta(100,7,13,12,'bla bla bla',23.00,(2019,1,1)).

% prestador: #IdPrestador,#IdServ,Nome,Idade,Sexo ↝ { 𝕍, 𝔽 }
-prestador(13,14,mateus,60,'M').


%-----NEGAÇÃO FORTE---------------------------- - - - - - - - - - -  -  -  -  -   -

% utente: #IdUt, Nome, Idade, Sexo, Cidade ↝ { 𝕍, 𝔽 }
-utente(IdUt,Nome,Idade,Sexo,Cidade):-
			  				nao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
              				nao(excecao(utente(IdUt,Nome,Idade,Sexo,Cidade))).

% servico: #IdServ,Especialidade,Instituição, Cidade ↝ { 𝕍, 𝔽 }
-servico(IdServ,Especialidade,Instituicao,Cidade):-
							nao(servico(IdServ,Especialidade,Instituicao,Cidade)),
							nao(excecao(servico(IdServ,Especialidade,Instituicao,Cidade))).


% consulta:#IdConsult,#IdUt,#IdPrestador,#IdServ,Descrição,Custo,Data ↝ { 𝕍, 𝔽 }
-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data):-
						    nao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)),
						    nao(excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data))).


% prestador: #IdPrestador,#IdServ,Nome,Idade,Sexo ↝ { 𝕍, 𝔽 }
-prestador(IdPrestador,IdServ,Nome,Idade,Sexo):-
						    nao(prestador(IdPrestador,IdServ,Nome,Idade,Sexo)),
                            nao(excecao(prestador(IdPrestador,IdServ,Nome,Idade,Sexo))).



%-- REPRESENTAR CONHECIMENTO IMPERFEITO

%-----Valores Nulos do Tipo Incerto---------------------------- - - - - - - - - - -  -  -  -  -   -

% Utentes ------------------------------------------------------------------

% Entrou um utente em algum centro de saúde cujo o seu nome é incerto.
utente(11,name1,33,'F','Famalicao').
excecao(utente(IdUt,Nome,Idade,Sexo,Cidade)):- utente(IdUt,name1,Idade,Sexo,Cidade).
uncertaintyName(name1).

% Entrou um utente em algum centro de saúde cuja cidade é incerta.
 utente(12,messi,31,'M',adress1).
 excecao(utente(IdUt,Nome,Idade,Sexo,Cidade)):- utente(IdUt,Nome,Idade,Sexo,adress1).
 uncertaintyAdress(adress1).


 % Servicos ------------------------------------------------------------------

% Seriço de Psiquiatria cujo nome da Instituição é Incerto.
servico(12,'Psiquiatria',institution1,'Lisboa').
excecao(servico(IdServ,Especialidade,Instituicao,Cidade)):-servico(IdServ,Especialidade,institution1,Cidade). 
uncertaintyInstitution(institution1).


 % Consulta ------------------------------------------------------------------

% consulta:#IdConsult,#IdUt,#IdPrestador,#IdServ,Descrição,Custo,Data ↝ { 𝕍, 𝔽 }
consulta(18,1,12,9,'Consulta de Rotina',cost1,(2019,7,4)).
excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)):-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,cost1,Data).
uncertaintyCost(cost1).


%-----Valores Nulos do Tipo Impreciso---------------------------- - - - - - - - - - -  -  -  -  -   -

% Utentes ------------------------------------------------------------------

% a Mariana é de Leiria e encontra-se representada na base de conhecimento pelo número 25, sabe-se que a sua idade encontra-se entre os 18 e os 34 anos.
excecao(utente(13,teresa,Idade,'F','Paris')):- Idade>=18,
											  Idade=<32.


excecao(utente(14,marta,55,'F','Guimaraes')).
excecao(utente(14,marta,55,'F','Braga')).


% Prestador ------------------------------------------------------------------

excecao(prestador(14,IdServ,martins,36,'M')):- IdServ>=1,
  												IdServ=<11.

% Consulta ------------------------------------------------------------------

excecao(consulta(1,1,12,9,'Consulta de rotina',Custo,(2018,3,11))):- Custo >= 0.0,
																	 Custo=< 15.0.


%-----Valores Nulos do Tipo Interdito---------------------------- - - - - - - - - - - 

% Utentes ------------------------------------------------------------------

% o utente representado pelo número 28 tem como seu nome Zulmira e tem 45 anos, no entanto, ninguém pode conhecer a sua morada por vontade da mesma.

nulo(interdito1).
utente(15,bernardo,24,'M',interdito1).
excecao(utente(IdUt,Nome,Idade,Sexo,Cidade)):- utente(IdUt,Nome,Idade,Sexo,interdito1).

+utente(IdUt,Nome,Idade,Sexo,Cidade)::(findall((IdUt,Nome,Sexo,City),(utente(15,bernardo,24,'M',City),nao(nulo(City))),L),
									    length(L,0)).


nulo(interdito2).
utente(16,martina,interdito2,'F','Madrid').
excecao(utente(IdUt,Nome,Idade,Sexo,Cidade)):- utente(IdUt,Nome,interdito2,Sexo,Cidade).

+utente(IdUt,Nome,Idade,Sexo,Cidade):: (findall(Age,(utente(16,martina,Age,'M','Madrid'),nao(nulo(Age))),L),
									    length(L,0)).



% Consulta ------------------------------------------------------------------

consulta(19,1,12,9,'Consulta de rotina',interdito3,(2019,3,11)).
excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)):-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,interdito3,Data).
nulo(interdito3).


+consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data) :: (findall(Custo,(consulta(19,1,12,9,'Consulta de rotina',Custo,(2019,3,11)),nao(nulo(Custo))),L),
																	 length(L,N),
																	 N==0).

%----------------------REGRAS AUXILIARES------------------------------------------

%------------------------------------------------------------------------------%
%------------------------------- Invariantes  ---------------------------------%
%------------------------------------------------------------------------------%


validaSexo(S) :- S == 'F'.
validaSexo(S) :- S == 'M'.

validaData((Ano,Mes,Dia)) :- Ano > 0 ,
                            Mes > 0 , Mes < 13 ,
                            Dia > 0 , Dia < 32.


% (1) Utente ------------------------------------------------------------------

%Impede a evolução do utente(positivo) caso já exista conhecimento positivo, negativo ou desconhecido com o mesmo Id
+utente(IdUt,Nome,Idade,Sexo,Cidade)::
								(integer(IdUt),
								findall(IdUt,utente(IdUt,_,_,_,_),S1),
                                findall(IdUt,-utente(IdUt,_,_,_,_),S2),
                                findall(IdUt,excecao(utente(IdUt,_,_,_)),S3),
                                Idade>=0,
                                validaSexo(Sexo),
                                length(S1,N1),
                                length(S2,N2),
                                length(S3,N3),
                                N is N1 + N2 + N3,
                                N=<1).


%Não existem cuidados referentes a utente
  -utente(IdUt,_,_,_,_) :: (findall(IdUt,utente(IdUt,Nome,Sexo,Idade,Morada),S),
                  		    length(S,N),
                  			N==0).


%Impede a evolução do utente(negativo) caso já exista conhecimento positivo, negativo ou desconhecido com o mesmo Id
+(-utente(IdUt,Nome,Idade,Sexo,Cidade))::
								(findall(IdUt,utente(IdUt,_,_,_,_),S1),
                                findall(IdUt,-utente(IdUt,_,_,_,_),S2),
                                findall(IdUt,excecao(utente(IdUt,_,_,_,_)),S3),
                                length(S1,N1),
                                length(S2,N2),
                                length(S3,N3),
                                N is N1 + N2 + N3,
                                N=<1).

 % (2) Serviços ------------------------------------------------------------------

+servico(IdServ,Especialidade,Instituicao,Cidade)::
       
		(integer(IdUt),
        findall(IdServ,servico(IdServ,_,_,_),S1),
        findall((Especialidade,Instituicao),servico(_,Especialidade,Instituicao,_),S2),
        findall(IdServ,-servico(IdServ,_,_,_),S3),
        findall(IdServ,excecao(servico(IdServ,_,_,_)),S4),
        length(S1,N1),
        length(S2,N2),
        length(S3,N3),
        length(S4,N4),
        N is N1+N2+N3+N4,
         N=<1).

-servico(IdServ,Especialidade,Instituicao,Cidade) :: 
 		(findall(IdServ,servico(IdServ,Especialidade,Instituicao,Cidade),S1),
 		length(S1,N),
 		N==0).

+(-servico(IdServ,Especialidade,Instituicao,Cidade))::(
	findall(IdServ,servico(IdServ,_,_,_),S1),
	findall(IdServ,-servico(IdServ,_,_,_),S2),
	findall(IdServ,excecao(servico(IdServ,_,_,_)),S3),
	length(S1,N1),
	length(S2,N2),
	length(S3,N3),
	N is N1+N2+N3,
	N=<1).

% (3) Consulta ------------------------------------------------------------------

+consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)::(
										    integer(IdConsult),
										    Custo>=0,
										    validaData(Data),
                                            utente(IdUt,_,_,_,_),
                                            prestador(IdPrestador,IdServ,_,_,_),
                                            servico(IdServ,_,_,_),
                                            findall(IdConsult,consulta(IdConsult,_,_,_,_,_,_),S1),
                                            findall(IdConsult,-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),S2),
                                            findall(IdConsult,excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)),S3),
                                            length(S1,N1),
                                            length(S2,N2),
                                            length(S3,N3),
                                            N is N1+N2+N3,
											N=<1).


-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data) :: 
 		(findall(IdConsult,consulta(IdConsult,_,_,_,_,_,_),S1),
 		length(S1,N),
 		N==0).


+(-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data))::(
	findall(IdConsult,consulta(IdConsult,_,_,_,_,_,_),S1),
    findall(IdConsult,-consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),S2),
    findall(IdConsult,excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)),S3),
    length(S1,N1),
    length(S2,N2),
    length(S3,N3),
    N is N1+N2+N3,
	N=<1).


% (4) Prestador ------------------------------------------------------------------

+prestador(IdPrestador,IdServ,Nome,Idade,Sexo)::(
	integer(IdPrestador),
	validaSexo(Sexo),
	Idade>=0,
	findall(IdPrestador,prestador(IdPrestador,_,_,_,_),S1),
    findall(IdPrestador,-prestador(IdPrestador,_,_,_,_),S2),
    findall(IdPrestador,excecao(prestador(IdPrestador,_,_,_,_)),S3),
    length(S1,N1),
    length(S2,N2),
    length(S3,N3),
    N is N1+N2+N3,
	N=<1).

-prestador(IdPrestador,IdServ,Nome,Idade,Sexo) :: (
	findall(IdConsult,prestador(IdPrestador,_,_,_,_),S1),
 	length(S1,N),
 	N==0).

+(-prestador(IdPrestador,IdServ,Nome,Idade,Sexo))::(
	findall(IdPrestador,prestador(IdPrestador,_,_,_,_),S1),
    findall(IdPrestador,-prestador(IdPrestador,_,_,_,_),S2),
    findall(IdPrestador,excecao(prestador(IdPrestador,_,_,_,_)),S3),
    length(S1,N1),
    length(S2,N2),
    length(S3,N3),
    N is N1+N2+N3,
	N=<1).
%-----------------Evolução do Conhecimento----------------------------------------------------------------

% (1) Utente ------------------------------------------------------------------

evolucaoAdressPerfeito(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
							nao(existeAdressIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade))),
							existeAdressIncerto2(utente(IdUt,Nome,Idade,Sexo,Cidade),L),
							involucaoAdressIncerto(utente(IdUt,Nome,Idade,Sexo,L)),
							evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)). 

evolucaoAdressPerfeito(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
									existeAdressIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)),
									evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)).



evolucaoNamePerfeito(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
							nao(existeNameIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade))),
							existeNameIncerto2(utente(IdUt,Nome,Idade,Sexo,Cidade),L),
							involucaoNameIncerto(utente(IdUt,L,Idade,Sexo,Cidade)),
							evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)). 


evolucaoNamePerfeito(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
									existeNameIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)),
									evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)).


% (2) Serviços ------------------------------------------------------------------

evolucaoInstitutionPerfeito(servico(IdServ,Especialidade,Instituicao,Cidade)) :-
							nao(existeInstitutionIncerto(servico(IdServ,Especialidade,Instituicao,Cidade))),
							existeInstitutionIncerto2(servico(IdServ,Especialidade,Instituicao,Cidade),L),
							involucaoInstitutionIncerto(servico(IdServ,Especialidade,L,Cidade)),
							evolucao(servico(IdServ,Especialidade,Instituicao,Cidade)). 


evolucaoInstitutionPerfeito(servico(IdServ,Especialidade,Instituicao,Cidade)) :-
									existeInstitutionIncerto(servico(IdServ,Especialidade,Instituicao,Cidade)),
									evolucao(servico(IdServ,Especialidade,Instituicao,Cidade)).


% (3) Consulta ------------------------------------------------------------------




% EVOLUÇÃO DO CONHECIMENTO INCERTO

% (1) Utente ------------------------------------------------------------------

evolucaoNameIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
        evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
        assert(((excecao(utente(Id,N,I,S,C))) :- utente(Id,Nome,I,S,C))),
        assert(uncertaintyName(Nome)).


involucaoNameIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
          involucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
          retract(((excecao(utente(Id,N,I,S,C))) :- utente(Id,Nome,I,S,C))),
          retract(uncertaintyName(Nome)).


evolucaoAgeIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
        evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
        assert(((excecao(utente(Id,N,I,S,C))) :- utente(Id,N,Idade,S,C))),
        assert(uncertaintyAge(Idade)).

involucaoAgeIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
          involucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
          retract(((excecao(utente(Id,N,I,S,C))) :- utente(Id,N,Idade,S,C))),
          retract(uncertaintyAge(Idade)).


evolucaoAdressIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
        evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
        assert(((excecao(utente(Id,N,I,S,C))) :- utente(Id,N,I,S,Cidade))),
        assert(uncertaintyAdress(Cidade)).


involucaoAdressIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-
          involucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
          retract(((excecao(utente(Id,N,I,S,C))) :- utente(Id,N,I,S,Cidade))),
          retract(uncertaintyAdress(Cidade)).



% (2) Serviços ------------------------------------------------------------------
evolucaoInstitutionIncerto(servico(IdServ,Especialidade,Instituicao,Cidade)) :-
        evolucao(servico(IdServ,Especialidade,Instituicao,Cidade)),
        assert(((excecao(servico(Id,E,I,C))) :- servico(Id,E,Instituicao,C))),
        assert(uncertaintyInstitution(Instituicao)).



involucaoInstitutionIncerto(servico(IdServ,Especialidade,Instituicao,Cidade)) :-
          involucao(servico(IdServ,Especialidade,Instituicao,Cidade)),
          retract(((excecao(servico(Id,E,I,C))) :- servico(Id,E,Instituicao,C))),
          retract(uncertaintyInstitution(Instituicao)).


% (3) Consulta ------------------------------------------------------------------

involucaoCostIncerto(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)):-
involucao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)),
retract(((excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data))) :- consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data))),
retract(uncertaintyCost(Custo)).




% EVOLUÇÃO DO CONHECIMENTO IMPRECISO

% (1) Consulta ------------------------------------------------------------------

evolucaoCostImpreciso(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista,result):-
   existeCostImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
   nao(existeCostIncerto(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data))),
   existeCostIncerto2(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),L),
   involucaoCostIncerto(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,L,Data)),
   costRecursivo(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista),
   assert(costImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Custo)).

evolucaoCostImpreciso(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista,result):-
existeCostImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
existeCostIncerto(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)),
costRecursivo(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista),
assert(costImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Custo)).



involucaoCostImpreciso(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista,result):-
costAux(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Lista),
retract(costImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Custo)).



% EVOLUÇÃO DO CONHECIMENTO INTERDITO

% (1) Utente ------------------------------------------------------------------
   
   evolucaoAdressInterdito(utente(IdUt,Nome,Idade,Sexo,Cidade)) :-(
    nao(excecao(utente(IdUt,Nome,Idade,Sexo,Cidade))),
    evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)),
    assert((excecao(utente(Id,N,I,S,C)):- utente(Id,N,I,S,Cidade))),
    assert(nulo(Cidade)),
    assert(+utente(ID,No,Ida,Se,Cid)::(findall((ID,No,Ida,Se,C),(utente(IdUt,Nome,Idade,Sexo,C),nao(nulo(C))),L),
    length(L,0)))).

%---------------------------------------------------------------------------------

%Extensao do meta-predicado demo: Questao,Resposta -> {V,F,D}

demo( Questao,verdadeiro):-Questao.

demo(Questao,falso):- -Questao.

demo(Questao,desconhecido):-nao(Questao),
	         				nao(-Questao).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado demo: Questao1, Tipo, Questao2, Flag -> {V, F, D}
% Tipo :
% ou - disjuncao
% e  - conjuncao

demo(Q1, ou, Q2, F) :- demo(Q1, F1),
	                   demo(Q2, F2),
	                   disjuncao(F1, F2, F).

demo(Q1, e, Q2, F) :- demo(Q1, F1),
	                  demo(Q2, F2),
	                  conjuncao(F1, F2, F).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado disjuncao: Tipo, Tipo, Tipo -> {V, F, D}
% Tipo pode ser verdadeiro, falso ou desconhecido

disjuncao(verdadeiro, X, verdadeiro).
disjuncao(X, verdadeiro, verdadeiro).
disjuncao(desconhecido, Y, desconhecido) :- Y \= verdadeiro.
disjuncao(Y, desconhecido, desconhecido) :- Y \= verdadeiro.
disjuncao(falso, falso, falso).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado conjuncao: Tipo, Tipo, Tipo -> {V, F, D}
% Tipo pode ser verdadeiro, falso ou desconhecido

conjuncao(verdadeiro, verdadeiro, verdadeiro).
conjuncao(falso, _, falso).
conjuncao(_, falso, falso).
conjuncao(desconhecido, verdadeiro, desconhecido).
conjuncao(verdadeiro, desconhecido, desconhecido).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado demoMap: Lista de Questoes, Lista de Respostas -> {V, F, D}
% Determina o tipo de um conjunto de questoes
% O resultado é uma lista de tipos (verdadeiro, falso ou desconhecido)

demoMap([], []).
demoMap([X|L], [R|S]) :- demo(X, R),
                          demoMap(L, S).

%--------------------------------------------------------------------------------------------
% Extensao do meta-predicado nao: Termo -> {V, F}
nao(T) :- T, !, fail.
nao(T).
%--------------------------------------------------------------------------------------------
%- teste: L -> {V,F}
teste([]).
teste([I|Is]):-I,teste(Is).


%- insercao: T -> {V,F}
insercao(T) :- assert(T).
insercao(T) :- retract(T), !, fail.


%- remocao: T -> {V,F}
remocao(T) :- retract(T).
remocao(T) :- assert(T), !, fail.


%- evolucao: T -> {V,F}
evolucao(T) :-  findall(I,+T::I,Li),
                insercao(T),
                teste(Li).

%- involucao: T -> {V,F}
involucao(T) :- T,
                findall(I,-T::I,Li),
                remocao(T),
                teste(Li).

evolucaoNeg(T) :- findall(I,+(-T)::I,Li),
                  teste(Li),
                  assert(-T).

involucaoNeg(T) :- findall(I,+(-T)::I,Li),
                   teste(Li),
                   retract(-T).     

%-----------------PREDICADOS AUXILIARES---------------------------------------------------------------------------

existeAdressIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :- (findall(City,(utente(IdUt,N,I,S, City),uncertaintyAdress(City)),L),
                                                             length(L,0)).
                                                              

existeAdressIncerto2(utente(IdUt,Nome,Idade,Sexo,Cidade),L) :- (findall( (City), (utente(IdUt,N,I,S, City),uncertaintyAdress(City)),[L|Ls])).

%-----------------------------------------------------------------------------------------------------------------

existeNameIncerto(utente(IdUt,Nome,Idade,Sexo,Cidade)) :- (findall(Name,(utente(IdUt,Name,I,S,C),uncertaintyName(Name)),L),
                                                             length(L,N),
                                                              N==0). 

existeNameIncerto2(utente(IdUt,Nome,Idade,Sexo,Cidade),L) :- (findall( (Name), (utente(IdUt,Name,I,S,C),uncertaintyName(Name)),[L|Ls])).

%-----------------------------------------------------------------------------------------------------------------


existeInstitutionIncerto(servico(IdServ,Especialidade,Instituicao,Cidade)) :- (findall(Insti,(servico(IdServ,E,Insti,C),uncertaintyInstitution(Insti)),L),
                                                             length(L,0)).
                                                               

existeInstitutionIncerto2(servico(IdServ,Especialidade,Instituicao,Cidade),L) :- (findall((Insti),(servico(IdServ,E,Insti,C),uncertaintyInstitution(Insti)),[L|Ls])).

%-----------------------------------------------------------------------------------------------------------------
existeCostIncerto(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)) :- (findall(Cost,(consulta(IdConsult,IdUt,IdPrestador,IdServ,D,Cost,Da), uncertaintyCost(Cost)),L ),
                                                                     length(L,0)).

existeCostIncerto2(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data), L) :- 
(findall( (Cost), (consulta(IdConsult,IdUt,IdPrestador,IdServ,D,Cost,Da), uncertaintyCost(Cost)),[L|Ls])).
                                                                 

existeCostImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data):-
(findall((Idc,Idu,Idp,Ids),(costImpreciso(IdConsult,IdUt,IdPrestador,IdServ,Custo)),L),
				length(L,0)).
%-----------------------------------------------------------------------------------------------------------------
costRecursivo(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),[Head|[]]) :- 
evolucao(excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Head,Data))).

costRecursivo(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),[Head|Tail]) :- 
evolucao(excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Head,Data))),
costRecursivo(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Tail).


costAux(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),[Head|[]]) :- 
involucao(excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Head,Data))).

costAux(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),[Head|Tail]) :- 
involucao(excecao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Head,Data))),
costAux(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),Tail).

