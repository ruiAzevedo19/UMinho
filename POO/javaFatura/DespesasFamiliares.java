
/**
 * Write a description of class DespesasFamiliares here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DespesasFamiliares extends CAE
{
    /**
     * Método construtor não parametrizado
     */
    public DespesasFamiliares(){
        super("3", 0.35, 250, "Fatura de Despesas Familiares");
    }

    /**
     * Método que calcula o montante de dedução fiscal de uma fatura
     */
    public double calcula(double valor_despesa, double coeficiente_fiscal){
        return valor_despesa * 0.35 * coeficiente_fiscal;
    }
    
    /**
     * Método construtor por cópia 
     * 
     * @param DespesasFamiliares s
     */
    public DespesasFamiliares(DespesasFamiliares df){
        super(df);
    }
    
    /**
     * Método que cria um clone
     */
    public DespesasFamiliares clone(){
        return new DespesasFamiliares(this);
    }
}
