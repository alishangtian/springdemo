/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipInfoMapper {
	void insert(EquipInfo equipInfo);

	void delete(Long id);

	void update(EquipInfo equipInfo);

	EquipInfo select(Long id);

	List<EquipInfo> selectList(EquipInfoQB queryBean);
	
	List<EquipInfo> selectUsefulList(EquipInfoQB queryBean);

	List<EquipInfo> findFreeControlEquip(EquipInfoQB queryBean);

	List<EquipInfo> selectOtherList(EquipInfoQB queryBean);

	List<EquipInfo> selectPage(PagingQueryBean<EquipInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipInfoQB> pagingQueryBean);

	Object proc(EquipInfo equipInfo);

	List<EquipInfo> selectPageForward(
			PagingQueryBean<EquipInfoQB> pagingQueryBean);

	Integer selectCountForward(PagingQueryBean<EquipInfoQB> pagingQueryBean);

	List<EquipInfo> selectListForward(EquipInfoQB queryBean);
	
	Integer cgqCountInfo(EquipInfoQB queryBean);
}