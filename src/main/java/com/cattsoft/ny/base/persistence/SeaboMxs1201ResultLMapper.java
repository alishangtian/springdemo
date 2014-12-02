/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.SeaboMxs1201ResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1201ResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface SeaboMxs1201ResultLMapper {
	void insert(SeaboMxs1201ResultL seaboMxs1201ResultL);

	void delete(Long nodeid);

	void update(SeaboMxs1201ResultL seaboMxs1201ResultL);

	SeaboMxs1201ResultL select(Long nodeid);

	List<SeaboMxs1201ResultL> selectList(SeaboMxs1201ResultLQB queryBean);

	List<SeaboMxs1201ResultL> selectPage(PagingQueryBean<SeaboMxs1201ResultLQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<SeaboMxs1201ResultLQB> pagingQueryBean);

	Object proc(SeaboMxs1201ResultL seaboMxs1201ResultL);
	
}