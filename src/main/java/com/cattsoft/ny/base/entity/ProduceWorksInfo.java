/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class ProduceWorksInfo extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	/**
	 * 负责人标示
	 */
	private java.lang.Long userId;
	/**
	 * 温室编号
	 */
	private java.lang.Long houseId;
	/**
	 * 种植季编号
	 */
	private java.lang.Long produceSeasonId;
	/**
	 * 操作类型
	 */
	private java.lang.String workType;
	/**
	 * 品类
	 */
	private java.lang.String type;
	/**
	 * 数量
	 */
	private java.lang.String amount;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private java.lang.String remark;
	private java.lang.String produceSeasonName;
	private java.lang.String typeName;
	private String userName;
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setHouseId(java.lang.Long houseId) {
		this.houseId = houseId;
	}
	
	public java.lang.Long getHouseId() {
		return this.houseId;
	}
	public void setProduceSeasonId(java.lang.Long produceSeasonId) {
		this.produceSeasonId = produceSeasonId;
	}
	
	public java.lang.Long getProduceSeasonId() {
		return this.produceSeasonId;
	}
	public void setWorkType(java.lang.String workType) {
		this.workType = workType;
	}
	
	public java.lang.String getWorkType() {
		return this.workType;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setAmount(java.lang.String amount) {
		this.amount = amount;
	}
	
	public java.lang.String getAmount() {
		return this.amount;
	}
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public java.lang.String getProduceSeasonName() {
		return produceSeasonName;
	}

	public void setProduceSeasonName(java.lang.String produceSeasonName) {
		this.produceSeasonName = produceSeasonName;
	}

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}