
/**
 * Write a description of class FaturaInexistenteException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class FaturaInexistenteException extends Exception
{
    /**
     * Método construtor não parametrizado
     */
    public FaturaInexistenteException(){
        super();
    }
    
    /**
     * Método construtor parametrizado
     * 
     * @param String s
     */
    public FaturaInexistenteException(String s){
        super(s);
    }
}
