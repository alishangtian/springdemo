/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity;

import com.cattsoft.baseplatform.core.entity.Entity;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class ProduceThresholdSetup extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long thresholdInfoId;
	private java.lang.String type;
	private java.lang.String typePram;
	private java.lang.String name;
	private java.lang.String upperLimit;
	private java.lang.String downLimit;
	private java.lang.String conditions;
	private java.lang.String message;
	private java.util.Date createTime;
	private java.util.Date modifyTime;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setThresholdInfoId(java.lang.Long thresholdInfoId) {
		this.thresholdInfoId = thresholdInfoId;
	}
	
	public java.lang.Long getThresholdInfoId() {
		return this.thresholdInfoId;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setTypePram(java.lang.String typePram) {
		this.typePram = typePram;
	}
	
	public java.lang.String getTypePram() {
		return this.typePram;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setUpperLimit(java.lang.String upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	public java.lang.String getUpperLimit() {
		return this.upperLimit;
	}
	public void setDownLimit(java.lang.String downLimit) {
		this.downLimit = downLimit;
	}
	
	public java.lang.String getDownLimit() {
		return this.downLimit;
	}
	public void setConditions(java.lang.String conditions) {
		this.conditions = conditions;
	}
	
	public java.lang.String getConditions() {
		return this.conditions;
	}
	public void setMessage(java.lang.String message) {
		this.message = message;
	}
	
	public java.lang.String getMessage() {
		return this.message;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
}