/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
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
public interface PrdcConsumerAccidentsService {
	/**
	 * 增加prdc_consumer_accidents信息
	 * 
	 * @param prdcConsumerAccidents prdc_consumer_accidents信息
	 *
	 * @return prdc_consumer_accidents标识
	 */
	Long createPrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents);

	/**
	 * 修改prdc_consumer_accidents信息
	 * 
	 * @param prdcConsumerAccidents prdc_consumer_accidents信息
	 */
	void updatePrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents);

	/**
	 * 删除prdc_consumer_accidents信息
	 * 
	 * @param id prdc_consumer_accidents标识
	 */
	void removePrdcConsumerAccidents(Long id);

	/**
	 * 获取prdc_consumer_accidents信息
	 * 
	 * @param id prdc_consumer_accidents标识
	 * @return prdc_consumer_accidents信息
	 */
	PrdcConsumerAccidents getPrdcConsumerAccidents(Long id);

	/**
	 * 获取所有prdc_consumer_accidents
	 * 
	 * @return 所有prdc_consumer_accidents信息的列表
	 */
	List<PrdcConsumerAccidents> getAllPrdcConsumerAccidentss();
	
	/**
	 * 根据查询对象查询prdc_consumer_accidents结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  prdc_consumer_accidents记录列表
	 */
	List<PrdcConsumerAccidents> getPrdcConsumerAccidentss(PrdcConsumerAccidentsQB queryBean);

	/**
	 * 分页获取prdc_consumer_accidents列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页prdc_consumer_accidents列表
	 */
	PagingResultBean<List<PrdcConsumerAccidents>> getAllPrdcConsumerAccidentssPaging(PagingQueryBean<PrdcConsumerAccidentsQB> pagingQueryBean);
}
