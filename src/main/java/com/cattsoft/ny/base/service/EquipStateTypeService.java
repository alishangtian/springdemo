/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.querybean.EquipStateTypeQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipStateTypeService {
	/**
	 * 增加equip_state_type信息
	 * 
	 * @param equipStateType equip_state_type信息
	 *
	 * @return equip_state_type标识
	 */
	Long createEquipStateType(EquipStateType equipStateType);

	/**
	 * 修改equip_state_type信息
	 * 
	 * @param equipStateType equip_state_type信息
	 */
	void updateEquipStateType(EquipStateType equipStateType);

	/**
	 * 删除equip_state_type信息
	 * 
	 * @param id equip_state_type标识
	 */
	void removeEquipStateType(Long id);

	/**
	 * 获取equip_state_type信息
	 * 
	 * @param id equip_state_type标识
	 * @return equip_state_type信息
	 */
	EquipStateType getEquipStateType(Long id);

	/**
	 * 获取所有equip_state_type
	 * 
	 * @return 所有equip_state_type信息的列表
	 */
	List<EquipStateType> getAllEquipStateTypes();
	
	/**
	 * 根据查询对象查询equip_state_type结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  equip_state_type记录列表
	 */
	List<EquipStateType> getEquipStateTypes(EquipStateTypeQB queryBean);

	/**
	 * 分页获取equip_state_type列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页equip_state_type列表
	 */
	PagingResultBean<List<EquipStateType>> getAllEquipStateTypesPaging(PagingQueryBean<EquipStateTypeQB> pagingQueryBean);
}
