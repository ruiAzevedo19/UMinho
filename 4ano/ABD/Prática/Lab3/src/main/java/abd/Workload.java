package abd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Workload {
     /** Instance variables **/
     private final Random rand;
     private static final int populate_size = 5000;
     private final Connection c;

     /**
      * Parametrized constructor
      * @param rand Random
      * @param c Connection
      * @throws Exception Connection exception
      */
     public Workload(Random rand, Connection c) throws Exception {
          this.rand = rand;
          this.c = c;

          //---- DEMO WORKLOAD ----
          // initialize connection, e.g. c.setAutoCommit(false);
          // or create prepared statements...
          //-----------------------
     }

     /* ------------------------------------------------------------------------------------------------------------- */

     /**
      * Create database tables
      * @param c Connection
      */
     private static void tables(Connection c) throws SQLException {
          Statement s = c.createStatement();
          /* sql statements */
          String client = "CREATE TABLE Client (" +
                  "id SERIAL , " +
                  "name VARCHAR(255), "   +
                  "address VARCHAR(255), " +
                  "PRIMARY KEY(id)" +
                  ")";
          String product = "CREATE TABLE Product (" +
                  "id SERIAL , " +
                  "description VARCHAR(255), " +
                  "stock INT not NULL, " +
                  "min INT not NULL, " +
                  "max INT not NULL, " +
                  "PRIMARY KEY(id) " +
                  ")";
          String invoice = "CREATE TABLE Invoice (" +
                  "id SERIAL , " +
                  "clientID INTEGER , " +
                  "PRIMARY KEY(id), " +
                  "FOREIGN KEY(clientID) REFERENCES Client(id)" +
                  ")";
          String invoice_line = "CREATE TABLE InvoiceLine (" +
                  "id SERIAL, " +
                  "invoiceID INT , " +
                  "productID INT, " +
                  "PRIMARY KEY(id), " +
                  "FOREIGN KEY(invoiceID) REFERENCES Invoice(id), " +
                  "FOREIGN KEY(productID) REFERENCES Product(id) " +
                  ")";
          String order = "CREATE TABLE Orders(" +
                  "id SERIAL , " +
                  "productID INT , " +
                  "supplier VARCHAR(256), " +
                  "items INT not NULL, " +
                  "PRIMARY KEY(id), " +
                  "FOREIGN KEY(productID) REFERENCES Product(id) " +
                  ")";

          s.executeUpdate( client );
          s.executeUpdate( product );
          s.executeUpdate( invoice );
          s.executeUpdate( invoice_line );
          s.executeUpdate( order );

          s.close();
     }

     /**
      * Insert values in database tables
      * @param c Connection
      * @throws SQLException SQL Query Exception
      */
     private static void insert(Connection c) throws SQLException {
          Random r = new Random();

          String clients = "INSERT INTO Client(name, address) VALUES (?,?)";
          String products = "INSERT INTO Product(description, stock, min, max) VALUES (?,?,?,?)";

          PreparedStatement client_record  = c.prepareStatement(clients);
          PreparedStatement product_record = c.prepareStatement(products);

          for(int i = 0 ; i < populate_size ; i++){
               /* insert clients */
               client_record.setString(1,"Name " + i);
               client_record.setString(2,"Address " + i);
               client_record.addBatch();
               /* insert products */
               int max = r.nextInt(20);
               int min = r.nextInt(max > 1 ? max - 1 : 1);
               int stock = r.nextInt(max > 0 ? max : 1);
               product_record.setString(1,"Product " + i);
               product_record.setInt( 2, stock );
               product_record.setInt( 3, min );
               product_record.setInt( 4, max );
               product_record.addBatch();
          }
          /* execute batches */
          client_record.executeBatch();
          product_record.executeBatch();
          /* close connections */
          client_record.close();
          product_record.close();
     }

     /**
      * Drops database schema
      * @param c Connection
      * @throws SQLException
      */
     public static void dropSchema(Connection c) throws SQLException {
          try{
               Statement s = c.createStatement();
               String dropSchema = "DROP SCHEMA IF EXISTS public CASCADE;";
               String setSchema  = "CREATE SCHEMA public; " +
                       "GRANT ALL ON SCHEMA public TO public;";

               s.executeUpdate(dropSchema);
               s.executeUpdate(setSchema);

               s.close();
          }catch (SQLException sql){
               sql.printStackTrace();
          }
     }

     public static void populate(Random rand, Connection c) throws Exception {
          Statement s = c.createStatement();
          //---- DEMO WORKLOAD ----
          tables( c );
          insert( c );
          //-----------------------
          s.close();
     }

     public void transaction() throws Exception {
          Statement s = c.createStatement();

          String account = "SELECT DISTINCT P.name FROM Product as P " +
                  "JOIN Invoice AS I ON P.id=I.productID " +
                  "JOIN Client AS C ON I.clientID = ?";
          String top10 = "SELECT P.name, count(P.id) FROM Product as P " +
                  "JOIN Invoice AS I ON I.productID=P.id " +
                  "GROUP BY P.name " +
                  "ORDER BY COUNT(P.id) DESC LIMIT 10";

          switch(rand.nextInt(2)) {
               // sell
               case 0:
                    List<Integer> prods;
                    int r = rand.nextInt(30);
                    int n = r > 0 ? r : 1;
                    prods = IntStream.range( 0, n )
                                     .mapToObj( i -> rand.nextInt( populate_size ) )
                                     .collect( Collectors.toList() );
                    int cli = rand.nextInt(populate_size);
                    cli = cli > 0 ? cli : 1;
                    // insert into invoice : cli -> inv
                    break;
               case 1:
                    ResultSet rs = s.executeQuery("select * from demo");
                    while(rs.next())
                         ;
                    break;
          }
          //-----------------------

          s.close();
     }
}