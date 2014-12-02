package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class UserFuncAlloc extends Entity {

	private static final long serialVersionUID = -4330258399697244619L;
	private Long userFuncAllocId;
	private Long userId;
	private Long funcNodeId;
	private String designate;
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;

	public Long getUserFuncAllocId() {
		return userFuncAllocId;
	}

	public void setUserFuncAllocId(Long userFuncAllocId) {
		this.userFuncAllocId = userFuncAllocId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFuncNodeId() {
		return funcNodeId;
	}

	public void setFuncNodeId(Long funcNodeId) {
		this.funcNodeId = funcNodeId;
	}

	public String getDesignate() {
		return designate;
	}

	public void setDesignate(String designate) {
		this.designate = designate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getStsTime() {
		return stsTime;
	}

	public void setStsTime(Timestamp stsTime) {
		this.stsTime = stsTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
