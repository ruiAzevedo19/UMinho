
/**
 * Write a description of class Par here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class Par implements Serializable
{
    private Contribuinte c;
    private double totalFaturado;
    private double deducao;
    
    /**
     * Método construtor parametrizado
     */
    public Par(Contribuinte c, double totalFaturado){
        this.c             = c.clone();
        this.totalFaturado = totalFaturado;
        this.deducao       = 0;
    }
    
    /**
     * Método construtor parametrizado
     */
    public Par(Contribuinte c, double totalFaturado, double deducao){
        this.c             = c.clone();
        this.totalFaturado = totalFaturado;
        this.deducao       = deducao;
    }
    
    /**
     * Método contrutor por cópia
     */
    public Par(Par p){
        this.c             = p.getContribuinte();
        this.totalFaturado = p.getTotalFaturado();
        this.deducao       = p.getDeducao();
    }
    
    // -*-*- Get's -*-*-
    
    /**
     * Método que devolve o contribuinte
     */
    public Contribuinte getContribuinte(){
        return this.c;
    }
    
    /**
     * Método que devolve o total faturado
     */
    public double getTotalFaturado(){
        return this.totalFaturado;
    }
    
    /**
     * Método que devolve a dedução
     */
    public double getDeducao(){
        return this.deducao;
    }
    
    /**
     * Método que define o contribuinte
     */
    public void setContribuinte(Contribuinte c){
        this.c = c;
    }
    
    /**
     * Método que define o total faturado
     */
    public void setTotalFaturado(double totalFaturado){
        this.totalFaturado = totalFaturado;
    }
    
    /**
     * Método que define a dedução
     */
    public void setDeducao(double deducao){
        this.deducao = deducao;
    }
    
    /**
     * Método de verifica se dois Pares sao o mesmo
     */
    public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
       
       Par p = (Par) o;
       
       return this.c.equals(p.getContribuinte()) && this.totalFaturado == p.getTotalFaturado() && this.deducao == p.getDeducao();
    }
    
    /**
     * Método que compara dois pares
     */
    public int compareTo(Par p){
        if( this.totalFaturado > p.getTotalFaturado() )
            return 1;
        if( this.totalFaturado < p.getTotalFaturado() )
            return -1;
        return 0;
    }
}
