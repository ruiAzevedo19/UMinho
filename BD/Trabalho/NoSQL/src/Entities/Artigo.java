package Entities;

import java.time.LocalDate;

public class Artigo {

    private String id;
    private String revista_id;
    private LocalDate data;
    private String titulo;
    private String corpo;
    private String consultas;
    private String categoria;

    public Artigo(String id, String revista_id, LocalDate data, String titulo, String corpo, String consultas, String categoria) {
        this.id = id;
        this.revista_id = revista_id;
        this.data = data;
        this.titulo = titulo;
        this.corpo = corpo;
        this.consultas = consultas;
        this.categoria = categoria;
    }


    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public String getConsultas() {
        return consultas;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Artigo{" +
                "id='" + id + '\'' +
                ", revista_id='" + revista_id + '\'' +
                ", data='" + data.toString() + '\'' +
                ", titulo='" + titulo + '\'' +
                ", corpo='" + corpo + '\'' +
                ", consultas='" + consultas + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
