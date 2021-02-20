
/**
 * Write a description of class Restauracao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Restauracao extends CAE
{
    /**
     * Método construtor não parametrizado
     */
    public Restauracao(){
        super("1", 0.15, 250, "Fatura de Restauração");
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
     * @param Restauracao r
     */
    public Restauracao(Restauracao r){
        super(r);
    }
    
    /**
     * Método que cria um clone
     */
    public Restauracao clone(){
        return new Restauracao(this);
    }
}
