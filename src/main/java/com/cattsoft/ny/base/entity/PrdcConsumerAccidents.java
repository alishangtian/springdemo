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
public class PrdcConsumerAccidents extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long houseId;
	private java.lang.Long produceSeasonId;
	private java.lang.String detail;
	private java.lang.String complainant;
	private java.lang.String phone;
	private java.util.Date accidentsDate;
	private java.lang.String email;
	private java.lang.String state;
	private java.util.Date createDate;
	private java.lang.String operator;
	private java.lang.String solve;
	private java.util.Date solveDate;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
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
	public void setDetail(java.lang.String detail) {
		this.detail = detail;
	}
	
	public java.lang.String getDetail() {
		return this.detail;
	}
	public void setComplainant(java.lang.String complainant) {
		this.complainant = complainant;
	}
	
	public java.lang.String getComplainant() {
		return this.complainant;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setAccidentsDate(java.util.Date accidentsDate) {
		this.accidentsDate = accidentsDate;
	}
	
	public java.util.Date getAccidentsDate() {
		return this.accidentsDate;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setOperator(java.lang.String operator) {
		this.operator = operator;
	}
	
	public java.lang.String getOperator() {
		return this.operator;
	}
	public void setSolve(java.lang.String solve) {
		this.solve = solve;
	}
	
	public java.lang.String getSolve() {
		return this.solve;
	}
	public void setSolveDate(java.util.Date solveDate) {
		this.solveDate = solveDate;
	}
	
	public java.util.Date getSolveDate() {
		return this.solveDate;
	}
}