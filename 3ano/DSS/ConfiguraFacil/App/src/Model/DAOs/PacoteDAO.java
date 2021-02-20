package Model.DAOs;
import Model.Class.Pacote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacoteDAO extends DAO {

    private Connection connection;
    private Connect con;

    public List<Pacote> list() throws Exception {
        List<Pacote> res = new ArrayList();
        String id, desc;
        double preco, desconto;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM pacote");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getString("id");
            desc = rs.getString("designacao");
            preco = rs.getDouble("preco");
            desconto = rs.getDouble("desconto");
            Pacote u = new Pacote(id, desc, preco, desconto, getCompPack(id));
            res.add(u);
        }
        con.close(connection);
        return res;
    }

    /**
     * Método get
     */
    public Pacote get(Object key) {
        Pacote u = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM pacote WHERE id = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String desc = rs.getString("designacao");
                double preco = rs.getDouble("preco");
                double desconto = rs.getDouble("desconto");
                u = new Pacote(id, desc, preco, desconto, getCompPack(id));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    public List<String> getCompPack (Object key) {
        List<String> u = new ArrayList<>();
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT componente FROM pacoteComp WHERE pacote = ?");
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
}
