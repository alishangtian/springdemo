/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipControlInfoH;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoHQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipControlInfoHService {
	/**
	 * 增加equip_control_info_h信息
	 * 
	 * @param equipControlInfoH equip_control_info_h信息
	 *
	 * @return equip_control_info_h标识
	 */
	Long createEquipControlInfoH(EquipControlInfoH equipControlInfoH);

	/**
	 * 修改equip_control_info_h信息
	 * 
	 * @param equipControlInfoH equip_control_info_h信息
	 */
	void updateEquipControlInfoH(EquipControlInfoH equipControlInfoH);

	/**
	 * 删除equip_control_info_h信息
	 * 
	 * @param id equip_control_info_h标识
	 */
	void removeEquipControlInfoH(Long id);

	/**
	 * 获取equip_control_info_h信息
	 * 
	 * @param id equip_control_info_h标识
	 * @return equip_control_info_h信息
	 */
	EquipControlInfoH getEquipControlInfoH(Long id);

	/**
	 * 获取所有equip_control_info_h
	 * 
	 * @return 所有equip_control_info_h信息的列表
	 */
	List<EquipControlInfoH> getAllEquipControlInfoHs();
	
	/**
	 * 根据查询对象查询equip_control_info_h结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  equip_control_info_h记录列表
	 */
	List<EquipControlInfoH> getEquipControlInfoHs(EquipControlInfoHQB queryBean);

	/**
	 * 分页获取equip_control_info_h列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页equip_control_info_h列表
	 */
	PagingResultBean<List<EquipControlInfoH>> getAllEquipControlInfoHsPaging(PagingQueryBean<EquipControlInfoHQB> pagingQueryBean);
}
