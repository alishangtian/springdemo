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
import com.cattsoft.ny.base.entity.ProduceWorksInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceWorksInfoQB;
import com.cattsoft.ny.base.persistence.ProduceWorksInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class ProduceWorksInfoComponent {
	
	@Autowired
	private ProduceWorksInfoMapper produceWorksInfoMapper;

	/**
	 * 插入produce_works_info一条记录
	 * 
	 * @param produceWorksInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createProduceWorksInfo(ProduceWorksInfo produceWorksInfo) {
		produceWorksInfoMapper.insert(produceWorksInfo);
		return produceWorksInfo.getId();
	}

	/**
	 * 根据主键删除指定的produce_works_info记录
	 * 
	 * @param id 主键值
	 */
	public void removeProduceWorksInfo(Long id) {
		produceWorksInfoMapper.delete(id);
	}

	/**
	 * 更新指定的produce_works_info记录
	 * 
	 * @param produceWorksInfo
	 */
	public void updateProduceWorksInfo(ProduceWorksInfo produceWorksInfo) {
		produceWorksInfoMapper.update(produceWorksInfo);
	}

	/**
	 * 根据主键查询一条produce_works_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public ProduceWorksInfo getProduceWorksInfo(Long id) {
		return produceWorksInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询produce_works_info结果列表
	 * 
	 * @return  produce_works_info记录列表
	 */
	public List<ProduceWorksInfo> getProduceWorksInfos(ProduceWorksInfoQB queryBean) {
		return produceWorksInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有produce_works_info记录
	 * 
	 * @return 所有 produce_works_info记录
	 */
	public List<ProduceWorksInfo> getAllProduceWorksInfos() {
		return produceWorksInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有produce_works_info记录
	 * 
	 * @return 当前页的 produce_works_info记录
	 */
	public PagingResultBean<List<ProduceWorksInfo>> getAllProduceWorksInfosPaging(PagingQueryBean<ProduceWorksInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<ProduceWorksInfo> produceWorksInfoList = produceWorksInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<ProduceWorksInfo>> result = new PagingResultBean<List<ProduceWorksInfo>>();
		result.setResultList(produceWorksInfoList);

		// 查询记录数
		Integer count = produceWorksInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(ProduceWorksInfo ProduceWorksInfo) {
		produceWorksInfoMapper.proc(ProduceWorksInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceWorksInfoMapper(ProduceWorksInfoMapper produceWorksInfoMapper) {
		this.produceWorksInfoMapper = produceWorksInfoMapper;
	}
}
