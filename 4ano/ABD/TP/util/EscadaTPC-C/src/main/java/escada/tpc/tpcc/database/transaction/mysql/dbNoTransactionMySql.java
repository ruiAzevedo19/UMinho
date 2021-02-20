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

package escada.tpc.tpcc.database.transaction.mysql;

import java.sql.Connection;
import java.util.Properties;

/**
 * It is an interface to a MySQL, which based is based on the the
 * distributions of the TPC-C.
 */
public class dbNoTransactionMySql extends dbTransactionMySql {

	protected void InitTransaction(Properties obj, Connection con,
			String transaction) throws java.sql.SQLException {
	}

	protected void CommitTransaction(Connection con)
			throws java.sql.SQLException {
	}

	protected void RollbackTransaction(Connection con, Exception dump)
			throws java.sql.SQLException {
	}
}
