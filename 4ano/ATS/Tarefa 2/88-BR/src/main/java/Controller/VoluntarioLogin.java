package Controller;

import Model.*;
import View.*;

import java.util.*;

public class VoluntarioLogin extends Login {
    public VoluntarioLogin(Controller controller) {
        super(controller);
    }

    private void login() {
        View v = new DefaultView();

        v.show(Messages.askUsername);
        String user = Input.readString();

        v.show("\n" + Messages.askPassword);
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
            v.show(Messages.loginFailed);
            return;
        }
        Voluntario donoDaSessao = sgv.getSpecificVoluntario(this.getUsername());
        v.show(Messages.sucessefulLogin);
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            v.show(Messages.askCommand);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "check":
                    this.rateReview(donoDaSessao);
                    break;
                case "sd0":
                case "set disponibilidade 0":
                    this.getChanges().firePropertyChange("voluntario_set_disponibilidade", true, false);
                    donoDaSessao.disponivel = false;
                    break;
                case "sd1":
                case "set disponibilidade 1":
                    this.getChanges().firePropertyChange("voluntario_set_disponibilidade", false, true);
                    donoDaSessao.disponivel = true;
                    break;
                case "exit":
                    v.show(Messages.logOff);
                    break;
                case "accept order":
                    this.acceptOrder(donoDaSessao);
                    break;
                case "cp":
                case "change password":
                    this.changePassword();
                    break;
                case "help":
                    v.show("Para mudar disponibilidade: set disponibilidade x (sdx) sendo x: 1 (disponivel) ou 0 (nao disponivel)\n"
                            + "Sair da sessao: exit\n"
                            + "Aceitar o pedido mais pendente: accept order\n"
                            + "Verificar a tua classificação: check\n"
                            + "Mudar a Palavra-passe: change password (cp)\n");
                    break;
                default:
                    v.show(Messages.invalidCommand);
                    break;
            }
        }
    }

    // Lista todas as encomendas disponiveis e da a opçao de aceitar
    // 1. encomenda xyz, distancia, money money
    // 2. encomenda xyz, distancia, money money
    // 3. encomenda xyz, distancia, money money
    // 4. encomenda xyz, distancia, money money
    // input 4 => aceitar a encomenda 4
    private void acceptOrder(Voluntario voluntario) {
        View v = new DefaultView();

        Map.Entry<Double , Encomenda> seleçao = voluntario.getEncomendasNaQueue().poll();

        if (seleçao == null) {
            v.show("Nao tens encomendas para entregar.\n");
            return;
        }

        v.show("Encomenda " + seleçao.getValue().getCod_Encomenda() + ":\nDistancia: " + seleçao.getKey() + "\nPeso: " + seleçao.getValue().getPeso() + "\n");
        v.show("Quer recusar ou aceitar?");
        String action = Input.readString();
        switch (action) {
            case "recusar":
                seleçao.getValue().setCod_Transporte(voluntario.getCodigo());
                this.redirecionamentoAutomatico(seleçao.getValue());
                break;
            case "aceitar":
                seleçao.getValue().setCod_Transporte(voluntario.getCodigo());
                Double tempo = seleçao.getKey() / voluntario.getVelocidade();
                tempo *= 60;
                voluntario.getEncomendasEntregadas().add(new AbstractMap.SimpleEntry<>(tempo , seleçao.getValue()));
                Loja loja = sgv.getSpecificLoja(seleçao.getValue().getCod_Loja());
                loja.setPorEntregar(loja.getPorEntregar() - 1);
                break;
        }
    }

    private void rateReview(Voluntario voluntario) {
        View v = new DefaultView();
        String rating = "A tua classificação é de " + voluntario.getRating() + " em 5 estrelas.\n";
        rating += "Foste classificado por " + voluntario.getNrReviews() + " utilizadores.\n";

        v.show(rating);
    }
}
