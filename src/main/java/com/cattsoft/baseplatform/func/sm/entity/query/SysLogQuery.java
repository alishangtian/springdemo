package com.cattsoft.baseplatform.func.sm.entity.query;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.core.entity.QueryBean;

public class SysLogQuery extends QueryBean {

	private static final long serialVersionUID = 1L;
	private Long logCategory;
	private String logOperator;
	private String logLevel;
	private Timestamp startTime;
	private Timestamp endTime;

	public Long getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(Long logCategory) {
		this.logCategory = logCategory;
	}

	public String getLogOperator() {
		return StringUtils.isBlank(logOperator) ? null : logOperator.trim();
	}

	public void setLogOperator(String logOperator) {
		this.logOperator = logOperator;
	}

	public String getLogLevel() {
		return StringUtils.isBlank(logLevel) ? null : logLevel.trim();
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}
