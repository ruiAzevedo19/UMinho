package Entities;

import java.time.LocalDate;

public class Revista {

    private String id;
    private String nome;
    private LocalDate edicao;
    private String categoria;
    private String consultas;
    private String editor_id;
    private String empresa_id;


    public Revista(String id, String nome, LocalDate edicao, String categoria, String consultas, String editor_id, String empresa_id) {
        this.id = id;
        this.nome = nome;
        this.edicao = edicao;
        this.categoria = categoria;
        this.consultas = consultas;
        this.editor_id = editor_id;
        this.empresa_id = empresa_id;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getEdicao() {
        return edicao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getConsultas() {
        return consultas;
    }

    @Override
    public String toString() {
        return "Revista{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", edicao='" + edicao.toString() + '\'' +
                ", categoria='" + categoria + '\'' +
                ", consultas='" + consultas + '\'' +
                ", editor_id='" + editor_id + '\'' +
                ", empresa_id='" + empresa_id + '\'' +
                '}';
    }
}
