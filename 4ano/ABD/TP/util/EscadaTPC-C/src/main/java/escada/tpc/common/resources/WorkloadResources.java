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

import escada.tpc.common.TPCConst;
import escada.tpc.tpcc.TPCCConst;
import escada.tpc.tpcc.database.populate.jmx.DatabasePopulate;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WorkloadResources implements WorkloadResourcesMBean {

	private static final Logger logger = Logger.getLogger(WorkloadResources.class);
	
	public final static String DEFAULT_NUMBER_OF_WAREHOUSES = "4";
	
	private int numberOfWarehouses;



    private String ebClass;
    private String stClass;
    private String dbClass;
    private Integer poolSize;
    private boolean connectionPoolEnabled;
    private int rampUpTime;
    private int measurementTime;
    private int rampDownTime;
    private boolean thinkTime;
    private boolean resubmit;
    private String prefix;
    private Integer clients;
    private Integer frag;
    private Integer hostId;
    private String trace;

    private Integer probNewOrder;
    private Integer probOrderStatus;
    private Integer probPayment; 
    private Integer probDelivery;
    private Integer probStockLevel;

    public WorkloadResources() {
		
		InputStream inStream = DatabasePopulate.class.getResourceAsStream("/workload-config.properties");
		Properties props = new Properties();
		try {
			props.load(inStream);
			inStream.close();
		} catch (Exception e) {
			logger.fatal("Unable to load properties from file (workload-config.properties). Using defaults!", e);
		}

		TPCConst.setNumMinClients(Integer.parseInt(props.getProperty("tpcc.numclients", Integer.toString(TPCCConst.getCliWareHouse()))));
		TPCCConst.setNumCustomer(Integer.parseInt(props.getProperty("tpcc.numcustomers", Integer.toString(TPCCConst.getNumCustomer()))));
		TPCCConst.setNumDistrict(Integer.parseInt(props.getProperty("tpcc.numdistricts", Integer.toString(TPCCConst.getNumDistrict()))));
		TPCCConst.setNumItem(Integer.parseInt(props.getProperty("tpcc.numitems", Integer.toString(TPCCConst.getNumItem()))));
		TPCCConst.setNumLastName(Integer.parseInt(props.getProperty("tpcc.numnames", Integer.toString(TPCCConst.getNumLastName()))));
		
		this.numberOfWarehouses = Integer.parseInt(props.getProperty("tpcc.number.warehouses", DEFAULT_NUMBER_OF_WAREHOUSES));

        this.ebClass= props.getProperty("eb.class");
        this.stClass=props.getProperty("st.class");
        this.dbClass=props.getProperty("db.class");
        this.trace=props.getProperty("trace.flag","TRACE");

        this.poolSize=new Integer(props.getProperty("pool.size","0"));
        this.connectionPoolEnabled = new Boolean(props.getProperty("pool.enabled","true"));

        this.thinkTime = new Boolean(props.getProperty("measurement.think.time"));
        this.rampUpTime=new Integer(props.getProperty("measurement.rampUp.time","600")).intValue();
        this.rampDownTime=new Integer(props.getProperty("measurement.rampDown.time","300")).intValue();
        this.measurementTime=new Integer(props.getProperty("measurement.time","1800")).intValue();

        this.resubmit=new Boolean(props.getProperty("resubmit"));

        this.prefix=props.getProperty("prefix");
        this.clients=new Integer(props.getProperty("clients", "-1"));
        this.frag=new Integer(props.getProperty("frag"));
        this.hostId=new Integer(props.getProperty("hostId"));

        if (this.clients < 0)
            this.clients = this.numberOfWarehouses*this.getNumMinClients();

	this.probNewOrder=new Integer(props.getProperty("tpcc.prob.NewOrder","45")).intValue();
	this.probPayment=new Integer(props.getProperty("tpcc.prob.Payment","43")).intValue();
	this.probOrderStatus=new Integer(props.getProperty("tpcc.prob.OrderStatus","4")).intValue();
	this.probDelivery=new Integer(props.getProperty("tpcc.prob.Delivery","4")).intValue();
	this.probStockLevel=new Integer(props.getProperty("tpcc.prob.StockLevel","4")).intValue();
	}

	public synchronized void setNumberOfWarehouses(int numberOfWarehouses) {
		this.numberOfWarehouses = numberOfWarehouses;
	}

	public synchronized int getNumberOfWarehouses() {
		return numberOfWarehouses;
	}

	public int getNumMinClients() {
		return TPCConst.getNumMinClients();
	}

	public void setNumMinClients(int i) {
		TPCConst.setNumMinClients(i);
	}

    public String getEbClass() {
        return ebClass;
    }

    public void setEbClass(String ebClass) {
        this.ebClass = ebClass;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public String getDbClass() {
        return dbClass;
    }

    public void setDbClass(String dbClass) {
        this.dbClass = dbClass;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public boolean isConnectionPoolEnabled() {
        return connectionPoolEnabled;
    }

    public void setConnectionPoolEnabled(Boolean connectionPoolEnabled) {
        this.connectionPoolEnabled = connectionPoolEnabled;
    }

    public int getRampUpTime() {
        return rampUpTime;
    }

    public void setRampUpTime(int rampUpTime) {
        this.rampUpTime = rampUpTime;
    }

    public int getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(int measurementTime) {
        this.measurementTime = measurementTime;
    }

    public int getRampDownTime() {
        return rampDownTime;
    }

    public void setRampDownTime(int rampDownTime) {
        this.rampDownTime = rampDownTime;
    }

    public boolean isThinkTime() {
        return thinkTime;
    }

    public void setThinkTime(boolean thinkTime) {
        this.thinkTime = thinkTime;
    }

    public boolean isResubmit() {
        return resubmit;
    }

    public void setResubmit(boolean resubmit) {
        this.resubmit = resubmit;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getClients() {
        return clients;
    }

    public void setClients(Integer clients) {
        this.clients = clients;
    }

    public Integer getFrag() {
        return frag;
    }

    public void setFrag(Integer frag) {
        this.frag = frag;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

   public Integer getProbNewOrder(){
		return probNewOrder;
	}
	public void setProbNewOrder(Integer prob){
		this.probNewOrder = prob;
	}

	public Integer getProbOrderStatus(){
		return probOrderStatus;
	}
	public void setProbOrderStatus(Integer prob){
		this.probOrderStatus = prob;
	}

	public Integer getProbPayment(){
		return probPayment;
	}
	public void setProbPayment(Integer prob){
		this.probPayment = prob;
	}

	public Integer getProbDelivery(){
		return probDelivery;
	}
	public void setProbDelivery(Integer prob){
		this.probDelivery = prob;
	}

	public Integer getProbStockLevel(){
		return probStockLevel;
	}
	public void setProbStockLevel(Integer prob){
		this.probStockLevel = prob;
	}
}
