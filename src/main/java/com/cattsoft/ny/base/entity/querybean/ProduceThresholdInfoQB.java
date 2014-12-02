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
public class ProduceThresholdInfoQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.String crops;
	private java.lang.String state;
	private java.util.Date createTime;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setCrops(java.lang.String crops) {
		this.crops = crops;
	}
	
	public java.lang.String getCrops() {
		return this.crops;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}