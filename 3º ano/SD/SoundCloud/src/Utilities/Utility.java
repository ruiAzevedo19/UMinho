package Utilities;

import com.jakewharton.fliptables.FlipTable;

import java.io.*;
import Model.Music;
import com.jakewharton.fliptables.FlipTableConverters;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utility {
    /** Numero maximo de bytes a transferir de uma unica vez **/
    private static final int MAXSIZE = 5 * 1024;  /* 5KB */

    /**
     * @return devolve o numero maximo de bytes de transmissao.
     */
    public static int getMaxsize() {
        return MAXSIZE;
    }

    /**
     * Cria um pedido com o formato csv para enviar para o servidor
     *
     * @param c : tipo do pedido
     * @param var : lista de variaveis do pedido
     * @return : String com o pedido
     */
    public static String request(char c, List<String> var){
        StringBuilder sb = new StringBuilder();

        sb.append(c);
        for(String s : var)
            sb.append(";" + s);
        return sb.toString();
    }

    /**
     * Cria um caminho de um ficheiro para um dado sistema operativo
     * @param path : Caminho para fazer append
     * @param folder : pasta para fazer append
     * @param file : ficheiro para fazer append
     * @return : caminho absoluto
     */
    public static String makePath(String path, String folder, String file){
        String[] os = System.getProperty("os.name").split(" ");
        String fullPath = null;
        switch (os[0]){
            case "Windows" : fullPath = path + "\\src\\Client\\Downloads\\" + folder + "\\" + file + ".mp3";
                             break;
            case "Mac"     : fullPath = path + "/src/Client/Downloads/" + folder + "/" + file + ".mp3";
                             break;
        }
        return fullPath;
    }

    /**
     * Cria um caminho para a base de dados conforme o sistema operativo
     * @param path : caminho base de dados
     * @return : caminho aabsoluto da base de dados
     */
    public static String makePath(String path){
        String[] os = System.getProperty("os.name").split(" ");
        String fullPath = null;
        switch (os[0]){
            case "Windows" : fullPath = path + "\\src\\Server\\DataBase\\";
                             break;
            case "Mac"     : fullPath = path + "/src/Server/DataBase/";
                             break;
        }
        return fullPath;
    }

    /**
     * Cria uma diretoria com o nome de utilizador
     * @param path : caminho da pasta
     * @param username : nome de utilizador
     */
    public static void createFolder(String path, String username){
        String[] os = System.getProperty("os.name").split(" ");
        String fullPath = null;
        File dir;
        switch (os[0]){
            case "Windows" : fullPath = path + "\\src\\Client\\Downloads\\" + username;
                             break;
            case "Mac"     : fullPath = path + "/src/Client/Downloads/" + username;
                             break;
        }
        dir = new File(fullPath);
        if( !dir.exists() )
            dir.mkdir();

    }

    /**
     * Envia uma musica para uma determinada extremidade de escrita no socket
     * @param music : musica a enviar
     * @param out : extremidade de escrita do socket
     * @param sendSize : tamanho do ficheiro
     */
    public static void send_music(File music, DataOutputStream out, boolean sendSize){
        try {
            FileInputStream fis = new FileInputStream(music.getPath());
            File file = new File(music.getPath());
            long N = file.length();

            if(sendSize){
                out.writeLong(N);
                out.flush();
            }
            byte[] send = new byte[MAXSIZE];
            int n;
            while ((n = fis.read(send)) > 0) {
                out.write(send, 0, n);
                out.flush();
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    /**
     * Converte uma lista de tags numa string
     * @param tags : tags a converter
     * @return : String com as tags
     */
    private static String tagToString(List<String> tags){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int n = tags.size();

        for(String tag : tags) {
            if (i != n - 1)
                sb.append(tag + ", ");
            else
                sb.append(tag);
            i++;
        }
        return sb.toString();
    }

    /**
     * Cria uma tabela para apresentar ao utilizador com um determinado conjunto de musicas
     * @param musics : lista de musicaas a apresentaar
     */
    public static void prettyTable(List<Music> musics){
        String[] headers = {"Título","Artista","Ano", "Downloads","Tags"};
        Object[][] data = new Object[musics.size()][5];
        int i = 0;

        for(Music m : musics){
            data[i][0] = m.gettitle();
            data[i][1] = m.getartist();
            data[i][2] = m.getyear();
            data[i][3] = m.getDownloads();
            data[i][4] = tagToString(m.getTags());
            i++;
        }
        System.out.println(FlipTableConverters.fromObjects(headers, data));
    }

    /**
     * Apresenta uma tabela ao utilizador com o conteudo de uma notificacao
     * @param notify : notificacao a apresentar
     */
    public static void prettyNotify(String notify){
        String[] parser = notify.split(";");
        String[] innerHeaders = { "Artista", "Título" };
        String[][] innerData = { { parser[0], parser[1] } };
        String inner = FlipTable.of(innerHeaders, innerData);
        String[] headers = { "- Notificação -" };
        String[][] data = { { inner } };
        System.out.println(FlipTable.of(headers, data));
    }

    /**
     * Limpa o terminal com um conjunto de linhaas em branco
     */
    public static void clearScreen(){
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
}
