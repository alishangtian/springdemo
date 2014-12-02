/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity.querybean;

import java.util.Date;

import com.cattsoft.baseplatform.core.entity.QueryBean;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class ProduceWorksInfoQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Long houseId;
	private Long produceSeasonId;
	private String workType;
	private String type;
	private String amount;
	private Date beginTime;
	private Date endTime;
	private String remark;
	private String produceSeasonName;
	private String typeName;
	private String userName;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	
	public Long getHouseId() {
		return this.houseId;
	}
	public void setProduceSeasonId(Long produceSeasonId) {
		this.produceSeasonId = produceSeasonId;
	}
	
	public Long getProduceSeasonId() {
		return this.produceSeasonId;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	
	public String getWorkType() {
		return this.workType;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAmount() {
		return this.amount;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getProduceSeasonName() {
		return produceSeasonName;
	}

	public void setProduceSeasonName(String produceSeasonName) {
		this.produceSeasonName = produceSeasonName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}