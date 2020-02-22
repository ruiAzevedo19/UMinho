package Entities;

public class Anuncio {

    // Atributos //
    private String id;
    private String titulo;
    private String conteudo;
    private String categoria;
    private String nrConsultas;
    private String contacto;
    private String empresaID;


    public Anuncio(String id, String titulo, String conteudo, String categoria, String nrConsultas, String contacto, String empresaID) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.categoria = categoria;
        this.nrConsultas = nrConsultas;
        this.contacto = contacto;
        this.empresaID = empresaID;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNrConsultas() {
        return nrConsultas;
    }

    public String getContacto() {
        return contacto;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", nrConsultas='" + nrConsultas + '\'' +
                ", contacto='" + contacto + '\'' +
                ", empresaID='" + empresaID + '\'' +
                '}';
    }
}
