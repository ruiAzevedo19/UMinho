package Model.DAOs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO {

    private Connection connection;
    private Connect c;

    /**
     * Devolver a conexao
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Método clear
     */
    public void clear(String table){
        try{
            this.connection = c.connect();
            PreparedStatement stm = connection.prepareStatement("DELECT FROM " + table);
            stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally{
            c.close(connection);
        }
    }

    /**
     * Método remove
     */
    public void remove(String table, String column, String key){
        try{
            this.connection = c.connect();
            PreparedStatement stm = connection.prepareStatement("DELETE FROM " + table +
                    "\nWHERE " + column + " = " + key);
            stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            c.close(connection);
        }
    }

    /**
     * Método size
     */
    public int size(String table){
        int n = 0;

        try{
            this.connection = c.connect();
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM " + table);
            ResultSet rs = stm.executeQuery();

            rs.next();

            return rs.getInt(1);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        finally {
            c.close(connection);
        }
    }

    /**
     * Método hashcode
     */
    public int hashCode(){
        return this.connection.hashCode();
    }

    /**
     * Método equals
     */
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(this.getClass() != o.getClass() || o == null)
            return false;
        DAO d = (DAO) o;

        return connection.equals(d.getConnection());
    }
}
