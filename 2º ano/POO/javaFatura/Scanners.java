
/**
 * Write a description of class Scanners here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Scanner;
import java.time.LocalDate;
import static java.lang.System.out;
import java.util.*;
public class Scanners
{
    private Scanner scanner;
    
    /**
     * Método contrutor não parametrizado
     */
    public Scanners(){
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Método que lê uma data do System.in 
     */
    public LocalDate testeData(){
        LocalDate date = null;
        while(true){
            out.println("Insira uma data no formato yyyy-mm-dd");
            try {
                date = LocalDate.parse(scanner.nextLine());
                break;
            }catch(Exception e){
                out.println("Formato inválido!!");
            }
        }
        return date;
    } 
    
    /**
     * Método que lê um inteiro do System.in
     */
    public int teste(){
        int op;
        try {
            op = scanner.nextInt();
        }catch(Exception e){
            op = -1;
        }
        scanner.nextLine();
        return op;
    }
    
    /**
     * Método que lê um float do System.in
     */
    public float testef(){
        float op;
        try {
            op = scanner.nextFloat();
        }catch(Exception e){
            op = -1;
        }
        scanner.nextLine();
        return op;
    }
    
    /**
     * Método que lê um double do System.in
     */
    public double testeD(){
        double op;
        try {
            op = scanner.nextDouble();
        }catch(Exception e){
            op = -1;
        }
        scanner.nextLine();
        return op;
    }
    
    public void listagem(Set<Fatura> f){
        int i,j,n,op, size = f.size();
        Iterator it = f.iterator();
        
        if(f.size() == 0)
            out.println("Não existem faturas!");
            
        for(i = 0; i < size;){
            for(j = 0; j < 10 && it.hasNext(); j++, i++){
                out.println(it.next().toString());
            }
            if (i == size){
                out.println("Fim...");
                return;
            }
            while(true){
                out.println("1- Ver mais...");
                out.println("0- Sair");
                op = teste();
                
                switch(op){
                    case 1 : break;
                    
                    case 0 : return;
                    
                    default: out.println("Opção Inválida!!");
                }
                if (op == 1) break;
            }
        }
    }
    
    public void listagem1(TreeSet<Par> p){
        if(p.size() == 0)
            out.println("Não existem elementos a apresentar!!");
        else
            for(Par par: p){
                out.print(par.getContribuinte().toString());
                out.print("Total faturado: " + par.getTotalFaturado() + "\n\n");
            }
        
    }
    
    public void listagem2(TreeSet<Par> p){
        if(p.size() == 0)
            out.println("Não existem elementos a apresentar!!");
        else
            for(Par par: p){
                out.print(par.getContribuinte().toString());
                out.println("Número de faturas: " + par.getTotalFaturado());
                out.println("Dedução total: " + par.getDeducao() + "\n");
            }
    }
    
        public void listagem3(TreeSet<Par> p){
        int i,j,n,op, size = p.size();
        Iterator it = p.iterator();
        Par pp;
        
        if(p.size() == 0)
            out.println("Não existem contribuintes!");
            
        for(i = 0; i < size;){
            for(j = 0; j < 1 && it.hasNext(); j++, i++){
                pp = (Par)it.next();
                out.print(pp.getContribuinte().toString());
                out.print("Total faturado: " + pp.getTotalFaturado() + "\n\n");
            }
            if (i == size){
                out.println("Fim...");
                return;
            }
            while(true){
                out.println("1- Ver mais...");
                out.println("0- Sair");
                op = teste();
                
                switch(op){
                    case 1 : break;
                    
                    case 0 : return;
                    
                    default: out.println("Opção Inválida!!");
                }
                if (op == 1) break;
            }
        }
    }
    
    public void listagem4(TreeSet<Contribuinte> c){
        if(c == null)
            out.println("Não existem contribuintes");
        else{
            for(Contribuinte cc: c){
                out.println("Nome: " + cc.getNome());
                out.println("NIF : " + cc.getNIF() + "\n");
            }
        }      
    }
    
    public void listagem5(TreeSet<Fatura> fs){
        if(fs == null)
            out.println("Não existem faturas");
        else{
            for(Fatura f: fs){
                out.println("Código da fatura: " + f.getCodFatura());
                out.println("Descrição: " + f.getDescricaoDespesa() + "\n");
            }
        }
    }
}
