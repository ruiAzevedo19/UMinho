package Model.DAOs;

import Model.Class.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class UserDAO extends DAO {

    private Connection connection;
    private Connect con;

    /**
     *
     */
    public List<User> list() throws Exception {
        List<User> res = new ArrayList();
        String nif, nome, username, password, contacto, tipo;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM user");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            nif = rs.getString("nif");
            nome = rs.getString("nome");
            username = rs.getString("username");
            password = rs.getString("password");
            contacto = rs.getString("contacto");
            tipo = rs.getString("tipo");

            User a = new User(nif, nome, username, password, contacto, tipo);
            res.add(a);
        }

        con.close(connection);
        return res;
    }

    public boolean setUsers(List<User> l) {
        boolean success = false;
        try {
            connection = con.connect();


            for (User u : l) {
                PreparedStatement stm = connection.prepareStatement("UPDATE user SET nome = ?, username = ?, password = ?, contacto = ? WHERE nif = ?");
                stm.setString(1, u.getNome());
                stm.setString(2, u.getUsername());
                stm.setString(3, u.getPassword());
                stm.setString(4, u.getContacto());
                stm.setInt(5, Integer.parseInt(u.getNif()));
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
     * Método put
     */
    public User put(String key, User u) {
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?,?,?,?)");
            stm.setInt(1, Integer.parseInt(u.getNif()));
            stm.setString(2, u.getNome());
            stm.setString(3, u.getUsername());
            stm.setString(4, u.getPassword());
            stm.setString(5, u.getContacto());
            stm.setString(6, u.getTipo());
            stm.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    /**
     * Método get
     */
    public User get(Object key) {
        User u = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String nif = rs.getString("nif");
                String nome = rs.getString("nome");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contacto = rs.getString("contacto");
                String tipo = rs.getString("tipo");
                u = new User(nif, nome, username, password, contacto, tipo);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    /**
     * Método remove
     */
    public User remove(Object key) {
        User u = this.get((String) key);
        super.remove("user", "nif", (String) key);
        return u;
    }

    /**
     * Método containsKey
     */
    public boolean containsKey(Object key) {
        boolean c = false;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT username FROM user WHERE username = " + (String) key);
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