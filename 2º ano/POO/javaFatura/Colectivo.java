
/**
 * Write a description of class Colectivo here.
 *
 * @author Gonçalo Costeira
 * @author Rui Costa
 * @author Rui Azevedo
 * @version 00003 (modificado por: Rui Azevedo)
 */
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Colectivo extends Contribuinte 
{
    /**
     * Variáveis de instância
     */    
    private ArrayList<String> actividade_economica;      
    private double factor_fiscal;  
    
    /**
     * Método construtor não parametrizado
     */
    public Colectivo(){
        super();
        this.actividade_economica  = new ArrayList<>();
        this.factor_fiscal         = 0;        
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param String nome
     * @param String nif
     * @param String email
     * @param String morada
     * @param String password
     * @param Set<String> fs
     * @param ArrayList<String> act_eco
     * @param double factor_fiscal
     */    
    public Colectivo (String nome, String nif, String email, String morada, String password, double factor_fiscal){
        super(nome, nif, email, morada, password);      
        this.actividade_economica = new ArrayList<>();
        this.factor_fiscal = factor_fiscal; 
    }
    
    /**
     * Método construtor por cópia
     * 
     * @param Colectivo e
     */
    public Colectivo(Colectivo e){
        super(e);
        this.actividade_economica = (ArrayList)e.getActividadeEconomica();
        this.factor_fiscal        = e.getFactorFiscal();
    }
    
    /* - Get's - */
    
    /**
     * Método que devolve a lista das atividades economicas
     */
    public List<String> getActividadeEconomica(){
        return this.actividade_economica.stream().collect(Collectors.toList());
    }
    
    /**
     * Método que devolve o factor fiscal
     */
    public double getFactorFiscal(){
        return this.factor_fiscal;
    }
    
    /* - Set's - */
    
    /**
     * Método que define a lista das actividades económicas
     * 
     * @param ArrayList<String> act_eco
     */
    public void setActividadesEconomicas(ArrayList<String> act_eco){
        this.actividade_economica = (ArrayList)act_eco.stream().collect(Collectors.toList());
    }
    
    /**
     * Método que adiciona uma actividade económica à lista das actividades económicas
     * 
     * @param String actividade
     */
    public void addAtividadeEconomica(String actividade){
        this.actividade_economica.add(actividade);
    }
    
    /**
     * Método que define o factor_fiscal
     * 
     * @param double factor_fiscal
     */
    public void setFactorFiscal(double factor_fiscal){
        this.factor_fiscal = factor_fiscal;
    }
    
    /* - Outros métodos - */

    /**
    * Método que verifica se uma Colectivo é igual a um Object
    * 
    * @param Object o
    */
     public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
            
       Colectivo e = (Colectivo) o;
       
       return super.equals(e) && this.actividade_economica.equals(e.getActividadeEconomica()) && 
              this.factor_fiscal == e.getFactorFiscal();
    }
    
    /**
     * Método que devolve uma nova Colectivo com o mesmo valor das variáveis de instância 
     */
    public Colectivo clone(){
        return new Colectivo(this);
    }
   
    /**
     * Método que converte um Contribuinte numa String
     */
     public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append(super.toString());
           
        sb.append("Lista das actividades económicas: ");
        for(String act: this.actividade_economica){
            sb.append(act + " || ");
        }
        sb.append("\n");
        
        sb.append("Factor Fiscal: " + this.factor_fiscal + "\n");
       
        return sb.toString();
    }
}