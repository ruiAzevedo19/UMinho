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

package escada.tpc.tpcc.database.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import org.apache.log4j.Logger;

import escada.tpc.common.database.DatabaseManager;

/**
 * It defines the common methods that must be available at any dbImplementation
 * in order to emulate the TPC-C benchmark. Basically, it defines one method for
 * each transaction available in the TPC-C.
 */
abstract public class dbTPCCDatabase extends DatabaseManager {

	private static Logger logger = Logger.getLogger(dbTPCCDatabase.class);

	/**
	 * It defines the behavior of the transaction new order.
	 * 
	 * @param OutInfo
	 *            it contains information in the format string mapping to objet
	 *            in order to build the transaction request
	 * @param String
	 *            the host id to which the client is attached to
	 * @return the result of the transaction
	 */
	public Object TraceNewOrderDB(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;		
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction new order.");

			Date NetStartTime = new java.util.Date();

			con = getConnection();

			InitTransaction(con, "tx neworder", "w");

			dbtrace = NewOrderDB(obj, con);

			CommitTransaction(con, "tx neworder", "w");

			Date NetFinishTime = new java.util.Date();

			processLog(NetStartTime, NetFinishTime, "commit", "w",
					"tx neworder");

			logger.info("Finishing transaction new order.");
		} catch (java.sql.SQLException sqlex) {
			cleanup(con, sqlex);
		} finally {
				returnConnection(con);
		}
		return (dbtrace);
	}

	/**
	 * It defines the behavior of the transaction delivery.
	 * 
	 * @param OutInfo
	 *            it contains information in the format string mapping to objet
	 *            in order to build the transaction request.
	 * @param String
	 *            the host id to which the client is attached to
	 * @return the result of the transaction
	 */
	public Object TraceDeliveryDB(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning trasaction delivery.");

			Date NetStartTime = new java.util.Date();

			con = getConnection(); 

			InitTransaction(con, "tx delivery", "w");

			dbtrace = DeliveryDB(obj, con);

			CommitTransaction(con, "tx delivery", "w");

			Date NetFinishTime = new java.util.Date();

			processLog(NetStartTime, NetFinishTime, "commit", "w",
					"tx delivery");

			logger.info("Finishing trasaction delivery.");
		} catch (java.sql.SQLException sqlex) {
			cleanup(con, sqlex);
		} finally {
			returnConnection(con);
		}
		return (dbtrace);
	}

	/**
	 * It defines the behavior of the transaction order status.
	 * 
	 * @param OutInfo
	 *            it contains information in the format string mapping to objet
	 *            in order to build the transaction request
	 * @param String
	 *            the host id to which the client is attached to
	 * @return the result of the transaction
	 */
	public Object TraceOrderStatusDB(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction order status.");

			String str = (String) (obj).get("cid");
			if (str.equals("0")) {
				Date NetStartTime = new java.util.Date();

				con = getConnection();
				
				InitTransaction(con, "tx orderstatus 01", "r");
				dbtrace = OrderStatusDB(obj, con);
				CommitTransaction(con, "tx orderstatus 01", "r");
				Date NetFinishTime = new java.util.Date();

				processLog(NetStartTime, NetFinishTime, "commit", "r",
						"tx orderstatus 01");

			} else {
				Date NetStartTime = new java.util.Date();

				con = getConnection();
				
				InitTransaction(con, "tx orderstatus 02", "r");
				dbtrace = OrderStatusDB(obj, con);
				CommitTransaction(con, "tx orderstatus 02", "r");

				Date NetFinishTime = new java.util.Date();

				processLog(NetStartTime, NetFinishTime, "commit", "r",
						"tx orderstatus 02");
			}

			logger.info("Finishing transaction order status.");
		} catch (java.sql.SQLException sqlex) {
			cleanup(con, sqlex);
		} finally {
				returnConnection(con);
		}
		return (dbtrace);
	}

	/**
	 * It defines the behavior of the transaction payment.
	 * 
	 * @param OutInfo
	 *            it contains information in the format string mapping to objet
	 *            in order to build the transaction request
	 * @param String
	 *            the host id to which the client is attached to
	 * @return the result of the transaction
	 */
	public Object TracePaymentDB(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction payment.");

			String str = (String) (obj).get("cid");
			if (str.equals("0")) {
				Date NetStartTime = new java.util.Date();

				con = getConnection();

				InitTransaction(con, "tx payment 01", "w");

				dbtrace = PaymentDB(obj, con);

				CommitTransaction(con, "tx payment 01", "w");

				Date NetFinishTime = new java.util.Date();

				processLog(NetStartTime, NetFinishTime, "commit", "w",
						"tx payment 01");

			} else {
				Date NetStartTime = new java.util.Date();

				con = getConnection();

				InitTransaction(con, "tx payment 02", "w");
				dbtrace = PaymentDB(obj, con);

				CommitTransaction(con, "tx payment 02", "w");

				Date NetFinishTime = new java.util.Date();

				processLog(NetStartTime, NetFinishTime, "commit", "w",
						"tx payment 02");

			}

			logger.info("Finishing transaction payment.");

		} catch (java.sql.SQLException sqlex) {
			cleanup(con, sqlex);
		} finally {
			returnConnection(con);
		}
		return (dbtrace);
	}

	/**
	 * It defines the behavior of the transaction stock level.
	 * 
	 * @param OutInfo
	 *            it contains information in the format string mapping to objet
	 *            in order to build the transaction request
	 * @param String
	 *            the host id to which the client is attached to
	 * @return the result of the transaction
	 */
	public Object TraceStockLevelDB(Properties obj, String hid)
			throws java.sql.SQLException {

		Connection con = null;
		HashSet dbtrace = null;

		try {
			logger.info("Beginning transaction stock level");

			Date NetStartTime = new java.util.Date();

			con = getConnection();

			InitTransaction(con, "tx stocklevel", "r");

			dbtrace = StockLevelDB(obj, con);

			CommitTransaction(con, "tx stocklevel", "r");

			Date NetFinishTime = new java.util.Date();

			processLog(NetStartTime, NetFinishTime, "commit", "r",
					"tx stocklevel");

			logger.info("Finishing transaction stock level");
		} catch (java.sql.SQLException sqlex) {
			cleanup(con, sqlex);
		} finally {
			returnConnection(con);
		}
		return (dbtrace);
	}
	
	private boolean isUnexpected(SQLException sqlex){
		if ((sqlex.getMessage().indexOf("serialize") == -1)
                                        && (sqlex.getMessage().indexOf("deadlock") == -1)
                                        && (sqlex.getMessage().indexOf("timeout") == -1)
                                        && (sqlex.getMessage().indexOf("Deadlock") == -1)
                                        && (sqlex.getMessage().indexOf("not found") == -1)
                                        && (sqlex.getMessage().indexOf("Generated Abort") == -1)
                                        && (sqlex.getMessage().indexOf("Exception in Java Side") == -1)
                                        && (sqlex.getMessage().indexOf("Before start of result set") == -1)
					&& (sqlex.getMessage().indexOf("transaction commit") == -1)) return true;
		else return false;
	}
	
	private void cleanup(Connection con, SQLException sqlex) throws SQLException {
		if (isUnexpected(sqlex)) {
			if (con != null) {
				try {
					con.close();
				} catch(SQLException ex) {
					logger.error("error while cleaning up, see next exception for root cause", ex);
				}
			}
			throw sqlex;
		}
	}

	protected abstract HashSet NewOrderDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract HashSet DeliveryDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract HashSet OrderStatusDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract HashSet PaymentDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract HashSet StockLevelDB(Properties obj, Connection con)
			throws java.sql.SQLException;

	protected abstract void InitTransaction(Connection con, String strTrans,
			String strAccess) throws java.sql.SQLException;

	protected abstract void CommitTransaction(Connection con, String strTrans,
			String strAccess) throws java.sql.SQLException;

	protected abstract void RollbackTransaction(Connection con,
			java.lang.Exception dump, String strTrans, String strAccess)
			throws java.sql.SQLException;
}

