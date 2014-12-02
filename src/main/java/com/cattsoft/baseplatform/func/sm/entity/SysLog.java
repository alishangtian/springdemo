package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class SysLog extends Entity {

	private static final long serialVersionUID = 1L;
	private Long sysLogId;
	private Long logCategory;
	private String logLevel;
	private String logOperator;
	private String logContent;
	private Timestamp logTime;

	public Long getSysLogId() {
		return sysLogId;
	}

	public void setSysLogId(Long sysLogId) {
		this.sysLogId = sysLogId;
	}

	public Long getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(Long logCategory) {
		this.logCategory = logCategory;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogOperator() {
		return logOperator;
	}

	public void setLogOperator(String logOperator) {
		this.logOperator = logOperator;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

}
