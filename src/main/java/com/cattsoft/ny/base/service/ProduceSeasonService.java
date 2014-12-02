/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.ProduceAccident;
import com.cattsoft.ny.base.entity.ProduceSeason;
import com.cattsoft.ny.base.entity.querybean.ProduceAccidentQB;
import com.cattsoft.ny.base.entity.querybean.ProduceSeasonQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface ProduceSeasonService {
	/**
	 * 增加produce_season信息
	 * 
	 * @param produceSeason
	 *            produce_season信息
	 * 
	 * @return produce_season标识
	 */
	Long createProduceSeason(ProduceSeason produceSeason);

	/**
	 * 修改produce_season信息
	 * 
	 * @param produceSeason
	 *            produce_season信息
	 */
	void updateProduceSeason(ProduceSeason produceSeason);

	/**
	 * 删除produce_season信息
	 * 
	 * @param id
	 *            produce_season标识
	 */
	void removeProduceSeason(Long id);

	/**
	 * 获取produce_season信息
	 * 
	 * @param id
	 *            produce_season标识
	 * @return produce_season信息
	 */
	ProduceSeason getProduceSeason(Long id);

	/**
	 * 获取所有produce_season
	 * 
	 * @return 所有produce_season信息的列表
	 */
	List<ProduceSeason> getAllProduceSeasons();

	/**
	 * 根据查询对象查询produce_season结果列表
	 * 
	 * @param queryBean
	 *            查询对象
	 * 
	 * @return produce_season记录列表
	 */
	List<ProduceSeason> getProduceSeasons(ProduceSeasonQB queryBean);

	/**
	 * 分页获取produce_season列表
	 * 
	 * @param pagingQueryBean
	 *            分页查询对象
	 * 
	 * @return 分页produce_season列表
	 */
	PagingResultBean<List<ProduceSeason>> getAllProduceSeasonsPaging(
			PagingQueryBean<ProduceSeasonQB> pagingQueryBean);

	/**
	 * 添加一个生产事故
	 * 
	 * @param produceAccident
	 * @return
	 */
	public Long createProduceAccident(ProduceAccident produceAccident);

	/**
	 * 删除一个生产事故
	 * 
	 * @param id
	 */
	public void deleteProduceAccident(Long id);

	/**
	 * 更新生产事故
	 * 
	 * @param produceAccident
	 */
	public void updateProduceAccient(ProduceAccident produceAccident);

	/**
	 * 查找一个生产事故
	 * 
	 * @param id
	 * @return
	 */
	public ProduceAccident findOneProduceAccident(Long id);

	/**
	 * 查找生产事故列表
	 * 
	 * @param produceAccident
	 * @return
	 */
	public List<ProduceAccident> findProduceAccident(
			ProduceAccident produceAccident);

	/**
	 * 查找生产事故分页数据
	 * 
	 * @param pagingQueryBean
	 * @return
	 */
	public PagingResultBean<List<ProduceAccident>> findPageProduceAccident(
			PagingQueryBean<ProduceAccidentQB> pagingQueryBean);

}
