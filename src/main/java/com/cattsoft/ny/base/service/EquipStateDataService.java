/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateDataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipStateDataQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipStateDataService {
	/**
	 * 增加equip_state_data信息
	 * 
	 * @param equipStateData equip_state_data信息
	 *
	 * @return equip_state_data标识
	 */
	Long createEquipStateData(EquipStateData equipStateData);

	/**
	 * 修改equip_state_data信息
	 * 
	 * @param equipStateData equip_state_data信息
	 */
	void updateEquipStateData(EquipStateData equipStateData);

	/**
	 * 删除equip_state_data信息
	 * 
	 * @param id equip_state_data标识
	 */
	void removeEquipStateData(Long id);

	/**
	 * 获取equip_state_data信息
	 * 
	 * @param id equip_state_data标识
	 * @return equip_state_data信息
	 */
	EquipStateData getEquipStateData(Long id);

	/**
	 * 获取所有equip_state_data
	 * 
	 * @return 所有equip_state_data信息的列表
	 */
	List<EquipStateData> getAllEquipStateDatas();
	
	/**
	 * 根据查询对象查询equip_state_data结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  equip_state_data记录列表
	 */
	List<EquipStateData> getEquipStateDatas(EquipStateDataQB queryBean);
	List<EquipStateDataInfo> getEquipStateDatasInfo(EquipStateDataQB queryBean);

	/**
	 * 分页获取equip_state_data列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页equip_state_data列表
	 */
	PagingResultBean<List<EquipStateData>> getAllEquipStateDatasPaging(PagingQueryBean<EquipStateDataQB> pagingQueryBean);
}
