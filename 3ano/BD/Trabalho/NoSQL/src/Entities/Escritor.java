package Entities;

public class Escritor {

    // Atributos //
    private String id;
    private String nome;

    /**
     * Construtor
     */
    public Escritor(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Escritor{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
