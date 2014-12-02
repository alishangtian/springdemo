package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class FuncItem extends Entity {

	private static final long serialVersionUID = -5183533540376428770L;
	private Long funcItemId;
	private Long funcNodeId;
	private String funcItemName;
	private String funcItemDesc;
	private String actionName;
	private String methodName;
	private String sts;
	private Timestamp stsTime;

	public Long getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(Long funcItemId) {
		this.funcItemId = funcItemId;
	}

	public Long getFuncNodeId() {
		return funcNodeId;
	}

	public void setFuncNodeId(Long funcNodeId) {
		this.funcNodeId = funcNodeId;
	}

	public String getFuncItemName() {
		return funcItemName;
	}

	public void setFuncItemName(String funcItemName) {
		this.funcItemName = funcItemName;
	}

	public String getFuncItemDesc() {
		return funcItemDesc;
	}

	public void setFuncItemDesc(String funcItemDesc) {
		this.funcItemDesc = funcItemDesc;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

}
