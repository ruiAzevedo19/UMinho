package Model.DAOs;

import Model.Class.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDAO {
    private Connection connection;
    private Connect con;

    /**
     * Método put
     */
    public void put (Cliente u) {
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO cliente VALUES (?,?,?,?)");
            stm.setInt(1, Integer.parseInt(u.getNif()));
            stm.setString(2, u.getNome());
            stm.setString(3, u.getMorada());
            stm.setString(4, u.getContacto());
            stm.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
    }

    /**
     * Método get
     */
    public Cliente get (Object key) {
        Cliente u = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM cliente WHERE nif = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String nif = rs.getString("nif");
                String nome = rs.getString("nome");
                String morada = rs.getString("morada");
                String contacto = rs.getString("contacto");
                u = new Cliente (nif, nome, morada, contacto);
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
            PreparedStatement stm = connection.prepareStatement("SELECT nif FROM cliente WHERE nif = " + (String) key);
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
