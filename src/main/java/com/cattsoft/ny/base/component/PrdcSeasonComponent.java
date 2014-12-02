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
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.persistence.PrdcSeasonMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class PrdcSeasonComponent {
	
	@Autowired
	private PrdcSeasonMapper prdcSeasonMapper;

	/**
	 * 插入prdc_season一条记录
	 * 
	 * @param prdcSeason 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createPrdcSeason(PrdcSeason prdcSeason) {
		prdcSeasonMapper.insert(prdcSeason);
		return prdcSeason.getId();
	}

	/**
	 * 根据主键删除指定的prdc_season记录
	 * 
	 * @param id 主键值
	 */
	public void removePrdcSeason(Long id) {
		prdcSeasonMapper.delete(id);
	}

	/**
	 * 更新指定的prdc_season记录
	 * 
	 * @param prdcSeason
	 */
	public void updatePrdcSeason(PrdcSeason prdcSeason) {
		prdcSeasonMapper.update(prdcSeason);
	}

	/**
	 * 根据主键查询一条prdc_season记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public PrdcSeason getPrdcSeason(Long id) {
		return prdcSeasonMapper.select(id);
	}

	/**
	 * 根据查询对象查询prdc_season结果列表
	 * 
	 * @return  prdc_season记录列表
	 */
	public List<PrdcSeason> getPrdcSeasons(PrdcSeasonQB queryBean) {
		return prdcSeasonMapper.selectList(queryBean);
	}

	/**
	 * 查询所有prdc_season记录
	 * 
	 * @return 所有 prdc_season记录
	 */
	public List<PrdcSeason> getAllPrdcSeasons() {
		return prdcSeasonMapper.selectList(null);
	}

	/**
	 * 分页查询所有prdc_season记录
	 * 
	 * @return 当前页的 prdc_season记录
	 */
	public PagingResultBean<List<PrdcSeason>> getAllPrdcSeasonsPaging(PagingQueryBean<PrdcSeasonQB> pagingQueryBean) {
		// 分页查询列表
		List<PrdcSeason> prdcSeasonList = prdcSeasonMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<PrdcSeason>> result = new PagingResultBean<List<PrdcSeason>>();
		result.setResultList(prdcSeasonList);

		// 查询记录数
		Integer count = prdcSeasonMapper.selectCount(pagingQueryBean);
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
	public void proc(PrdcSeason PrdcSeason) {
		prdcSeasonMapper.proc(PrdcSeason);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcSeasonMapper(PrdcSeasonMapper prdcSeasonMapper) {
		this.prdcSeasonMapper = prdcSeasonMapper;
	}
}
