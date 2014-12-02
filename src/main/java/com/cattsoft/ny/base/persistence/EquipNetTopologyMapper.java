/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipNetTopologyMapper {
	void insert(EquipNetTopology equipNetTopology);

	void delete(Long id);

	void deleteByEquipId(Long id);

	void update(EquipNetTopology equipNetTopology);

	EquipNetTopology select(Long id);

	List<EquipNetTopology> selectList(EquipNetTopologyQB queryBean);

	List<EquipNetTopology> find(EquipNetTopologyQB queryBean);

	List<EquipNetTopology> selectPage(
			PagingQueryBean<EquipNetTopologyQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipNetTopologyQB> pagingQueryBean);

	Object proc(EquipNetTopology equipNetTopology);

}