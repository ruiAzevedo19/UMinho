/**
 * Classe Individual
 *
 * @author Gonçalo Costeira
 * @author Rui Costa
 * @author Rui Azevedo
 * @version 00003 (modificado por: Rui Azevedo)
 */
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Set;

public class Individual extends Contribuinte
{
   /**
    * Variáveis de instância
    */
   private int numero_agregado;                 // número do agregado familiar      
   private ArrayList<String> lista_agregado;    // lista dos NIF's do agregado familiar de um contribuinte individual
   private double coeficiente_fiscal;           // coeficiente fiscal
   private ArrayList<String> codigo;            // Lista dos códigos das atividades económicas para as quais um contribuinte
                                                // individual tem possibilidade de deduzir despesas

   /**
    * Método construtor não parametrizado
    */
   public Individual(){
       super();
       this.numero_agregado    = 0;
       this.lista_agregado     = new ArrayList<>();
       this.coeficiente_fiscal = 0;
       this.codigo             = new ArrayList<>();
   }
    
   /**
    * Método construtor parametrizado
    * 
    * @param nome                Nome do contribuinte
    * @param nif                 Número de identificação fiscal do contribuinte
    * @param email               E-mail do contribuinte
    * @param morada              Morada do contribuinte
    * @param password            Password do contribuinte
    * @param numero_agregado     Número de elementos do agregado familiar 
    * @param coeficiente_fiscal  Coeficiente fiscal
    */
   public Individual(String nome, String nif, String email, String morada, String password, int numero_agregado,
                     double coeficiente_fiscal){
       super(nome, nif, email, morada, password);
       this.numero_agregado    = numero_agregado;
       this.lista_agregado     = new ArrayList<>();
       this.coeficiente_fiscal = coeficiente_fiscal;
       this.codigo             = new ArrayList<>();
   }
   
   /**
    * Método construtor por cópia
    * 
    * @param c  Contribuinte Individual
    */
   public Individual(Individual c){
       super(c);
       this.numero_agregado    = c.getNumeroAgregado();
       this.lista_agregado     = (ArrayList)c.getListaAgregado();
       this.coeficiente_fiscal = c.getCoeficienteFiscal();
       this.codigo             = (ArrayList)c.getCodigo();
   }
   
   /* - Get's - */
   
   /**
    * Método que devolve o número do agregado familiar
    */
   public int getNumeroAgregado(){
       return this.numero_agregado;
   }
   
   /**
    * Método que devolve a lista dos números de identificação fiscal do agregado familiar
    */
   public List<String> getListaAgregado(){
       return this.lista_agregado.stream().collect(Collectors.toList());
   }
   
   /**
    * Método que devolve o coeficiente fiscal
    */
   public double getCoeficienteFiscal(){
       return this.coeficiente_fiscal;
   }
   
   /**
    * Método que devolve o código das atividades para qual o Individual pode deduzir as despesas
    */
   public List<String> getCodigo(){
       return this.codigo.stream().collect(Collectors.toList());
   }
   
   /* - Set's - */
   
   /**
    * Método que define o número de agregado familiar
    * 
    * @param numero_agregado    Número de elementos do agregado familiar
    */
   public void setNumeroAgregado(int numero_agregado){
       this.numero_agregado = numero_agregado;
   }
   
   /**
    * Método que define a lista de agregado familiar
    * 
    * @param lista_agr   Conjunto dos NIF's do agregado familiar
    */
   public void setListaAgregado(ArrayList<String> lista_agr){
       this.lista_agregado = (ArrayList)lista_agr.stream().collect(Collectors.toList());
   }
   
   /**
    * Método que define o coeficiente fiscal
    * 
    * @param coeficiente_fiscal     Coeficiente Fiscal
    */
   public void setCoeficienteFiscal(double coeficiente_fiscal){
       this.coeficiente_fiscal = coeficiente_fiscal;
   }
   
   /**
    * Método que define o código das atividades para as quais o contribuinte individual pode deduzir as despesas
    * 
    * @param cods   Códigos das atividades para os quais o contribuinte pode deduzir as despesas
    */
   public void setCodigo(ArrayList<String> cods){
       this.codigo = (ArrayList)cods.stream().collect(Collectors.toList());
   }
   
    /**
    * Método que adiciona um número de identificação fiscal à lista de agregado familiar
    * 
    * @param nif    Número de identificação fiscal de um familiar
    */
   public void addNifAgr(String nif){
       this.lista_agregado.add(nif);
   }
   
   /**
    * Método que adiciona um código de uma atividade económica à lista de códigos
    * 
    * @param cod    Código de uma atividade económica que se pretende adicionar à lista dos códigos das atividades económicas
    */
   public void addCodigo(String cod){
       this.codigo.add(cod);
   }
   
   /**
    * Método que verifica se um Individual é igual a um Object
    * 
    * @param o  Objecto a ser comparado
    */
   public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
            
       Individual c = (Individual) o;
       
       return super.equals(c) && this.numero_agregado == c.getNumeroAgregado() && this.lista_agregado.equals(c.getListaAgregado()) &&
              this.coeficiente_fiscal == c.getCoeficienteFiscal() && this.codigo.equals(c.getCodigo());
   }
   
   /**
    * Método que devolve um novo Individual com o mesmo valor das variáveis de instância 
    */
   public Individual clone(){
       return new Individual(this);
   }
   
   /**
    * Método que converte um Individual numa String
    */
   public String toString(){
       StringBuilder sb = new StringBuilder();
       
       sb.append(super.toString());
       sb.append("Número do agregado familiar: " + this.numero_agregado + "\n");
       
       sb.append("Lista de NIF do agregado familiar: ");
       for(String nif: this.lista_agregado){
           sb.append(nif + "  " + "\n");
       }
       sb.append("\n");
       
       sb.append("Coeficiente Fiscal: "  + this.coeficiente_fiscal + "\n");
       
       sb.append("Lista de Códigos: ");
       for(String cod : this.codigo){
           sb.append(cod + "  " + "\n");
       }
       if(codigo.size() == 0)
            sb.append("\n");
            
       return sb.toString();
   }
}