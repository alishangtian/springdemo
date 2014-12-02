/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
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
public interface PrdcSeasonService {
	/**
	 * 增加prdc_season信息
	 * 
	 * @param prdcSeason prdc_season信息
	 *
	 * @return prdc_season标识
	 */
	Long createPrdcSeason(PrdcSeason prdcSeason);

	/**
	 * 修改prdc_season信息
	 * 
	 * @param prdcSeason prdc_season信息
	 */
	void updatePrdcSeason(PrdcSeason prdcSeason);

	/**
	 * 删除prdc_season信息
	 * 
	 * @param id prdc_season标识
	 */
	void removePrdcSeason(Long id);

	/**
	 * 获取prdc_season信息
	 * 
	 * @param id prdc_season标识
	 * @return prdc_season信息
	 */
	PrdcSeason getPrdcSeason(Long id);

	/**
	 * 获取所有prdc_season
	 * 
	 * @return 所有prdc_season信息的列表
	 */
	List<PrdcSeason> getAllPrdcSeasons();
	
	/**
	 * 根据查询对象查询prdc_season结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  prdc_season记录列表
	 */
	List<PrdcSeason> getPrdcSeasons(PrdcSeasonQB queryBean);

	/**
	 * 分页获取prdc_season列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页prdc_season列表
	 */
	PagingResultBean<List<PrdcSeason>> getAllPrdcSeasonsPaging(PagingQueryBean<PrdcSeasonQB> pagingQueryBean);
}
