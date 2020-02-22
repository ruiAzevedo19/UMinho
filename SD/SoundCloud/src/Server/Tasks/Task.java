package Server.Tasks;

import Server.*;

import java.io.PrintWriter;

/**
 * Classe abstrata que permite generalizar um pedido.
 * Desta maneira conseguimos implementar a logica de cada pedido
 * individualmente.
 */

public abstract class Task implements Runnable{
    private SoundCloud soundCloud;
    private PrintWriter out;

    /**
     * Metodo construtor
     *
     * @param soundCloud : estruturas de dados partilahdas
     * @param out : extremidade de escrita para as respostas ao cliente
     */
    public Task(SoundCloud soundCloud, PrintWriter out) {
        this.soundCloud = soundCloud;
        this.out = out;
    }

    /**
     * @return : devolve o soundcloud
     */
    public SoundCloud getSoundCloud(){
        return this.soundCloud;
    }

    /**
     * @return : devolve a extremidade de escrita
     */
    public PrintWriter getPrintWriter(){
        return this.out;
    }
}
