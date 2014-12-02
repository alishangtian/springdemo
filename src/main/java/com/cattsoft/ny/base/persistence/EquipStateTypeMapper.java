/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.querybean.EquipStateTypeQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipStateTypeMapper {
	void insert(EquipStateType equipStateType);

	void delete(Long id);

	void update(EquipStateType equipStateType);

	EquipStateType select(Long id);

	List<EquipStateType> selectList(EquipStateTypeQB queryBean);

	List<EquipStateType> selectPage(PagingQueryBean<EquipStateTypeQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipStateTypeQB> pagingQueryBean);

	Object proc(EquipStateType equipStateType);
	
}