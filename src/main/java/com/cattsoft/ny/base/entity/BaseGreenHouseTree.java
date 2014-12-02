package com.cattsoft.ny.base.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

public class BaseGreenHouseTree extends Entity {

	private static final long serialVersionUID = 6633337142732550879L;
	private Long nodeTreeId;
	private String nodeTreeName;
	private Long parentId;
	private String descb;
	private String subSystem;
	private Long displayOrder;
	private boolean isParent;
	private String levelTree;
	/**
	 * 0-否 1-是
	 */
	private String isVirtualNode = "0";
	/**
	 * 当是控制器维护时：（0：所有节点 1：节点2：控制器）当是基地主页时：（0：所有基地 1：基地2：温室）
	 */
	private Integer type;
	private String isEquip;
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

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getIsEquip() {
		return isEquip;
	}

	public void setIsEquip(String isEquip) {
		this.isEquip = isEquip;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getLevelTree() {
		return levelTree;
	}

	public void setLevelTree(String levelTree) {
		this.levelTree = levelTree;
	}

	 

 
 

}
