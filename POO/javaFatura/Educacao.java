
/**
 * Write a description of class Educacao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Educacao extends CAE
{
    /**
     * Método construtor não parametrizado
     */
    public Educacao(){
        super("5", 0.3, 800, "Fatura de Educação");
    }
    
    /**
     * Método que calcula o montante de dedução fiscal de uma fatura
     */
    public double calcula(double valor_despesa, double coeficiente_fiscal){
        return valor_despesa * 0.3 * coeficiente_fiscal;
    }
    
    /**
     * Método construtor por cópia 
     * 
     * @param Educacao s
     */
    public Educacao(Educacao e){
        super(e);
    }
    
    /**
     * Método que cria um clone
     */
    public Educacao clone(){
        return new Educacao(this);
    }
}