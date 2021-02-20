package Collections;

import Connections.ConnectSQL;
import Entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoadSQL {

    private Connection connection;
    private ConnectSQL con;


    /**
     * Devolver a lista de todas as Revistas
     */
    public List<Revista> getRevistas(){
        List<Revista> revistas = new ArrayList<>();
        Revista r = null;
        String id, nome,  categoria, nrConsultas, editorID, empresaID;
        LocalDate edicao;


        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Revista");
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                id = rs.getString("ID");
                nome = rs.getString("Nome");
                edicao = rs.getDate("Edicao").toLocalDate();
                categoria = rs.getString("Categoria");
                nrConsultas = rs.getString("NrConsultas");
                editorID = rs.getString("Editor_ID");
                empresaID = rs.getString("Empresa_ID");
                r = new Revista(id,nome,edicao,categoria,nrConsultas,editorID,empresaID);
                revistas.add(r);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        finally{
            con.close(connection);
        }
        return revistas;
    }

    /**
     * Devolver a empresa de uma revista -------------------------------------------------------------------------------
     */
    public Empresa getEmpresa(String id) {
        Empresa e = null;
        String empresaID;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT Empresa.* FROM Empresa " +
                                                                        "INNER JOIN Revista ON Revista.Empresa_ID = Empresa.ID " +
                                                                            "WHERE Revista.ID = " + id);
            //stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String i = rs.getString("ID");
                String n = rs.getString("Nome");
                String r = rs.getString("Rua");
                String c = rs.getString("Cidade");
                String q = rs.getString("QuantidadeRevistas");
                e = new Empresa(i, n, c, r, q);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        finally{
            con.close(connection);
        }
        return e;
    }

    /**
     * Devolver os contactos de uma empresa ----------------------------------------------------------------------------
     */
    public List<String> getContactosEmpresa(String idEmpresa) {
        List<String> contactos = new ArrayList<>();
        String contacto;

        try{
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT Contacto FROM Contactos " +
                                                                        "WHERE Empresa_ID = " + idEmpresa);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                contacto = rs.getString("Contacto");
                contactos.add(contacto);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
            con.close(connection);
        }
        return contactos;
    }


    /**
     * Devolver o editor de uma revista --------------------------------------------------------------------------------
     */
    public Editor getEditor(String idRevista){
        Editor editor = null;
        String id, nome;

        try{
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT Editor.* FROM Editor " +
                                                                        "INNER JOIN Revista " +
                                                                            "ON Revista.Editor_ID = Editor.ID " +
                                                                                "WHERE Revista.ID = " + idRevista);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                id = rs.getString("ID");
                nome = rs.getString("Nome");
                editor = new Editor(id,nome);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
            con.close(connection);
        }
        return editor;
    }


    /**
     * Devolver a lista de anuncios de uma revista ---------------------------------------------------------------------
     */
    public List<Anuncio> getAnuncios(String idRevista) throws Exception{
        List<Anuncio> artigos = new ArrayList<>();
        Anuncio a = null;
        String idR, titulo, conteudo, categoria,nrConsultas,contacto,empresaID;

        try{
            connection = con.connect();
            PreparedStatement
                    stm = connection.prepareStatement("SELECT Anuncio_ID FROM AnuncioRevista " +
                                                                    "WHERE Revista_ID = " + idRevista);
            ResultSet rs = stm.executeQuery();
            ResultSet r;
            while(rs.next()){
                stm = connection.prepareStatement("SELECT * FROM Anuncio " +
                                                            "WHERE ID = " + rs.getString("Anuncio_ID"));
                r = stm.executeQuery();
                if(r.next()){
                    idR = r.getString("ID");
                    titulo = r.getString("Titulo");
                    conteudo = r.getString("Conteudo");
                    categoria = r.getString("Categoria");
                    nrConsultas = r.getString("NrConsultas");
                    contacto = r.getString("Contacto");
                    empresaID = r.getString("Empresa_ID");
                    a = new Anuncio(idR,titulo,conteudo,categoria,nrConsultas,contacto,empresaID);
                    artigos.add(a);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close(connection);
        }
        return artigos;
    }


    /**
     * Devolver a lista de artigos de uma revista ----------------------------------------------------------------------
     */
    public List<Artigo> getArtigos(String idRevista){
        List<Artigo> artigos = new ArrayList<>();
        Artigo a;
        String id, revistaID, titulo, corpo, nrConsultas,categoria;
        LocalDate data;

        try{
            connection = con.connect();
            PreparedStatement
                    stm = connection.prepareStatement("SELECT * FROM Artigo " +
                                                            "INNER JOIN Revista ON Artigo.Revista_ID = Revista.ID " +
                                                                "WHERE Artigo.Revista_ID = " + idRevista);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                id = rs.getString("ID");
                revistaID = rs.getString("Revista_ID");
                data = rs.getDate("Data").toLocalDate();
                titulo = rs.getString("Titulo");
                corpo = rs.getString("Corpo");
                nrConsultas = rs.getString("NrConsultas");
                categoria = rs.getString("Categoria");
                a = new Artigo(id,revistaID,data,titulo,corpo,nrConsultas, categoria);
                artigos.add(a);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close(connection);
        }
        return artigos;
    }

    /**
     * Devolver a lista de escritores de um artigo ---------------------------------------------------------------------
     */
    public List<Escritor> getEscritores (String idArtigo) {
        List<Escritor> esc = new ArrayList<>();
        Escritor e = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT Escritor.* FROM Escritor " +
                                                                        "INNER JOIN EscritorArtigo " +
                                                                            "ON Escritor.ID = EscritorArtigo.Escritor_ID " +
                                                                                "WHERE EscritorArtigo.Artigo_ID = " + idArtigo);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                e = new Escritor(rs.getString("ID"), rs.getString("Nome"));
                esc.add(e);
            }

        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        finally {
            con.close(connection);
        }
        return esc;
    }
}
