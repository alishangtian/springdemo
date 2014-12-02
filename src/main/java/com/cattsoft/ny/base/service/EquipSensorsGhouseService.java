/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipSensorsGhouseService {
	/**
	 * 增加equip_sensors_ghouse信息
	 * 
	 * @param equipSensorsGhouse
	 *            equip_sensors_ghouse信息
	 * 
	 * @return equip_sensors_ghouse标识
	 */
	Long createEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse);

	/**
	 * 修改equip_sensors_ghouse信息
	 * 
	 * @param equipSensorsGhouse
	 *            equip_sensors_ghouse信息
	 */
	void updateEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse);

	/**
	 * 删除equip_sensors_ghouse信息
	 * 
	 * @param id
	 *            equip_sensors_ghouse标识
	 */
	void removeEquipSensorsGhouse(Long id);

	/**
	 * 根据设备id删除温室传感器中间表信息
	 * 
	 * @Title: removeEquipSensorsByEquipId
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	void removeEquipSensorsByEquipId(Long equipId);

	/**
	 * 获取equip_sensors_ghouse信息
	 * 
	 * @param id
	 *            equip_sensors_ghouse标识
	 * @return equip_sensors_ghouse信息
	 */
	EquipSensorsGhouse getEquipSensorsGhouse(Long id);

	/**
	 * 获取所有equip_sensors_ghouse
	 * 
	 * @return 所有equip_sensors_ghouse信息的列表
	 */
	List<EquipSensorsGhouse> getAllEquipSensorsGhouses();

	/**
	 * 根据查询对象查询equip_sensors_ghouse结果列表
	 * 
	 * @param queryBean
	 *            查询对象
	 * 
	 * @return equip_sensors_ghouse记录列表
	 */
	List<EquipSensorsGhouse> getEquipSensorsGhouses(
			EquipSensorsGhouseQB queryBean);

	/**
	 * 分页获取equip_sensors_ghouse列表
	 * 
	 * @param pagingQueryBean
	 *            分页查询对象
	 * 
	 * @return 分页equip_sensors_ghouse列表
	 */
	PagingResultBean<List<EquipSensorsGhouse>> getAllEquipSensorsGhousesPaging(
			PagingQueryBean<EquipSensorsGhouseQB> pagingQueryBean);

	List<EquipInfo> getAllEquipDetail(EquipSensorsGhouseQB queryBean);

	/**
	 * 获取一个中间信息
	 * 
	 * @Title: findOneEquipSensorsGhouse
	 * @author Mao Xiaobing
	 * @return EquipSensorsGhouse 返回类型
	 * @throws
	 */
	EquipSensorsGhouse findOneEquipSensorsGhouse(EquipSensorsGhouseQB queryBean);

	/**
	 * 根据父标签类型获从设备温室中间表中获取设备名称
	 * 
	 * @Title: getEquipSensorGhouseControlEquip
	 * @author Mao Xiaobing
	 * @return List<EquipInfo> 返回类型
	 * @throws
	 */
	public List<EquipInfo> getEquipSensorGhouseControlEquip(
			EquipSensorsGhouseQB query);

}
