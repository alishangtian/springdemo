package com.cattsoft.baseplatform.func.sm.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

public class FuncTree extends Entity {

	private static final long serialVersionUID = 6633337142732550879L;
	private Long nodeTreeId;
	private String nodeTreeName;
	private Long parentId;
	private String descb;
	private String subSystem;
	private Long displayOrder;

	public Long getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(Long nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
	}

	public String getNodeTreeName() {
		return nodeTreeName;
	}

	public void setNodeTreeName(String nodeTreeName) {
		this.nodeTreeName = nodeTreeName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescb() {
		return descb;
	}

	public void setDescb(String descb) {
		this.descb = descb;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}
