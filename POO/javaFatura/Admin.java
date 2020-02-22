
/**
 * Write a description of class Admin here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.lang.StringBuilder;
import java.util.Set;

public class Admin extends Contribuinte 
{
    private String codAdmin;
    
    /**
     * Método construtor não parametrizado
     */
    public Admin(){
        super();
        this.codAdmin = "";
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param String nome
     * @param String nif
     * @param String email
     * @param String morada
     * @param String password
     * @param Set<Fatura> fs
     * @param String codAdmin
     */
    public Admin(String nome, String nif, String email, String morada, String password, String codAdmin){
        super(nome, nif, email, morada, password);
        this.codAdmin = codAdmin;
    }
    
    /**
     * Método construtor por cópia 
     * 
     * @param Admin adm
     */
    public Admin(Admin adm){
        super(adm);
        this.codAdmin = adm.getCodAdmin();
    }
    
    /**
     * Método que devolve o código de administrador
     */
    public String getCodAdmin(){
        return this.codAdmin;
    }
    
    /**
     * Método que define o código de Administrador
     * 
     * @param String codAdmin
     */
    public void setCodAdmin(String codAdmin){
        this.codAdmin = codAdmin;
    }
    
    /**
     * Método que permite fazer ao administrador fazer login
     * 
     * @param String password
     */
   
    public boolean login(String password){
        return super.getPassword().equals(password);
    }
   
    /**
    * Método que verifica se um Admin é igual a um Object
    * 
    * @param Object o
    */
     public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
            
       Admin adm = (Admin) o;
       
       return super.equals(adm) && this.codAdmin.equals(adm.getCodAdmin());
    }
    
   /**
    * Método que devolve uma nova Colectivo com o mesmo valor das variáveis de instância 
    */
   public Admin clone(){
       return new Admin(this);
   }
   
   /**
    * Método que converte um Contribuinte numa String
    */
   public String toString(){       
       return super.toString();
   }
}
