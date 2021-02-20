
/**
 * Write a description of class MenuAdmin here.
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

public class MenuAdmin
{
    private String cod;
    private GestaoFaturas gf;
    private Scanner scanner;
    private Scanners sc;
    
    public MenuAdmin(GestaoFaturas gf, String cod){
        this.cod     = cod;
        this.gf      = gf;
        this.scanner = new Scanner(System.in);
        this.sc      = new Scanners();
    }
    
    public void menuAdmin() throws ContribuinteNaoExistenteException{
        int op;
        
        while( true ){
            out.println("******************** Menu Administrador ***********************");
            out.println("*                                                             *");
            out.println("*  [1] Pesquisa de faturas por contribuinte                   *");
            out.println("*  [2] Contribuintes que mais gastam                          *");
            out.println("*  [3] Empresas com mais faturas                              *");
            out.println("*  [4] Faturas do sistema                                     *");
            out.println("*  [5] Contribuintes individuais do sistema                   *");
            out.println("*  [6] Contribuintes colectivos do sistema                    *");
            out.println("*  [0] Menu Inicial                                           *");
            out.println("*                                                             *");
            out.println("***************************************************************\n");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : pesquisa();
                         break;
                case 2 : try{
                            sc.listagem3(gf.contribuintesMaisFaturam());
                         }catch(ContribuinteNaoExistenteException e){
                            out.println("Contribuinte não existente");
                         }
                         break;
                case 3 : faturacaoEmpresas();
                         break;
                case 4 : sc.listagem(gf.todasFaturas());
                         break;
                case 5 : sc.listagem4(gf.getContribuintes());
                         break;
                case 6 : sc.listagem4(gf.getEmpresas());
                         break;
                case 0 : return;
                
                default : out.println("Opção Inválida!!");
            }
        }
    }
    
    private void pesquisa() throws ContribuinteNaoExistenteException{
        String nif;
 
        out.println("********** Pesquisa de faturas **********");
        out.println("Introduza o NIF do contribuinte: ");
        nif = scanner.nextLine();
        out.println();
        
        Contribuinte c;
        try{
            c = gf.getContribuinte(nif);
            sc.listagem(gf.faturasOrdData(nif));
        }catch(ContribuinteNaoExistenteException e){
            out.println("O contribuinte com o NIF " + nif + " não existe!");
        }
    }
    
    private void faturacaoEmpresas(){
        int n;
        while(true){
            out.println("Quantas empresas deseja ver? ");
            n = sc.teste();
            
            if(n < 1)
                out.println("Número Inválido!");
            else {
                try{
                    sc.listagem3(gf.empresasMaisFaturas(n));
                    return;  
                }catch(ContribuinteNaoExistenteException e){
                    out.println("Contribuinte não existente");
                }
            }
        }
    }
}
