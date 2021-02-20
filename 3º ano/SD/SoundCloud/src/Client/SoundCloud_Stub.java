package Client;

import Model.Music;
import Model.SoundCloud;
import Exception.*;
import Utilities.Utility;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundCloud_Stub implements SoundCloud {
    private Socket socket;          /** Socket do cliente **/
    private PrintWriter out;        /** Extremidade de escrita **/
    private BufferedReader in;      /** Extremidade de leitura **/
    private String downloadPath;    /** Caminho da pasta para downloads **/
    private String username;        /** Nome de utilizador **/

    /**
     * Metodo construtor
     * @param socket : socket do cliente
     */
    public SoundCloud_Stub(Socket socket) {
        try {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path currentRelativePath = Paths.get("");
        this.downloadPath = currentRelativePath.toAbsolutePath().toString();
    }

    /**
     * Envia pedido de registo para o servidor
     * @param username : nome de utilizador
     * @param password : password do utilizador
     * @throws AlreadyRegistedException
     */
    @Override
    public void registration(String username, String password) throws AlreadyRegistedException {
        List<String> vars = new ArrayList<>();
        vars.add(username);
        vars.add(password);
        out.println(Utility.request('R', vars));
        out.flush();
        try {
            String[] answer = in.readLine().split(";");
            if(answer[0].equals("e")) throw new AlreadyRegistedException(answer[1]);
            this.username = username;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia um pedido de autenticacao para o servidor
     * @param username : nome de utilizador
     * @param password : password do utilizador
     * @throws InvalidLoginException
     */
    @Override
    public void authentication(String username, String password) throws InvalidLoginException{
        List<String> vars = new ArrayList<>();
        vars.add(username);
        vars.add(password);
        out.println(Utility.request('A', vars));
        out.flush();
        try {
            String[] answer = in.readLine().split(";");
            if(answer[0].equals("e")) throw new InvalidLoginException(answer[1]);
            this.username = username;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia pedido de consulta para o servidor
     * @param type : tipo de consulta
     * @param args : argumentos do tipo da procura
     * @return : lista de musicas apos a consulta
     */
    public List<Music> consult(String type, String[] args){
        ArrayList<Music> musics = new ArrayList<>();
        List<String> args1 = new ArrayList<>();
        args1.add(type);
        args1.addAll(Arrays.asList(args));
        String reply;

        out.println(Utility.request('C',args1));
        out.flush();
        try{
            reply = in.readLine();
            while( !reply.equals("Finish") ){
                Music music = musicFromString(reply);
                musics.add(music);
                reply = in.readLine();
            }
        }catch (IOException | InvalidIDException ie){
            ie.printStackTrace();
        }
        return musics;
    }

    /**
     * Envia o pedido de download para o servidor e recebe a musica pedida
     * @param id
     * @return
     * @throws InvalidIDException
     */
    @Override
    public Music download(int id) throws InvalidIDException {
        Utility.createFolder(this.downloadPath, this.username);

        DataInputStream inputStream = null;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> vars = new ArrayList<>();
        Music music = null;

        vars.add(Integer.toString(id));
        out.println(Utility.request('D', vars));
        out.flush();
        try {
            music = musicFromString(in.readLine());
            /* Reading stuff */
            String name = music.getartist() + " - " + music.gettitle();
            String path = Utility.makePath(downloadPath,username,name);
            FileOutputStream fos = new FileOutputStream(path);

            int maxsize = Utility.getMaxsize();
            long totalSize = inputStream.readLong();
            long currentSize = 0, read;
            byte[] bytes = new byte[maxsize];

            while(currentSize < totalSize){
                read = inputStream.read(bytes);
                currentSize += read;
                fos.write(bytes, 0, (int) read);
                fos.flush();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return music;
    }

    /**
     * Envia pedido de upload e envia a musica
     * @param m : musica a transferir
     * @return : id da musica
     */
    @Override
    public int upload(Music m){
        DataOutputStream outputStream = null;
        try {
           outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String request;
        int id = -1;

        try {
            File music = new File(m.getPath());
            request = "U;" + music.length() + ";" + m.toString();
            out.println(request);
            out.flush();

            /* Send file */
            Utility.send_music(music, outputStream, false);

            /* Read ID assigned */
            id = Integer.parseInt(in.readLine());
        } catch (IOException io){ io.printStackTrace(); }
        return id;
    }

    /**
     * Faz logout do sistema enviando pedido para o servidor
     */
    public void logout(){
        out.println("L");
        out.flush();
    }

    /**
     * Converte uma string contendo os meta dados de uma musica num objeto Music
     * @param s : string a converter
     * @return : Musica correspondente
     * @throws InvalidIDException
     */
    private Music musicFromString(String s) throws InvalidIDException {
        String[] parser = s.split(";");
        if(parser[0].equals("e")) throw new InvalidIDException(parser[1]);
        int id = Integer.parseInt(parser[0]);
        String title = parser[1];
        String artist = parser[2];
        int year = Integer.parseInt(parser[3]);
        int downloads = Integer.parseInt(parser[4]);

        Music music = new Music(title,artist,year,downloads);
        music.setId(id);

        for(int i = 5; i < parser.length; i++) music.addTag(parser[i]);
        return music;
    }
}
