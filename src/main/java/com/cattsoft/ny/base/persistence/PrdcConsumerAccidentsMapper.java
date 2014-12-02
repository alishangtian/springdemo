/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.PrdcConsumerAccidents;
import com.cattsoft.ny.base.entity.querybean.PrdcConsumerAccidentsQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface PrdcConsumerAccidentsMapper {
	void insert(PrdcConsumerAccidents prdcConsumerAccidents);

	void delete(Long id);

	void update(PrdcConsumerAccidents prdcConsumerAccidents);

	PrdcConsumerAccidents select(Long id);

	List<PrdcConsumerAccidents> selectList(PrdcConsumerAccidentsQB queryBean);

	List<PrdcConsumerAccidents> selectPage(PagingQueryBean<PrdcConsumerAccidentsQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<PrdcConsumerAccidentsQB> pagingQueryBean);

	Object proc(PrdcConsumerAccidents prdcConsumerAccidents);
	
}