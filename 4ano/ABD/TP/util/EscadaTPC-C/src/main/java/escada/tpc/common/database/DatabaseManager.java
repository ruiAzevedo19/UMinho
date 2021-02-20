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

package escada.tpc.common.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import escada.tpc.common.PerformanceCounters;
import escada.tpc.logger.PerformanceLogger;

/**
 * It implements a generic database interface with connection control
 * (connection pool).
 */
public class DatabaseManager {

	private ConnectionManager cn = new ConnectionManager();

	private Date baseTime = new java.util.Date();

	/**
	 * It instanciates the CommonDatabase class.
	 */
	public DatabaseManager() {
	}

	/**
	 * It defines the maximum number of available connections. This information
	 * must be configured according to the sets of the database, therefore
	 * avoiding to exceed the maximum allowed number of connections of the
	 * database.
	 * 
	 * @param int
	 *            the maximum number of connections
	 */
	public void setMaxConnection(int mConn) {
		cn.setMaxConnection(mConn);
	}

	/**
	 * It is used to enable or disable the connection pool.
	 * 
	 * @param boolean
	 *            (true) enables the connection pool or (false) disables the
	 *            connection pool
	 */
	public void setConnectionPool(boolean pool) {
		cn.setConnectionPool(pool);
	}

	public boolean getConnectionPool() {
		return (cn.getConnectionPool());
	}

	/**
	 * It sets the driver used to connect to the database. It is important to
	 * notice that this information is dependent of the database used. It is
	 * also important to remember to set the classpath in order to locate the
	 * correct driver.
	 * 
	 * @param String
	 *            the driver
	 */
	public void setDriverName(String dName) {
		cn.setDriverName(dName);
	}

	/**
	 * It sets the jdbc path used to connect to the database. It is important to
	 * notice that this information is dependent of the database used. For that
	 * reason, it must be set carefully.
	 * 
	 * @param String
	 *            the jdbc path
	 */
	public void setjdbcPath(String jdbc) {
		cn.setjdbcPath(jdbc);
	}

	/**
	 * It sets the information about the user indentification and its password
	 * in order to connect to the database.
	 * 
	 * @param user
	 *            the user identification
	 * @param passwd
	 *            the password used to connect
	 */
	public void setUserInfo(String usr, String pass) {
		cn.setUserInfo(usr, pass);
	}

	public void setIsolation(String isolation) {
		cn.setIsolation(isolation);
	}

	/**
	 * It retrieves a new connection in order to access the database. It is
	 * important to notice that this new connection is usually obtained from a
	 * pool. However, if there is not an idle connection available, it tries to
	 * create a new one whenever the number of open connections does not exceed
	 * the maximun configured value.
	 */
	public Connection getConnection() throws SQLException {
		return cn.getConnection();
	}

	public void releaseConnections() throws SQLException {
		cn.releaseConnections();
	}

	/**
	 * It returns the connection to the pool in order to improve performance,
	 * instead of closing the connection.
	 * 
	 * @param ConnectionInterface
	 *            the connection be released and stored into the pool
	 */
	public void returnConnection(Connection con) {
		cn.returnConnection(con);
	}

	public void processLog(Date startTime, Date finishTime, String transResult,
			String transAccess, String transName) {

		if (PerformanceLogger.isPerformanceLoggerEnabled()) {

			if (transResult.equalsIgnoreCase("beginning")) {

				PerformanceCounters.setIncommingRate();

			} else if (transResult.equalsIgnoreCase("commit")) {

				PerformanceCounters.setCommitRate();

                if (transName.contains("neworder"))
                {
                    PerformanceCounters.setTPMC();
                }
				// register latency time
				PerformanceCounters.setLatency(finishTime.getTime()
						- startTime.getTime());

			} else if (transResult.equalsIgnoreCase("aborting")) {

				PerformanceCounters.setAbortRate();

			}

			PerformanceLogger.info((startTime.getTime() - baseTime.getTime())
					+ ":0:" + (finishTime.getTime() - baseTime.getTime()) + ":"
					+ (finishTime.getTime() - startTime.getTime()) + ":"
					+ transResult + ":" + transAccess + ":" + transName);
		}
	}
}
