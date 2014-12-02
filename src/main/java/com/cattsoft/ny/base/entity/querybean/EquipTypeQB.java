package com.cattsoft.ny.base.entity.querybean;

import com.cattsoft.baseplatform.core.entity.QueryBean;

/**
 * 设备类型查询bean
 * 
 * @author John
 * 
 */
public class EquipTypeQB extends QueryBean {

	private static final long serialVersionUID = 7687605392301451387L;

	private Long id;// id
	private String type;// 设备类型（ex：网关，控制器（无父节点）or （风机，加湿器等有父节点控制器））
	private Long parentId;// 父节点id（ex:网关，控制器的id）
	private String description;// 设备类型描述
	private int level = 0;// 设备级别，默认是0

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}