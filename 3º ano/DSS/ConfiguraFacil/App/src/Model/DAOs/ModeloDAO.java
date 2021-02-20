package Model.DAOs;
import Model.Class.Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO extends DAO {

    private Connection connection;
    private Connect con;

    public List<Modelo> list() throws Exception {
        List<Modelo> res = new ArrayList();
        String id, desc;
        double preco;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM modelo");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getString("id");
            desc = rs.getString("descricao");
            preco = rs.getDouble("preco");
            Modelo u = new Modelo(id, desc, preco, getCompMod(id));
            res.add(u);
        }
        con.close(connection);
        return res;
    }

    /**
     * Método get
     */
    public Modelo get(Object key) {
        Modelo u = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM modelo WHERE id = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String desc = rs.getString("descricao");
                System.out.println(desc);
                double preco = rs.getDouble("preco");
                u = new Modelo(id, desc, preco, new ArrayList<>());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    public List<String> getCompMod (Object key) {
        List<String> u = new ArrayList<>();
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT componente FROM compCompativelModelo WHERE modelo = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u.add(rs.getString("componente"));
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
            PreparedStatement stm = connection.prepareStatement("SELECT id FROM modelo WHERE id = " + (String) key);
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
