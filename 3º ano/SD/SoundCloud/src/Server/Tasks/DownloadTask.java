package Server.Tasks;

import Utilities.Utility;
import Model.Music;
import Server.*;

import Exception.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Classe que implementa a logica da transferencia de uma musica
 */
public class DownloadTask extends Task{
    private int id;
    private DataOutputStream dataOutputStream;
    private String downloadPath;

    /**
     * Metodo construtor
     *
     * @param soundCloud : estruturas de dados
     * @param id : identificador da musica para fazer download
     * @param socket : socket do cliente
     * @param out : extremidade de escrita
     */
    public DownloadTask(SoundCloud soundCloud, int id, Socket socket, PrintWriter out) {
        super(soundCloud, out);
        this.id = id;
        this.downloadPath = Utility.makePath(Paths.get("").toAbsolutePath().toString());
        try {
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Faz get da musicaa e envia a musica por fragmentos para o cliente
     */
    @Override
    public void run() {
        try {
            Music music = super.getSoundCloud().download(id);
            String musicInfo = music.toString();
            /* Send info */
            super.getPrintWriter().println(musicInfo);
            super.getPrintWriter().flush();

            /* Send file */
            String path = this.downloadPath + music.getartist() + " - " + music.gettitle() + ".mp3";
            File musicFile = new File(path);
            Utility.send_music(musicFile, this.dataOutputStream, true);
        } catch (InvalidIDException invalidIDException) {
            Utility.send_music(null, this.dataOutputStream, true);
        }
    }
}
