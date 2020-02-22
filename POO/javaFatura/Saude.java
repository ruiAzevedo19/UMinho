
/**
 * Write a description of class Saude here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Saude extends CAE
{   
    /**
     * Método construtor não parametrizado
     */
    public Saude(){
        super("2", 0.15, 1000, "Fatura de Saúde");
    }
 
    /**
     * Método que calcula o montante de dedução fiscal de uma fatura
     */
    public double calcula(double valor_despesa, double coeficiente_fiscal){
        return valor_despesa * 0.15 * coeficiente_fiscal;
    }
    
    /**
     * Método construtor por cópia 
     * 
     * @param Saude s
     */
    public Saude(Saude s){
        super(s);
    }
    
    /**
     * Método que cria um clone
     */
    public Saude clone(){
        return new Saude(this);
    }
}
