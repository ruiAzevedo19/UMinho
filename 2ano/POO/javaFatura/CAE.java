
/**
 * Write a description of class CAE here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.Serializable;

public abstract class CAE implements Serializable 
{
    /**
     * Variáveis de instância
     */
    private String cae;            // codigo da atividade economica
    private double deducao_irs;    // deducao que a atividade tem no IRS
    private int valor_maximo;      // valor maximo que e possivel deduzir
    private String descricao;      // descricaoo da atividade economica
    
    /**
     * Método construtor não parametrizado
     */
    public CAE(){
        this.cae          = "";
        this.deducao_irs  = 0;
        this.valor_maximo = 0;
        this.descricao    = "";        
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param String cae
     * @param double deducao_irs
     * @param int valor_maximo
     * @param String descricao
     */
    public CAE(String cae, double deducao_irs, int valor_maximo, String descricao){
        this.cae          = cae;
        this.deducao_irs  = deducao_irs;
        this.valor_maximo = valor_maximo;
        this.descricao    = descricao;
    }
    
    /**
     * Método construtor por cópia
     * 
     * @param CAE c
     */
    public CAE(CAE c){
        this.cae          = c.getCAE();
        this.deducao_irs  = c.getDeducaoIRS();
        this.valor_maximo = c.getValorMaximo();
        this.descricao    = c. getDescricao();
    }
    
    /**
     * Método que devolve o código de atividade económica
     */
    public String getCAE(){
        return this.cae;
    }
    
    /**
     * Método que devolve o valor da dedução
     */
    public double getDeducaoIRS(){
        return this.deducao_irs;
    }
    
    /**
     * Método que devolve o valor máximo de dedução
     */
    public int getValorMaximo(){
        return this.valor_maximo;
    }
    
    /**
     * Método que devolve a descrição da atividade económica
     */
    public String getDescricao(){
        return this.descricao;
    }
    
    /**
     * Método que define o código da atividade económica
     * 
     * @param String cae
     */
    public void setCAE(String cae){
        this.cae = cae;
    }
    
    /**
     * Método que define a dedução
     * 
     * @param double deducao_irs
     */
    public void setDeducaoIRS(double deducao_irs){
        this.deducao_irs = deducao_irs;
    }
    
    /**
     * Método que define o valor máximo de dedução
     * 
     * @param int valor_maximo
     */
    public void setValorMaximo(int valor_maximo){
        this.valor_maximo = valor_maximo;
    }
    
    /**
     * Método que define a descrição da atividade económica
     * 
     * @param String descricao
     */
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    
    /**
     * Método que calcula o montante de dedução fiscal
     */
    public abstract double calcula(double valor_despesa, double coeficiente_fiscal);
    
    /**
    * Método que verifica se um CAE é igual a um Object
    * 
    * @param Object o
    */
     public boolean equals(Object o){
       if( this == o )
            return true;
       if( o == null || this.getClass() != o.getClass() )
            return false;
            
       CAE c = (CAE) o;
       
       return this.cae.equals(c.getCAE()) && this.deducao_irs == c.getDeducaoIRS() && 
              this.valor_maximo == c.getValorMaximo() && this.descricao.equals(c.getDescricao());
    }
   
    /**
     * Método que cria um CAE com os mesmos valores de instância
     */
    public abstract CAE clone();
    
    /**
     * Método que converte um CAE numa string
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("CAE: "          + this.cae          + "\n") ; sb.append("Valor da Dedução: " + this.deducao_irs          + "\n");
        sb.append("Valor máximo: " + this.valor_maximo + "\n") ; sb.append("Descricão: "        + this.descricao.toString() + "\n");
        
        return sb.toString();
    }
}
