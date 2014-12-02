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
public class EquipNetTopologyQB extends QueryBean {
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;
	private java.lang.Long equipInfoId;
	private java.lang.Long parentNodeId;
	private java.lang.Integer nodeOrder;
	private java.lang.String type;
	private Integer nodeDeep;
	private Integer parentPort;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}

	public java.lang.Long getEquipInfoId() {
		return this.equipInfoId;
	}

	public void setParentNodeId(java.lang.Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public java.lang.Long getParentNodeId() {
		return this.parentNodeId;
	}

	public void setNodeOrder(java.lang.Integer nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public java.lang.Integer getNodeOrder() {
		return this.nodeOrder;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getType() {
		return this.type;
	}

	public Integer getNodeDeep() {
		return nodeDeep;
	}

	public void setNodeDeep(Integer nodeDeep) {
		this.nodeDeep = nodeDeep;
	}

	public Integer getParentPort() {
		return parentPort;
	}

	public void setParentPort(Integer parentPort) {
		this.parentPort = parentPort;
	}

}