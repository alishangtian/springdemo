/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface PrdcSeasonMapper {
	void insert(PrdcSeason prdcSeason);

	void delete(Long id);

	void update(PrdcSeason prdcSeason);

	PrdcSeason select(Long id);

	List<PrdcSeason> selectList(PrdcSeasonQB queryBean);

	List<PrdcSeason> selectPage(PagingQueryBean<PrdcSeasonQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<PrdcSeasonQB> pagingQueryBean);

	Object proc(PrdcSeason prdcSeason);
	
}