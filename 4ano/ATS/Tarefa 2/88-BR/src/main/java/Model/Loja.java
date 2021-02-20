package Model;

import java.io.Serializable;
import java.util.*;

public class Loja implements Comparable<Loja>, Serializable {
    private String codigo;
    private String nome;
    private GPS    local;
    private Set<LinhaEncomenda> produtosDisponiveis;
    private Queue<Encomenda> encomendaQueue;
    private int porEntregar = 0;

    public int getPorEntregar() {
        return porEntregar;
    }

    public void setPorEntregar(int porEntregar) {
        this.porEntregar = porEntregar;
    }

    public Queue<Encomenda> getEncomendaQueue() {
        return encomendaQueue;
    }

    public void receivePedido (Encomenda encomenda) {
        this.encomendaQueue.add(encomenda);
    }

    public Loja() {
        this.codigo = "Invalid codigo";
        this.nome = "Invalid nome";
        this.local = new GPS();
        this.encomendaQueue = new ArrayDeque<>();
    }

    public Loja(String codigo, String nome, GPS local) {
        this.codigo = codigo;
        this.nome = nome;
        this.local = local.clone();
        this.encomendaQueue = new ArrayDeque<>();
    }

    public Loja(String codigo, String nome, GPS local, Set<LinhaEncomenda> produtosDisponiveis) {
        this.codigo = codigo;
        this.nome = nome;
        this.local = local.clone();
        this.produtosDisponiveis = produtosDisponiveis;
        this.encomendaQueue = new PriorityQueue<>();
    }

    public Loja(Loja outro) {
        this.codigo = outro.getCodigo();
        this.nome = outro.getNome();
        this.local = outro.getLocal().clone();
    }

    public Loja fromArgs(String[] args) {
        if (args.length != 4)
            return null;
        String codigo = args[0].split(":")[1];
        String nome   = args[1];
        GPS    local  = new GPS(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        return new Loja(codigo, nome, local, this.DEFAULT_produtosDisponiveis());
    }

    private double randomPrice() {
        Random r = new Random();
        return 0.5 + 49.5 * r.nextDouble();
    }

    private Set<LinhaEncomenda> DEFAULT_produtosDisponiveis() {
        SortedSet<LinhaEncomenda> produtosDisponiveis = new TreeSet<>();
        produtosDisponiveis.add(new LinhaEncomenda("p10","Condicionador" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p11","Desinfetante" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p12","Lustra moveis" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p13","Tira manchas" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p14","Limpa vidros" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p15","Alcool" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p16", "Saco de lixo 30l" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p17", "Saco de lixo 50l" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p19","Sumo garrafa 1l" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p20","Sumo caixa 500ml" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p21","Leite integral litro" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p22","Leite desnatado litro" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p24","Feijao 2kg" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p25","Macarrao" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p26","Extrato de tomate" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p28","Sal" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p29","Acucar" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p31","Bolacha" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p33","Farofa pronta" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p34","Farinha de trigo" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p35","Farinha de milho" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p36","Farinha de mandioca" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p37","Sardinha" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p38","Atum" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p4","Detergente" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p40","Molho de pimenta" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p41","Ervilha" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p42","Milho verde" , 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p43","Doce de leite", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p44","Goiabada", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p48","Creme de leite", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p49","Leite condensado", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p5", "Agua sanitaria", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p51","Alface", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p52","Couve", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p53","Batata", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p55","Cenoura", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p56","Beterraba", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p59","Espinafre", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p6", "Esponja de aco", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p60","Banana", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p61","Ovos", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p62","Uva", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p63","Abacate", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p65","Melancia", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p67","Salsa", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p68","Cebola", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p69","Queijo", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p7", "Sabao em pedra", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p70","Queijo Mussarela", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p71","Queijo outros", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p72","Manteiga", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p73","Margarina", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p74","Iogurte", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p76","Peixe", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p77","Frango", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p79","Carne seca", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p8", "Sabonete", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p80","Salsicha", 1 , randomPrice()));
        produtosDisponiveis.add(new LinhaEncomenda("p9", "Shampoo", 1 , randomPrice()));

        return produtosDisponiveis;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public GPS getLocal() {
        return local;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocal(GPS local) {
        this.local = local;
    }

    public Set<LinhaEncomenda> getProdutosDisponiveis() {
        return produtosDisponiveis;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || obj.getClass() != this.getClass()) return false;
        Loja l = (Loja) obj;

        return (l.getCodigo().equals(this.getCodigo()) &&
                l.getLocal().equals(this.getLocal()) &&
                l.getNome().equals(this.getNome()));
    }


    @Override
    public Loja clone(){
        return new Loja(this);
    }


    @Override
    public String toString() {
        return "Model.Loja{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", local=" + local +
                '}';
    }



    @Override
    public int compareTo(Loja loja) {
        return this.codigo.compareTo(loja.codigo);
    }
}
