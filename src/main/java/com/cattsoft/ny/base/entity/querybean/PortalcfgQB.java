/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity.querybean;

import com.cattsoft.baseplatform.core.entity.QueryBean;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class PortalcfgQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long portalId;
	private java.lang.Long custId;
	private java.lang.String databaseIp;
	private java.lang.String serverName;
	private java.lang.String databaseName;
	private java.lang.String userName;
	private java.lang.String userPasswd;
	private java.lang.String instanceType;
	private java.util.Date createDate;
	private java.lang.String sts;
	private java.lang.String remark;
	private java.lang.String uuid;
	
	public void setPortalId(java.lang.Long portalId) {
		this.portalId = portalId;
	}
	
	public java.lang.Long getPortalId() {
		return this.portalId;
	}
	public void setCustId(java.lang.Long custId) {
		this.custId = custId;
	}
	
	public java.lang.Long getCustId() {
		return this.custId;
	}
	public void setDatabaseIp(java.lang.String databaseIp) {
		this.databaseIp = databaseIp;
	}
	
	public java.lang.String getDatabaseIp() {
		return this.databaseIp;
	}
	public void setServerName(java.lang.String serverName) {
		this.serverName = serverName;
	}
	
	public java.lang.String getServerName() {
		return this.serverName;
	}
	public void setDatabaseName(java.lang.String databaseName) {
		this.databaseName = databaseName;
	}
	
	public java.lang.String getDatabaseName() {
		return this.databaseName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setUserPasswd(java.lang.String userPasswd) {
		this.userPasswd = userPasswd;
	}
	
	public java.lang.String getUserPasswd() {
		return this.userPasswd;
	}
	public void setInstanceType(java.lang.String instanceType) {
		this.instanceType = instanceType;
	}
	
	public java.lang.String getInstanceType() {
		return this.instanceType;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setSts(java.lang.String sts) {
		this.sts = sts;
	}
	
	public java.lang.String getSts() {
		return this.sts;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	
	public java.lang.String getUuid() {
		return this.uuid;
	}
}