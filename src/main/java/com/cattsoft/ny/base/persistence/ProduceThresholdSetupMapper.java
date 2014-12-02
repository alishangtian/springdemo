/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceThresholdSetup;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdSetupQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface ProduceThresholdSetupMapper {
	void insert(ProduceThresholdSetup produceThresholdSetup);

	void delete(Long id);

	void update(ProduceThresholdSetup produceThresholdSetup);

	ProduceThresholdSetup select(Long id);

	List<ProduceThresholdSetup> selectList(ProduceThresholdSetupQB queryBean);

	List<ProduceThresholdSetup> selectPage(PagingQueryBean<ProduceThresholdSetupQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<ProduceThresholdSetupQB> pagingQueryBean);

	Object proc(ProduceThresholdSetup produceThresholdSetup);
	
}