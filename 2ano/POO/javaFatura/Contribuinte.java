
/**
 * Write a description of class Contribuinte here.
 *
 * @author Gonçalo Costeira
 * @author Rui Costa
 * @author Rui Azevedo
 * @version 00003 (modificado por: Rui Azevedo)
 */

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.io.Serializable;

public abstract class Contribuinte implements Autenticavel, Serializable 
{
   /**
    * Variáveis de instância de um contribuinte
    */
   private String nome;                 // Nome do contribuinte
   private String nif;                  // Número de identificação fiscal do contribuinte
   private String email;                // E-mail do contribuinte
   private String morada;               // Morada do contribuinte
   private String password;             // Password contribuinte
   private Set<String> codFaturas;      // Código das faturas passadas em nome do contribuinte
   
   /**
    * Método construtor não parametrizado
    */
   public Contribuinte() {
       this.nome        = "";
       this.nif         = "";
       this.email       = "";
       this.morada      = "";
       this.password    = "";
       this.codFaturas  = new TreeSet<>();
   }
   
   /**
    * Método construtor parametrizado
    * 
    * @param nome        Nome do contribuinte
    * @param nif         Número de identificação fiscal do contribuinte
    * @param email       E-mail do contribuinte
    * @param morada      Morada do contribuinte
    * @param password    Password do contribuinte
    */
   public Contribuinte(String nome, String nif, String email, String morada, String password) {
       this.nome        = nome;
       this.nif         = nif;
       this.email       = email;
       this.morada      = morada;
       this.password    = password;
       this.codFaturas  = new TreeSet<>();
   }
   
   /**
    * Método construtor por cópia
    * 
    * @param u  Contribuinte 
    */
   public Contribuinte(Contribuinte u){
       this.nome       = u.getNome();
       this.nif        = u.getNIF();
       this.email      = u.getEmail();
       this.morada     = u.getMorada();
       this.password   = u.getPassword();
       this.codFaturas = u.getCodFaturas();
   }
   
   /* - Get's - */
   
   /**
    * Método que devolve o nome do contribuinte
    */
   public String getNome() {
       return this.nome;
   }
   
   /**
    * Método que devolve o número de identificação fiscal do contribuinte
    */
   public String getNIF() {
       return this.nif;
   }
   
   /**
    * Método que devolve o email do contribuinte
    */
   public String getEmail() {
       return this.email;
   }
   
   /**
    * Método que devolve a morada do contribuinte
    */
   public String getMorada(){
       return this.morada;
   }
   
   /**
    * Método que devolve a password do contribuinte
    */
   public String getPassword(){
       return this.password;
   }
   
   /**
    * Método que devolve o Set das faturas
    */
   public TreeSet<String> getCodFaturas(){
       return this.codFaturas.stream().collect(Collectors.toCollection(()->new TreeSet<>()));
   }
   
   /* - Set's - */
   
   /**
    * Método que define o nome do contribuinte
    * 
    * @param nome   Nome do contribuinte
    */
   public void setNome(String nome) {
       this.nome = nome;
   }
   
   /**
    * Método que define o número de identificação fiscal do contribuinte
    * 
    * @param nif   Número de identificação fiscal do contribuinte   
    */
   public void setNIF(String nif) {
       this.nif = nif;
   }
   
   /**
    * Método que define o email do contribuinte
    * 
    * @param email   E-mail do contribuinte
    */
   public void setEmail(String email) {
       this.email = email;
   }
   
   /**
    * Método que define a morada do contribuinte
    * 
    * @param morada   Morada do contribuinte
    */
   public void setMorada(String morada) {
       this.morada = morada;
   }
   
   /**
    * Método que define a password do contribuinte
    * 
    * @param password   Password do contribuinte
    */
   public void setPassword(String password) {
       this.password = password;
   }
   
   /**
    * Método que define o Set das faturas
    * 
    * @param Set<String>   Conjunto dos códigos das faturas emitidas em nome do contribuinte
    */
   public void setFaturas(Set<String> codFs){
       this.codFaturas = codFs.stream().collect(Collectors.toSet());
   }
   
   /* - Outros métodos - */
   
   /**
    * Método que adiciona uma fatura ao Set das faturas
    * 
    * @param codFatura  Adiciona um código de fatura ao conjunto dos códigos de fatura
    */
   public void addFatura(String codFatura){
       this.codFaturas.add(codFatura);
   }
   
   /* - Métodos necessários - */
   
   /**
    * Método que permite ao contribuinte individual fazer login
    * 
    * @param password   Password do contribuinte
    */
   public boolean login(String password){
       return this.password.equals(password);
   }
   
   /**
    * Método que verifica se dois Contribuintes são o mesmo
    * 
    * @param o   Objecto a ser comparado
    */
   public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
       
       Contribuinte u = (Contribuinte) o;
       
       return this.nome.equals(u.getNome()) && this.nif.equals(u.getNIF()) && this.email.equals(u.getEmail()) &&
              this.morada.equals(u.getMorada()) && this.password.equals(u.getPassword()) && this.codFaturas.equals(u.getCodFaturas());
   }
   
   /**
    * Método que compara dois Contribuintes
    * 
    * @param u   Contribuinte
    */
   public int compareTo(Contribuinte u){
       return this.nif.compareTo(u.getNIF());
   }
   
   /**
    * Método que devolve um novo Contribuinte com o mesmo valor das variáveis de instância 
    */
   public abstract Contribuinte clone();
   
   /**
    * Método que converte um Contribuinte numa string
    */
   public String toString(){
       StringBuilder sb = new StringBuilder();
       
       sb.append("Nome: "   + this.nome   + "\n"); 
       sb.append("NIF: "    + this.nif    + "\n");
       sb.append("Email: "  + this.email  + "\n");
       sb.append("Morada: " + this.morada + "\n");
       
       for(String codFatura: this.codFaturas){
           sb.append("Código da fatura: " + codFatura.toString() + "\n");
       }
       
       return sb.toString();
   }
}
