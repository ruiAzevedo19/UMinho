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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;


import org.apache.log4j.Logger;

public class ConnectionManager {
	private static Logger logger = Logger.getLogger(ConnectionManager.class);

	private Vector availConn = new Vector(0);

	private boolean connectionpool = true;

	private HashMap<Long, Connection> thread_conn = new HashMap<Long, Connection>();

	private int checkedOut = 0;

	private int totalConnections = 0;

	private String user = "tpcc";

	private String passwd = null;

	private String driverName = "org.postgresql.Driver";

	private String jdbcPath = "jdbc:postgresql://localhost:5432/tpcc";

	private int maxConn = 1;

	private String isolation = "";

	/*
	 * It defines the maximum number of available connections. This information
	 * must be configured according to the sets of the database, therefore
	 * avoiding to exceed the maximum allowed number of connections of the
	 * database.
	 * 
	 * @param int the maximum number of connections
	 */
	public void setMaxConnection(int mConn) {
		maxConn = mConn;
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
		driverName = dName;
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
		jdbcPath = jdbc;
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
		user = usr;
		passwd = pass;
	}

	public void setIsolation(String isolation) {
		this.isolation = isolation.toUpperCase();
	}

	/**
	 * It retrieves a new connection in order to access the database. It is
	 * important to notice that this new connection is usually obtained from a
	 * pool. However, if there is not an idle connection available, it tries to
	 * create a new one whenever the number of open connections does not exceed
	 * the maximun configured value.
	 */
	public synchronized Connection getConnection() throws SQLException {
		Connection con = null;
		boolean acquiredResource = false;
		while (!acquiredResource) {
			if (availConn.size() > 0 && (connectionpool == true)) {
				con = (Connection) availConn.firstElement();
				availConn.removeElementAt(0);
				if (con.isClosed()) {
					totalConnections--;
					continue;
				}
				acquiredResource = true;
			} else if ((connectionpool == false)
					|| (totalConnections < maxConn)) {
				if (connectionpool == false) {
					Long thread_id = Thread.currentThread().getId();
					con = thread_conn.get(thread_id);
					if (con != null) {
						availConn.remove(con);
						thread_conn.remove(thread_id);
					}
				}
				if (con == null) {
					con = createConnection();
				}
				acquiredResource = true;
			} else if (!(checkedOut < totalConnections)) {
				try {
					logger.info("Waiting to acquire connection.");
					wait();
				} catch (InterruptedException it) {
					continue;
				}
			}
		}

		if (con != null)
			checkedOut++;

		logger.info("Connection " + this.jdbcPath + " for thread "
				+ Thread.currentThread().getName());

		return con;
	}

	public synchronized void releaseConnections() throws SQLException {
		Connection con;

		thread_conn.clear();
		while (availConn.size() > 0) {
			con = (Connection) availConn.firstElement();
			availConn.removeElementAt(0);

			if (!con.isClosed()) {
				con.close();
			}

		}
		checkedOut = 0;
		totalConnections = 0;
	}

	/**
	 * It returns the connection to the pool in order to improve performance,
	 * instead of closing the connection.
	 * 
	 * @param Connection
	 *            the connection be released and stored into the pool
	 */
	public synchronized void returnConnection(Connection con) {
		if (con != null) {
			checkedOut--;
			availConn.addElement(con);

			if (connectionpool == false) {
				thread_conn.put(Thread.currentThread().getId(), con);
			}

			notify();
		}
	}

	public void setConnectionPool(boolean pool) {
		this.connectionpool = pool;
	}

	public boolean getConnectionPool() {
		return (this.connectionpool);
	}

	/**
	 * It returns a new connection to the database, according to the paramters
	 * configured at the initialization process.
	 * 
	 * @return the new connection
	 */
	private synchronized Connection createConnection() throws SQLException {
		try {
			Class.forName(driverName);
			Connection con;
			while (true) {
				con = DriverManager.getConnection(jdbcPath, user, passwd);
				con.setAutoCommit(false);
				if (!isolation.equals("") && !isolation.equals("DEFAULT")) {
					switch (isolation) {
						case "SERIALIZABLE":
						case "S":
							con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
							break;
						case "REPEATABLE_READ":
						case "RR":
							con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
							break;
						case "READ_COMMITTED":
						case "RC":
							con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
							break;
						case "READ_UNCOMMITTED":
						case "RU":
							con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
							break;
						default:
							throw new RuntimeException("invalid isolation level: "+isolation);
					}
				}
				break;
			}
			totalConnections++;
			return con;
		} catch (java.lang.Exception ex) {
			logger.error("Error while acquiring connection ", ex);
			throw new SQLException(ex.getMessage());
		}
	}
}
