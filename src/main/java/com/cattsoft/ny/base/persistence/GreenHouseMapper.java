/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.GreenHouse;
import com.cattsoft.ny.base.entity.querybean.GreenHouseQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface GreenHouseMapper {
	void insert(GreenHouse greenHouse);

	void delete(Long id);

	void update(GreenHouse greenHouse);

	GreenHouse select(Long id);

	List<GreenHouse> selectList(GreenHouseQB queryBean);

	List<GreenHouse> selectPage(PagingQueryBean<GreenHouseQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<GreenHouseQB> pagingQueryBean);

	Object proc(GreenHouse greenHouse);
	
}