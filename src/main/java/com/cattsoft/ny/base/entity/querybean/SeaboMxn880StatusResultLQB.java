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
public class SeaboMxn880StatusResultLQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Integer nodeid;
	private java.lang.Integer boardId;
	private java.lang.Integer parent;
	private java.lang.Integer port;
	private java.lang.Integer chargeVol;
	private java.lang.Integer battVol;
	private java.util.Date time;
	private java.lang.String uid;
	
	public void setNodeid(java.lang.Integer nodeid) {
		this.nodeid = nodeid;
	}
	
	public java.lang.Integer getNodeid() {
		return this.nodeid;
	}
	public void setBoardId(java.lang.Integer boardId) {
		this.boardId = boardId;
	}
	
	public java.lang.Integer getBoardId() {
		return this.boardId;
	}
	public void setParent(java.lang.Integer parent) {
		this.parent = parent;
	}
	
	public java.lang.Integer getParent() {
		return this.parent;
	}
	public void setPort(java.lang.Integer port) {
		this.port = port;
	}
	
	public java.lang.Integer getPort() {
		return this.port;
	}
	public void setChargeVol(java.lang.Integer chargeVol) {
		this.chargeVol = chargeVol;
	}
	
	public java.lang.Integer getChargeVol() {
		return this.chargeVol;
	}
	public void setBattVol(java.lang.Integer battVol) {
		this.battVol = battVol;
	}
	
	public java.lang.Integer getBattVol() {
		return this.battVol;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setUid(java.lang.String uid) {
		this.uid = uid;
	}
	
	public java.lang.String getUid() {
		return this.uid;
	}
}