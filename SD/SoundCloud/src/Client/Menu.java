package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Exception.*;
import Model.Music;
import Utilities.Utility;

import javax.swing.*;

public class Menu {

    private SoundCloud_Stub stub;
    private JFileChooser fileChooser;
    private String username;
    private BufferedReader bufferedReader;
    
    public Menu(SoundCloud_Stub stub){
        this.stub = stub;
        this.fileChooser = new JFileChooser();
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void firstMenu() throws IOException {
        int option = -1;
        while(option != 3){
            System.out.println("|----------------|------------------------------|");
            System.out.println("|  Menu Inicial  |                              |");
            System.out.println("|----------------|                              |");
            System.out.println("|                                               |");
            System.out.println("|     [ 1 ] - Autenticação                      |");
            System.out.println("|     [ 2 ] - Registo                           |");
            System.out.println("|     [ 3 ] - Fechar                            |");
            System.out.println("|                                               |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("$ Opção : ");
            try {
                option = Integer.valueOf(this.bufferedReader.readLine());
            }catch (NumberFormatException n){
                Utility.clearScreen();
                System.out.println("Formato inválido!");
                firstMenu();
                break;
            }
            if(option == 1 || option == 2){
                System.out.println("Nome de utilizador:");
                String username = this.bufferedReader.readLine();
                System.out.println("Password:");
                String password = this.bufferedReader.readLine();
                if(option == 1){
                    try {
                        this.stub.authentication(username, password);
                        this.username = username;
                        Utility.clearScreen();
                        System.out.println("Autenticação completa!");
                    } catch (InvalidLoginException e) {
                        Utility.clearScreen();
                        System.out.println("Dados Inválidos!");
                    }
                } else {
                    try {
                        this.stub.registration(username, password);
                        this.username = username;
                        Utility.clearScreen();
                        System.out.println("Utilizador criado com sucesso!");
                    } catch (AlreadyRegistedException e) {
                        Utility.clearScreen();
                        System.out.println("Username já existente!");
                    }
                }
                mainMenu();
                option = 3;
            } else if (option != 3) {
                Utility.clearScreen();
            }
        }
        Utility.clearScreen();
        this.stub.logout();
    }

    private void mainMenu() throws IOException {
        int option = -1;

        /* Create notification socket */
        Socket socketNotify = new Socket(InetAddress.getLocalHost(), 12345);
        /* Warn server about it */
        PrintWriter outNotify = new PrintWriter(socketNotify.getOutputStream());
        outNotify.println("N;" + this.username);
        outNotify.flush();
        /* Create notification thread */
        Thread notifications = new Thread(new NotifyWorker(socketNotify));
        notifications.start();

        while(option != 4){
            System.out.println("|------------------|----------------------------|");
            System.out.println("|  Menu Principal  |                            |");
            System.out.println("|------------------|                            |");
            System.out.println("|                                               |");
            System.out.println("|     [ 1 ] - Procurar música                   |");
            System.out.println("|     [ 2 ] - Download                          |");
            System.out.println("|     [ 3 ] - Upload                            |");
            System.out.println("|     [ 4 ] - Logout                            |");
            System.out.println("|                                               |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("$ Opção : ");
            try {
                option = Integer.valueOf(this.bufferedReader.readLine());
            }catch (NumberFormatException n){
                option = -1;
            }
            switch (option){
                case 1: {
                    searchMenu();
                    break;
                }
                case 2: {
                    try {
                        System.out.println("Id da música:");
                        int id = Integer.valueOf(this.bufferedReader.readLine());
                        Music m = this.stub.download(id);
                        ArrayList<Music> music = new ArrayList<>();
                        music.add(m);
                        Utility.prettyTable(music);
                        System.out.println("Download feito com sucesso!");
                        break;
                    } catch (InvalidIDException invalidIDException) {
                        Utility.clearScreen();
                        System.out.println(invalidIDException.getMessage());
                        break;
                    } catch (NumberFormatException n){
                        Utility.clearScreen();
                        System.out.println("Formato inválido!");
                        break;
                    }
                }
                case 3: {
                    System.out.println("Nome:");
                    String nome = this.bufferedReader.readLine();
                    System.out.println("Artista:");
                    String artista = this.bufferedReader.readLine();
                    System.out.println("Ano:");
                    int ano = Integer.valueOf(this.bufferedReader.readLine()), downloads = 0, id;
                    File file = null;
                    int result = this.fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        file = this.fileChooser.getSelectedFile();
                    }
                    if(file != null) {
                        Music music = new Music(nome, artista, ano, downloads);
                        music.setPath(file.getAbsolutePath());
                        System.out.println("Tags (separadas por ;)");
                        String[] tags = this.bufferedReader.readLine().split(";");
                        for (String tag : tags) music.addTag(tag);
                        id = this.stub.upload(music);
                        System.out.println("Upload feito com sucesso! O id atribuido foi " + id + ".");
                    } else System.out.println("Upload cancelado.");
                    break;
                }
                case 4: {
                    try {
                        this.stub.logout();
                        notifications.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        socketNotify.shutdownInput();
                        socketNotify.shutdownOutput();
                        socketNotify.close();
                    }
                    break;
                }
                default: {
                    Utility.clearScreen();
                    break;
                }
            }
        }
    }

    private void searchMenu() throws IOException {
        int option = -1;
        while(option != 5){
            System.out.println("|------------|----------------------------------|");
            System.out.println("|  Procurar  |                                  |");
            System.out.println("|------------|                                  |");
            System.out.println("|                                               |");
            System.out.println("|     Procura por :                             |");
            System.out.println("|     [ 1 ] - Identificador                     |");
            System.out.println("|     [ 2 ] - Nome                              |");
            System.out.println("|     [ 3 ] - Artista                           |");
            System.out.println("|     [ 4 ] - Tags                              |");
            System.out.println("|     [ 5 ] - Voltar                            |");
            System.out.println("|                                               |");
            System.out.println("|-----------------------------------------------|");
            System.out.println("$ Opção : ");
            option = Integer.valueOf(this.bufferedReader.readLine());

            List<Music> musics;
            String[] args;
            switch (option){
                case 1: {
                    System.out.println("ID:");
                    try {
                        /* read id */
                        args = new String[1];
                        args[0] = this.bufferedReader.readLine();
                        musics = this.stub.consult("identificador", args);
                        Utility.clearScreen();
                        Utility.prettyTable(musics);
                    }catch (NumberFormatException n){
                        Utility.clearScreen();
                        System.out.println("Formato Inválido!");
                        option = -1;
                    }
                    break;
                }
                case 2: {
                    args = new String[1];
                    System.out.println("Nome:");
                    args[0] = this.bufferedReader.readLine();
                    musics = this.stub.consult("nome", args);
                    Utility.clearScreen();
                    Utility.prettyTable(musics);
                    break;
                }
                case 3: {
                    args = new String[1];
                    System.out.println("Artista:");
                    args[0] = this.bufferedReader.readLine();
                    musics = this.stub.consult("artista", args);
                    Utility.clearScreen();
                    Utility.prettyTable(musics);
                    break;
                }
                case 4: {
                    System.out.println("Tags (separadas por ;):");
                    args = Arrays.stream(this.bufferedReader.readLine().split(";")).toArray(String[]::new);
                    musics = this.stub.consult("tags", args);
                    Utility.clearScreen();
                    Utility.prettyTable(musics);
                    break;
                }
                case 5: Utility.clearScreen();
                        break;
                default: {
                    option = -1;
                    Utility.clearScreen();
                    break;
                }
            }
        }
    }
}
