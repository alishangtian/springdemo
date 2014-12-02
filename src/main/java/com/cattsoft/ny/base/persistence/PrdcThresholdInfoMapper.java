/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.PrdcThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.PrdcThresholdInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface PrdcThresholdInfoMapper {
	void insert(PrdcThresholdInfo prdcThresholdInfo);

	void delete(Long id);

	void update(PrdcThresholdInfo prdcThresholdInfo);

	PrdcThresholdInfo select(Long id);

	List<PrdcThresholdInfo> selectList(PrdcThresholdInfoQB queryBean);

	List<PrdcThresholdInfo> selectPage(PagingQueryBean<PrdcThresholdInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<PrdcThresholdInfoQB> pagingQueryBean);

	Object proc(PrdcThresholdInfo prdcThresholdInfo);
	
}