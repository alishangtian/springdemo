/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipSensorsGhouseMapper {
	void insert(EquipSensorsGhouse equipSensorsGhouse);

	void delete(Long id);

	void deleteByEquipId(Long id);

	void update(EquipSensorsGhouse equipSensorsGhouse);

	EquipSensorsGhouse select(Long id);

	List<EquipSensorsGhouse> selectList(EquipSensorsGhouseQB queryBean);

	List<EquipInfo> selectAllEquip(EquipSensorsGhouseQB queryBean);

	List<EquipInfo> selectControlEquip(EquipSensorsGhouseQB queryBean);

	List<EquipSensorsGhouse> selectPage(
			PagingQueryBean<EquipSensorsGhouseQB> pagingQueryBean);

	EquipSensorsGhouse findOne(EquipSensorsGhouseQB queryBean);

	Integer selectCount(PagingQueryBean<EquipSensorsGhouseQB> pagingQueryBean);

	Object proc(EquipSensorsGhouse equipSensorsGhouse);

}