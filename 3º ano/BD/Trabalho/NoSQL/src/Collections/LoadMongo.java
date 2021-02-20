package Collections;

import Connections.ConnectMongo;
import Entities.*;
import org.bson.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoadMongo {

    private ConnectMongo con = new ConnectMongo();

    /**
     * Criar uma lista de Anuncios
     */
    public List<Document> listAnuncios(List<Anuncio> anuncio){
        con.getDb();

        List<Document> docs = new ArrayList<>();
        Document art;

        for(Anuncio a: anuncio){
            art = anuncioDoc(a);
            docs.add(art);
        }
        con.close();

        return docs;
    }

    /**
     * Criar uma lista de Anuncios
     */
    public List<Document> listArtigos(List<Artigo> artigo){
        con.getDb();
        LoadSQL sql = new LoadSQL();
        List<Document> docs = new ArrayList<>();
        List<Escritor> esc;
        Document art;

        for(Artigo a: artigo){
            esc = sql.getEscritores(a.getId());
            art = artigoDoc(a, esc);
            docs.add(art);
        }
        con.close();
        return docs;
    }

    /**
     * Criar o documento Empresa de uma Revista
     */
    public Document empresaDoc(Empresa e, List<String> contactos){
        con.getDb();
        Document empresa = new Document();
        Document contacto;
        List<Document> docs = new ArrayList<>();

        for(String c : contactos) {
            contacto = new Document();
            contacto.put("Contacto", c);
            docs.add(contacto);
        }

        empresa.put("ID", e.getId());
        empresa.put("Nome", e.getNome());
        empresa.put("Cidade", e.getCidade());
        empresa.put("Rua", e.getRua());
        empresa.put("QuantidadeRevistas",e.getQuantidadeRevistas());
        empresa.put("Contactos", docs);

        con.close();
        return empresa;
    }

    /**
     * Criar o documento Anuncio
     */
    public Document anuncioDoc(Anuncio a){
        con.getDb();
        Document anuncio = new Document();

        anuncio.put("ID", a.getId());
        anuncio.put("Titulo", a.getTitulo());
        anuncio.put("Conteudo", a.getConteudo());
        anuncio.put("Categoria", a.getCategoria());
        anuncio.put("NrConsultas", a.getNrConsultas());
        anuncio.put("Contacto", a.getContacto());

        con.close();
        return anuncio;
    }

    /**
     * Criar o documento Artigo
     */
    public Document artigoDoc(Artigo a, List<Escritor> escritores){
        con.getDb();
        Document artigo = new Document();
        Document escritor;
        List<Document> esc = new ArrayList<>();

        for(Escritor e : escritores){
            escritor = new Document();
            escritor.put("ID",e.getId());
            escritor.put("Nome", e.getNome());
            esc.add(escritor);
        }

        artigo.put("ID",a.getId());
        Instant instant = a.getData().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        artigo.put("Data", date);
        artigo.put("Titulo", a.getTitulo());
        artigo.put("Corpo", a.getCorpo());
        artigo.put("NrConsultas", a.getConsultas());
        artigo.put("Categoria", a.getCategoria());
        artigo.put("Escritores", esc);

        con.close();
        return artigo;
    }

    /**
     * Criar o documento Editor
     */
    public Document editorDoc(Editor e){
        con.getDb();
        Document editor = new Document();

        editor.put("ID", e.getId());
        editor.put("Nome", e.getNome());

        return editor;
    }
}
