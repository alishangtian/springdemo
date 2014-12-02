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
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.OperationWorksInfo;
import com.cattsoft.ny.base.entity.querybean.OperationWorksInfoQB;
import com.cattsoft.ny.base.persistence.OperationWorksInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class OperationWorksInfoComponent {
	
	@Autowired
	private OperationWorksInfoMapper operationWorksInfoMapper;

	/**
	 * 插入produce_works_info一条记录
	 * 
	 * @param operationWorksInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createOperationWorksInfo(OperationWorksInfo operationWorksInfo) {
		operationWorksInfoMapper.insert(operationWorksInfo);
		return operationWorksInfo.getId();
	}

	/**
	 * 根据主键删除指定的produce_works_info记录
	 * 
	 * @param id 主键值
	 */
	public void removeOperationWorksInfo(Long id) {
		operationWorksInfoMapper.delete(id);
	}

	/**
	 * 更新指定的produce_works_info记录
	 * 
	 * @param operationWorksInfo
	 */
	public void updateOperationWorksInfo(OperationWorksInfo operationWorksInfo) {
		operationWorksInfoMapper.update(operationWorksInfo);
	}

	/**
	 * 根据主键查询一条produce_works_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public OperationWorksInfo getOperationWorksInfo(Long id) {
		return operationWorksInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询produce_works_info结果列表
	 * 
	 * @return  produce_works_info记录列表
	 */
	public List<OperationWorksInfo> getOperationWorksInfos(OperationWorksInfoQB queryBean) {
		return operationWorksInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有produce_works_info记录
	 * 
	 * @return 所有 produce_works_info记录
	 */
	public List<OperationWorksInfo> getAllOperationWorksInfos() {
		return operationWorksInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有produce_works_info记录
	 * 
	 * @return 当前页的 produce_works_info记录
	 */
	public PagingResultBean<List<OperationWorksInfo>> getAllOperationWorksInfosPaging(PagingQueryBean<OperationWorksInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<OperationWorksInfo> operationWorksInfoList = operationWorksInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<OperationWorksInfo>> result = new PagingResultBean<List<OperationWorksInfo>>();
		result.setResultList(operationWorksInfoList);

		// 查询记录数
		Integer count = operationWorksInfoMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}
	public List<News> getNews() {
		// TODO Auto-generated method stub
		return operationWorksInfoMapper.selectNews();
	}

	/**
	 * 存储过程调用
	 * 
	 * @return
	 */
	public void proc(OperationWorksInfo OperationWorksInfo) {
		operationWorksInfoMapper.proc(OperationWorksInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setOperationWorksInfoMapper(OperationWorksInfoMapper operationWorksInfoMapper) {
		this.operationWorksInfoMapper = operationWorksInfoMapper;
	}


}
