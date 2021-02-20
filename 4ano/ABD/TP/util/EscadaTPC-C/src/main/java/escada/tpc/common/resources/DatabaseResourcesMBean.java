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

package escada.tpc.common.resources;

public interface DatabaseResourcesMBean {

	public abstract String getDriver();

	public abstract void setDriver(String driver);

	public abstract String getConnectionString();

	public abstract void setConnectionString(String connString);

	public abstract String getUserName();

	public abstract void setUserName(String userName);

	public abstract String getPassword();

	public abstract void setPassword(String password);

}