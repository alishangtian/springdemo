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
import com.cattsoft.ny.base.entity.ProduceThresholdSetup;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdSetupQB;
import com.cattsoft.ny.base.persistence.ProduceThresholdSetupMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class ProduceThresholdSetupComponent {
	
	@Autowired
	private ProduceThresholdSetupMapper produceThresholdSetupMapper;

	/**
	 * 插入produce_threshold_setup一条记录
	 * 
	 * @param produceThresholdSetup 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup) {
		produceThresholdSetupMapper.insert(produceThresholdSetup);
		return produceThresholdSetup.getId();
	}

	/**
	 * 根据主键删除指定的produce_threshold_setup记录
	 * 
	 * @param id 主键值
	 */
	public void removeProduceThresholdSetup(Long id) {
		produceThresholdSetupMapper.delete(id);
	}

	/**
	 * 更新指定的produce_threshold_setup记录
	 * 
	 * @param produceThresholdSetup
	 */
	public void updateProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup) {
		produceThresholdSetupMapper.update(produceThresholdSetup);
	}

	/**
	 * 根据主键查询一条produce_threshold_setup记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public ProduceThresholdSetup getProduceThresholdSetup(Long id) {
		return produceThresholdSetupMapper.select(id);
	}

	/**
	 * 根据查询对象查询produce_threshold_setup结果列表
	 * 
	 * @return  produce_threshold_setup记录列表
	 */
	public List<ProduceThresholdSetup> getProduceThresholdSetups(ProduceThresholdSetupQB queryBean) {
		return produceThresholdSetupMapper.selectList(queryBean);
	}

	/**
	 * 查询所有produce_threshold_setup记录
	 * 
	 * @return 所有 produce_threshold_setup记录
	 */
	public List<ProduceThresholdSetup> getAllProduceThresholdSetups() {
		return produceThresholdSetupMapper.selectList(null);
	}

	/**
	 * 分页查询所有produce_threshold_setup记录
	 * 
	 * @return 当前页的 produce_threshold_setup记录
	 */
	public PagingResultBean<List<ProduceThresholdSetup>> getAllProduceThresholdSetupsPaging(PagingQueryBean<ProduceThresholdSetupQB> pagingQueryBean) {
		// 分页查询列表
		List<ProduceThresholdSetup> produceThresholdSetupList = produceThresholdSetupMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<ProduceThresholdSetup>> result = new PagingResultBean<List<ProduceThresholdSetup>>();
		result.setResultList(produceThresholdSetupList);

		// 查询记录数
		Integer count = produceThresholdSetupMapper.selectCount(pagingQueryBean);
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
	public void proc(ProduceThresholdSetup ProduceThresholdSetup) {
		produceThresholdSetupMapper.proc(ProduceThresholdSetup);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceThresholdSetupMapper(ProduceThresholdSetupMapper produceThresholdSetupMapper) {
		this.produceThresholdSetupMapper = produceThresholdSetupMapper;
	}
}
