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
public class EquipControlInfoHQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long equipportid;
	private java.lang.String name;
	private java.lang.String content;
	private java.util.Date time;
	private java.lang.Long userId;
	private java.lang.Long result;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setEquipportid(java.lang.Long equipportid) {
		this.equipportid = equipportid;
	}
	
	public java.lang.Long getEquipportid() {
		return this.equipportid;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setResult(java.lang.Long result) {
		this.result = result;
	}
	
	public java.lang.Long getResult() {
		return this.result;
	}
}