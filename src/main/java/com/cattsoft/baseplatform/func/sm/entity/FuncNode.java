package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.Entity;

public class FuncNode extends Entity {

	private static final long serialVersionUID = -33933256570098568L;
	private Long funcNodeId;
	private Long nodeTreeId;
	private String funcNodeCode;
	private String funcNodeName;
	private String funcNodeDesc;
	private String funcNodeUrl;
	private String sts;
	private Timestamp stsTime;
	private String actionName;
	private String methodName;
	private Long displayOrder;

	// 功能点包含的功能操作
	private List<FuncItem> funcItemList = new ArrayList<FuncItem>();

	public List<FuncItem> getFuncItemList() {
		return funcItemList;
	}

	public void setFuncItemList(List<FuncItem> funcItemList) {
		this.funcItemList = funcItemList;
	}

	public Long getFuncNodeId() {
		return funcNodeId;
	}

	public void setFuncNodeId(Long funcNodeId) {
		this.funcNodeId = funcNodeId;
	}

	public Long getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(Long nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
	}

	public String getFuncNodeCode() {
		return funcNodeCode;
	}

	public void setFuncNodeCode(String funcNodeCode) {
		this.funcNodeCode = funcNodeCode;
	}

	public String getFuncNodeName() {
		return funcNodeName;
	}

	public void setFuncNodeName(String funcNodeName) {
		this.funcNodeName = funcNodeName;
	}

	public String getFuncNodeDesc() {
		return funcNodeDesc;
	}

	public void setFuncNodeDesc(String funcNodeDesc) {
		this.funcNodeDesc = funcNodeDesc;
	}

	public String getFuncNodeUrl() {
		return funcNodeUrl;
	}

	public void setFuncNodeUrl(String funcNodeUrl) {
		this.funcNodeUrl = funcNodeUrl;
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

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}
