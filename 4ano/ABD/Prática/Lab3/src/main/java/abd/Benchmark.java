package abd;

import com.beust.jcommander.JCommander;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class Benchmark extends Thread {

    private static Options options = new Options();

    private static boolean started, stopped;
    private static int n, aborted;
    private static long total;

    private static Random rand = new Random();

    public synchronized static void startBench() {
        started = true;

        System.out.println("Started!");
    }

    public synchronized static void logTransaction(long tr, boolean success) {
        if (started && !stopped) {
            n++;
            total += tr;
            if (!success)
                aborted +=1;
        }
    }

    public synchronized static boolean isStopped() {
        return stopped;
    }

    public synchronized static void stopBench() {
        stopped = true;

        System.out.println("throughput (txn/s) = "+(n/((double) options.runtime)));
        System.out.println("response time (s) = "+(total/(n*1e9d)));
        System.out.println("abort rate (%) = "+(aborted*100/((double)n)));
    }

    public void run() {
        try {
            Connection c = DriverManager.getConnection(options.database, options.user, options.passwd);
            Workload wl = new Workload(rand, c);
            while(!isStopped()) {
                long before = System.nanoTime();
                boolean success = true;
                try {
                    wl.transaction();
                } catch(SQLException e) {
                    // check if it is an isolation-related exception
                    // make sure other exceptions are shown
                    if (e.getSQLState().startsWith("40")) {
                        try {
                            c.rollback();
                        } catch(Exception e2) { }
                        success = false;
                    } else
                        throw e;
                } finally {
                    long after = System.nanoTime();
                    logTransaction(after - before, success);
                }
            }
            c.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void populate() throws Exception {
        Connection c = DriverManager.getConnection(options.database, options.user, options.passwd);
        Workload.populate(rand, c);
        c.close();
    }

    public static void execute() throws Exception {
        Benchmark[] r = new Benchmark[options.clients];
        for(int i=0; i<r.length; i++)
            r[i] = new Benchmark();
        for(int i=0; i<r.length; i++)
            r[i].start();
        Thread.sleep(options.warmup*1000); // aquecimento
        startBench();
        Thread.sleep(options.runtime*1000);  // medidas!!!
        stopBench();
        for(int i=0; i<r.length; i++)
            r[i].join();
    }

    /** other methods */
    public static void dropSchema() throws Exception{
        Connection c = DriverManager.getConnection(options.database, options.user, options.passwd);
        Workload.dropSchema(c);
        c.close();
    }

    public static void checkConnection() throws SQLException{
        Connection c = DriverManager.getConnection(options.database, options.user, options.passwd);
        c.close();
    }

    public static void main(String[] args) throws Exception {

        JCommander parser = JCommander.newBuilder()
                .addObject(options)
                .build();
        try {
            parser.parse(args);
            if (options.help) {
                parser.usage();
                return;
            }
            if(options.connection){
                checkConnection();
                System.out.println("Connection to database established");
                System.out.println("\t- database: " + options.database);
                System.out.println("\t- user: " + options.user);
            }
            if(options.dropSchema){
                dropSchema();
            }
        } catch(Exception e) {
            parser.usage();
            return;
        }

        if (options.populate)
            populate();
        if (options.execute)
            execute();
    }

}
