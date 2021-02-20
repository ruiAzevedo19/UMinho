package Server;

import Model.Music;
import Model.User;
import Exception.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class SoundCloud implements Model.SoundCloud {
    /** Estruturas de dados **/
    private int nr_musics;
    private HashMap<String, User> users;
    private HashMap<Integer, Music> musics;
    private HashMap<String,PrintWriter> notification;
    /** Numero maximo de downloads **/
    private int current_downloads;
    /** Locks para cada estrutura **/
    private ReentrantLock nr_musics_lock;
    private ReentrantLock users_lock;
    private ReentrantLock musics_lock;
    private ReentrantLock tasks_lock;
    private ReentrantLock notification_lock;
    private ReentrantLock maxdown_lock;
    /** DB path **/
    private final String USER_PATH;
    private final String MUSIC_PATH;

    /** ------------------------------------------------------------------------------------------------------------ **/

    /**
     * Metodo construtor
     */
    public SoundCloud(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        USER_PATH  = s + "/src/Server/DataBase/user.csv";
        MUSIC_PATH = s + "/src/Server/DataBase/music.csv";
        current_downloads = 0;
        users = new HashMap<>();
        musics = new HashMap<>();
        notification = new HashMap<>();
        nr_musics_lock = new ReentrantLock();
        users_lock = new ReentrantLock();
        musics_lock = new ReentrantLock();
        tasks_lock = new ReentrantLock();
        notification_lock = new ReentrantLock();
        maxdown_lock = new ReentrantLock();
        fillDAO();
    }

    /**
     * Preenche as estruturas de dados users e musics com a informacao dos ficheiros csv
     */
    private void fillDAO(){
        try {
            BufferedReader user_br = new BufferedReader(new FileReader(USER_PATH));
            BufferedReader music_br = new BufferedReader(new FileReader(MUSIC_PATH));
            String line;
            /* UserDAO*/
            User user;
            String[] userLine;
            while( (line = user_br.readLine()) != null ){
                userLine = line.split(";");
                user = new User(userLine[0], userLine[1]);
                users.put(user.getUsername(), user.clone());
            }
            /* MusicDAO */
            Music m;
            List<String> tags = new ArrayList<>();
            String[] musicLine;

            if( (line = music_br.readLine()) != null )
                this.nr_musics = Integer.parseInt(line);
            while( (line = music_br.readLine()) != null ){
                musicLine = line.split(";");
                m = new Music(musicLine[2], musicLine[1], Integer.parseInt(musicLine[4]), Integer.parseInt(musicLine[5]));
                m.setId(Integer.parseInt(musicLine[0]));
                m.setPath(musicLine[3]);
                for(int i = 6; i < musicLine.length; i++)
                    m.addTag(musicLine[i]);
                musics.put(m.getId(), m.clone());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /** ------------------------------------------------------------------------------------------------------------ **/

    /**
     * Adicionar a correspondencia de user e socket de notificacoes
     *
     * @param user : utilizador a adicionar
     * @param out : extremidade de escrita do socket
     */
    public void addToNotifications(String user, PrintWriter out){
        this.notification.put(user, out);
    }

    /**
     * Devolve e incrementa o número de musicas no sistema
     *
     * @return
     */
    public int getNrMusics() {
        int id;
        this.nr_musics_lock.lock();
        id = ++nr_musics;
        this.nr_musics_lock.unlock();
        return id;
    }

    /**
     * Autenticacao do utilizador
     *
     * @param username
     * @param password
     * @throws InvalidLoginException
     */
    @Override
    public void authentication(String username, String password) throws InvalidLoginException {
        try{
            users_lock.lock();
            if( !users.containsKey(username) || !users.get(username).getPassword().equals(password) )
                throw new InvalidLoginException("Login Inválido");
        }finally {
            users_lock.unlock();
        }
    }

    /**
     * Registo de utilizador
     *
     * @param username
     * @param password
     * @throws AlreadyRegistedException
     */
    @Override
    public void registration(String username, String password) throws AlreadyRegistedException {
        try{
            users_lock.lock();
            if( users.containsKey(username) )
                throw new AlreadyRegistedException("Utilizador já registado");
            else
                users.put(username, new User(username,password));
        }finally {
            users_lock.unlock();
        }
    }

    /**
     * Consulta de musicas com filtragem
     *
     * @param type : tipo de procura
     * @param args : argumentos da procura
     * @return : lista das musicas resultantes da procura
     */
    public List<Music> consult(String type, String[] args){
        List<Music> music = new ArrayList<>();
        this.musics_lock.lock();

        switch (type){
            case "identificador" : music.add(musics.get(Integer.parseInt(args[0])));
                                   break;
            case "nome"    : music = musics.values().stream().filter( m -> m.gettitle().equals(args[0])).collect(Collectors.toList());
                             break;
            case "artista" : music = musics.values().stream().filter( m -> m.getartist().equals(args[0])).collect(Collectors.toList());
                             break;
            case "tags"    : for(Music m : musics.values())
                                for(String tag : args)
                                    if( m.getTags().contains(tag) ){
                                        music.add(m);
                                        break;
                                    }
                             break;
        }
        this.musics_lock.unlock();
        return music;
    }

    /**
     * Download de uma musica dado o id
     * @param id : id da musica
     * @return : Musica com o respetivo id
     * @throws InvalidIDException
     */
    @Override
    public Music download(int id) throws InvalidIDException {
        try{
            Music m;
            this.musics_lock.lock();
            if(!this.musics.containsKey(id))
                throw new InvalidIDException("Música com o ID " + id + "não existe!");
            m = this.musics.get(id);
            m.incrementDownload();
            return m;
        }finally {
            this.musics_lock.unlock();
        }
    }

    /**
     * Upload de uma musica
     * @param m : musica a carregar
     * @return identificador da musica
     */
    @Override
    public int upload(Music m) {
        int id = getNrMusics();
        m.setId(id);
        this.musics_lock.lock();
        this.musics.put(id, m);
        this.musics_lock.unlock();
        return id;
    }

    /**
     * Notifica o socket de notificacao do cliente quando prentende sair do sistema
     * @param user : utilizador que pretende sair do sistema
     * @param message : mensagem a enviar
     */
    public void sendNotification(String user, String message){
        this.notification_lock.lock();
        this.notification.get(user).println(message);
        this.notification.get(user).flush();
        this.notification_lock.unlock();
    }

    /**
     * Notifica todos os clientes ligados das musicas carregadas no sistema
     *
     * @param artist : artista da musica
     * @param title : titulo da musica
     */
    public void uploadNofity(String artist, String title){
        String notify = artist + ";" + title;
        this.notification_lock.lock();
        this.notification.values().forEach(out -> {out.println(notify); out.flush();});
        this.notification_lock.unlock();
    }

    /**
     * Avisa todos os clientes que o servidor vai ser desligado
     */
    public void warnClientsAboutShutdown(){
        this.notification_lock.lock();
        this.notification.values().forEach(out -> {out.println("serverDown"); out.flush();});
        this.notification_lock.unlock();
    }

    /**
     * Remove o socket de notificacoes associado a um user
     * @param user : utilizador ao qual se vai remover o socket de notificacoes
     */
    public void removeNotification(String user){
        this.notification_lock.lock();
        this.notification.remove(user);
        this.notification_lock.unlock();
    }
}
