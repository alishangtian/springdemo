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
public class Customer extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long custId;
	private java.lang.String name;
	private java.lang.String uuid;
	private java.lang.String contacts;
	private java.lang.String phone;
	private java.lang.String mobile;
	private java.lang.String email;
	private java.lang.String address;
	private java.util.Date ctime;
	private java.lang.String state;
	private java.lang.String remark;
	private java.lang.String homePageAddress;
	
	public void setCustId(java.lang.Long custId) {
		this.custId = custId;
	}
	
	public java.lang.Long getCustId() {
		return this.custId;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	
	public java.lang.String getUuid() {
		return this.uuid;
	}
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public java.lang.String getHomePageAddress() {
		return homePageAddress;
	}

	public void setHomePageAddress(java.lang.String homePageAddress) {
		this.homePageAddress = homePageAddress;
	}
	
}