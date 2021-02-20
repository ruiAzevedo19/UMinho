package Controller;

import Model.*;
import View.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class LojaLogin extends Login {
    public LojaLogin(Controller controller) {
        super(controller);
    }

    private void login() {
        View v = new DefaultView();

        v.show(Messages.askUsername);
        String user = Input.readString();

        v.show("\n" + Messages.askPassword);
        String password = Input.readString();

        boolean sucessLogin = super.validateLogin(user, password);

        if (sucessLogin && user.charAt(0) == 'l') {
            super.setUsername(user);
        }
    }

    @Override
    public void run() {
        View v = new DefaultView();
        this.login();
        if (this.getUsername() == null) {
            v.show(Messages.loginFailed);
            return;
        }
        v.show(Messages.sucessefulLogin);
        Loja donoDaSessao = sgv.getSpecificLoja(this.getUsername());
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            if (donoDaSessao.getPorEntregar() > 0) {
                v.show(donoDaSessao.getPorEntregar() + " encomendas ainda não foram entregues.\n");
            }
            v.show(Messages.askCommand);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "fila de espera":
                    filaDeEspera(donoDaSessao);
                    break;
                case "exit":
                    v.show(Messages.logOff);
                    break;
                case "cp":
                case "change password":
                    this.changePassword();
                    break;
                case "send to":
                    this.transportadorMaisProximo(donoDaSessao);
                    break;
                case "help":
                    v.show("Sair da sessao: exit\n"
                            + "Enviar automaticamente ao transporte que percorrerá uma menor distancia: send to\n"
                            + "Ver fila de clientes que ainda não foram atendidos: fila de espera\n"
                            + "Mudar a Palavra-passe: change password (cp)\n");
                    break;
                default:
                    v.show(Messages.invalidCommand);
                    break;
            }
        }
    }

    private void filaDeEspera (Loja loja) {
        View v = new DefaultView();
        v.show(loja.getEncomendaQueue().size() + " Clientes à espera!\n");

        for (Encomenda e : loja.getEncomendaQueue()) {
            v.show(e.getCod_Encomenda() + ": cliente " + e.getCod_Utilizador() + "\n");
        }
    }

    private void transportadorMaisProximo (Loja loja) {
        View v = new DefaultView();
        v.show("Este processo só precisa de ser ativado uma vez, depois essa encomenda vai percorrer todos os servicos que puder automaticamente.\n");
        Encomenda encomendaPrioritaria = loja.getEncomendaQueue().poll();

        if (encomendaPrioritaria == null) {
            v.show("Sem pedidos ainda.\n");
            return;
        }

        GPS gpsDaLoja = loja.getLocal();

        GPS localUtilizador = sgv.getSpecificUtilizador(encomendaPrioritaria.getCod_Utilizador()).getLocal();

        double distanciaEntreLojaEUtilizador = gpsDaLoja.calculaDistancia(localUtilizador);

        ArrayList<Map.Entry<Double , String>> transportes = new ArrayList<>();
        ArrayList<Map.Entry<Double , String>> transportes2 = new ArrayList<>();

        for (Transportadora a : sgv.getTransportadoras()) {
            Double distancia = a.getLocal().calculaDistancia(gpsDaLoja);

            if (distancia >= a.getRaio()) {
                transportes.add(new AbstractMap.SimpleEntry<>(distancia + distanciaEntreLojaEUtilizador , a.getCodigo()));
            }
        }
        for (Voluntario a : sgv.getVoluntarios()) {
            Double distancia = a.getLocal().calculaDistancia(gpsDaLoja);

            if (distancia >= a.getRaio()) {
                transportes2.add(new AbstractMap.SimpleEntry<>(distancia + distanciaEntreLojaEUtilizador , a.getCodigo()));
            }
        }

        transportes.sort(Comparator.comparingDouble(Map.Entry::getKey));
        transportes2.sort(Comparator.comparingDouble(Map.Entry::getKey));
        transportes.addAll(transportes2);


        Map.Entry<Double , Encomenda> entrega = new AbstractMap.SimpleEntry<Double , Encomenda>(transportes.get(0).getKey() , encomendaPrioritaria);

        for (Map.Entry<Double , String> a : transportes) {
            if (a == null)
                break;

            if (entrega.getValue().getCod_Transporte().equals("")) {
                v.show("Foi enviado para " + a.getValue() + "\n");
                if (a.getValue().charAt(0) == 'v') {
                    if (sgv.getSpecificVoluntario(a.getValue()).disponivel) {
                        sgv.getSpecificVoluntario(a.getValue()).enviaPedido(entrega);
                        loja.setPorEntregar(loja.getPorEntregar() + 1);
                        return;
                    } else {
                        v.show("Nao esta disponivel\n");
                    }
                } else {
                        Transportadora b = sgv.getSpecificTransportadora(a.getValue());
                        if (b.disponivel) {
                            entrega.getValue().setCod_Transporte(b.getCodigo());
                            loja.setPorEntregar(loja.getPorEntregar() + 1);
                            b.enviaPedido(entrega);
                            return;
                        } else {
                            v.show("Nao esta disponivel\n");
                        }
                    }
                break;
            }

            if (entrega.getValue().getCod_Transporte().equals(a.getValue()))
                entrega.getValue().setCod_Transporte("");
        }
        v.show("Afinal nao existe nenhum transporte disponivel neste momento para entregar esta encomenda\nRepondo encomenda na fila de espera.\n");
        loja.getEncomendaQueue().add(encomendaPrioritaria);
    }
}
