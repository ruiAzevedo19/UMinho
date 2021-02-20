
/**
 * Write a description of class MenuColectivo here.
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
import java.util.stream.Collectors;

public class MenuColectivo
{
    private String nif;
    private GestaoFaturas gf;
    private Scanner scanner;
    private Scanners sc;
    
    /**
     * Método construtor parametrizado
     */
    public MenuColectivo(GestaoFaturas gf, String nif){
        this.nif     = nif;
        this.gf      = gf;
        this.scanner = new Scanner(System.in);
        this.sc      = new Scanners();
    }
    
    /**
     * Método que cria o menu colectivo
     */
    public void menuColectivo() throws ContribuinteNaoExistenteException{
        int op;
        
        while( true ){
            out.println("*********************** Menu do Contribuinte Colectivo ************************");
            out.println("*                                                                             *");
            out.println("*=========================== Listagem de faturas =============================*");
            out.println("*                                                                             *");
            out.println("*  [1] Lista de Faturas(Ordenadas por Data)                                   *");
            out.println("*  [2] Lista de Faturas(Ordenadas por ordem decrescente do valor da despesa)  *");
            out.println("*  [3] Lista de Faturas(Ordenadas entre duas datas)                           *");
            out.println("*                                                                             *");
            out.println("*====================== Códigos de actividade económica ======================*");
            out.println("*                                                                             *");
            out.println("*  [4] Adicionar código de actividade económica                               *");
            out.println("*  [5] Lista de códigos de actividades económicas                             *");
            out.println("*                                                                             *");
            out.println("*====================== Processo de emissão de faturas =======================*");
            out.println("*                                                                             *");
            out.println("*  [6] Emissão de faturas                                                     *");
            out.println("*  [7] Total faturado                                                         *");
            out.println("*                                                                             *");
            out.println("*======================== Contribuintes Relacionados==========================*");
            out.println("*                                                                             *");
            out.println("*  [8] Listagem por contribuinte(Ordenada entre duas datas)                   *");
            out.println("*  [9] Listagem por contribuinte(Ordenada por valor da despesa)               *");
            out.println("* [10] Listagem de todos os contribuintes relacionados                        *");
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
                case 1  : sc.listagem(gf.faturasOrdData(nif));
                          break;
                case 2  : sc.listagem(gf.faturasOrdValor(nif));
                          break;
                case 3  : lerDatas();
                          break;
                case 4  : adicionaCAE();
                          break;
                case 5  : out.println("** Códigos de actividade económica **");
                          out.println(gf.showCAE(nif));
                          break;
                case 6  : emissaoFatura();
                          break; 
                case 7  : lerDatasFaturacao();
                          break;
                case 8  : listagemData();
                          break;
                case 9  : listagemValor();
                          break;
                case 10 : sc.listagem4(gf.contribuintesEmpresa(nif));
                          break;  
                case 0  : return;
                
                default : out.println("Opção inválida!!\n");
            }
        }
    }
    
    private void listagemValor() {
        String n;
        
        while(true){
            out.print("NIF do cliente pretendido: ");
            n = scanner.nextLine();
            
            try{
                if(gf.getContribuinte(nif) == null){
                    out.println("NIF " + nif + " não existente");
                    return;
                }
                else{
                    sc.listagem(faturasContribuinte(gf.faturaTotalOrDesp(nif), n));
                    return;
                }
            }catch(ContribuinteNaoExistenteException e){
                out.println("NIF " + e.getMessage() + " não existente");
                break;
            }
        }
    }
    
    private void listagemData(){
        String n;
        LocalDate d1,d2;
        
        out.println("NIF do cliente pretendido: ");
        n = scanner.nextLine();
        
        out.print("Introduza a data de inicio: ");
        d1 = sc.testeData();
        out.print("Introduza a data de fim: ");
        d2 = sc.testeData();
        out.println();
        
        try{
            sc.listagem(faturas(gf.faturaTotalUser(n, d1, d2),n,nif));
        }catch(ContribuinteNaoExistenteException e){
            out.println("NIF " + e.getMessage() + " não existente");
        }
    }
    
    private TreeSet<Fatura> faturas(Map<String,List<Fatura>> ml, String nif, String nif1){
        List<Fatura> f = ml.get(nif);
        
        return (TreeSet)f.stream().filter(c -> c.getNifEmitente().equals(nif1))
                        .collect(Collectors.toCollection(()->new TreeSet<>(new FaturaValorComparator())));
    }
    
    private TreeSet<Fatura> faturasContribuinte(Map<String,List<Fatura>> ml, String nif){
        List<Fatura> f = ml.get(nif);
        
        return (TreeSet)f.stream().collect(Collectors.toCollection(()->new TreeSet<>(new FaturaValorComparator())));
    }
    
    /**
     * Emissão de faturas
     */
    private void emissaoFatura() throws ContribuinteNaoExistenteException{
        int op;
        String nif_cliente, descricao;
        float valor_despesa;
        
        out.print("NIF cliente: ");
        nif_cliente = scanner.nextLine();
        
        out.print("Descrição da despesa: ");
        descricao = scanner.nextLine();
        
        out.print("Valor da Despesa: ");
        while((valor_despesa = sc.testef()) <= 0) 
            out.print("Insira um número válido: ");
        
        while(true){
            out.println("1- Confirmar");
            out.println("2- Cancelar");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : gf.addFaturas(nif , gf.getContribuinte(nif).getNome(), nif_cliente, descricao, valor_despesa);
                         return;
                case 2 : return;
                
                default : out.println("Opção Inválida!!");
            }
        }
    }
    
    /**
     * Método que adiciona um código CAE
     */
    private void adicionaCAE() throws ContribuinteNaoExistenteException{
        String cod;
        
        out.println("************ Tabela de códigos ************");
        out.println(gf.showCAE());
        out.println("*******************************************");
        
        while(true){
            out.println("Código da actividade económica: ");
            cod = scanner.nextLine();
            
            if( gf.getAtividadeEconomica().containsKey(cod)){
                gf.addCAE(nif,cod);
                break;
            }
            else
                out.println("Código de actividade inválido!!");
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
    
    /**
     * Lê datas do System.in
     */
    private void lerDatasFaturacao(){
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
            out.println("Total facturado: " + gf.faturacaoTotal(nif, data1, data2));
        }catch(ContribuinteNaoExistenteException e){
            out.println("Contribuinte não existente!");
        }
    }
}
