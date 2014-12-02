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
import com.cattsoft.ny.base.entity.ProduceThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdInfoQB;
import com.cattsoft.ny.base.persistence.ProduceThresholdInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class ProduceThresholdInfoComponent {
	
	@Autowired
	private ProduceThresholdInfoMapper produceThresholdInfoMapper;

	/**
	 * 插入produce_threshold_info一条记录
	 * 
	 * @param produceThresholdInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo) {
		produceThresholdInfoMapper.insert(produceThresholdInfo);
		return produceThresholdInfo.getId();
	}

	/**
	 * 根据主键删除指定的produce_threshold_info记录
	 * 
	 * @param id 主键值
	 */
	public void removeProduceThresholdInfo(Long id) {
		produceThresholdInfoMapper.delete(id);
	}

	/**
	 * 更新指定的produce_threshold_info记录
	 * 
	 * @param produceThresholdInfo
	 */
	public void updateProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo) {
		produceThresholdInfoMapper.update(produceThresholdInfo);
	}

	/**
	 * 根据主键查询一条produce_threshold_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public ProduceThresholdInfo getProduceThresholdInfo(Long id) {
		return produceThresholdInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询produce_threshold_info结果列表
	 * 
	 * @return  produce_threshold_info记录列表
	 */
	public List<ProduceThresholdInfo> getProduceThresholdInfos(ProduceThresholdInfoQB queryBean) {
		return produceThresholdInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有produce_threshold_info记录
	 * 
	 * @return 所有 produce_threshold_info记录
	 */
	public List<ProduceThresholdInfo> getAllProduceThresholdInfos() {
		return produceThresholdInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有produce_threshold_info记录
	 * 
	 * @return 当前页的 produce_threshold_info记录
	 */
	public PagingResultBean<List<ProduceThresholdInfo>> getAllProduceThresholdInfosPaging(PagingQueryBean<ProduceThresholdInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<ProduceThresholdInfo> produceThresholdInfoList = produceThresholdInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<ProduceThresholdInfo>> result = new PagingResultBean<List<ProduceThresholdInfo>>();
		result.setResultList(produceThresholdInfoList);

		// 查询记录数
		Integer count = produceThresholdInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(ProduceThresholdInfo ProduceThresholdInfo) {
		produceThresholdInfoMapper.proc(ProduceThresholdInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceThresholdInfoMapper(ProduceThresholdInfoMapper produceThresholdInfoMapper) {
		this.produceThresholdInfoMapper = produceThresholdInfoMapper;
	}
}
