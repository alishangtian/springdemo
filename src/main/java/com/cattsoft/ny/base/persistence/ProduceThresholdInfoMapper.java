/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface ProduceThresholdInfoMapper {
	void insert(ProduceThresholdInfo produceThresholdInfo);

	void delete(Long id);

	void update(ProduceThresholdInfo produceThresholdInfo);

	ProduceThresholdInfo select(Long id);

	List<ProduceThresholdInfo> selectList(ProduceThresholdInfoQB queryBean);

	List<ProduceThresholdInfo> selectPage(PagingQueryBean<ProduceThresholdInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<ProduceThresholdInfoQB> pagingQueryBean);

	Object proc(ProduceThresholdInfo produceThresholdInfo);
	
}