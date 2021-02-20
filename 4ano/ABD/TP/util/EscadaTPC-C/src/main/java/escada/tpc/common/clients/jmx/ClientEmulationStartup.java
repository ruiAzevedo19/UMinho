/*
 * Copyright 2013 Universidade do Minho
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software   distributed under the License is distributed on an "AS IS" BASIS,   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and limitations under the License.
 */

package escada.tpc.common.clients.jmx;

import escada.tpc.common.PerformanceCounters;
import escada.tpc.common.clients.ClientEmulation;
import escada.tpc.common.clients.ClientEmulationMaster;
import escada.tpc.common.database.DatabaseManager;
import escada.tpc.common.resources.DatabaseResources;
import escada.tpc.common.resources.WorkloadResources;
import escada.tpc.logger.PerformanceLogger;
import escada.tpc.tpcc.database.populate.jmx.DatabasePopulate;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ClientEmulationStartup implements ClientEmulationStartupMBean,
        ClientEmulationMaster {

    private final static Logger logger = Logger
            .getLogger(ClientEmulationStartup.class);

    private ExecutorService executor = Executors.newCachedThreadPool();

    private DatabaseResources databaseResources;

    private WorkloadResources workloadResources;

    private ServerControl server = new ServerControl();


    public ClientEmulationStartup() throws InvalidTransactionException {
        if (logger.isInfoEnabled()) {
            logger.info("Loading resources!");
        }

        this.databaseResources = new DatabaseResources();
        this.workloadResources = new WorkloadResources();
    }

    public static void main(String args[]) {
        // create Options object
        Options options = new Options();
        // add clients option
        options.addOption("clients", true, "Number of clients concurrently accessing the database.");
        options.addOption("frag", true, "It shifts the clients in order to access different warehouses.");
        options.addOption("hostId",true,"Host identifier, allow to have statistics per host.");
        options.addOption("dbConnectionString",true,"Database JDBC url.");
        try {
            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse( options, args);

            ClientEmulationStartup c = new ClientEmulationStartup();
            String clients=cmd.getOptionValue("clients");
            if (clients!=null)
            {
                c.getWorkloadResources().setClients(new Integer(clients));
            }
            String frag=cmd.getOptionValue("frag");
            if (frag!=null)
            {
                c.getWorkloadResources().setFrag(new Integer(frag));
            }
            String hostId=cmd.getOptionValue("hostId");
            if (hostId!=null)
            {
                c.getWorkloadResources().setHostId(new Integer(hostId));
            }
            String dbConnectionString=cmd.getOptionValue("dbConnectionString");
            if (dbConnectionString!=null)
            {
                c.getDatabaseResources().setConnectionString(dbConnectionString);
            }
            c.start(true);
        }catch(ParseException e)
        {
            System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ClientEmulationStartup", options );
        }catch (NumberFormatException e)
        {
            System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ClientEmulationStartup", options );
        }
        catch (Exception ex) {
            Thread.dumpStack();
            System.exit(-1);
        }
    }

    private synchronized void start(boolean exit)
            throws InvalidTransactionException {
        this.server.addServer(this.databaseResources.getConnectionString());
        Stage stg = this.server.getClientStage(this.workloadResources.getPrefix());
        if (stg == null) {
            server.setClientStage(this.workloadResources.getPrefix(), Stage.RUNNING);
            this.executor.execute(new Start(exit));
        } else {
            throw new InvalidTransactionException(this.workloadResources.getPrefix() + " start on " + stg);
        }
    }

    public synchronized void startClients(String prefix, String connectionString, int clients,int frag,int hostId,boolean exit)
            throws InvalidTransactionException {
        logger.info("Starting clients " + clients);
        this.databaseResources.setConnectionString(connectionString);
        this.workloadResources.setClients(clients);
        this.workloadResources.setFrag(frag);
        this.workloadResources.setHostId(hostId);
        this.workloadResources.setPrefix(prefix);
        this.start(exit);
    }

    public synchronized void pause(String key)
            throws InvalidTransactionException {
        server.pauseClient(key);
    }

    public synchronized void resume(String key)
            throws InvalidTransactionException {
        server.resumeClient(key);
    }

    public synchronized void stop(String key)
            throws InvalidTransactionException {
        server.stopClient(key);
    }

    public void kill() {
        this.executor.submit(new Kill());
    }

    private void startClientEmulation(boolean exit){
        ClientEmulation e = null;
        Vector<ClientEmulation> ebs = new Vector<ClientEmulation>();
        DatabaseManager dbManager = null;
        try {

            logger.info("Starting up the client application.");
            logger.info("Remote Emulator for Database Benchmark ...");
            logger.info("Universidade do Minho (Grupo de Sistemas Distribuidos)");
            logger.info("Version 0.1");

            Class cl = null;
            Constructor co = null;
            cl = Class.forName(this.workloadResources.getDbClass());
            try {
                co = cl.getConstructor(new Class[] { Integer.TYPE });
            } catch (Exception ex) {
            }
            if (co == null) {
                dbManager = (DatabaseManager) cl.newInstance();
            } else {
                dbManager = (DatabaseManager) co
                        .newInstance(new Object[] { new Integer(this.workloadResources.getClients()) });
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD_HH_mm");
            String date=sdf.format(new Date());
            PerformanceLogger.setPrintWriter("TPCC-"+date+"-"+ this.workloadResources.getPrefix()
                    + "-time-" + this.workloadResources.getMeasurementTime() + "-clients-"
                    + this.workloadResources.getClients() + "-frag-" + this.workloadResources.getFrag()
                    + "-think-" + this.workloadResources.isThinkTime() + ".dat");
            PerformanceCounters.getReference();//Initialize instance

            dbManager.setConnectionPool(this.workloadResources.isConnectionPoolEnabled());
            dbManager.setMaxConnection(this.workloadResources.getPoolSize());
            dbManager.setDriverName(this.databaseResources.getDriver());
            dbManager.setjdbcPath(this.databaseResources.getConnectionString());
            dbManager.setUserInfo(this.databaseResources.getUserName(), this.databaseResources.getPassword());
            dbManager.setIsolation(this.databaseResources.getIsolation());

            ScheduledThreadPoolExecutor ses = new ScheduledThreadPoolExecutor(this.workloadResources.getPoolSize());
            if (this.workloadResources.getClients() > this.workloadResources.getPoolSize())
            	ses.setMaximumPoolSize(this.workloadResources.getClients());

            if (this.workloadResources.getNumberOfWarehouses()*this.workloadResources.getNumMinClients() != this.workloadResources.getClients()) {
                logger.warn("Client emulators not evenly distributed across warehouses!!!");
            }

            for (int i = 0; i < this.workloadResources.getClients(); i++) {

                e = new ClientEmulation();

                e.setFinished(false);
                e.setTraceInformation(this.workloadResources.getTrace());
                e.setNumberConcurrentEmulators(this.workloadResources.getClients());
                e.setStatusThinkTime(this.workloadResources.isThinkTime());
                e.setStatusReSubmit(this.workloadResources.isResubmit());
                e.setDatabase(dbManager);
                e.setEmulationName(this.workloadResources.getPrefix());
                e.setHostId(Integer.toString(this.workloadResources.getHostId()));

                e.create(ses, this.workloadResources.getEbClass(), this.workloadResources.getStClass(),
                        i, this.workloadResources.getFrag(), this, this.workloadResources.getPrefix());
                
                e.start();

                ebs.add(e);
            }

            synchronized (this) {
                server.setClientEmulations(this.workloadResources.getPrefix(), ebs);
                server.attachClientToServer(this.workloadResources.getPrefix(),this.databaseResources.getConnectionString());
            }

            logger.info("Running simulation for " + this.workloadResources.getMeasurementTime() + " minute(s).");

            waitForRampDown(this.workloadResources.getPrefix(), 0,  this.workloadResources.getMeasurementTime());

            for (int i = 0; i < this.workloadResources.getClients(); i++) {
                e = (ClientEmulation) ebs.elementAt(i);
                //logger.info("Waiting for the eb " + i + " to finish its job..");
                //try {
                    e.setCompletion(true);
                    //e.getThread().join();
                /*} catch (InterruptedException inte) {
                    inte.printStackTrace();
                    continue;
                }*/
            }
            
            logger.info("EBs finished.");

            ses.shutdownNow();
            
            PerformanceLogger.info("-------------------- SUMMARY ---------------------------");
            PerformanceLogger.info("Abort rate:" + PerformanceCounters.getReference().getTotalAbortRate());
            PerformanceLogger.info("Average latency:"+PerformanceCounters.getReference().getAverageLatency());
            PerformanceLogger.info("Measured tpmC:"+PerformanceCounters.getReference().getTotalNewOrderCommitRate());
            PerformanceLogger.close();
        } catch (Exception ex) {
            logger.info("Error while creating clients: ", ex);
        } finally {
            synchronized (this) {
                this.server.removeClientEmulations(this.workloadResources.getPrefix());
                this.server.removeClientStage(this.workloadResources.getPrefix());
                this.server.detachClientToServer(this.workloadResources.getPrefix(),this.databaseResources.getConnectionString());
                notifyAll();
            }

            try {
                dbManager.releaseConnections();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            logger.info("Ebs finished their jobs..");
            if (exit)
                System.exit(0);
        }
    }

    public synchronized void notifyThreadsCompletion(String key) {
        server.setClientStage(key, Stage.STOPPED);
        notifyAll();
    }

    public synchronized void notifyThreadsError(String key) {
        server.setClientStage(key, Stage.FAILOVER);
        notifyAll();
    }

    private synchronized void waitForRampDown(String key, int start, int term) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("Start time " + start + " completion time " + term);
            }

            waitForStart(start);

            if (term < 0) {
                return;
            }

            long ini = System.currentTimeMillis();
            long end = 0;
            long remaining = term * 60 * 1000; // TODO: It must be changed to a
            // constant

            while (remaining > 0 && this.server.getClientStage(key) != null
                    && !this.server.getClientStage(key).equals(Stage.STOPPED)
                    && !this.server.getClientStage(key).equals(Stage.FAILOVER)) {

                wait(remaining);

                end = System.currentTimeMillis();

                if (logger.isInfoEnabled()) {
                    logger.info("Start remain " + remaining + " ini " + ini
                            + " end " + end);
                }

                remaining = remaining - (end - ini);
                ini = end;

                if (logger.isInfoEnabled()) {
                    logger.info("Start remain " + remaining + " ini " + ini
                            + " end " + end);
                }
            }
        } catch (InterruptedException ie) {
            logger.error("In waitforrampdown, caught interrupted exception");
        }
    }

    private void waitForStart(int start) throws InterruptedException {
        if (start < 0) {
            return;
        }

        Thread.sleep(start * 60 * 1000); // TODO - It must be changed to a
        // constant.
    }

    public DatabaseResources getDatabaseResources() {
        return databaseResources;
    }

    public void setDatabaseResources(DatabaseResources databaseResources) {
        this.databaseResources = databaseResources;
    }

    public WorkloadResources getWorkloadResources() {
        return workloadResources;
    }

    public void setWorkloadResources(WorkloadResources workloadResources) {
        this.workloadResources = workloadResources;
    }

    public synchronized void addServer(String key)
            throws InvalidTransactionException {
        this.server.addServer(key);
    }

    public synchronized void removeServer(String key)
            throws InvalidTransactionException {
        this.server.removeServer(key);
    }

    public HashSet<String> getClients() throws InvalidTransactionException {
        return (this.server.getClients());
    }

    public int getNumberOfClients(String key)
            throws InvalidTransactionException {
        return (this.server.getNumberOfClients(key));
    }

    public int getNumberOfClients() throws InvalidTransactionException {
        return (this.server.getNumberOfClients("*"));
    }

    public int getNumberOfClientsOnServer(String key)
            throws InvalidTransactionException {
        return (this.server.getNumberOfClientsOnServer(key));
    }

    public int getNumberOfClientsOnServer() throws InvalidTransactionException {
        return (this.server.getNumberOfClientsOnServer("*"));
    }

    public HashSet<String> getServers() throws InvalidTransactionException {
        return (this.server.getServers());
    }

    private String configure(String prefix,String url,String frag,String clients) {
        StringBuilder str=new StringBuilder();
        try{
            Class.forName(databaseResources.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load database driver!", e);
        }

        InputStream inStream = DatabasePopulate.class.getResourceAsStream("/workload-config.properties");
        Properties props = new Properties();
        try {
            props.load(inStream);
        } catch (IOException e) {
            logger.fatal("Unable to load properties from file (workload-config.properties). Using defaults!", e);
        }

        str.append("-EBclass "+props.getProperty("eb.class"));
        str.append(" -LOGconfig etc/logger.xml");
        str.append(" -KEY "+props.getProperty("think.time"));
        str.append(" -CLI "+clients);
        str.append(" -STclass "+props.getProperty("st.class"));
        str.append(" -DBclass "+props.getProperty("db.class"));
        str.append(" -TRACEFLAG TRACE");
        str.append(" -PREFIX "+props.getProperty("prefix"));
        str.append(" -DBpath "+ url);
        str.append(" -DBdriver "+databaseResources.getDriver());
        str.append(" -DBusr "+databaseResources.getUserName());
        str.append(" -DBpasswd "+databaseResources.getPassword());
        str.append(" -POOL "+props.getProperty("pool"));
        str.append(" -FRAG "+ frag);
        str.append(" -MI "+props.getProperty("measurement.time"));
        str.append(" -RESUBMIT "+props.getProperty("resubmit.aborted"));

        return str.toString();
    }

    class Kill implements Runnable {
        public void run() {
            System.exit(0);
        }
    }

    class Start implements Runnable {
        private boolean exit;

        public Start(boolean exit) {
            this.exit=exit;
        }

        public void run() {
            startClientEmulation(this.exit);
        }
    }

    public void stop() throws InvalidTransactionException {
        server.stopFirstClient();
    }
}
