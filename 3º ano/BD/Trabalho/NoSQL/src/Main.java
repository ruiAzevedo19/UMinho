import Collections.LoadMongo;
import Collections.LoadSQL;
import Connections.ConnectMongo;
//import Data.*;
import Entities.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jna.platform.win32.OaIdl;
import org.bson.Document;
//import javax.swing.text.Document;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Connections.ConnectMongo.db;
import static Connections.ConnectMongo.mongoClient;

public class Main {

    public static MongoClient mongoClient;
    public static DB db;

    public static void main(String[] args) {

        mongoClient = new MongoClient("localhost",27017);
        db = mongoClient.getDB("revista");

        /** Conecao Mongo **/
        ConnectMongo con = new ConnectMongo();
        MongoDatabase db = con.getDb();
        Document tmp;
        Document art;
        Document anu;
        Document revista;

        /** Dados SQL e Mongo **/
        LoadSQL sql = new LoadSQL();
        LoadMongo mongo = new LoadMongo();
        List<Document> docs;

        /** Variaveis **/
        List<Revista> revistas = sql.getRevistas();

        Empresa empresa;
        List<String> contactos;

        Editor editor;

        List<Anuncio> anuncios;
        List<Artigo> artigos;
        List<Escritor> escritores;

        MongoCollection<Document> revistaColection = db.getCollection("Arquivo");

        for (Revista r : revistas) {
            revista = new Document();
            revista.put("ID", r.getId());
            Instant instant = r.getEdicao().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            revista.put("Nome", r.getNome());
            revista.put("Edicao", date);
            revista.put("Categoria",r.getCategoria());
            revista.put("NrConsultas", r.getConsultas());

            // adicionar empresa
            empresa = sql.getEmpresa(r.getId());
            contactos = sql.getContactosEmpresa(empresa.getId());
            tmp = mongo.empresaDoc(empresa,contactos);
            revista.put("Empresa", tmp);

            
            // adicionar editor
            editor = sql.getEditor(r.getId());
            tmp = mongo.editorDoc(editor);
            revista.put("Editor", tmp);

            // adicionar anuncios
            try {
                anuncios = sql.getAnuncios(r.getId());
                docs = mongo.listAnuncios(anuncios);
                revista.put("Anuncios",docs);
            } catch (Exception e) {
                e.getMessage();
            }

            // adicionar artigos
            artigos = sql.getArtigos(r.getId());
            docs = mongo.listArtigos(artigos);
            revista.put("Artigos",docs);

            revistaColection.insertOne(revista);
        }
        con.getDb();
    }
}
