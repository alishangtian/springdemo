package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.ny.base.entity.EquipAttribute;
import com.cattsoft.ny.base.persistence.EquipAttributeMapper;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipAttributeComponent {

	@Autowired
	private EquipAttributeMapper equipAttributeMapper;

	/**
	 * 插入一条设备属性数据
	 * 
	 * @Title: createEquipAttribute
	 * @author Mao Xiaobing
	 * @return Long 返回类型
	 * @throws
	 */
	public Long createEquipAttribute(EquipAttribute equipAttribute) {
		equipAttributeMapper.insert(equipAttribute);
		return equipAttribute.getId();
	}

	/**
	 * 删除一条设备属性数据
	 * 
	 * @Title: removeEquipAttribute
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void removeEquipAttribute(Long id) {
		equipAttributeMapper.delete(id);
	}

	/**
	 * 更新设备属性
	 * 
	 * @Title: updateEquipAttribute
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void updateEquipAttribute(EquipAttribute equipAttribute) {
		equipAttributeMapper.update(equipAttribute);
	}

	/**
	 * 查询一条设备属性信息
	 * 
	 * @Title: getEquipAttribute
	 * @author Mao Xiaobing
	 * @return EquipAttribute 返回类型
	 * @throws
	 */
	public EquipAttribute getEquipAttribute(Long id) {
		return equipAttributeMapper.select(id);
	}

	/**
	 * 查询设备属性列表
	 * 
	 * @Title: getEquipAttributes
	 * @author Mao Xiaobing
	 * @return List<EquipAttribute> 返回类型
	 * @throws
	 */
	public List<EquipAttribute> getEquipAttributes(EquipAttribute equipAttribute) {
		return equipAttributeMapper.selectList(equipAttribute);
	}

	/**
	 * 获取所有的设备属性值
	 * 
	 * @Title: getAllEquipAttributes
	 * @author Mao Xiaobing
	 * @return List<EquipAttribute> 返回类型
	 * @throws
	 */
	public List<EquipAttribute> getAllEquipAttributes() {
		return equipAttributeMapper.selectList(null);
	}

	/**
	 * 根据设备id和属性名称获取设备属性
	 * 
	 * @Title: getEquipAttributeByEqidAndName
	 * @author Mao Xiaobing
	 * @return EquipAttribute 返回类型
	 * @throws
	 */
	public EquipAttribute getEquipAttributeByEqidAndName(EquipAttribute ea) {
		return equipAttributeMapper.selectByEqidAndName(ea);
	}

	public EquipAttributeMapper getEquipAttributeMapper() {
		return equipAttributeMapper;
	}

	public void setEquipAttributeMapper(
			EquipAttributeMapper equipAttributeMapper) {
		this.equipAttributeMapper = equipAttributeMapper;
	}
}
