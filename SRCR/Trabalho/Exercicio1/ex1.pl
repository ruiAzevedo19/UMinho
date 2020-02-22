% SICStus PROLOG: Declaracoes iniciais
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).
:- set_prolog_flag( unknown,fail ).
:- set_prolog_flag(toplevel_print_options,
    [quoted(true), portrayed(true), max_depth(0)]).

:- use_module(library(lists)).
:- consult(menu).

% SICStus PROLOG: Definicoes iniciais
:- op( 900, xfy,'::' ).
:- dynamic (utente/5).
:- dynamic (servico/4).
:- dynamic (consulta/7).
:- dynamic (prestador/5).
:- dynamic (medicamento/3).
:- dynamic (receita/4).


% ------------------------ Base de Conhecimento ------------------------------ %

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

% medicamento: #IdMed,Nome,Custo ↝ { 𝕍, 𝔽 }
medicamento(1,'Paracetamol',1.10).
medicamento(2,'Rennie',5.35).
medicamento(3,'Ben-U-Ron',1.80).
medicamento(4,'Valdispert',14.30).
medicamento(5,'Antigripine',4.35).
medicamento(6,'Calmoten',35.10).
medicamento(7,'Clorocil',7.43).
medicamento(8,'Voltaren',5.00).
medicamento(9,'VapoRub',3.69).
medicamento(10,'Aspirina',3.00).

% receita:#IdCons,#IdMed,DataValidade,Quantidade ↝ { 𝕍, 𝔽 }
 receita(1,9,(2018,4,11),1).
 receita(1,4,(2018,4,11),3).
 receita(10,6,(2012,7,12),2).
 receita(9,8,(2011,7,12),3).
 receita(3,5,(2015,10,1),2).
 receita(3,3,(2015,10,1),1).

 %------------------------------------------------------------------------------%
 %-------------------------------- Inserções -----------------------------------%
 %------------------------------------------------------------------------------%

 % Registo de utentes
 registarUtente(IdUt,Nome,Idade,Sexo,Cidade) :-
 			evolucao(utente(IdUt,Nome,Idade,Sexo,Cidade)).

 % Registo de serviços
 registarServico(IdServ,Especialidade,Instituicao, Cidade) :-
 			evolucao(servico(IdServ,Especialidade,Instituicao, Cidade)).

 % Registo de consultas
 registarConsulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data) :-
 			evolucao(consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data)).

 % Registo de prestador
 registarPrestador(IdPrestador,IdServ,Nome,Idade,Sexo) :-
 			evolucao(prestador(IdPrestador,IdServ,Nome,Idade,Sexo)).

 % Registo de medicamento
 registarMedicamento(IdMed,Nome,Custo) :-
 			evolucao(medicamento(IdMed,Nome,Custo)).

 % Registo de receita
 registarReceita(IdCons,IdMed,DataValidade,Quantidade) :-
 			evolucao(receita(IdCons,IdMed,DataValidade,Quantidade)).

%------------------------------------------------------------------------------%
%------------------------------- Remoções -------------------------------------%
%------------------------------------------------------------------------------%

% Remover utente
  removerUtente(IdUt) :- involucao(utente(IdUt,_,_,_,_)).

% Remover servico
  removerServico(IdServ):- involucao(servico(IdServ,_,_,_)).

% Remover consulta
  removerConsulta(IdConsult) :- involucao(consulta(IdConsult,_,_,_,_,_,_)).

% Remover prestador
  removerPrestador(IdPrestador) :- involucao(prestador(IdPrestador,_,_,_,_)).

% Remover medicamento
  removerMedicamento(IdMed) :- involucao(medicamento(IdMed,_,_)).

% Remover receita
  removerReceita(IdCons,IdMed) :- involucao(receita(IdCons,IdMed,_,_)).

%------------------------------------------------------------------------------%
%---------------------------------- Regras  -----------------------------------%
%------------------------------------------------------------------------------%

%- Listar todas as instituicoes na base de conhecimento -----------------------%

%- Extensao do predicado listarInstituicoes: R ↝ { 𝕍, 𝔽 }
listarInstituicoes(R) :- findall(Instituicao,servico(_,_,Instituicao,_),S),
					               sort(S,R).

%--- Identificar utentes atraves de diferentes criterios ----------------------%

%- Extensao do predicado utenteByID: #IdUt , R ↝ { 𝕍, 𝔽 }
utenteByID(IdUt,R) :- findall((IdUt,Nome,Idade,Sexo,Cidade),utente(IdUt,Nome,Idade,Sexo,Cidade), R).

%- Extensao do predicado utenteByNome: Nome , R ↝ { 𝕍, 𝔽 }
utenteByNome(Nome,R) :- findall((IdUt,Nome,Idade,Sexo,Cidade), utente(IdUt,Nome,Idade,Sexo,Cidade), R).

%- Extensao do predicado utenteByIdade: Idade , R ↝ { 𝕍, 𝔽 }
utenteByIdade(Idade,R) :- findall((IdUt, Nome, Idade, Sexo, Cidade), utente(IdUt,Nome,Idade,Sexo,Cidade), R).

%- Extensao do predicado utenteBySexo: Sexo , R ↝ { 𝕍, 𝔽 }
utenteBySexo(Sexo,R) :- findall((IdUt, Nome, Idade, Sexo, Cidade), utente(IdUt,Nome,Idade,Sexo,Cidade), R).

%- Extensao do predicado utenteByCidade: Cidade , R ↝ { 𝕍, 𝔽 }
utenteByCidade(Cidade,R) :- findall((IdUt, Nome, Idade, Sexo, Cidade), utente(IdUt,Nome,Idade,Sexo,Cidade), R).


%--- Identificar servicos atraves de diferentes criterios ---------------------%

%- Extensao do predicado servicoByID: #IdServ , R ↝ { 𝕍, 𝔽 }
servicoByID(IdServ,R) :-
				findall((IdServ,Especialidade,Instituicao,Cidade), servico(IdServ,Especialidade,Instituicao,Cidade), R).

%- Extensao do predicado servicoByEspecialidade: Especialidade , R ↝ { 𝕍, 𝔽 }
servicoByEspecialidade(Especialidade,R) :-
		findall((IdServ,Especialidade,Instituicao,Cidade), servico(IdServ,Especialidade,Instituicao,Cidade), R).

%- Extensao do predicado servicoByInstituicao: #IdServ , R ↝ { 𝕍, 𝔽 }
servicoByInstituicao(Instituicao,R) :-
		findall((IdServ,Especialidade,Instituicao,Cidade), servico(IdServ,Especialidade,Instituicao,Cidade), R).

%- Extensao do predicado servicoByCidade: #IdServ , R ↝ { 𝕍, 𝔽 }
servicoByCidade(Cidade,R) :-
		findall((IdServ,Especialidade,Instituicao,Cidade), servico(IdServ,Especialidade,Instituicao,Cidade), R).


%--- Identificar consultas atraves de diferentes criterios --------------------%

%- Extensao do predicado consultaByIdConsulta: #IdConsult , R ↝ { 𝕍, 𝔽 }
consultaByIdConsulta(IdConsult,R) :- findall( (IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
                                    consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),R).

%- Extensao do predicado consultaByIdUtente: #IdUt , R ↝ { 𝕍, 𝔽 }
consultaByIdUtente(IdUt,R) :- findall((IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
                              consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data), R).

%- Extensao do predicado consultaByIdPrestador: #IdPrestador , R ↝ { 𝕍, 𝔽 }
consultaByIdPrestador(IdPrestador,R) :- findall( (IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
                                        consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),R).

%- Extensao do predicado consultaByIdServ: #IdServ , R ↝ { 𝕍, 𝔽 }
consultaByIdServ(IdServ,R) :- findall( (IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),
                              consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),R).

%- Extensao do predicado consultaByData: Data , R ↝ { 𝕍, 𝔽 }
consultaByData(Data,R) :- findall( (IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo),
                          consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),R).

%- Extensao do predicado consultaByCusto: Custo , R ↝ { 𝕍, 𝔽 }
consultaByCusto(Custo,R) :- findall( (IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo),
                            consulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data),R).

%--- Identificar prestador atraves de diferentes criterios ----------------------%

%- Extensao do predicado prestadorByID: #IdPrestador , R ↝ { 𝕍, 𝔽 }
prestadorByID(IdPrestador,R) :- findall((IdPrestador,IdServ,Nome,Idade,Sexo),prestador(IdPrestador,IdServ,Nome,Idade,Sexo),R).

%- Extensao do predicado prestadorByIdServ: #IdServ , R ↝ { 𝕍, 𝔽 }
prestadorByIdServ(IdServ,R) :- findall((IdPrestador,IdServ,Nome,Idade,Sexo),prestador(IdPrestador,IdServ,Nome,Idade,Sexo),R).

%- Extensao do predicado prestadorByNome: Nome , R ↝ { 𝕍, 𝔽 }
prestadorByNome(Nome,R) :- findall((IdPrestador,IdServ,Nome,Idade,Sexo), prestador(IdPrestador,IdServ,Nome,Idade,Sexo), R).

%- Extensao do predicado prestadorByIdade: Idade , R ↝ { 𝕍, 𝔽 }
prestadorByIdade(Idade,R) :- findall((IdPrestador,IdServ, Nome, Idade, Sexo), prestador(IdPrestador,IdServ,Nome,Idade,Sexo), R).

%- Extensao do predicado utenteBySexo: Sexo , R ↝ { 𝕍, 𝔽 }
prestadorBySexo(Sexo,R) :- findall((IdPrestador,IdServ,Nome,Idade,Sexo), prestador(IdPrestador,IdServ,Nome,Idade,Sexo), R).

%--- Identificar medicamentos atraves de diferentes criterios ----------------------%

%- Extensao do predicado medicamentoByID: #IdMed , R ↝ { 𝕍, 𝔽 }
medicamentoByID(IdMed,R):-findall((IdMed,Nome,Custo),medicamento(IdMed,Nome,Custo),R).

%- Extensao do predicado medicamentoByNome: Nome , R ↝ { 𝕍, 𝔽 }
medicamentoByNome(Nome,R):-findall((IdMed,Nome,Custo),medicamento(IdMed,Nome,Custo),R).

%- Extensao do predicado medicamentoByCusto: Custo , R ↝ { 𝕍, 𝔽 }
medicamentoByCusto(Custo,R):-findall((IdMed,Nome,Custo),medicamento(IdMed,Nome,Custo),R).

%--- Identificar receitas atraves de diferentes criterios -------------------------%

%- Extensao do predicado receitaByID: IdMed , R ↝ { 𝕍, 𝔽 }
receitaByID(IdConsult,R):-findall((Data,IdConsult,IdMed,Quantidade),receita(IdConsult,IdMed,Data,Quantidade),R).

%- Extensao do predicado receitaByData: Data , R ↝ { 𝕍, 𝔽 }
receitaByData(Data,R):-findall((Data,IdConsult,IdMed,Quantidade),receita(IdConsult,IdMed,Data,Quantidade),R).

%- Extensao do predicado receitaByQuantidade: Quantidade , R ↝ { 𝕍, 𝔽 }
receitaByQuantidade(Quantidade,R):-findall((Data,IdConsult,IdMed,Quantidade),receita(IdConsult,IdMed,Data,Quantidade),R).


%--- Identificar servicos prestados por Instituicao -------------------------%
servicoPerInstituicao(Instituicao,R):-findall((Especialidade,Cidade),servico(_,Especialidade,Instituicao,Cidade),R).

%--- Identificar servicos prestados por Cidade -------------------------%
servicoPerCidade(Cidade,R):-findall((Instituicao,Especialidade),servico(_,Especialidade,Instituicao,Cidade),R).


%--- Identificar os utentes de um servico prestado -------------------------%
searchUtentePerService(Especialidade,R):-findall(IdServ,servico(IdServ,Especialidade,Instituicao,_),S),
                                         auxserUt(S,K),
                                         infoUtente(K,R).

%--- Identificar os utentes que frequentaram uma Instituicao -------------------------%
searchUtentePerInstituicao(Instituicao,R):- findall(IdServ,servico(IdServ,_,Instituicao,_),S),
                                            auxserUt(S,K),
                                            infoUtente(K,R).

%--- Identificar servicos realizados por utente/instituicao/datas/custo

consultaUtente(IdUt,R) :- findall(((Data,IdPrestador,IdServ,Custo)),consulta(_,IdUt,IdPrestador,IdServ,_,Custo,Data),S) ,
                          map(S,R).

consultaData(Data,R) :- findall(((Data,IdPrestador,IdServ,Custo)),consulta(_,IdUt,IdPrestador,IdServ,_,Custo,Data),S) ,
                        map(S,R).

consultaCusto(Custo,R) :- findall(((Data,IdPrestador,IdServ,Custo)),consulta(_,IdUt,IdPrestador,IdServ,_,Custo,Data),S) ,
                          map(S,R).

consultaByInst(Instituicao,R) :- findall(IdServ, servico(IdServ,_,Instituicao,_), LS),
                                 auxInst(LS, S),
                                 map(S,R).

%--- Calcular o custo total dos cuidados de saude por utente / servico / instituicao / data

custoByUtente(IdUt,R) :- findall(Custo,consulta(_,IdUt,_,_,_,Custo,_),C) , sumlist(C,R).

custoByServico(IdServ,R) :- findall(Custo,consulta(_,_,_,IdServ,_,Custo,_),C) , sumlist(C,R).

custoByInstituicao(Instituicao,R) :-findall(IdServ,servico(IdServ,_,Instituicao,_),S),
                                    auxiliar(S,T),
                                    sumlist(T,R).

custoByData(Data,R) :- findall(Custo,consulta(_,_,_,_,_,_,Data),C),sumlist(C,R).

%------------------------------------------------------------------------------%
%---------------------------------- Extras  -----------------------------------%
%------------------------------------------------------------------------------%

%- Extensao do predicado getCustoReceita: #IdCons , R ↝ { 𝕍, 𝔽 }
getCustoReceita(IdCons, R) :- findall((IdMed,Quantidade), receita(IdCons,IdMed,_,Quantidade), S) ,
                              getCustoMedicamento(S,R).

%- Extensao do predicado getPrestadores: #IdUt , R ↝ { 𝕍, 𝔽 }
getPrestadores(IdUt , R) :- findall((IdPrest,Data), consulta(_,IdUt,IdPrest,_,_,_,Data),S1) ,
                            auxiliarPrestador(S1,R).

% listar todos os medicamentos de uma receita
getMedicamentosByReceita(IdConsult, R) :- findall(IdMed, receita(IdConsult, IdMed,_,_), S) ,
                                          auxiliarMedicamentos(S,R).

% saber quantos medicos existem para uma determinada especialidade numa determinada instituicao
getNumeroMedicosByInst(Especialidade, Instituicao, R) :- findall(IdServ, servico(IdServ,Especialidade,Instituicao,_),S1) ,
                                                         head(S1,H) ,
                                                         findall(IdPrest, prestador(IdPrest,H,_,_,_), S2),
                                                         length(S2,R).

% despesa anual em consultas de um determinado utente
despesaAnualConsultas(IdUt, Ano, R) :- findall(Custo, consulta(_,IdUt,_,_,_,Custo,(Ano,_,_)), S) ,
                                       sumlist(S,R).

% medicamento mais caro
medicamentoMaisCaro(R) :- findall((Custo,Nome), medicamento(_,Nome,Custo),S) ,
                          sort(S,N) ,
                          last(N,L),
                          swap_pair(L,R).

% medicamento mais barato
medicamentoMaisBarato(R) :- findall((Custo, Nome), medicamento(_,Nome,Custo),S),
                            sort(S,N) ,
                            head(N,L),
                            swap_pair(L,R).


% dada uma especialidade devolve a frequencia de utentes dessa especialidade
freq_esp(Especialidade, R) :- findall(IdServ, servico(IdServ,Especialidade,_,_), L),
                              auxFreq(L, S),
                              findall(IdConsult, consulta(IdConsult,_,_,_,_,_,_), A),
                              length(S,S1), length(A,A1),
                              R is S1/A1 * 100.


% devolve as frequencias de utentes de todas as especialidades
freq_all(R) :- findall(Especialidade, servico(_,Especialidade,_,_), L),
               sort(L, L1),
               maplist(freq_esp, L1, L2),
               zip(L1, L2, R).

%------------------------------------------------------------------------------%
%------------------------------- Invariantes  ---------------------------------%
%------------------------------------------------------------------------------%
% Auxiliares
validaSexo(S) :- S == 'F'.
validaSexo(S) :- S == 'M'.

validaData((Ano,Mes,Dia)) :- Ano > 0 ,
                            Mes > 0 , Mes < 13 ,
                            Dia > 0 , Dia < 32.

% (1) Utentes ------------------------------------------------------------------

%- Invariante Estrutural : Não podem existir utentes com o mesmo ID.
  +utente(IdUt,_,_,_,_) :: (integer(IdUt),
                           findall(IdUt,utente(IdUt,Nome,Idade,Sexo,Cidade),S),
                           length(S,L),
                           L == 1).

%- Invariante Referencial : A idade não pode ser negativa e o sexo tem que ser valido.
  +utente(_,_,Idade,Sexo,_) :: (integer(Idade) ,
                               Idade >= 0 ,
                               validaSexo(Sexo)).


%- Invariante Estrutural : Só é possível eliminar caso o IdUt faça parte da base de conhecimento.
  -utente(IdUt,_,_,_,_) :: (findall(IdUt,utente(IdUt,Nome,Sexo,Idade,Morada),S),
                  				  length(S,N),
                  				  N==0).

% (2) Servicos -----------------------------------------------------------------

%- Invariante Estrutural : um registo de serviço tem que ser unico.
  +servico(IdServ,_,_,_) :: (integer(IdServ) ,
                            findall(IdServ,servico(IdServ,_,_,_),S),
                            length(S,L),L == 1).

%- Invariante Estrutural : Não pode ocorrer a mesma Especialidade na mesma Instituicao apesar de possuir um Id diferente
 +servico(_,Especialidade,Instituicao,_) :: (findall((Especialidade,Instituicao),servico(_,Especialidade,Instituicao,_),S),
                                            length(S,L),L == 1).

%- Invariante Estrutural : Só é possível remover um serviço se ele existir
 -servico(IdServ,_,_,_,_) :: (findall(IdServ,servico(IdServ,Especialidade,Descricao,Instituicao,Cidade),S),
                             length(S,N),
                             N==0).
%- (3)Consultas ----------------------------------------------------------------

%- Invariante Estrutural : validar uma data, custo e o ID do utente e do servico tem que ser validos
    +consulta(IdConsult,IdUt,IdPrestador,IdServ,_,_,_) :: (integer(IdConsult),
                                                          utente(IdUt,_,_,_,_),
                                                          prestador(IdPrestador,IdServ,_,_,_),
                                                          servico(IdServ,_,_,_),
                                                          findall(IdConsult,consulta(IdConsult,_,_,_,_,_,_),S),
                                                          length(S,N),
                                                          N==1).
%- Invariante Referencial:
  +consulta(_,_,_,_,_,_,Data) :: (validaData(Data)).

%- Invariante Referencial:
 +consulta(_,_,_,_,_,Custo,_) :: (Custo>=0).

% (4) Prestador ----------------------------------------------------------------

%- Invariante Estrutural : um registo de prestador tem que ser único.
    +prestador(IdPrest,IdServ,_,_,_) :: (integer(IdPrest) ,
                                          servico(IdServ,_,_,_),
                                          findall(IdPrest,prestador(IdPrest,_,_,_,_),S),
                                          length(S,L) , L== 1).

%- Invariante Referencial : a idade nao pode ser negativa e o sexo deve ser válido.
  +prestador(_,_,_,Idade,_) :: (Idade > 0).

%- Invariante Referencial : O sexo deve ser válido (M ou F).
  +prestador(_,_,_,_,Sexo) :: (validaSexo(Sexo)).

%- Invariante Estrutural : para remover um prestador este tem que existir
  -prestador(IdPrest,_,_,_,_) :: (integer(IdPrest) ,
                                   findall(IdPrest,prestador(IdPrest,_,_,_,_),S) ,
                                   length(S,L) , L == 0).


% (5) Medicamento --------------------------------------------------------------

%- Invariante Estrutural : um registo de medicamento tem que ser unico.
  +medicamento(IdMed,_,_) :: (integer(IdMed) ,
                          findall(IdMed,medicamento(IdMed,_,_),S) ,
                          length(S,L) , L == 1).

%- Invariante Referencial : o custo do medicamento tem que ser positivo.
                          +medicamento(_,_,Custo) :: (Custo > 0).

%- Invariante Estrutural : para remover um prestador este tem que existir.
  -medicamento(IdMed,_,_) :: (integer(IdMed) ,
                             findall(IdMed,medicamento(IdMed,_,_),S) ,
                             length(S,L) , L == 0).

% (6) Receita ------------------------------------------------------------------

%- Invariante Estrutural : Não permite a inserção de conhecimento repetido e inválido.
  +receita(IdCons,IdMed,_,_) :: (integer(IdCons) ,
                                integer(IdMed) ,
                                consulta(IdCons,_,_,_,_,_,_),
                                 medicamento(IdMed,_,_),
                        	      findall((IdCons,IdMed),receita(IdCons,IdMed,_,_),S) ,
                        	      length(S,L) , L == 1).

%- Invariante Referencial : a data deve ser valida. e a quantidade positiva
  +receita(_,_,Data,_) :: (validaData(Data)).

%- Invariante Referencial : A quantidade de comprimidos deve estar compreendida entre ]0,5].

+receita(_,_,_,Quantidade) :: (Quantidade>0,
                               Quantidade=<5).

%------------------------------------------------------------------------------%
%----------------------------- Regras auxiliares ------------------------------%
%------------------------------------------------------------------------------%

%- insercao: T -> {V,F}
insercao(T) :- assert(T).
insercao(T) :- retract(T), !, fail.

%- remocao: T -> {V,F}
remocao(T) :- retract(T).
remocao(T) :- assert(T), !, fail.

%- teste: L -> {V,F}
teste([]).
teste([I|Is]):-I,teste(Is).

%- evolucao: T -> {V,F}
evolucao(T) :-  findall(I,+T::I,Li),
                insercao(T),
                teste(Li).

%- involucao: T -> {V,F}
involucao(T) :- T,
                findall(I,-T::I,Li),
                remocao(T),
                teste(Li).


%------------------------------------------------------------------------------%
auxiliar([],[]).
auxiliar([IdServ|Tail],R):-findall(Custo,consulta(_,_,_,IdServ,_,Custo,_),S),
                          auxiliar(Tail,T),
                          append(S,T,R).

auxiliarPrestador([],[]).
auxiliarPrestador([(IdPrest,Ano,Mes,Dia)|Tail],R) :- findall(Nome,prestador(IdPrest,_,Nome,_,_),S),
                                                    auxiliarPrestador(Tail,T),
                                                    head(S,P) ,
                                                    append(([P,Ano,Mes,Dia]),T,R).

auxiliarMedicamentos([],[]).
auxiliarMedicamentos([IdMed|Tail],R) :- findall(Nome,medicamento(IdMed,Nome,_),S),
                                        auxiliarMedicamentos(Tail,T),
                                        append(S,T,R).

auxInst([],[]).
auxInst([IdServ|Tail], R) :- findall((Data,IdPrestador,IdServ,Custo), consulta(_,_,IdPrestador,IdServ,_,Custo,Data), L),
                            auxInst(Tail,S),
                            append(L,S,R).

map([],[]).
map([((Data,IdPrestador,IdServ,Custo))|Tail],R) :- map(Tail,T) ,
                                                  findall(Nome,prestador(IdPrestador,_,Nome,_,_),P) ,
                                                  findall(Especialidade,servico(IdServ,Especialidade,_,_),S),
                                                  findall(Instituicao,servico(IdServ,_,Instituicao,_),Q),
                                                  head(P,PR) , head(S,SR) ,head(Q,INS),
                                                  append([(Data,PR,SR,Custo,INS)],T,R).

zip([],[],[]).
zip([X|XS],[Y|YS], [(X,Y)|Z]) :- zip(XS,YS,Z).


infoUtente([],[]).
infoUtente([IdUt|Tail],R):-findall((IdUt,Nome,Idade,Sexo,Cidade),utente(IdUt,Nome,Idade,Sexo,Cidade),S),
                                                          infoUtente(Tail,T),
                                                          append(S,T,R).

auxserUt([],[]).
auxserUt([Id|Tail],R):-findall(IdUt,consulta(_,IdUt,_,Id,_,_,_),S),
                        auxserUt(Tail,T),
                        append(S,T,K),
                        sort(K,R).

getCustoMedicamento([],0).
getCustoMedicamento([Head | Tail] , R) :- getCustoMedicamento(Tail,S1) ,
                                          factor(Head,S2) ,
                                          R is S1 + S2.

factor((IdMed,Quantidade), R) :- findall(Custo, medicamento(IdMed,_,Custo), S) ,
                                  head(S,S1) ,
                                  R is S1 * Quantidade.

auxFreq([],[]).
auxFreq([IdServ|Tail], R) :- findall(IdConsult, consulta(IdConsult,_,_,IdServ,_,_,_), L),
                              auxFreq(Tail, S),
                              append(L,S,R).

swap_pair((X,Y),(Y,X)).
