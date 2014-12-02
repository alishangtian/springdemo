package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Date;

import com.cattsoft.baseplatform.core.entity.Entity;

public class RunSysConfig extends Entity {

	private static final long serialVersionUID = 1602864273862040570L;
	
	private Long configId = null;
	
	private String paramName= null;
	
	private String paramValue = null;
	
	private String sts = null;
	
	private Date stsTime;
	
	private Date createTime = null;
	
	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public Date getStsTime() {
		return stsTime;
	}

	public void setStsTime(Date stsTime) {
		this.stsTime = stsTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
