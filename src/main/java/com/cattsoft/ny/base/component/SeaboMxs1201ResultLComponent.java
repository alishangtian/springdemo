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
import com.cattsoft.ny.base.entity.SeaboMxs1201ResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1201ResultLQB;
import com.cattsoft.ny.base.persistence.SeaboMxs1201ResultLMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class SeaboMxs1201ResultLComponent {
	
	@Autowired
	private SeaboMxs1201ResultLMapper seaboMxs1201ResultLMapper;

	/**
	 * 插入seabo_mxs1201_result_l一条记录
	 * 
	 * @param seaboMxs1201ResultL 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL) {
		seaboMxs1201ResultLMapper.insert(seaboMxs1201ResultL);
		return (long)seaboMxs1201ResultL.getNodeid();
	}

	/**
	 * 根据主键删除指定的seabo_mxs1201_result_l记录
	 * 
	 * @param nodeid 主键值
	 */
	public void removeSeaboMxs1201ResultL(Long nodeid) {
		seaboMxs1201ResultLMapper.delete(nodeid);
	}

	/**
	 * 更新指定的seabo_mxs1201_result_l记录
	 * 
	 * @param seaboMxs1201ResultL
	 */
	public void updateSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL) {
		seaboMxs1201ResultLMapper.update(seaboMxs1201ResultL);
	}

	/**
	 * 根据主键查询一条seabo_mxs1201_result_l记录
	 * 
	 * @param nodeid 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public SeaboMxs1201ResultL getSeaboMxs1201ResultL(Long nodeid) {
		return seaboMxs1201ResultLMapper.select(nodeid);
	}

	/**
	 * 根据查询对象查询seabo_mxs1201_result_l结果列表
	 * 
	 * @return  seabo_mxs1201_result_l记录列表
	 */
	public List<SeaboMxs1201ResultL> getSeaboMxs1201ResultLs(SeaboMxs1201ResultLQB queryBean) {
		return seaboMxs1201ResultLMapper.selectList(queryBean);
	}

	/**
	 * 查询所有seabo_mxs1201_result_l记录
	 * 
	 * @return 所有 seabo_mxs1201_result_l记录
	 */
	public List<SeaboMxs1201ResultL> getAllSeaboMxs1201ResultLs() {
		return seaboMxs1201ResultLMapper.selectList(null);
	}

	/**
	 * 分页查询所有seabo_mxs1201_result_l记录
	 * 
	 * @return 当前页的 seabo_mxs1201_result_l记录
	 */
	public PagingResultBean<List<SeaboMxs1201ResultL>> getAllSeaboMxs1201ResultLsPaging(PagingQueryBean<SeaboMxs1201ResultLQB> pagingQueryBean) {
		// 分页查询列表
		List<SeaboMxs1201ResultL> seaboMxs1201ResultLList = seaboMxs1201ResultLMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<SeaboMxs1201ResultL>> result = new PagingResultBean<List<SeaboMxs1201ResultL>>();
		result.setResultList(seaboMxs1201ResultLList);

		// 查询记录数
		Integer count = seaboMxs1201ResultLMapper.selectCount(pagingQueryBean);
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
	public void proc(SeaboMxs1201ResultL SeaboMxs1201ResultL) {
		seaboMxs1201ResultLMapper.proc(SeaboMxs1201ResultL);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxs1201ResultLMapper(SeaboMxs1201ResultLMapper seaboMxs1201ResultLMapper) {
		this.seaboMxs1201ResultLMapper = seaboMxs1201ResultLMapper;
	}
}
