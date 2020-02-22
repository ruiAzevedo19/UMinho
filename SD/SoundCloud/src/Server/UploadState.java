package Server;

/**
 * Classe que permite a thread de um cliente saber quando um upload ja foi feito
 */
public class UploadState {
    private boolean uploadFinished;

    /**
     * Metodo construtor
     */
    public UploadState(){
        this.uploadFinished = false;
    }

    /**
     * Muda o estado do upload
     */
    public synchronized void changeUploadFinished(){
        this.uploadFinished = true;
    }

    /**
     * @return : devolve o estado do upload
     */
    public synchronized boolean isUploadFinished(){
        return this.uploadFinished;
    }
}
