
/**
 * Write a description of class MenuInicial here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Scanner;
import static java.lang.System.out;

public class MenuInicial
{
    private GestaoFaturas gf;
    private Scanner scanner;
    private Scanners sc;
    private Admin a;
    
    /**
     * Método construtor parametrizado
     */
    public MenuInicial(GestaoFaturas g){
        scanner = new Scanner(System.in);
        this.gf = g;    
        this.sc = new Scanners();
        this.a  = new Admin("Administrador", "codAdmin", "admin@gmail.com", "Azurém", "passAdmin","codAdmin");
    }
    
    /**
     * Método que cria o menu inicial
     */
    public void menuInicial() throws ContribuinteNaoExistenteException, FaturaInexistenteException{
        int op;
        
        while(true){
            out.println("*************** Menu Inicial ***************");
            out.println("*                                          *");
            out.println("*  [1] Login                               *");
            out.println("*  [2] Registo                             *");
            out.println("*  [0] Fechar                              *");
            out.println("*                                          *");
            out.println("********************************************\n");
            out.print("Opção: ");
            
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : menuLogin();
                         break;
                case 2 : menuRegisto();
                         break;
                case 0 : return;
                
                default : out.println("Opção Inválida!!\n");
            }
        }
    }
    
    /**
     * Método que cria o menu login
     */
    private void menuLogin() throws ContribuinteNaoExistenteException, FaturaInexistenteException{
        Contribuinte c;
        String nif, password;
        int op;
        
        while(true){            
            out.print("NIF: ");
            nif = scanner.nextLine();
            
            out.print("Password: ");
            password = scanner.nextLine();
            
            if(a.getNIF().equals(nif) && a.getPassword().equals(password)){
                new MenuAdmin(gf,nif).menuAdmin();
                return;
            }
            
            if( (c = gf.login(nif, password)) == null){
                out.println("NIF ou password inválida!!\n");
                while(true){
                    out.println("1- Tentar outra vez");
                    out.println("0- Menu Inicial");
                    out.print("Opção: ");
                    op = sc.teste();
                    out.println();
                    
                    if(op == 1) break;
                    if(op == 0) return;
                    out.println("Opção Inválida!!");
                }               
            }
            else{
                if(c instanceof Individual){
                    new MenuIndividual(gf,nif).menuIndividual();
                    return;
                }
                if(c instanceof Colectivo){
                    new MenuColectivo(gf,nif).menuColectivo();
                    return;
                }
            }
        }   
    }
    
    /**
     * Método que cria o menu de registo
     */
    private void menuRegisto() throws ContribuinteNaoExistenteException, FaturaInexistenteException{
        int op;
        
        while(true){
            out.println("*************** Tipo de utilizador ***************");
            out.println("*                                                *");
            out.println("*  [1] Contribuinte Individual                   *");
            out.println("*  [2] Contribuinte Colectivo                    *");
            out.println("*  [0] Menu Inicial                              *");
            out.println("*                                                *");
            out.println("**************************************************\n");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : if(menuRegistoIndividual())
                            return;
                         break;
                case 2 : if(menuRegistoColectivo())
                            return;
                         break;
                case 0 : return;
                
                default : out.println("Opção Inválida!!\n");
            }
        }
    }
    
    /**
     * Método que cria o menu de registo individual
     */
    private boolean menuRegistoIndividual() throws ContribuinteNaoExistenteException,FaturaInexistenteException{
        int op;
        String nif, password, nome, email, morada;
        int numero_agregado, filhos; double coef_fiscal;
        
        out.print("NIF: ");
        nif = scanner.nextLine();
        
        out.print("Password: ");
        password = scanner.nextLine();
        
        out.print("Nome: ");
        nome = scanner.nextLine();
        
        out.print("E-mail: ");
        email = scanner.nextLine();
        
        out.print("Morada: ");
        morada = scanner.nextLine();
        
        out.print("Número de elementos do agregado familiar: ");
        while((numero_agregado = sc.teste()) < 0) out.print("Insira um número válido: ");
        
        out.print("Coeficiente Fiscal: ");
        while((coef_fiscal = sc.testeD()) < 0) out.print("Insira um número válido: ");
        
        out.print("Número de filhos: ");
        filhos = sc.teste();
        
        while(true){
            out.println("1- Confirmar");
            out.println("2- Cancelar");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : if(filhos < 4){
                             Individual c = new Individual(nome, nif, email, morada, password,numero_agregado, coef_fiscal);
                             if(!gf.registo(c)) {
                                 out.println("NIF já existente!!");
                                 return false;
                             }
                         }
                         else{
                             FamiliaNumerosa f = new FamiliaNumerosa(nome, nif, email, morada, password,numero_agregado, 
                                                 coef_fiscal,filhos);
                             if(!gf.registo(f)) {
                                 out.println("NIF já existente!!");
                                 return false;
                             }
                         }
                         new MenuIndividual(gf, nif).menuIndividual();
                         return true;
                case 2 : return false;
                
                default : out.println("Opção Inválida!!");
            }
        }
    }
    
    /**
     * Método que cria um menu de registo colectivo
     */
    private boolean menuRegistoColectivo() throws ContribuinteNaoExistenteException{
        int op;
        String nif, password, nome, email, morada, distrito;
        double factor_fiscal;
        
        out.print("NIF: ");
        nif = scanner.nextLine();
        
        out.print("Password: ");
        password = scanner.nextLine();
        
        out.print("Nome: ");
        nome = scanner.nextLine();
        
        out.print("E-mail: ");
        email = scanner.nextLine();
        
        out.print("Morada: ");
        morada = scanner.nextLine();
        
        out.print("Factor Fiscal: ");
        while((factor_fiscal = sc.testeD()) < 0) out.print("Insira um número válido: ");
        
        out.println("\nLista dos distritos com incentivo\n");
        out.println(gf.incentivos());
        out.print("Distrito: ");
        distrito = scanner.nextLine();
        
        while(true){
            out.println("1- Confirmar");
            out.println("2- Cancelar");
            out.print("Opção: ");
            op = sc.teste();
            out.println();
            
            switch(op){
                case 1 : if(gf.takeIncentivo(distrito) == 0){
                             Colectivo c = new Colectivo(nome, nif, email, morada, password,factor_fiscal);
                             if(!gf.registo(c)){
                                 out.println("NIF já existente!!");
                                 return false;
                             }
                         }
                         else{
                             EmpresaInterior i = new EmpresaInterior(nome,nif,email, morada, password, factor_fiscal,
                                                                     distrito, gf.takeIncentivo(distrito)); 
                         }
                         new MenuColectivo(gf, nif).menuColectivo();
                         return true;
                case 2 : return false;
                
                default : out.println("Opção Inválida!!");
            }
        }
    }
}
