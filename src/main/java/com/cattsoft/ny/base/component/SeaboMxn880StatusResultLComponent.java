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
import com.cattsoft.ny.base.entity.SeaboMxn880StatusResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxn880StatusResultLQB;
import com.cattsoft.ny.base.persistence.SeaboMxn880StatusResultLMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class SeaboMxn880StatusResultLComponent {
	
	@Autowired
	private SeaboMxn880StatusResultLMapper seaboMxn880StatusResultLMapper;

	/**
	 * 插入seabo_mxn880_status_result_l一条记录
	 * 
	 * @param seaboMxn880StatusResultL 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL) {
		seaboMxn880StatusResultLMapper.insert(seaboMxn880StatusResultL);
		return (long)seaboMxn880StatusResultL.getNodeid();
	}

	/**
	 * 根据主键删除指定的seabo_mxn880_status_result_l记录
	 * 
	 * @param nodeid 主键值
	 */
	public void removeSeaboMxn880StatusResultL(Long nodeid) {
		seaboMxn880StatusResultLMapper.delete(nodeid);
	}

	/**
	 * 更新指定的seabo_mxn880_status_result_l记录
	 * 
	 * @param seaboMxn880StatusResultL
	 */
	public void updateSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL) {
		seaboMxn880StatusResultLMapper.update(seaboMxn880StatusResultL);
	}

	/**
	 * 根据主键查询一条seabo_mxn880_status_result_l记录
	 * 
	 * @param nodeid 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public SeaboMxn880StatusResultL getSeaboMxn880StatusResultL(Long nodeid) {
		return seaboMxn880StatusResultLMapper.select(nodeid);
	}

	/**
	 * 根据查询对象查询seabo_mxn880_status_result_l结果列表
	 * 
	 * @return  seabo_mxn880_status_result_l记录列表
	 */
	public List<SeaboMxn880StatusResultL> getSeaboMxn880StatusResultLs(SeaboMxn880StatusResultLQB queryBean) {
		return seaboMxn880StatusResultLMapper.selectList(queryBean);
	}

	/**
	 * 查询所有seabo_mxn880_status_result_l记录
	 * 
	 * @return 所有 seabo_mxn880_status_result_l记录
	 */
	public List<SeaboMxn880StatusResultL> getAllSeaboMxn880StatusResultLs() {
		return seaboMxn880StatusResultLMapper.selectList(null);
	}

	/**
	 * 分页查询所有seabo_mxn880_status_result_l记录
	 * 
	 * @return 当前页的 seabo_mxn880_status_result_l记录
	 */
	public PagingResultBean<List<SeaboMxn880StatusResultL>> getAllSeaboMxn880StatusResultLsPaging(PagingQueryBean<SeaboMxn880StatusResultLQB> pagingQueryBean) {
		// 分页查询列表
		List<SeaboMxn880StatusResultL> seaboMxn880StatusResultLList = seaboMxn880StatusResultLMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<SeaboMxn880StatusResultL>> result = new PagingResultBean<List<SeaboMxn880StatusResultL>>();
		result.setResultList(seaboMxn880StatusResultLList);

		// 查询记录数
		Integer count = seaboMxn880StatusResultLMapper.selectCount(pagingQueryBean);
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
	public void proc(SeaboMxn880StatusResultL SeaboMxn880StatusResultL) {
		seaboMxn880StatusResultLMapper.proc(SeaboMxn880StatusResultL);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxn880StatusResultLMapper(SeaboMxn880StatusResultLMapper seaboMxn880StatusResultLMapper) {
		this.seaboMxn880StatusResultLMapper = seaboMxn880StatusResultLMapper;
	}
}
