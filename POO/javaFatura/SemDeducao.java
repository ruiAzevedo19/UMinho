
/**
 * Write a description of class SemDeducao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SemDeducao extends CAE
{
    /**
     * Método construtor não parametrizado
     */
    public SemDeducao(){
        super("6", 0, 0, "Fatura sem Dedução");
    }
    
    /**
     * Método que calcula o montante de dedução fiscal de uma fatura
     */
    public double calcula(double valor_despesa, double coeficiente_fiscal){
        return 0;
    }
    
    /**
     * Método construtor por cópia 
     * 
     * @param SemDeducao sd
     */
    public SemDeducao(SemDeducao sd){
        super(sd);
    }
    
    /**
     * Método que cria um clone
     */
    public SemDeducao clone(){
        return new SemDeducao(this);
    }
}