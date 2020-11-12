package abd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Workload {
    /** --- class variables -------------------------------------------------------------------- */
    private static final int max = 4096;
    private static final int dataSize = 1024;
    private static int sells = 0;
    private static int accounts = 0;
    private static int top10s = 0;
    private static int invoiceID = 1;
    /* ----------------------------------------------------------------------------------------- */

    /** --- Getters ---------------------------------------------------------------------------- */
    public static int getSells() {
        return sells;
    }

    public static int getAccounts() {
        return accounts;
    }

    public static int getTop10s() {
        return top10s;
    }

    /* --- generates random data info ---------------------------------------------------------- */
    private static String generateDate(Random rand){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        int size = alpha.length();
        StringBuilder sb = new StringBuilder(dataSize);

        for(int i = 0; i < dataSize; i++)
            sb.append(alpha.charAt(rand.nextInt(size)));
        return sb.toString();
    }

    /* --- creates database tables (client, product, invoice) ---------------------------------- */
    private static void createTables(Connection c) throws Exception{
        Statement s = c.createStatement();
        /* sql statements */
        String createClient = "CREATE TABLE Client (" +
                "id INT not NULL, " +
                "name VARCHAR(255), "   +
                "address VARCHAR(255)," +
                "data VARCHAR(" + dataSize + ") "  +
                ")";
        String createProduct = "CREATE TABLE Product (" +
                "id INT not NULL, " +
                "name VARCHAR(255), " +
                "description VARCHAR(255), " +
                "data VARCHAR(" + dataSize + ") "  +
                ")";
        String createInvoice = "CREATE TABLE Invoice (" +
                "id INT not NULL, " +
                "productID INTEGER not NULL, " +
                "clientID INTEGER not NULL," +
                "data VARCHAR(" + dataSize + ")"  +
                ")";
        /* create tables */
        s.executeUpdate(createClient);
        s.executeUpdate(createProduct);
        s.executeUpdate(createInvoice);
        /* close connection */
        s.close();
    }

    /* --- inserts random info into client and product tables----------------------------------- */
    private static void tableInsertions(Connection c, Random rand) throws Exception{
        String insertClients  = "INSERT INTO Client(id,name,address,data) VALUES (?,?,?,?)";
        String insertProducts = "INSERT INTO Product(id,name,description,data) VALUES (?,?,?,?)";

        PreparedStatement psClients  = c.prepareStatement(insertClients);
        PreparedStatement psProducts = c.prepareStatement(insertProducts);

        for(int i = 0; i < max; i++){
            /* insert clients */
            psClients.setInt(1,i);
            psClients.setString(2,"Name " + i);
            psClients.setString(3,"Address " + i);
            psClients.setString(4, generateDate(rand));
            psClients.addBatch();
            /* insert products */
            psProducts.setInt(1,i);
            psProducts.setString(2,"Product " + i);
            psProducts.setString(3,"Description " + i);
            psProducts.setString(4, generateDate(rand));
            psProducts.addBatch();
        }
        /* execute batches */
        psClients.executeBatch();
        psProducts.executeBatch();
        /* close connections */
        psClients.close();
        psProducts.close();
    }

    public static void createIndexes(Connection c) throws Exception{
        String i1 = "create index cid on invoice(clientID);";
        String i2= "create index i2 on invoice(productID);";
        String v = "vacuum analyze;";

        Statement s = c.createStatement();
        s.executeUpdate( i1 );
        s.executeUpdate( i2 );
        s.executeUpdate( v );
        s.close();
    }

    /* --- populates database ------------------------------------------------------------------ */
    public static void populate(Random rand, Connection c) throws Exception {
        createTables(c);
        tableInsertions(c,rand);
        createIndexes( c );
    }

    /* --- makes a random transaction (sell, account, top10) ----------------------------------- */
    public static void transaction(Random rand, Connection c) throws Exception {
        String sell = "INSERT INTO Invoice(id, clientID, productID, data) VALUES (?,?,?,?)";
        String account = "SELECT DISTINCT P.name FROM Product as P " +
                "JOIN Invoice AS I ON P.id=I.productID " +
                "JOIN Client AS C ON I.clientID = ?";
        String top10 = "SELECT P.name, count(P.id) FROM Product as P " +
                "JOIN Invoice AS I ON I.productID=P.id " +
                "GROUP BY P.name " +
                "ORDER BY COUNT(P.id) DESC LIMIT 10";

        PreparedStatement ps = null;
        int clientID, productID, limit;
        try {
            switch (rand.nextInt(3)) {
                case 0:
                    ps = c.prepareStatement(sell);
                    clientID = rand.nextInt(max) | rand.nextInt(max);
                    productID = rand.nextInt(max) | rand.nextInt(max);
                    ps.setInt(1, invoiceID++);
                    ps.setInt(2, clientID);
                    ps.setInt(3, productID);
                    ps.setString(4, generateDate(rand));
                    ps.addBatch();
                    sells++;
                    break;
                case 1:
                    ps = c.prepareStatement(account);
                    clientID = rand.nextInt(max);
                    ps.setInt(1, clientID);
                    ps.addBatch();
                    accounts++;
                    break;
                case 2:
                    ps = c.prepareStatement(top10);
                    ps.addBatch();
                    top10s++;
                    break;
            }
            ps.executeBatch();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* --- clears database schema  ------------------------------------------------------------- */
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

    /* --- creates a string with stats about transaction operations ---------------------------- */
    public static String transactionStats(){
        int total = sells + accounts + top10s;
        int perSells = 0, perAccounts = 0, perTop10s = 0;

        if(total > 0){
            perSells = sells / total;
            perAccounts = accounts / total;
            perTop10s = top10s / total;
        }
        return "Total transactions = " + total + "\n" +
                     "\t- sells    = " + sells    + "(" + perSells    + "%)\n" +
                     "\t- accounts = " + accounts + "(" + perAccounts + "%)\n" +
                     "\t- top10s   = " + top10s   + "(" + perTop10s   + "%)\n";
    }
}
