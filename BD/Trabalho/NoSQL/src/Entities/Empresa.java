package Entities;

public class Empresa {

    // Atributos //
    private String id;
    private String nome;
    private String cidade;
    private String rua;
    private String quantidadeRevistas;

    public Empresa(String id, String nome, String cidade, String rua, String quantidadeRevistas) {
        this.id     = id;
        this.nome   = nome;
        this.cidade = cidade;
        this.rua    = rua;
        this.quantidadeRevistas = quantidadeRevistas;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public String getQuantidadeRevistas() {
        return quantidadeRevistas;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cidade='" + cidade + '\'' +
                ", rua='" + rua + '\'' +
                ", quantidadeRevistas='" + quantidadeRevistas + '\'' +
                '}';
    }
}
