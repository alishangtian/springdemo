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
public class MsgAlarmQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long alarmId;
	private java.lang.Long alarmTypeId;
	private java.lang.Long sensorId;
	private java.lang.Long houseId;
	private java.lang.Long equipStateTypeId;
	private java.lang.String alarmLevel;
	private java.lang.String detail;
	private java.lang.String state;
	private java.util.Date time;
	private java.util.Date ctime;
	private java.lang.String dealingUser;
	private java.lang.String remark;
	private Long custId;
	
	public void setAlarmId(java.lang.Long alarmId) {
		this.alarmId = alarmId;
	}
	
	public java.lang.Long getAlarmId() {
		return this.alarmId;
	}
	public void setAlarmTypeId(java.lang.Long alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}
	
	public java.lang.Long getAlarmTypeId() {
		return this.alarmTypeId;
	}
	public void setSensorId(java.lang.Long sensorId) {
		this.sensorId = sensorId;
	}
	
	public java.lang.Long getSensorId() {
		return this.sensorId;
	}
	public void setHouseId(java.lang.Long houseId) {
		this.houseId = houseId;
	}
	
	public java.lang.Long getHouseId() {
		return this.houseId;
	}
	public void setEquipStateTypeId(java.lang.Long equipStateTypeId) {
		this.equipStateTypeId = equipStateTypeId;
	}
	
	public java.lang.Long getEquipStateTypeId() {
		return this.equipStateTypeId;
	}
	public void setAlarmLevel(java.lang.String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	
	public java.lang.String getAlarmLevel() {
		return this.alarmLevel;
	}
	public void setDetail(java.lang.String detail) {
		this.detail = detail;
	}
	
	public java.lang.String getDetail() {
		return this.detail;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
	}
	public void setDealingUser(java.lang.String dealingUser) {
		this.dealingUser = dealingUser;
	}
	
	public java.lang.String getDealingUser() {
		return this.dealingUser;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}
	
}