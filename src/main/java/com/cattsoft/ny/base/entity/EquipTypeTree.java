package com.cattsoft.ny.base.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

public class EquipTypeTree extends Entity {

	private static final long serialVersionUID = 6633337142732550879L;
	private Long nodeTreeId;
	private String nodeTreeName;
	private Long parentId;
	private String descb;
	private String subSystem;
	private Long displayOrder;
	private String isVirtualNode = "0";
	private Long level;

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

	public String getIsVirtualNode() {
		return isVirtualNode;
	}

	public void setIsVirtualNode(String isVirtualNode) {
		this.isVirtualNode = isVirtualNode;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

}
