package controller;

import model.*;
import view.*;

import java.util.*;

public class VoluntarioLogin extends Login {
    public VoluntarioLogin(Controller controller) {
        super(controller);
    }

    private void login() {
        View v = new DefaultView();

        showView(v, Messages.ASKUSERNAME);
        String user = Input.readString();

        showView(v, "\n" + Messages.ASKPASSWORD);
        String password = Input.readString();

        boolean sucessLogin = super.validateLogin(user, password);

        if (sucessLogin && user.charAt(0) == 'v') {
            super.setUsername(user);
        }
    }

    @Override
    public void run() {
        View v = new DefaultView();
        this.login();
        if (this.getUsername() == null) {
            showView(v, Messages.LOGINFAILED);
            return;
        }
        Voluntario donoDaSessao = sgv.getSpecificVoluntario(this.getUsername());
        showView(v, Messages.SUCESSEFULLOGIN);
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            showView(v, Messages.ASKCOMMAND);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "check":
                    this.rateReview(donoDaSessao);
                    break;
                case "sd0":
                case "set disponibilidade 0":
                    this.getChanges().firePropertyChange("voluntario_set_disponibilidade", true, false);
                    donoDaSessao.setDisponivel(false);
                    break;
                case "sd1":
                case "set disponibilidade 1":
                    this.getChanges().firePropertyChange("voluntario_set_disponibilidade", false, true);
                    donoDaSessao.setDisponivel(true);
                    break;
                case "exit":
                    showView(v, Messages.LOGOFF);
                    break;
                case "accept order":
                    this.acceptOrder(donoDaSessao);
                    break;
                case "cp":
                case "change password":
                    this.changePassword();
                    break;
                case "help":
                    showView(v, "Para mudar disponibilidade: set disponibilidade x (sdx) sendo x: 1 (disponivel) ou 0 (nao disponivel)\n"
                            + "Sair da sessao: exit\n"
                            + "Aceitar o pedido mais pendente: accept order\n"
                            + "Verificar a tua classificação: check\n"
                            + "Mudar a Palavra-passe: change password (cp)\n");
                    break;
                default:
                    showView(v, Messages.INVALIDCOMMAND);
                    break;
            }
        }
    }

    private void showView(View v, String sucessefullogin) {
        v.show(sucessefullogin);
    }

    // Lista todas as encomendas disponiveis e da a opçao de aceitar
    // 1. encomenda xyz, distancia, money money
    // 2. encomenda xyz, distancia, money money
    // 3. encomenda xyz, distancia, money money
    // 4. encomenda xyz, distancia, money money
    // input 4 => aceitar a encomenda 4
    private void acceptOrder(Voluntario voluntario) {
        View v = new DefaultView();

        Map.Entry<Double , Encomenda> selecao = voluntario.getEncomendasNaQueue().poll();

        if (selecao == null) {
            showView(v, "Nao tens encomendas para entregar.\n");
            return;
        }

        v.show("Encomenda " + selecao.getValue().getCodEncomenda() + ":\nDistancia: " + selecao.getKey() + "\nPeso: " + selecao.getValue().getPeso() + "\n");
        showView(v, "Quer recusar ou aceitar?");
        String action = Input.readString();
        if ("recusar".equals(action)) {
            selecao.getValue().setCodTransporte(voluntario.getCodigo());
            this.redirecionamentoAutomatico(selecao.getValue());
        } else if ("aceitar".equals(action)) {
            selecao.getValue().setCodTransporte(voluntario.getCodigo());
            Double tempo = selecao.getKey() / voluntario.getVelocidade();
            tempo *= 60;
            voluntario.getEncomendasEntregadas().add(new AbstractMap.SimpleEntry<>(tempo, selecao.getValue()));
            Loja loja = sgv.getSpecificLoja(selecao.getValue().getCodLoja());
            loja.setPorEntregar(loja.getPorEntregar() - 1);
        }
    }

    private void rateReview(Voluntario voluntario) {
        View v = new DefaultView();
        String rating = "A tua classificação é de " + voluntario.getRating() + " em 5 estrelas.\n";
        rating += "Foste classificado por " + voluntario.getNrReviews() + " utilizadores.\n";

        showView(v, rating);
    }
}
