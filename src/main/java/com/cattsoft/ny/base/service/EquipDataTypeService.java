/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.querybean.EquipDataTypeQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipDataTypeService {
	/**
	 * 增加equip_data_type信息
	 * 
	 * @param equipDataType equip_data_type信息
	 *
	 * @return equip_data_type标识
	 */
	Long createEquipDataType(EquipDataType equipDataType);

	/**
	 * 修改equip_data_type信息
	 * 
	 * @param equipDataType equip_data_type信息
	 */
	void updateEquipDataType(EquipDataType equipDataType);

	/**
	 * 删除equip_data_type信息
	 * 
	 * @param id equip_data_type标识
	 */
	void removeEquipDataType(Long id);

	/**
	 * 获取equip_data_type信息
	 * 
	 * @param id equip_data_type标识
	 * @return equip_data_type信息
	 */
	EquipDataType getEquipDataType(Long id);

	/**
	 * 获取所有equip_data_type
	 * 
	 * @return 所有equip_data_type信息的列表
	 */
	List<EquipDataType> getAllEquipDataTypes();
	
	/**
	 * 根据查询对象查询equip_data_type结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  equip_data_type记录列表
	 */
	List<EquipDataType> getEquipDataTypes(EquipDataTypeQB queryBean);

	/**
	 * 分页获取equip_data_type列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页equip_data_type列表
	 */
	PagingResultBean<List<EquipDataType>> getAllEquipDataTypesPaging(PagingQueryBean<EquipDataTypeQB> pagingQueryBean);
	/**
	 * 根据设备id 取数据采集值信息
	 * 
	 */
	List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo(Long equipId);
}
