package Server.Tasks;

import Model.Music;
import Server.*;

import java.io.PrintWriter;
import java.util.List;

/**
 * Classe que implementa a logica de procura de musicas
 */
public class SearchTask extends Task {
    private String type;
    private String[] args;

    /**
     * Metodo construtor
     *
     * @param soundCloud : estruturas de dados
     * @param out : extremidade de escrita
     * @param type : tipo e procura
     * @param var : argumentos da procura
     */
    public SearchTask(SoundCloud soundCloud, PrintWriter out, String type, String[] var){
        super(soundCloud,out);
        this.type = type;
        args = new String[50];
        for(int i = 0, j = 2; j < var.length; i++, j++) {
            this.args[i] = var[j];
        }
    }

    /**
     * Procura a lista de musicas com a filtragem pretendida e envia musica a musica
     * para o cliente
     */
    @Override
    public void run() {
        List<Music> musics = super.getSoundCloud().consult(type,args);

        /** send musics **/
        if( musics != null){
            for(Music m : musics) {
                String send_music = m.toString();
                super.getPrintWriter().println(send_music);
                super.getPrintWriter().flush();
            }
        }
        super.getPrintWriter().println("Finish");
        super.getPrintWriter().flush();
    }
}
