
/**
 * Write a description of class MenuIndividual here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.time.LocalDate;
import java.lang.String;
import static java.lang.System.out;
import java.util.Scanner;
import java.lang.Exception;
import java.util.Set;
import java.util.TreeSet;
import java.util.*;

public class MenuIndividual
{
    private String nif;
    private GestaoFaturas gf;
    private Scanner scanner;
    private Scanners sc;
    
    public MenuIndividual(GestaoFaturas gf, String nif){
        this.nif     = nif;
        this.gf      = gf;
        this.scanner = new Scanner(System.in);
        this.sc      = new Scanners();
    }
    
    public void menuIndividual() throws ContribuinteNaoExistenteException, FaturaInexistenteException{
        int op;
        int ano, mes, dia, hora, minuto;
        
        while( true ){
            out.println("********************** Menu do Contribuinte Individual ************************");
            out.println("*                                                                             *");
            out.println("*============================== Faturas ======================================*");
            out.println("*                                                                             *");
            out.println("*  [1] Pesquisa de faturas                                                    *");
            out.println("*  [2] Lista de Faturas(Ordenadas por Data)                                   *");
            out.println("*  [3] Lista de Faturas(Ordenadas por ordem decrescente do valor da despesa)  *");
            out.println("*  [4] Lista de Faturas(Ordenadas entre duas datas)                           *");
            out.println("*  [5] Faturas pendentes                                                      *");
            out.println("*  [6] Resolver fatura                                                        *");
            out.println("*  [7] Corrigir fatura                                                        *");
            out.println("*                                                                             *");
            out.println("*========================= Cálculos de Dedução ===============================*");
            out.println("*                                                                             *");
            out.println("*  [8] Cálculo do montante de dedução fiscal                                  *");
            out.println("*                                                                             *");
            out.println("*============================== Outros =======================================*");
            out.println("*                                                                             *");
            out.println("*  [9] NIF's do agregado familiar                                             *");
            out.println("* [10] Códigos de actividade económica                                        *");
            out.println("*                                                                             *");
            out.println("*================================= SAIR ======================================*");
            out.println("*                                                                             *");
            out.println("*  [0] Menu Inicial                                                           *");
            out.println("*                                                                             *");
            out.println("*******************************************************************************\n");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : pesquisaFatura();
                         break;
                case 2 : sc.listagem(gf.faturasOrdData(nif));
                         break;
                case 3 : sc.listagem(gf.faturasOrdValor(nif));
                         break;
                case 4 : lerDatas();
                         break;
                case 5 : sc.listagem(gf.faturasPendentes(nif));
                         break;
                case 6 : resolveFatura();
                         break;
                case 7 : corrigeFatura();
                         break;
                case 8 : calculoDeducao();
                         break;
                case 9 : adicionaNifFamilia();
                         break;
                case 10 : adicionaCAE();
                         break;
                case 0 : return;
                
                default : out.println("Opção inválida!!");
            }
        }
    }
    
    private void pesquisaFatura() throws ContribuinteNaoExistenteException, FaturaInexistenteException{
        int op; String cod; Fatura f;
        
        while(true){
            out.println("********** Pesquisa de faturas **********");
            out.println("[1] Lista de faturas(Nome - Descrição)");
            out.println("[2] Procurar fatura");
            out.println("[0] Menu Individual");
            out.println("*****************************************");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : sc.listagem5((TreeSet)gf.faturasOrdData(nif));
                         break;
                case 2 : out.print("Introduza o número da fatura: ");
                         cod = scanner.nextLine();
                         try{
                             f = gf.getFatura(nif,cod);
                             if(f == null)
                                out.println("Fatura inexistente!");
                             else
                                out.println(f.toString());
                             break;
                        }catch(FaturaInexistenteException e){
                             out.println("Fatura inexistente!");
                        }
                case 0 : return;
                
                default : out.println("Opção inválida!");                         
            }
        }
    }
    
    private void adicionaCAE() throws ContribuinteNaoExistenteException{
        String cae; int op;
        Individual i = (Individual)gf.getContribuinte(nif);
        
        while(true){
            out.println("[1] Mostrar códigos de actividade associados");
            out.println("[2] Adicionar código de actividade económica");
            out.println("[0] Menu Individual");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : if( gf.showCAE(nif).length() == 0)
                            out.println("Não existem códigos associados");
                         else
                            out.println(gf.showCAE(nif));
                         break;
                case 2 : out.println("************ Tabela de códigos ************");
                         out.println(gf.showCAE());
                         out.println("*******************************************");
                         out.print("Insira o código: ");
                         cae = scanner.nextLine();
                         if(gf.existeCAE(cae) && i.getCodigo().contains(cae) == false)
                            gf.addCAE(nif, cae);
                         else
                            out.println("O código " + cae + " não existe no sistema ou o código já está associado");
                         break;
                case 0  : return;
                
                default : out.println("Opção inválida!!");
            }
        }
    }
    
    private void adicionaNifFamilia() throws ContribuinteNaoExistenteException{
        String nif1;
        Contribuinte c;
        int op;
        Individual i = (Individual)gf.getContribuinte(nif);
        
        
        while(true){
            out.println("******* Lista dos agregados familiares *******");
            out.println(i.getListaAgregado().toString());
            out.println("**********************************************");
            out.println("[1] Adicionar NIF");
            out.println("[0] Menu Individual");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : out.println("Introduza o NIF do familiar: ");
                         nif1 = scanner.nextLine();
                         try{
                             c = gf.getContribuinte(nif1);
                         }catch(ContribuinteNaoExistenteException e){
                             out.println("O NIF " + nif1 + " não existe no sistema");
                             break;
                         }    
                         if(i.getListaAgregado().contains(nif1))
                            out.println("NIF já existente");
                         else
                            gf.addNifAgregado(nif, nif1);
                         break;
                case 0 : return;
                
                default : out.println("Opção inválida!");
            }
        }
    }
    
    private void calculoDeducao() throws ContribuinteNaoExistenteException{
        int op;
        
        while(true){
            out.println("*** Cálculo de dedução fiscal ***");
            out.println("1- Cálculo do montante de dedução fiscal particular");
            out.println("2- Cálculo do montante de dedução fiscal do agregado familiar");
            out.println("0- Menu Individual");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : out.println("Valor da dedução(particular): " + gf.deducaoIncentivo(nif) + "\n");
                         break;
                case 2 : out.println("Valor da dedução(agregado): "   + gf.montanteDeducaoAgregado(nif) + "\n");
                         break;
                case 0 : return;
            }
        }
    }
    
    /**
     * Método que corrige uma fatura
     */
    private void corrigeFatura(){
        String cod, novoCod ; int op;
        Fatura f;
        
        while(true){
            out.println("**** Corrigir faturas ****");
            out.println("1- Corrigir fatura");
            out.println("2- Rasto de códigos");
            out.println("0- Menu Individual");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : out.print("Insira o código da fatura a corrigir: ");
                         cod = scanner.nextLine();
                         out.print("Insira o novo código da actividade económica: ");
                         novoCod = scanner.nextLine();
                         try{
                             if(gf.existeFatura(cod) && gf.existeCAE(novoCod)){
                                 gf.mudaAtividadeFatura(nif, cod, novoCod);
                             }
                             else{
                                 out.println("Dados inválidos!!");
                             }
                         }catch(ContribuinteNaoExistenteException e){
                             out.println("NIF ou códigos inválidos");
                         }
                         break;
                case 2 : out.print("Insira o código da fatura a corrigir que pretende rastear: ");
                         cod = scanner.nextLine();
                         try{
                             if(gf.existeFatura(cod)){
                                f = gf.getFatura(nif,cod);
                                out.println("*** Rasto das actividades económicas ***");
                                out.println(f.getRastoAtividades());
                             }
                        }catch(ContribuinteNaoExistenteException e){
                            out.println("NIF inválido!");
                        }catch(FaturaInexistenteException e){
                            out.println("Fatura não existente!");
                        }
                        break;  
                case 0 : return;
                
                default : out.println("Opção Inválida!!");
            }
        }
    }
    
    /**
     * Método que resolve uma fatura
     */
    private void resolveFatura(){
        String cod; String cae;
        
        while(true){
            out.println("** Resolver fatura **");
            out.println();
            out.println("************ Tabela de códigos ************");
            out.println(gf.showCAE());
            out.println("*******************************************");
            out.print("Insira o código da fatura: ");
            cod = scanner.nextLine();
            out.print("Insira o código da actividade económica: ");
            cae = scanner.nextLine();
            if( gf.finalizaFatura(cod, cae) == true )
                return;
            else
                out.println("Código da fatura e/ou código da actividade económica inválidos!!");
        }
    }
    
    /**
     * Lê datas do System.in
     */
    private void lerDatas(){
        LocalDate data1, data2;
        while(true){
            out.println("Insira a data de início: ");
            data1 = sc.testeData();
            out.println("Insira a data de fim: ");
            data2 = sc.testeData();
            if(data2.compareTo(data1) < 0)
                out.println("Data inicial maior que data final!!");
            else
                break;
        }
        
        try{
            sc.listagem(gf.faturasEntreDatas(nif, data1, data2));
        }catch(ContribuinteNaoExistenteException e){
            out.println("Contribuinte não existente!");
        }
    }
}
