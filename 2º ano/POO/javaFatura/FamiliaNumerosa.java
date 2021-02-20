
/**
 * Write a description of class FamiliaNumerosa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class FamiliaNumerosa extends Individual implements Imposto, Serializable
{
    /** Variável de instância */
    private int numeroFilhos;
    
    /**
     * Método contrutor não parametrizado
     */
    public FamiliaNumerosa(){
        super();
        this.numeroFilhos = 0;
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param numeroFilhos  numero de filhos 
     */
    public FamiliaNumerosa(String nome, String nif, String email, String morada, String password, int numero_agregado,
                           double coeficiente_fiscal, int numeroFilhos){
        super(nome, nif, email, morada, password,numero_agregado, coeficiente_fiscal);
        this.numeroFilhos = numeroFilhos;
    }
    
    /**
     * Método que calcula a reducao do imposto
     */
    public double reducaoImposto(double deducao){
        double x;
        
        if(this.numeroFilhos < 5)
            x = 1;
        else
            x = 0.05 * this.numeroFilhos + 0.8;
        
        return deducao * x; 
    }
}
