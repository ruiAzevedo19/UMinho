package Server.Tasks;

import Utilities.Utility;
import Model.Music;
import Server.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Classe que implementa a logica de upload de uma musica no sistema
 */
public class UploadTask extends Task{
    private DataInputStream dataInputStream;
    private String uploadPath;
    private String[] metadata;
    private UploadState uploadState;

    /**
     * Metodo construtor
     *
     * @param soundCloud : estruturas de dados
     * @param out : extremidade de escrita
     * @param socket : socket do cliente
     * @param metadata : meta dados da musica
     * @param uploadState : estado do upload
     */
    public UploadTask(SoundCloud soundCloud, PrintWriter out, Socket socket, String[] metadata, UploadState uploadState) {
        super(soundCloud, out);
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.metadata = metadata;
        this.uploadPath = Utility.makePath(Paths.get("").toAbsolutePath().toString());
        this.uploadState = uploadState;
    }

    /**
     * Recebe os metadados da musica bem como o seu tamanho, a partir dai comeca a
     * receber o conteudo da musica.
     */
    @Override
    public void run() {
        try {
            String s = metadata[1];
            int totalSize = Integer.valueOf(s);
            Music music = new Music(metadata[3], metadata[4], Integer.valueOf(metadata[5]), Integer.valueOf(metadata[6]));
            for(int i = 7; i < metadata.length; i++) music.addTag(metadata[i]);
            String name = music.getartist() + " - " + music.gettitle();
            String uploadPath = this.uploadPath + name + ".mp3";
            File file = new File(uploadPath);
            file.createNewFile();

            int maxsize = Utility.getMaxsize();
            FileOutputStream fos = new FileOutputStream(uploadPath);
            long currentSize = 0, read;
            byte[] bytes = new byte[maxsize];

            while(currentSize < totalSize){
                read = dataInputStream.read(bytes);
                currentSize += read;
                fos.write(bytes, 0, (int) read);
                fos.flush();
            }
            fos.close();

            /* Send the ID assigned */
            int id = super.getSoundCloud().upload(music);
            super.getPrintWriter().println(id);
            super.getPrintWriter().flush();

            /* Send notifications */
            super.getSoundCloud().uploadNofity(music.gettitle(), music.getartist());
            System.out.println("Changing upload state to true");
            this.uploadState.changeUploadFinished();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
