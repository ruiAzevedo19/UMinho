
% ------------------------------ Menu ------------------------------------------

menu:- write('\n'),
       write('-----------MENU-------------\n'),
       write('\n'),
       write('-----------Insert-----------\n'),
       write('1.Registar Utente \n'),
       write('2.Registar Servico \n'),
       write('3.Registar Consulta \n'),
       write('4.Registar Prestador \n'),
       write('5.Registar Medicamento \n'),
       write('6.Registar Receita \n'),
       write('\n'),
       write('-----------Delete-----------\n'),
       write('7.Remover Utente \n'),
       write('8.Remover Servico \n'),
       write('9.Remover Consulta \n'),
       write('10.Remover Prestador \n'),
       write('11.Remover Medicamento \n'),
       write('12.Remover Receita \n'),
       write('\n'),
       write('-----------List-----------\n'),
       write('13.Listar Instituicoes \n'),
       write('14.Listar Utente (ID) \n'),
       write('15.Listar Utente (Nome) \n'),
       write('16.Listar Utente (Idade) \n'),
       write('17.Listar Utente (Sexo) \n'),
       write('18.Listar Utente (Cidade) \n'),
       write('19.Listar Servico (ID) \n'),
       write('20.Listar Servico (Especialidade) \n'),
       write('21.Listar Servico (Instituicao) \n'),
       write('22.Listar Servico (Cidade) \n'),
       write('20.Listar Consulta (Data) \n'),
       write('21.Listar Consulta (IdUtente) \n'),
       write('22.Listar Consulta (IdServico) \n'),
       write('\n'),

       write('0.Sair \n'),
       read(Option),
       executar(Option).

executar(Option):-Option =:=1,adicionarUtente,menu;
                  Option =:=2,adicionarServico,menu;
                  Option =:=3,adicionarConsulta,menu;
                  Option =:=4,adicionarPrestador,menu;
                  Option =:=5,adicionarMedicamento,menu;
                  Option =:=6,adicionarReceita,menu;
                  Option =:=7,removerUtente,menu;
                  Option =:=8,removerServico,menu;
                  Option =:=9,removerConsulta,menu;
                  Option =:=10,removerPrestador,menu;
                  Option =:=11,removerMedicamento,menu;
                  Option =:=12,removerReceita,menu;
                  Option =:=13,listInstituicoes,menu;
                  Option =:=14,listUtenteByID,menu;
                  Option =:=15,listUtenteByName,menu;
                  Option =:=16,listUtenteByAge,menu;
                  Option =:=17,listUtenteByGender,menu;
                  Option =:=18,listUtenteByCity,menu;
                  Option =:=19,listServicoById,menu;
                  Option =:=20,listServicoByEspecialidade,menu;
                  Option =:=21,listServicoByInstituicao,menu;
                  Option =:=22,listServicoByCidade,menu;
                  Option =:=23,listConsultaByData,menu;
                  Option =:=24,listConsultaByIdUt,menu;
                  Option =:=25,listConsultaByIdServ,menu;
                  Option =:=0,true,write('BYE').

/*-------------------------------- INSERÇÕES -------------------------------- */

adicionarUtente:-write('IdUt: '),read(IdUt),
                 write('Nome: '),read(Nome),
                 write('Idade: '),read(Idade),
                 write('Sexo: '),read(Sexo),
                 write('Cidade: '),read(Cidade),
                 registarUtente(IdUt,Nome,Idade,Sexo,Cidade).

adicionarServico:-write('IdServ: '),read(IdServ),
                 write('Especialidade: '),read(Especialidade),
                 write('Instituicao: '),read(Instituicao),
                 write('Cidade: '),read(Cidade),
                registarServico(IdServ,Especialidade,Instituicao,Cidade).

adicionarConsulta:-write('IdConsult: '),read(IdConsult),
                   write('IdUt: '),read(IdUt),
                   write('IdPrestador: '),read(IdPrestador),
                   write('IdServ: '),read(IdServ),
                   write('Descricao: '),read(Descricao),
                   write('Custo: '),read(Custo),
                   write('Data: '),read(Data),
                   registarConsulta(IdConsult,IdUt,IdPrestador,IdServ,Descricao,Custo,Data).

adiconarPrestador:-write('IdPrestador: '),read(IdPrestador),
                   write('IdServ: '),read(IdServ),
                   write('Nome: '),read(Nome),
                   write('Idade: '),read(Idade),
                   write('Sexo: '),read(Sexo),
                   write('Especialidade: '),read(Especialidade),
                   registarPrestador(IdPrestador,IdServ,Nome,Idade,Sexo,Especialidade).

adicionarMedicamento:- write('IdMed: '),read(IdMed),
                       write('Nome: '),read(Nome),
                       write('Custo: '),read(Custo),
                       registarMedicamento(IdMed,Nome,Custo).

adicionarReceita:-write('IdCons: '),read(IdCons),
                  write('IdMed: '),read(IdMed),
                  write('DataValidade: '),read(DataValidade),
                  write('Quantidade: '),read(Quantidade),
                  registarReceita(IdCons,IdMed,DataValidade,Quantidade).

/*------------------------------- REMOÇÕES ---------------------------------- */

removerUtente:-write('IdUt: '),read(IdUt),
               removerUtente(IdUt).

removerServico:-write('IdServ: '),read(IdServ),
               removerServico(IdServ).

removerConsulta:-write('IdConsult: '),read(IdConsult),
               removerConsulta(IdConsult).

removerPrestador:-write('IdPrestador: '),read(IdPrestador),
               removerPrestador(IdPrestador).

removerMedicamento:-write('IdMed: '),read(IdMed),
               removerMedicamento(IdMed).

/* Existe a necessidade de remover uma receita ?
E quais os atomos que devem ser passados para uma involucao ?
removerReceita:-write('IdConsult: '),read(IdConsult),
                write('IdMed: '),read(IdMed),
               removerReceita().
                                                        */

/*----------------------------- LISTAGENS ----------------------------------- */

listInstituicoes:-listarInstituicoes(R),
                  write(R),write('\n').

/*-------------------------------- UTENTES ---------------------------------- */

listUtenteByID:-write('IdUt: '),
                read(IdUt),
                utenteByID(IdUt,R),
                write(R),
                write('\n').

listUtenteByName:-write('Nome: '),
                  read(Nome),
                  utenteByNome(Nome,R),
                  write(R),
                  write('\n').

listUtenteByAge:- write('Idade: '),
                  read(Idade),
                  utenteByIdade(Idade,R),
                  write(R),
                  write('\n').

listUtenteByGender:- write('Sexo: '),
                    read(Sexo),
                    utenteBySexo(Sexo,R),
                    write(R),
                    write('\n').

listUtenteByCity:-  write('Cidade: '),
                    read(Cidade),
                    utenteByCidade(Cidade,R),
                    write(R),
                    write('\n').

/*------------------------------ SERVIÇOS ---------------------------------- */

listServicoById:-write('IdServ: '),
                 read(IdServ),
                 servicoByID(IdServ,R),
                 write(R),
                 write('\n').

listServicoByEspecialidade:- write('Especialidade: '),
                             read(Especialidade),
                             servicoByEspecialidade(Especialidade,R),
                             write(R),
                             write('\n').


listServicoByInstituicao:- write('Instituicao: '),
                           read(Instituicao),
                           servicoByInstituicao(Instituicao,R),
                           write(R),
                           write('\n').


listServicoByCidade:- write('Cidade:'),
                      read(Cidade),
                      servicoByCidade(Cidade,R),
                      write(R),
                      write('\n').

/*------------------------------ CONSULTAS ---------------------------------- */


listConsultaByData:- write('Data: '),
                     read(Data),
                     consultaByData(Data,R),
                     write(R),
                     write('\n').


listConsultaByIdUt:- write('IdUt: '),
                     read(IdUt),
                     consultaByIdUt(IdUt,R),
                     write(R),
                     write('\n').

listConsultaByIdServ:-write('IdServ: '),
                     read(IdServ),
                     consultaByIdServ(IdServ,R),
                     write(R),
                     write('\n').
