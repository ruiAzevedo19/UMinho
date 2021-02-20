
/**
 * Write a description of class Habitacao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Habitacao extends CAE
{
    /**
     * Método construtor não parametrizado
     */
    public Habitacao(){
        super("4", 0.15, 296, "Fatura de Habitação");
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
     * @param Habitacao s
     */
    public Habitacao(Habitacao h){
        super(h);
    }
    
    /**
     * Método que cria um clone
     */
    public Habitacao clone(){
        return new Habitacao(this);
    }
}
