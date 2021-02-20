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

package escada.tpc.cluster.database.transaction;

import escada.tpc.common.database.DatabaseManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

abstract public class dbCLUSTERDatabase extends DatabaseManager {

	private static Logger logger = Logger.getLogger(dbCLUSTERDatabase.class);

	public Object TraceUpdateTrans(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction update trans.");

			Date NetStartTime = new java.util.Date();

			con = getConnection();

			InitTransaction(con, "tx updatetrans", "w");

			dbtrace = UpdateTransDB(obj, con);

			CommitTransaction(con, "tx updatetrans", "w");

			Date NetFinishTime = new java.util.Date();

			processLog(NetStartTime, NetFinishTime, "commit", "w",
					"tx updatetrans");

			logger.info("Finishing transaction update trans.");
		} catch (java.sql.SQLException sqlex) {
			if ((sqlex.getMessage().indexOf("serialize") == -1)
					&& (sqlex.getMessage().indexOf("deadlock") == -1)
					&& (sqlex.getMessage().indexOf("not found") == -1)
					&& (sqlex.getMessage().indexOf("Generated Abort") == -1)) {
				logger.fatal("Unexpected error. Something bad happend");
				sqlex.printStackTrace(System.err);
				System.exit(-1);
			} else {
				if (sqlex.getMessage().indexOf("certification") != -1)
					logger.warn("UpdateTrans - SQL Exception "
							+ sqlex.getMessage());
			}
		} catch (java.lang.Exception ex) {
			logger.fatal("Unexpected error. Something bad happend");
			ex.printStackTrace(System.err);
			System.exit(-1);
		} finally {
			returnConnection(con);
		}
		return (dbtrace);
	}

	public Object TraceReadOnlyTrans(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction readonly trans.");

			Date NetStartTime = new java.util.Date();

			con = getConnection();

			InitTransaction(con, "tx readonlytrans", "r");

			dbtrace = ReadOnlyTransDB(obj, con);

			CommitTransaction(con, "tx readonlytrans", "r");

			Date NetFinishTime = new java.util.Date();

			processLog(NetStartTime, NetFinishTime, "commit", "w",
					"tx readonlytrans");

			logger.info("Finishing transaction readonly trans.");
		} catch (java.sql.SQLException sqlex) {
			if ((sqlex.getMessage().indexOf("serialize") == -1)
					&& (sqlex.getMessage().indexOf("deadlock") == -1)
					&& (sqlex.getMessage().indexOf("not found") == -1)
					&& (sqlex.getMessage().indexOf("Generated Abort") == -1)) {
				logger.fatal("Unexpected error. Something bad happend");
				sqlex.printStackTrace(System.err);
				System.exit(-1);
			} else {
				if (sqlex.getMessage().indexOf("certification") != -1)
					logger.warn("ReadOnlyTrans - SQL Exception "
							+ sqlex.getMessage());
			}
		} catch (java.lang.Exception ex) {
			logger.fatal("Unexpected error. Something bad happend");
			ex.printStackTrace(System.err);
			System.exit(-1);
		} finally {
			returnConnection(con);
		}
		return (dbtrace);
	}

	protected abstract HashSet UpdateTransDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract HashSet ReadOnlyTransDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract void InitTransaction(Connection con, String strTrans,
			String strAccess) throws java.sql.SQLException;

	protected abstract void CommitTransaction(Connection con, String strTrans,
			String strAccess) throws java.sql.SQLException;

	protected abstract void RollbackTransaction(Connection con,
			java.lang.Exception dump, String strTrans, String strAccess)
			throws java.sql.SQLException;

}

