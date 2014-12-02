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
import com.cattsoft.ny.base.entity.PrdcThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.PrdcThresholdInfoQB;
import com.cattsoft.ny.base.persistence.PrdcThresholdInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class PrdcThresholdInfoComponent {
	
	@Autowired
	private PrdcThresholdInfoMapper prdcThresholdInfoMapper;

	/**
	 * 插入prdc_threshold_info一条记录
	 * 
	 * @param prdcThresholdInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createPrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo) {
		prdcThresholdInfoMapper.insert(prdcThresholdInfo);
		return prdcThresholdInfo.getId();
	}

	/**
	 * 根据主键删除指定的prdc_threshold_info记录
	 * 
	 * @param id 主键值
	 */
	public void removePrdcThresholdInfo(Long id) {
		prdcThresholdInfoMapper.delete(id);
	}

	/**
	 * 更新指定的prdc_threshold_info记录
	 * 
	 * @param prdcThresholdInfo
	 */
	public void updatePrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo) {
		prdcThresholdInfoMapper.update(prdcThresholdInfo);
	}

	/**
	 * 根据主键查询一条prdc_threshold_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public PrdcThresholdInfo getPrdcThresholdInfo(Long id) {
		return prdcThresholdInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询prdc_threshold_info结果列表
	 * 
	 * @return  prdc_threshold_info记录列表
	 */
	public List<PrdcThresholdInfo> getPrdcThresholdInfos(PrdcThresholdInfoQB queryBean) {
		return prdcThresholdInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有prdc_threshold_info记录
	 * 
	 * @return 所有 prdc_threshold_info记录
	 */
	public List<PrdcThresholdInfo> getAllPrdcThresholdInfos() {
		return prdcThresholdInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有prdc_threshold_info记录
	 * 
	 * @return 当前页的 prdc_threshold_info记录
	 */
	public PagingResultBean<List<PrdcThresholdInfo>> getAllPrdcThresholdInfosPaging(PagingQueryBean<PrdcThresholdInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<PrdcThresholdInfo> prdcThresholdInfoList = prdcThresholdInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<PrdcThresholdInfo>> result = new PagingResultBean<List<PrdcThresholdInfo>>();
		result.setResultList(prdcThresholdInfoList);

		// 查询记录数
		Integer count = prdcThresholdInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(PrdcThresholdInfo PrdcThresholdInfo) {
		prdcThresholdInfoMapper.proc(PrdcThresholdInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcThresholdInfoMapper(PrdcThresholdInfoMapper prdcThresholdInfoMapper) {
		this.prdcThresholdInfoMapper = prdcThresholdInfoMapper;
	}
}
