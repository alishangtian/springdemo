package com.cattsoft.baseplatform.func.sm.entity.query;

public class RoleFuncAuthQuery {
	private Long roleId;
	private Long funcNodeId;
	private Long nodeTreeId;
	private Long funcItemId;

	public Long getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(Long nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFuncNodeId() {
		return funcNodeId;
	}

	public void setFuncNodeId(Long funcNodeId) {
		this.funcNodeId = funcNodeId;
	}

	public Long getFuncItemId() {
		return funcItemId;
	}

	public void setFuncItemId(Long funcItemId) {
		this.funcItemId = funcItemId;
	}

}
