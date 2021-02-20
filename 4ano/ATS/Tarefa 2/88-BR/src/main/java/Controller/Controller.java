package Controller;

import Model.*;
import View.DefaultView;
import View.View;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Controller implements IController, Serializable {
    SistemaGestaoVendas sgv;
    LoginDatabase loginDatabase;

    public Controller(int mode) throws Exception {
        // Default mode
        if (mode == 0) {
            this.sgv = SistemaGestaoVendas.fromLogFile("logs/logs_20200416.txt");
        } else {
            throw new Exception("Invalid mode");
        }
        this.loginDatabase = new LoginDatabase();
        Set<String> allUsernames = new HashSet<>();
        this.sgv.getUtilizadores().stream()
                .map(Utilizador::getCodigo)
                .forEach(allUsernames::add);

        this.sgv.getVoluntarios().stream()
                .map(Voluntario::getCodigo)
                .forEach(allUsernames::add);

        this.sgv.getTransportadoras().stream()
                .map(Transportadora::getCodigo)
                .forEach(allUsernames::add);

        this.sgv.getLojas().stream()
                .map(Loja::getCodigo)
                .forEach(allUsernames::add);

        for (String username : allUsernames) {
            this.loginDatabase.addLogin(username, "123");
        }
    }

    public Controller(Controller controller) {
        this.sgv = controller.sgv;
        this.loginDatabase = controller.loginDatabase;
    }

    public void run() {
        String input = "";
        View v = new DefaultView();
        v.show(Messages.welcome);

        while (!input.equalsIgnoreCase("quit")) {
            v.show(Messages.askCommand);
            input = Input.readString();

            switch (input.toLowerCase()) {
                case "help":
                    v.show(Messages.help);
                    break;
                case "quit":
                    v.show(Messages.quit);
                    break;
                case "print":
                    v.show(sgv.toString());
                    break;
                case "login":
                    this.login();
                    break;
                case "save":
                    this.save();
                    break;
                case "load":
                    this.load();
                    break;
                case "print -ta":
                    v.show(this.sgv.getTransportadorasDisponiveis().toString());
                    break;
                case "print -d":
                case "print database":
                    v.show("Isto serve para debugging apenas, num programa a serio nao existiria");
                    v.show(this.loginDatabase.toString());
                    break;
                case "registar":
                    this.registar();
                default:
                    v.show(Messages.invalidCommand);
                    break;
            }
        }
    }


    private void save() {
        LocalDateTime tempo = LocalDateTime.now();
        String nome = tempo.truncatedTo(ChronoUnit.SECONDS).toString().replace(":", "-").concat(".dat");
        String path = "logs/" + nome;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            new DefaultView().show("Saved correctly with filename: " + nome);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void load() {
        View v = new DefaultView();
        v.show("Introduza o nome do ficheiro (tem de acabar em .dat): ");
        String nome = Input.readString();
        String path = "logs/" + nome;

        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Controller novo = (Controller) ois.readObject();
            this.loginDatabase = novo.loginDatabase;
            this.sgv = novo.sgv;
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void registar() {
        View v = new DefaultView();
        v.show(Messages.loginTypePrompt);

        String input = Input.readString();
        int valor = Integer.parseInt(input);

        if (valor < 0 || valor > 3) {
            v.show(Messages.invalidCommand);
            v.show(Messages.backToMenu);
        } else {
            char prefixo = 'u';
            switch (valor) {
                case 0:
                    prefixo = 'u';
                    break;
                case 1:
                    prefixo = 'v';
                    v.show(Messages.backToMenu);
                    break;
                case 2:
                    prefixo = 't';
                    v.show(Messages.backToMenu);
                    break;
                case 3:
                    prefixo = 'l';
                    break;
            }
            v.show("O username precisa de ter o prefixo '" + prefixo + "'");
            v.show("Introduza o seu nome de utilizador: ");
            String username = Input.readString();
            if (username.length() <= 1) {
                v.show("Username is too small");
            } else {
                if (username.charAt(0) != prefixo) {
                    v.show("Username nao tem o prefixo correto");
                } else {
                    if (this.loginDatabase.containsUser(username)) {
                        v.show("Ja existe um utilizador com esse username");
                    } else {
                        v.show(Messages.newPassword);
                        String pw1 = Input.readString();
                        v.show(Messages.newPasswordConfirmation);
                        String pw2 = Input.readString();
                        if (pw1.equals(pw2))
                            this.newRegisto(username, pw1, prefixo);
                        else
                            v.show(Messages.passwordConfirmationInvalid);
                    }
                }
            }
        }
    }

    private void newRegisto(String username, String pw1, char prefixo) {
        this.loginDatabase.addLogin(username, pw1);
        switch (prefixo) {
            case 'u':
                // this.sgv.addUtilizador(username);
                break;
            case 'v':
                // this.sgv.addVoluntario(username);
                break;
            case 't':
                // this.sgv.addTransportadora(username);
                break;
            case 'l':
                // this.sgv.addLoja(username);
                break;
        }
    }

    private void login() {
        View v = new DefaultView();
        v.show(Messages.loginTypePrompt);

        String input = Input.readString();
        int valor = Integer.parseInt(input);

        if (valor < 0 || valor > 3) {
            v.show(Messages.invalidCommand);
            v.show(Messages.backToMenu);
        } else {
            switch (valor) {
                case 0:
                    new UtilizadorLogin(this).run();
                    v.show(Messages.backToMenu);
                    break;
                case 1:
                    new VoluntarioLogin(this).run();
                    v.show(Messages.backToMenu);
                    break;
                case 2:
                    new TransportadoraLogin(this).run();
                    v.show(Messages.backToMenu);
                    break;
                case 3:
                    new LojaLogin(this).run();
                    v.show(Messages.backToMenu);
                    break;
            }
        }
    }

    protected boolean validateLogin(String user, String password) {
        return this.loginDatabase.validateLogin(user, password);
    }

    public void redirecionamentoAutomatico (Encomenda encomenda) {
        View v = new DefaultView();
        v.show("Reencaminhamento automatico\n");

        Loja loja = sgv.getSpecificLoja(encomenda.getCod_Loja());

        GPS gpsDaLoja = loja.getLocal();

        GPS localUtilizador = sgv.getSpecificUtilizador(encomenda.getCod_Utilizador()).getLocal();

        double distanciaEntreLojaEUtilizador = gpsDaLoja.calculaDistancia(localUtilizador);

        ArrayList<Map.Entry<Double , String>> transportes = new ArrayList<>();
        ArrayList<Map.Entry<Double , String>> transportes2 = new ArrayList<>();
        for (Transportadora a : sgv.getTransportadoras()) {
            Double distancia = a.getLocal().calculaDistancia(gpsDaLoja);

            if (distancia >= a.getRaio())
                transportes.add(new AbstractMap.SimpleEntry<>(distancia + distanciaEntreLojaEUtilizador , a.getNome()));
        }
        for (Voluntario a : sgv.getVoluntarios()) {
            Double distancia = a.getLocal().calculaDistancia(gpsDaLoja);

            if (distancia >= a.getRaio())
                transportes2.add(new AbstractMap.SimpleEntry<>(distancia + distanciaEntreLojaEUtilizador , a.getNome()));
        }

        transportes.sort(Comparator.comparingDouble(Map.Entry::getKey));
        transportes2.sort(Comparator.comparingDouble(Map.Entry::getKey));
        transportes.addAll(transportes2);

        Map.Entry<Double , Encomenda> entrega = new AbstractMap.SimpleEntry<Double , Encomenda>(transportes.get(0).getKey() , encomenda);

        for (Map.Entry<Double , String> a : transportes) {
            if (a == null)
                break;

            if (entrega.getValue().getCod_Transporte().equals("")) {
                v.show("Enviando para " + a.getValue() + "\n");
                if (a.getValue().contains("v")) {
                    if (sgv.getSpecificVoluntario(a.getValue()).disponivel) {
                        sgv.getSpecificVoluntario(a.getValue()).enviaPedido(entrega);
                        return;
                    } else {
                        v.show("Nao esta disponivel\n");
                    }
                }else {
                    if (sgv.getSpecificTransportadora(a.getValue()).disponivel) {
                        sgv.getSpecificTransportadora(a.getValue()).enviaPedido(entrega);
                        return;
                    } else {
                        v.show("Nao esta disponivel\n");
                    }
                }break;
            }

            if (entrega.getValue().getCod_Transporte().equals(a.getValue()))
                entrega.getValue().setCod_Transporte("");
        }
        v.show("Afinal nao existe nenhum transporte disponivel neste momento para entregar esta encomenda\nRepondo encomenda na fila de espera da loja dond veio.\n");
        loja.getEncomendaQueue().add(entrega.getValue());
        loja.setPorEntregar(loja.getPorEntregar() - 1);
    }

    protected void setLogin(String username, String password) {
        this.loginDatabase.setLogin(username, password);
    }
}
