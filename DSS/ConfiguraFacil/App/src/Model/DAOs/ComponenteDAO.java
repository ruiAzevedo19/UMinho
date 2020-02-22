package Model.DAOs;

import Model.Class.Componente;
import Model.Class.User;

import java.util.*;
import java.sql.*;

public class ComponenteDAO extends DAO {

    private Connection connection;
    private Connect con;

    public List<Componente> list() throws Exception {
        List<Componente> res = new ArrayList();
        String id, desc, tipo;
        double preco;
        int stock;

        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM componente");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            id = rs.getString("id");
            desc = rs.getString("descricao");
            preco = rs.getDouble("preco");
            tipo = rs.getString("tipo");
            stock = rs.getInt("stock");
            Componente u = new Componente(id, desc, stock, preco, tipo,getObrInc(id, "compIncompativel"), getObrInc(id, "compObrigatorio"));
            res.add(u);
        }

        con.close(connection);
        return res;
    }

    public Componente put(Componente c){

        try {
            connection = con.connect();

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM componente WHERE id = ?");
            stm.setString(1, c.getCodigo());
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                stm = connection.prepareStatement("UPDATE componente SET descricao = ?, preco = ?, tipo = ?, stock = ? WHERE id = ?");
                stm.setString(1, c.getDescricao());
                stm.setString(2, Double.toString(c.getPreco()));
                stm.setString(3, c.getTipo());
                stm.setString(4, Integer.toString(c.getStock()));
                stm.setString(5, c.getCodigo());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return c;
    }

    public boolean setComponentes(List<Componente> l) {
        boolean success = false;
        try {
            connection = con.connect();


            for (Componente c : l) {
                PreparedStatement stm = connection.prepareStatement("UPDATE componente SET descricao = ?, preco = ?, tipo = ?, stock = ? WHERE id = ?");
                stm.setString(1, c.getDescricao());
                stm.setString(2, Double.toString(c.getPreco()));
                stm.setString(3, c.getTipo());
                stm.setString(4, Integer.toString(c.getStock()));
                stm.setInt(5, Integer.parseInt(c.getCodigo()));
                stm.executeUpdate();
            }
            success = true;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return success;
    }
    /**
     * Método get
     */
    public Componente get(Object key) {
        Componente u = null;

        try {
            connection = con.connect();

            PreparedStatement stm = connection.prepareStatement("SELECT * FROM componente WHERE id = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String desc = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                String tipo = rs.getString("tipo");
                int stock = rs.getInt("stock");
                u = new Componente(id, desc, stock, preco, tipo, getObrInc(id, "compIncompativel"), getObrInc(id, "compObrigatorio"));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    public List<String> getObrInc(Object key, String table) {
        List<String> u = new ArrayList<>();
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT comp2 FROM " + table + " WHERE comp1 = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u.add(rs.getString("comp2"));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }
    /**
     * Método containsKey
     */
    public boolean containsKey(Object key) {
        boolean c = false;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT id FROM componente WHERE id = " + (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                c = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            con.close(connection);
        }
        return c;
    }
}
