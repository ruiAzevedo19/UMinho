package controller;

import model.*;
import view.*;

import java.util.*;

public class UtilizadorLogin extends Login {
    public UtilizadorLogin(Controller controller) {
        super(controller);
    }

    private void login() {
        View v = new DefaultView();

        viewShow(v, Messages.ASKUSERNAME);
        String user = Input.readString();

        viewShow(v, "\n" + Messages.ASKPASSWORD);
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
            viewShow(v, Messages.LOGINFAILED);
            return;
        }
        viewShow(v, Messages.SUCESSEFULLOGIN);
        String input = "";
        Utilizador donoDaSessao = sgv.getSpecificUtilizador(this.getUsername());
        while (!input.equalsIgnoreCase("exit")) {
            viewShow(v, Messages.ASKCOMMAND);
            input = Input.readString();
            switch (input.toLowerCase()) {
                case "history":
                    historico(donoDaSessao);
                    break;
                case "accept order":
                    acceptOrNotDelivery(donoDaSessao);
                    break;
                case "exit":
                    viewShow(v, Messages.LOGOFF);
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
                    viewShow(v, "Sair da sessao: exit\n"
                            + "Ver historico: history\n"
                            + "Classificar um serviço de entrega: rate\n"
                            + "Fazer um pedido de encomenda: make order (mo)\n"
                            + "Aceitar um pedido de entrega: accept order\n"
                            + "Mudar a Palavra-passe: change password (cp)\n");
                    break;
                default:
                    viewShow(v, Messages.INVALIDCOMMAND);
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

        if (lista.isEmpty()) {
            viewShow(v, "Não tens encomendas por classificar.\n");
            return;
        }

        StringBuilder listaVisual = new StringBuilder();
        int i = 0;
        for (Encomenda a : lista) {
            listaVisual.append(new StringBuilder().append(i++).append(". Encomenda ").append(a.getCodEncomenda()).append(" ").append(a.getDistancia()).append("\n").toString());
        }
        viewShow(v, listaVisual.toString());

        int choice = -1;
        while (choice < 0 || choice > i) {
            v.show("Escolha uma encomenda entre 0 - " + (i - 1) + ":\n");
            choice = Input.readInt();
        }

        String selecao = lista.get(choice).getCodTransporte();
        utilizador.getEncomendasClassificadas().add(lista.remove(i - 1));
        choice = 0;
        while (choice < 1 || choice > 5) {
            viewShow(v, "Quanto quer dar de classificaçao (1 - 5):");
            choice = Input.readInt();
        }

        if (selecao.charAt(0) == 'v')
            sgv.getSpecificVoluntario(selecao).review(choice);
        else if (selecao.charAt(0) == 't')
            sgv.getSpecificTransportadora(selecao).review(choice);

        viewShow(v, "Obrigado pelo seu feedback.\n");
    }

    private void viewShow(View v, String s) {
        v.show(s);
    }

    private void acceptOrNotDelivery (Utilizador utilizador) {
        View v = new DefaultView();

        Map.Entry<Double , Encomenda> entrega = utilizador.getEncomendasDeTransportadoras().poll();
        if (entrega == null) {
            viewShow(v, "Não tens nenhuma proposta de entrega.\n");
            return;
        }
        Transportadora a = sgv.getSpecificTransportadora(entrega.getValue().getCodTransporte());
        viewShow(v, a.getNome());
        v.show("\nPreço:" + entrega.getKey() + "\nClassificação:" + a.getRating() + " estrelas em " + a.getNrReviews() + " reviews.\n");

        String opcao = "";
        while (opcao.equals("")) {
            viewShow(v, "Insira 'aceito' ou insira 'recuso':");
            opcao = Input.readString();
            switch (opcao) {
                case "aceito":
                    utilizador.getEncomendasPorClassificar().add(entrega.getValue());
                    utilizador.getHistorico().add(entrega.getValue());
                    a.setKmPercorridos(a.getKmPercorridos() + entrega.getKey());
                    Double tempo = entrega.getKey() * a.getVelocidade();
                    sgv.getSpecificLoja(entrega.getValue().getCodLoja()).setPorEntregar(sgv.getSpecificLoja(entrega.getValue().getCodLoja()).getPorEntregar() - 1);
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

        viewShow(v, "Historico de encomendas:\n");

        for (Encomenda e : utilizador.getHistorico()) {
            v.show("Encomenda " + e.getCodEncomenda() + ": da loja " + e.getCodLoja() + " pela transportadora " + e.getCodTransporte() + "\n");
        }
    }

    private void makeOrder(Utilizador utilizador) {
        View v = new DefaultView();
        viewShow(v, "Lojas disponiveis:\n");
        for (Loja a : this.sgv.getLojas())
            System.out.println("Loja " + a.getCodigo() + "\nDominio : " + a.getNome() + "\nLocal- X: " + a.getLocal().getX() + " Y: " + a.getLocal().getY() + "\n");
        viewShow(v, "Escolha uma loja (ex: l8):");
        String loja = Input.readString();


        Loja comprasAqui = sgv.getSpecificLoja(loja);

        if (comprasAqui == null) {
            viewShow(v, "A loja não é valida.\n");
            return;
        }

        viewShow(v, "Faça a sua lista de compras:\n");
        for (LinhaEncomenda e : comprasAqui.getProdutosDisponiveis()) {
            System.out.println(e);
        }
        viewShow(v, "Para finalizar simplesmente não insira nenhum input.\n");
        String escolhas = "";
        Map<String , LinhaEncomenda> carrinho = new HashMap<>();
        while (escolhas.equals("")) {
            viewShow(v, "Escolha um produto pelo numero de produto (ex:p10):\n");
            escolhas = Input.readString();
            final String teste = escolhas;
            if (comprasAqui.getProdutosDisponiveis().stream().anyMatch(a -> a.getCodProduto().equals(teste))) {
                viewShow(v, "Quantas unidades?");
                int quantidade = Input.readInt();


                Optional<LinhaEncomenda> l =comprasAqui.getProdutosDisponiveis().stream().filter(a -> a.getCodProduto().equals(teste)).findFirst();


                if(l.isPresent()) {
                    carrinho.put(teste, l.get());
                    carrinho.get(teste).setQuant(quantidade);
                    viewShow(v, "Adicionado!\n");
                }
            }
            else if (!escolhas.equals("")) viewShow(v, "Produto não existe.\n");
        }
        double sum = 0.0;
        for (LinhaEncomenda a : carrinho.values()) {
            double quant = a.getQuant();
            sum += quant;
        }
        Encomenda encomenda = new Encomenda( "e" + utilizador.getHistorico().size() , utilizador.getCodigo() , comprasAqui.getCodigo() , sum, new ArrayList<>(carrinho.values()));

        comprasAqui.receivePedido(encomenda.clone());
        viewShow(v, "Pedido enviado.\nObrigado pela sua compra!\n");
    }
}
