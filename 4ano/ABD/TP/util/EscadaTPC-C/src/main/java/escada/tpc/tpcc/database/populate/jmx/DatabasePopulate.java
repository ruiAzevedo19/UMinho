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

package escada.tpc.tpcc.database.populate.jmx;

import escada.tpc.common.TPCConst;
import escada.tpc.common.clients.jmx.InvalidTransactionException;
import escada.tpc.common.resources.DatabaseResources;
import escada.tpc.common.resources.WorkloadResources;
import escada.tpc.tpcc.TPCCConst;
import escada.tpc.tpcc.database.populate.dbPopulate;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePopulate implements DatabasePopulateMBean {

    private static final Logger logger = Logger
            .getLogger(DatabasePopulate.class);

    private DatabaseResources databaseResources = null;

    private WorkloadResources workloadResources = null;

    public DatabasePopulate() {

        if (logger.isInfoEnabled()) {
            logger.info("Trying to load resources for populate!");
        }

        databaseResources = new DatabaseResources();
        workloadResources = new WorkloadResources();
    }

    public static void main(String[] args) {
        try {
            DatabasePopulate db=new DatabasePopulate();
	    logger.info("no main");
            if(args.length > 0){
		logger.info("args > 0");
		db.populate("history");
	    }
	    else db.populate(null);
        } catch (InvalidTransactionException e) {
            logger.error("Unable to populate!", e);
        }
    }


    public void populate(String table) throws InvalidTransactionException {

        if (logger.isInfoEnabled()) {
            logger.info("Connecting to database using driver: "
                    + databaseResources.getDriver() + ", username: "
                    + databaseResources.getUserName() + ", connection string: "
                    + databaseResources.getConnectionString());
        }

        Connection conn = null;
        try {
            Class.forName(databaseResources.getDriver());
            conn = DriverManager.getConnection(databaseResources
                    .getConnectionString(), databaseResources.getUserName(),
                    databaseResources.getPassword());
            conn.setAutoCommit(false);

            if (logger.isInfoEnabled()) {
                logger.info("Starting POPULATE process!");
            }

            dbPopulate db = new dbPopulate();
	    if (table == null) {
		db.populate(conn, workloadResources.getNumberOfWarehouses());
            }
	    else {
		logger.info("CALLING POPULATE HISTORY");
		db.populateHistory(conn, workloadResources.getNumberOfWarehouses());
           } 
	   conn.close();

            if (logger.isInfoEnabled()) {
                logger.info("POPULATE process ENDED!");
            }

        } catch (ClassNotFoundException e) {
            logger.error("Unable to load database driver!", e);
	    e.printStackTrace();
        } catch (SQLException e) {
            logger.error(
                    "Exception caught while talking (SQL) to the database!", e);
	    e.printStackTrace();
        }
    }

    public synchronized DatabaseResources getDatabaseResources() {
        return databaseResources;
    }

    public synchronized void setDatabaseResources(
            DatabaseResources databaseResources) {
        this.databaseResources = databaseResources;
    }

    public synchronized WorkloadResources getWorkloadResources() {
        return workloadResources;
    }

    public synchronized void setWorkloadResources(
            WorkloadResources workloadResources) {
        this.workloadResources = workloadResources;
    }

    private void configureScenario(String driver, String userName,
                                   String connectionString,String password, int numWarehouses) {
        databaseResources.setDriver(driver);
        databaseResources.setUserName(userName);
        databaseResources
                .setConnectionString(connectionString);

        databaseResources.setPassword(password);
        workloadResources.setNumberOfWarehouses(numWarehouses);

        TPCConst.setNumMinClients(10);
        TPCCConst.setNumCustomer(3000);
        TPCCConst.setNumDistrict(10);
        TPCCConst.setNumItem(100000);
        TPCCConst.setNumLastName(909);
    }
}
