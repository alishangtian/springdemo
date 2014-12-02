/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.persistence.EquipSensorsGhouseMapper;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipSensorsGhouseComponent {

	@Autowired
	private EquipSensorsGhouseMapper equipSensorsGhouseMapper;

	/**
	 * 插入equip_sensors_ghouse一条记录
	 * 
	 * @param equipSensorsGhouse
	 *            主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse) {
		equipSensorsGhouseMapper.insert(equipSensorsGhouse);
		return equipSensorsGhouse.getId();
	}

	/**
	 * 根据主键删除指定的equip_sensors_ghouse记录
	 * 
	 * @param id
	 *            主键值
	 */
	public void removeEquipSensorsGhouse(Long id) {
		equipSensorsGhouseMapper.delete(id);
	}

	/**
	 * 根据设备id删除温室设备信息
	 * 
	 * @Title: removeEquipSensorsByEquipId
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void removeEquipSensorsByEquipId(Long equipId) {
		equipSensorsGhouseMapper.deleteByEquipId(equipId);
	}

	/**
	 * 更新指定的equip_sensors_ghouse记录
	 * 
	 * @param equipSensorsGhouse
	 */
	public void updateEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse) {
		equipSensorsGhouseMapper.update(equipSensorsGhouse);
	}

	/**
	 * 根据主键查询一条equip_sensors_ghouse记录
	 * 
	 * @param id
	 *            主键值
	 * 
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipSensorsGhouse getEquipSensorsGhouse(Long id) {
		return equipSensorsGhouseMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_sensors_ghouse结果列表
	 * 
	 * @return equip_sensors_ghouse记录列表
	 */
	public List<EquipSensorsGhouse> getEquipSensorsGhouses(
			EquipSensorsGhouseQB queryBean) {
		return equipSensorsGhouseMapper.selectList(queryBean);
	}

	/**
	 * 查询所有equip_sensors_ghouse记录
	 * 
	 * @return 所有 equip_sensors_ghouse记录
	 */
	public List<EquipSensorsGhouse> getAllEquipSensorsGhouses() {
		return equipSensorsGhouseMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_sensors_ghouse记录
	 * 
	 * @return 当前页的 equip_sensors_ghouse记录
	 */
	public PagingResultBean<List<EquipSensorsGhouse>> getAllEquipSensorsGhousesPaging(
			PagingQueryBean<EquipSensorsGhouseQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipSensorsGhouse> equipSensorsGhouseList = equipSensorsGhouseMapper
				.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipSensorsGhouse>> result = new PagingResultBean<List<EquipSensorsGhouse>>();
		result.setResultList(equipSensorsGhouseList);

		// 查询记录数
		Integer count = equipSensorsGhouseMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}

	public EquipSensorsGhouse findOne(EquipSensorsGhouseQB queryBean) {
		return this.equipSensorsGhouseMapper.findOne(queryBean);
	}

	/**
	 * 
	 * Description: 查询所有关联的设备信息
	 * 
	 * @param queryBean
	 * @return
	 */
	public List<EquipInfo> getAllEquipSensorsGhouses(
			EquipSensorsGhouseQB queryBean) {
		return equipSensorsGhouseMapper.selectAllEquip(queryBean);
	}

	/**
	 * 存储过程调用
	 * 
	 * @return
	 */
	public void proc(EquipSensorsGhouse EquipSensorsGhouse) {
		equipSensorsGhouseMapper.proc(EquipSensorsGhouse);
	}

	/**
	 * 获取控制室设备信息
	 * 
	 * @Title: selectControlEquipInfo
	 * @author Mao Xiaobing
	 * @return List<EquipInfo> 返回类型
	 * @throws
	 */
	public List<EquipInfo> selectControlEquipInfo(EquipSensorsGhouseQB query) {
		return this.equipSensorsGhouseMapper.selectControlEquip(query);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipSensorsGhouseMapper(
			EquipSensorsGhouseMapper equipSensorsGhouseMapper) {
		this.equipSensorsGhouseMapper = equipSensorsGhouseMapper;
	}
}
