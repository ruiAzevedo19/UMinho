package controller;

import model.*;
import view.*;

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

        viewShow(v, Messages.ASKUSERNAME);
        String user = Input.readString();

        v.show("\n" + Messages.ASKPASSWORD);
        String password = Input.readString();

        boolean sucessLogin = super.validateLogin(user, password);

        if (sucessLogin && user.charAt(0) == 'l') {
            super.setUsername(user);
        }
    }

    private void viewShow(View v, String askusername) {
        v.show(askusername);
    }

    @Override
    public void run() {
        View v = new DefaultView();
        this.login();
        if (this.getUsername() == null) {
            v.show(Messages.LOGINFAILED);
            return;
        }
        v.show(Messages.SUCESSEFULLOGIN);
        Loja donoDaSessao = sgv.getSpecificLoja(this.getUsername());
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            if (donoDaSessao.getPorEntregar() > 0) {
                v.show(donoDaSessao.getPorEntregar() + " encomendas ainda não foram entregues.\n");
            }
            v.show(Messages.ASKCOMMAND);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "fila de espera":
                    filaDeEspera(donoDaSessao);
                    break;
                case "exit":
                    v.show(Messages.LOGOFF);
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
                    v.show(Messages.INVALIDCOMMAND);
                    break;
            }
        }
    }

    private void filaDeEspera (Loja loja) {
        View v = new DefaultView();
        v.show(loja.getEncomendaQueue().size() + " Clientes à espera!\n");

        for (Encomenda e : loja.getEncomendaQueue()) {
            v.show(e.getCodEncomenda() + ": cliente " + e.getCodUtilizador() + "\n");
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

        GPS localUtilizador = sgv.getSpecificUtilizador(encomendaPrioritaria.getCodUtilizador()).getLocal();

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


        Map.Entry<Double , Encomenda> entrega = new AbstractMap.SimpleEntry<>(transportes.get(0).getKey() , encomendaPrioritaria);

        for (int i = 0, transportesSize = transportes.size(); i < transportesSize; i++) {
            Map.Entry<Double, String> a = transportes.get(i);
            if (a != null) {
                if (entrega.getValue().getCodTransporte().equals("")) {
                    v.show("Foi enviado para " + a.getValue() + "\n");
                    if (a.getValue().charAt(0) == 'v') {
                        if (sgv.getSpecificVoluntario(a.getValue()).isDisponivel()) {
                            sgv.getSpecificVoluntario(a.getValue()).enviaPedido(entrega);
                            loja.setPorEntregar(loja.getPorEntregar() + 1);
                            return;
                        } else {
                            v.show("Nao esta disponivel\n");
                        }
                    } else {
                        Transportadora b = sgv.getSpecificTransportadora(a.getValue());
                        if (b.isDisponivel()) {
                            entrega.getValue().setCodTransporte(b.getCodigo());
                            loja.setPorEntregar(loja.getPorEntregar() + 1);
                            b.enviaPedido(entrega);
                            return;
                        } else {
                            v.show("Nao esta disponivel\n");
                        }
                    }
                    break;
                }

                if (entrega.getValue().getCodTransporte().equals(a.getValue()))
                    entrega.getValue().setCodTransporte("");
            } else {
                break;
            }

        }
        v.show("Afinal nao existe nenhum transporte disponivel neste momento para entregar esta encomenda\nRepondo encomenda na fila de espera.\n");
        loja.getEncomendaQueue().add(encomendaPrioritaria);
    }
}
