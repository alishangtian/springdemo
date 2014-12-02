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
import com.cattsoft.ny.base.entity.PrdcConsumerAccidents;
import com.cattsoft.ny.base.entity.querybean.PrdcConsumerAccidentsQB;
import com.cattsoft.ny.base.persistence.PrdcConsumerAccidentsMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class PrdcConsumerAccidentsComponent {
	
	@Autowired
	private PrdcConsumerAccidentsMapper prdcConsumerAccidentsMapper;

	/**
	 * 插入prdc_consumer_accidents一条记录
	 * 
	 * @param prdcConsumerAccidents 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createPrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents) {
		prdcConsumerAccidentsMapper.insert(prdcConsumerAccidents);
		return prdcConsumerAccidents.getId();
	}

	/**
	 * 根据主键删除指定的prdc_consumer_accidents记录
	 * 
	 * @param id 主键值
	 */
	public void removePrdcConsumerAccidents(Long id) {
		prdcConsumerAccidentsMapper.delete(id);
	}

	/**
	 * 更新指定的prdc_consumer_accidents记录
	 * 
	 * @param prdcConsumerAccidents
	 */
	public void updatePrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents) {
		prdcConsumerAccidentsMapper.update(prdcConsumerAccidents);
	}

	/**
	 * 根据主键查询一条prdc_consumer_accidents记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public PrdcConsumerAccidents getPrdcConsumerAccidents(Long id) {
		return prdcConsumerAccidentsMapper.select(id);
	}

	/**
	 * 根据查询对象查询prdc_consumer_accidents结果列表
	 * 
	 * @return  prdc_consumer_accidents记录列表
	 */
	public List<PrdcConsumerAccidents> getPrdcConsumerAccidentss(PrdcConsumerAccidentsQB queryBean) {
		return prdcConsumerAccidentsMapper.selectList(queryBean);
	}

	/**
	 * 查询所有prdc_consumer_accidents记录
	 * 
	 * @return 所有 prdc_consumer_accidents记录
	 */
	public List<PrdcConsumerAccidents> getAllPrdcConsumerAccidentss() {
		return prdcConsumerAccidentsMapper.selectList(null);
	}

	/**
	 * 分页查询所有prdc_consumer_accidents记录
	 * 
	 * @return 当前页的 prdc_consumer_accidents记录
	 */
	public PagingResultBean<List<PrdcConsumerAccidents>> getAllPrdcConsumerAccidentssPaging(PagingQueryBean<PrdcConsumerAccidentsQB> pagingQueryBean) {
		// 分页查询列表
		List<PrdcConsumerAccidents> prdcConsumerAccidentsList = prdcConsumerAccidentsMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<PrdcConsumerAccidents>> result = new PagingResultBean<List<PrdcConsumerAccidents>>();
		result.setResultList(prdcConsumerAccidentsList);

		// 查询记录数
		Integer count = prdcConsumerAccidentsMapper.selectCount(pagingQueryBean);
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
	public void proc(PrdcConsumerAccidents PrdcConsumerAccidents) {
		prdcConsumerAccidentsMapper.proc(PrdcConsumerAccidents);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcConsumerAccidentsMapper(PrdcConsumerAccidentsMapper prdcConsumerAccidentsMapper) {
		this.prdcConsumerAccidentsMapper = prdcConsumerAccidentsMapper;
	}
}
