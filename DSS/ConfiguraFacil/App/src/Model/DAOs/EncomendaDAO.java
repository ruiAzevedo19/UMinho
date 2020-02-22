package Model.DAOs;
import Model.Class.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EncomendaDAO extends DAO {

    private Connection connection;
    private Connect con;

    public List<Encomenda> list() throws Exception {
        List<Encomenda> res = new ArrayList();
        String id, modelo, cliente, vendedor, metodo;
        double preco;
        connection = con.connect();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM encomenda");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            id = rs.getString("id");
            modelo = rs.getString("modelo");
            cliente = rs.getString("cliente");
            preco = rs.getDouble("preco");
            vendedor = rs.getString("vendedor");
            metodo = rs.getString("metodo");
            Encomenda u = new Encomenda(id, modelo, preco, metodo, vendedor, cliente, getComponentes(modelo, id));
            res.add(u);
        }
        con.close(connection);
        return res;
    }

    /**
     * Método put
     */
    public void put (Encomenda enc) {
        try {
            connection = con.connect();
            int n = Integer.parseInt(enc.getCodigo());
            int modelo = Integer.parseInt(enc.getModelo());
            int cliente = Integer.parseInt(enc.getCliente());
            double preco = enc.getPreco();
            int vendedor = Integer.parseInt(enc.getVendedor());
            String metodo = enc.getMetodoPagamento();
            PreparedStatement stm = connection.prepareStatement("insert into encomenda values (?,?,?,?,?,?)");
            stm.setInt(1, n);
            stm.setInt(2, modelo);
            stm.setInt(3, cliente);
            stm.setDouble(4, preco);
            stm.setInt(5, vendedor);
            stm.setString(6, metodo);
            stm.executeUpdate();
            // ----- Adiciona Modelo
            stm = connection.prepareStatement("insert into EncomendaComponente values (?,?)");
            stm.setInt(1, n);
            stm.setInt(2, modelo);
            stm.executeUpdate();
            System.out.println("Adicionou na enc " + n + "o componente " + modelo);
            // ----- Adiciona Componentes
            List<String> componentes = enc.getComponentes();
            System.out.println("Tamanho da lista de componentes: " + componentes.size());
            for (String s : componentes){
                stm = connection.prepareStatement("insert into EncomendaComponente values (?,?)");
                stm.setInt(1, n);
                stm.setInt(2, Integer.parseInt(s));
                stm.executeUpdate();
                System.out.println("Adicionou na enc " + n + "o componente " + s);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
    }

    /**
     * Método get
     */

    public Encomenda get(Object key) {
        Encomenda u = null;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM encomenda WHERE id = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String modelo = rs.getString("modelo");
                String cliente = rs.getString("cliente");
                double preco = rs.getDouble("preco");
                String vendedor = rs.getString("vendedor");
                String metodo = rs.getString("metodo");
                u = new Encomenda(id, modelo, preco, metodo, vendedor, cliente, getComponentes(modelo, id));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return u;
    }

    public List<String> getComponentes (String m, Object key) {
        List<String> u = new ArrayList<>();
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT componente FROM EncomendaComponente WHERE encomenda = ?");
            stm.setString(1, (String) key);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                u.add(rs.getString("componente"));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        finally {
            con.close(connection);
        }
        return u;
    }

    public int size(){
        return super.size("encomendas");
    }

    public int lastEntry () {
        int r = 0;
        try {
            connection = con.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM encomenda order by id desc");
            ResultSet rs = stm.executeQuery();
            rs.next();
            r = rs.getInt("id");
            System.out.println("" + r);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            con.close(connection);
        }
        return r;
    }
}
