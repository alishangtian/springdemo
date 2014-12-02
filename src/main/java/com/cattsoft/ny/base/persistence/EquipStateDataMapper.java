/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
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
@MyBatisRepository
public interface EquipStateDataMapper {
	void insert(EquipStateData equipStateData);

	void delete(Long id);

	void update(EquipStateData equipStateData);

	EquipStateData select(Long id);

	List<EquipStateData> selectList(EquipStateDataQB queryBean);
	List<EquipStateDataInfo> selectListInfo(EquipStateDataQB queryBean);
	                         
	List<EquipStateData> selectPage(PagingQueryBean<EquipStateDataQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipStateDataQB> pagingQueryBean);

	Object proc(EquipStateData equipStateData);
	
}