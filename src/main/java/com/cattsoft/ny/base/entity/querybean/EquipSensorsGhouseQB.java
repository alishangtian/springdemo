/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity.querybean;

import java.util.Date;

import com.cattsoft.baseplatform.core.entity.QueryBean;
import com.cattsoft.ny.base.entity.EquipInfo;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class EquipSensorsGhouseQB extends QueryBean {
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;
	private java.lang.Long baseId;
	private java.lang.Long houseId;
	private java.lang.Long equipInfoId;
	private java.lang.Long custId;
	private java.lang.String type;
	private EquipInfo equipInfo;
	private String status;// o 停用 1 在用
	private Date startTime;// 使用时间
	private Date stopTime;// 停用时间
	/**
	 * 父设备类型
	 */

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setBaseId(java.lang.Long baseId) {
		this.baseId = baseId;
	}

	public java.lang.Long getBaseId() {
		return this.baseId;
	}

	public void setHouseId(java.lang.Long houseId) {
		this.houseId = houseId;
	}

	public java.lang.Long getHouseId() {
		return this.houseId;
	}

	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}

	public java.lang.Long getEquipInfoId() {
		return this.equipInfoId;
	}

	public void setCustId(java.lang.Long custId) {
		this.custId = custId;
	}

	public java.lang.Long getCustId() {
		return this.custId;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getType() {
		return this.type;
	}

	public EquipInfo getEquipInfo() {
		return equipInfo;
	}

	public void setEquipInfo(EquipInfo equipInfo) {
		this.equipInfo = equipInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

 
 

}