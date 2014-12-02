/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipControlInfoH;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoHQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipControlInfoHMapper {
	void insert(EquipControlInfoH equipControlInfoH);

	void delete(Long id);

	void update(EquipControlInfoH equipControlInfoH);

	EquipControlInfoH select(Long id);

	List<EquipControlInfoH> selectList(EquipControlInfoHQB queryBean);

	List<EquipControlInfoH> selectPage(PagingQueryBean<EquipControlInfoHQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipControlInfoHQB> pagingQueryBean);

	Object proc(EquipControlInfoH equipControlInfoH);
	
}