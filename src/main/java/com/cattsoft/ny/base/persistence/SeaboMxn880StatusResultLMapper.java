/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.SeaboMxn880StatusResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxn880StatusResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface SeaboMxn880StatusResultLMapper {
	void insert(SeaboMxn880StatusResultL seaboMxn880StatusResultL);

	void delete(Long nodeid);

	void update(SeaboMxn880StatusResultL seaboMxn880StatusResultL);

	SeaboMxn880StatusResultL select(Long nodeid);

	List<SeaboMxn880StatusResultL> selectList(SeaboMxn880StatusResultLQB queryBean);

	List<SeaboMxn880StatusResultL> selectPage(PagingQueryBean<SeaboMxn880StatusResultLQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<SeaboMxn880StatusResultLQB> pagingQueryBean);

	Object proc(SeaboMxn880StatusResultL seaboMxn880StatusResultL);
	
}