
/**
 * Escreva a descrição da classe IndiviualPasswdException aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ContribuinteNaoExistenteException extends Exception
{
    /**
     * Método construtor não parametrizado
     */
    public ContribuinteNaoExistenteException(){
        super();
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param String s
     */
    public ContribuinteNaoExistenteException(String s){
        super(s);
    }
}
