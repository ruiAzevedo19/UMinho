package Controller;

import Model.*;
import View.*;

import java.util.*;

public class UtilizadorLogin extends Login {
    public UtilizadorLogin(Controller controller) {
        super(controller);
    }

    private void login() {
        View v = new DefaultView();

        v.show(Messages.askUsername);
        String user = Input.readString();

        v.show("\n" + Messages.askPassword);
        String password = Input.readString();

        boolean sucessLogin = super.validateLogin(user, password);

        if (sucessLogin && user.charAt(0) == 'u') {
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
        String input = "";
        Utilizador donoDaSessao = sgv.getSpecificUtilizador(this.getUsername());
        while (!input.equalsIgnoreCase("exit")) {
            v.show(Messages.askCommand);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "history":
                    historico(donoDaSessao);
                    break;
                case "accept order":
                    acceptOrNotDelivery(donoDaSessao);
                    break;
                case "exit":
                    v.show(Messages.logOff);
                    break;
                case "mo":
                case "make order":
                    this.makeOrder(donoDaSessao);
                    break;
                case "rate":
                    this.rateOrder(donoDaSessao);
                    break;
                case "cp":
                case "change password":
                    this.changePassword();
                    break;
                case "help":
                    v.show( "Sair da sessao: exit\n"
                            + "Ver historico: history\n"
                            + "Classificar um serviço de entrega: rate\n"
                            + "Fazer um pedido de encomenda: make order (mo)\n"
                            + "Aceitar um pedido de entrega: accept order\n"
                            + "Mudar a Palavra-passe: change password (cp)\n");
                    break;
                default:
                    v.show(Messages.invalidCommand);
                    break;
            }
        }
    }

    // mostrar as orders sem rating
    // 1. order1
    // 2. order2
    // 3. order3
    //
    // Escolher 1 2 ou 3
    // escolher uma nota 0-5
    private void rateOrder(Utilizador utilizador) {
        View v = new DefaultView();
        List<Encomenda> lista = utilizador.getEncomendasPorClassificar();

        if (lista.size() == 0) {
            v.show("Não tens encomendas por classificar.\n");
            return;
        }

        String listaVisual = "";
        int i = 0;
        for (Encomenda a : lista) {
            listaVisual += i++ + ". Encomenda " + a.getCod_Encomenda() + " "
                    + a.getDistancia() + "\n";
        }
        v.show(listaVisual);

        int choice = -1;
        while (choice < 0 || choice > i) {
            v.show("Escolha uma encomenda entre 0 - " + (i - 1) + ":\n");
            choice = Input.readInt();
        }

        String seleçao = lista.get(choice).getCod_Transporte();
        utilizador.getEncomendasClassificadas().add(lista.remove(i - 1));
        choice = 0;
        while (choice < 1 || choice > 5) {
            v.show("Quanto quer dar de classificaçao (1 - 5):");
            choice = Input.readInt();
        }

        if (seleçao.charAt(0) == 'v')
            sgv.getSpecificVoluntario(seleçao).review(choice);
        else if (seleçao.charAt(0) == 't')
            sgv.getSpecificTransportadora(seleçao).review(choice);

        v.show("Obrigado pelo seu feedback.\n");
    }

    private void acceptOrNotDelivery (Utilizador utilizador) {
        View v = new DefaultView();

        Map.Entry<Double , Encomenda> entrega = utilizador.getEncomendasDeTransportadoras().poll();
        if (entrega == null) {
            v.show("Não tens nenhuma proposta de entrega.\n");
            return;
        }
        Transportadora a = sgv.getSpecificTransportadora(entrega.getValue().getCod_Transporte());
        v.show(a.getNome());
        v.show("\nPreço:" + entrega.getKey() + "\nClassificação:" + a.getRating() + " estrelas em " + a.getNrReviews() + " reviews.\n");

        String opcao = "";
        while (opcao.equals("")) {
            v.show("Insira 'aceito' ou insira 'recuso':");
            opcao = Input.readString();
            switch (opcao) {
                case "aceito":
                    utilizador.getEncomendasPorClassificar().add(entrega.getValue());
                    utilizador.getHistorico().add(entrega.getValue());
                    a.setKmPercorridos(a.getKmPercorridos() + entrega.getKey());
                    Double tempo = entrega.getKey() * a.getVelocidade();
                    sgv.getSpecificLoja(entrega.getValue().getCod_Loja()).setPorEntregar(sgv.getSpecificLoja(entrega.getValue().getCod_Loja()).getPorEntregar() - 1);
                    tempo *= 60;
                    a.getEncomendasEntregadas().add(new AbstractMap.SimpleEntry<>(tempo , entrega.getValue()));
                    break;
                case "recuso":
                    Encomenda este = entrega.getValue();
                    redirecionamentoAutomatico(este);
                    break;
                default:
                    opcao = "";
                    break;
            }
        }
    }

    private void historico (Utilizador utilizador) {
        View v = new DefaultView();

        v.show("Historico de encomendas:\n");

        for (Encomenda e : utilizador.getHistorico()) {
            v.show("Encomenda " + e.getCod_Encomenda() + ": da loja " + e.getCod_Loja() + " pela transportadora " + e.getCod_Transporte() + "\n");
        }
    }

    private void makeOrder(Utilizador utilizador) {
        View v = new DefaultView();
        v.show("Lojas disponiveis:\n");
        for (Loja a : this.sgv.getLojas())
            System.out.println("Loja " + a.getCodigo() + "\nDominio : " + a.getNome() + "\nLocal- X: " + a.getLocal().getX() + " Y: " + a.getLocal().getY() + "\n");
        v.show("Escolha uma loja (ex: l8):");
        String loja = Input.readString();


        Loja comprasAqui = sgv.getSpecificLoja(loja);

        if (comprasAqui == null) {
            v.show("A loja não é valida.\n");
            return;
        }

        v.show("Faça a sua lista de compras:\n");
        for (LinhaEncomenda e : comprasAqui.getProdutosDisponiveis()) {
            System.out.println(e);
        }
        v.show("Para finalizar simplesmente não insira nenhum input.\n");
        String escolhas = "";
        Map<String , LinhaEncomenda> carrinho = new HashMap<>();
        while (escolhas.equals("")) {
            v.show("Escolha um produto pelo numero de produto (ex:p10):\n");
            escolhas = Input.readString();
            final String teste = escolhas;
            if (comprasAqui.getProdutosDisponiveis().stream().anyMatch(a -> a.getCod_Produto().equals(teste))) {
                v.show("Quantas unidades?");
                int quantidade = Input.readInt();
                carrinho.put(teste , comprasAqui.getProdutosDisponiveis().stream().filter(a -> a.getCod_Produto().equals(teste)).findFirst().get().clone());
                carrinho.get(teste).setQuant(quantidade);
                v.show("Adicionado!\n");
            }
            else if (!escolhas.equals("")) v.show("Produto não existe.\n");
        }
        Encomenda encomenda = new Encomenda( "e" + utilizador.getHistorico().size() , utilizador.getCodigo() , comprasAqui.getCodigo() , carrinho.values().stream().mapToDouble(a -> a.getQuant()).sum() , new ArrayList<>(carrinho.values()));

        comprasAqui.receivePedido(encomenda.clone());
        v.show("Pedido enviado.\nObrigado pela sua compra!\n");
    }
}
