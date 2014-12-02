/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.ProduceSeason;
import com.cattsoft.ny.base.entity.querybean.ProduceSeasonQB;
import com.cattsoft.ny.base.persistence.ProduceSeasonMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class ProduceSeasonComponent {
	
	@Autowired
	private ProduceSeasonMapper produceSeasonMapper;

	/**
	 * 插入produce_season一条记录
	 * 
	 * @param produceSeason 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createProduceSeason(ProduceSeason produceSeason) {
		produceSeasonMapper.insert(produceSeason);
		return produceSeason.getId();
	}

	/**
	 * 根据主键删除指定的produce_season记录
	 * 
	 * @param id 主键值
	 */
	public void removeProduceSeason(Long id) {
		produceSeasonMapper.delete(id);
	}

	/**
	 * 更新指定的produce_season记录
	 * 
	 * @param produceSeason
	 */
	public void updateProduceSeason(ProduceSeason produceSeason) {
		produceSeasonMapper.update(produceSeason);
	}

	/**
	 * 根据主键查询一条produce_season记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public ProduceSeason getProduceSeason(Long id) {
		return produceSeasonMapper.select(id);
	}

	/**
	 * 根据查询对象查询produce_season结果列表
	 * 
	 * @return  produce_season记录列表
	 */
	public List<ProduceSeason> getProduceSeasons(ProduceSeasonQB queryBean) {
		return produceSeasonMapper.selectList(queryBean);
	}

	/**
	 * 查询所有produce_season记录
	 * 
	 * @return 所有 produce_season记录
	 */
	public List<ProduceSeason> getAllProduceSeasons() {
		return produceSeasonMapper.selectList(null);
	}

	/**
	 * 分页查询所有produce_season记录
	 * 
	 * @return 当前页的 produce_season记录
	 */
	public PagingResultBean<List<ProduceSeason>> getAllProduceSeasonsPaging(PagingQueryBean<ProduceSeasonQB> pagingQueryBean) {
		// 分页查询列表
		List<ProduceSeason> produceSeasonList = produceSeasonMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<ProduceSeason>> result = new PagingResultBean<List<ProduceSeason>>();
		result.setResultList(produceSeasonList);

		// 查询记录数
		Integer count = produceSeasonMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}

	/**
	 * 存储过程调用
	 * 
	 * @return
	 */
	public void proc(ProduceSeason ProduceSeason) {
		produceSeasonMapper.proc(ProduceSeason);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceSeasonMapper(ProduceSeasonMapper produceSeasonMapper) {
		this.produceSeasonMapper = produceSeasonMapper;
	}
}
