/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataEnvdataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipDataEnvdataService {
	/**
	 * 增加equip_data_envdata信息
	 * 
	 * @param equipDataEnvdata equip_data_envdata信息
	 *
	 * @return equip_data_envdata标识
	 */
	Long createEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata);

	/**
	 * 修改equip_data_envdata信息
	 * 
	 * @param equipDataEnvdata equip_data_envdata信息
	 */
	void updateEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata);

	/**
	 * 删除equip_data_envdata信息
	 * 
	 * @param dataId equip_data_envdata标识
	 */
	void removeEquipDataEnvdata(Long dataId);

	/**
	 * 获取equip_data_envdata信息
	 * 
	 * @param dataId equip_data_envdata标识
	 * @return equip_data_envdata信息
	 */
	EquipDataEnvdata getEquipDataEnvdata(Long dataId);

	/**
	 * 获取所有equip_data_envdata
	 * 
	 * @return 所有equip_data_envdata信息的列表
	 */
	List<EquipDataEnvdata> getAllEquipDataEnvdatas();
	
	/**
	 * 根据查询对象查询equip_data_envdata结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  equip_data_envdata记录列表
	 */
	List<EquipDataEnvdata> getEquipDataEnvdatas(EquipDataEnvdataQB queryBean);
	List<EquipDataEnvdata> getEquipDataEnvdatasTime(EquipDataEnvdataQB queryBean);
	List<EquipDataEnvdataInfo> getEquipDataEnvdatasInfo(Long typeid);
	/**
	 * 分页获取equip_data_envdata列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页equip_data_envdata列表
	 */
	PagingResultBean<List<EquipDataEnvdata>> getAllEquipDataEnvdatasPaging(PagingQueryBean<EquipDataEnvdataQB> pagingQueryBean);
	
	List<EquipDataEnvdata> getMaxMinData(EquipDataEnvdataQB queryBean);
}
