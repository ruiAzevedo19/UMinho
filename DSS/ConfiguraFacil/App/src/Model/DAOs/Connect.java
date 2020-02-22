package Model.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "localhost:3306";
    private static final String SCHEMA = "ConfiguraFacil";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection connect(){
        try{
            Class.forName(DRIVER);
            Connection cn = DriverManager.getConnection("jdbc:mysql://"+URL+"/"+SCHEMA+"?user="+USERNAME+"&password="+PASSWORD);
            return cn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection){
        try{
            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
