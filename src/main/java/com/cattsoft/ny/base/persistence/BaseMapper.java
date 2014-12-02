/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface BaseMapper {
	void insert(Base base);

	void delete(Long id);

	void update(Base base);

	Base select(Long id);

	List<Base> selectList(BaseQB queryBean);

	List<Base> selectPage(PagingQueryBean<BaseQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<BaseQB> pagingQueryBean);

	Object proc(Base base);
	
}