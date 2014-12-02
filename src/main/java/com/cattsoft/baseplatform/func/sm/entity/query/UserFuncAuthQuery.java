package com.cattsoft.baseplatform.func.sm.entity.query;

public class UserFuncAuthQuery {
	private Long userId;
	private Long funcNodeId;
	private Long nodeTreeId;
	private Long funcItemId;
	private String designate;

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

	public Long getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(Long nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
	}

	public Long getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(Long funcItemId) {
		this.funcItemId = funcItemId;
	}

	public String getDesignate() {
		return designate;
	}

	public void setDesignate(String designate) {
		this.designate = designate;
	}

}
