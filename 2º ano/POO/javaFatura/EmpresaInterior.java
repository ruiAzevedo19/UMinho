
/**
 * Write a description of class EmpresaInterior here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class EmpresaInterior extends Colectivo implements Imposto, Serializable
{
    /** Variável de instância | True se a empresa é do interior, False caso contrário */
    private String localizacao;
    private double incentivo;
    
    /**
     * Método construtor não parametrizado
     */
    public EmpresaInterior(){
        super();
        this.localizacao     = "";
        this.incentivo        = 0;
    }
    
    /**
     * Método construtor parametrizado
     */
    public EmpresaInterior(String nome, String nif, String email, String morada, String password, double factor_fiscal,
                           String localizacao, double incentivo){
        super(nome, nif, email, morada, password, factor_fiscal);
        this.localizacao     = localizacao; 
        this.incentivo        = incentivo;
    }
    
    /**
     * Método que devolve a localização da empresa
     */
    public String getLocalizacao(){
        return this.localizacao;
    }
    
    /**
     * Método que devolve o icentivo fiscal da empresa
     */
    public double getIncentivo(){
        return this.incentivo;
    }
    
    /**
     * Método que define a localização da empresa
     */
    public void setLocalizacao(String localizacao){
        this.localizacao = localizacao;
    }
    
    /**
     * Método que define o incentivo fiscal
     */
    public void setIncentivo(double incentivo){
        this.incentivo = incentivo;
    }
    
    /**
     * Método que calcula a redução do imposto
     */
    public double reducaoImposto(double deducao){
        return deducao * (1 + this.incentivo);
    }
}
