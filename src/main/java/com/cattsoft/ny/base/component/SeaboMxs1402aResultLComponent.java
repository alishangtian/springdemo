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
import com.cattsoft.ny.base.entity.SeaboMxs1402aResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1402aResultLQB;
import com.cattsoft.ny.base.persistence.SeaboMxs1402aResultLMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class SeaboMxs1402aResultLComponent {
	
	@Autowired
	private SeaboMxs1402aResultLMapper seaboMxs1402aResultLMapper;

	/**
	 * 插入seabo_mxs1402a_result_l一条记录
	 * 
	 * @param seaboMxs1402aResultL 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL) {
		seaboMxs1402aResultLMapper.insert(seaboMxs1402aResultL);
		return (long)seaboMxs1402aResultL.getNodeid();
	}

	/**
	 * 根据主键删除指定的seabo_mxs1402a_result_l记录
	 * 
	 * @param nodeid 主键值
	 */
	public void removeSeaboMxs1402aResultL(Long nodeid) {
		seaboMxs1402aResultLMapper.delete(nodeid);
	}

	/**
	 * 更新指定的seabo_mxs1402a_result_l记录
	 * 
	 * @param seaboMxs1402aResultL
	 */
	public void updateSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL) {
		seaboMxs1402aResultLMapper.update(seaboMxs1402aResultL);
	}

	/**
	 * 根据主键查询一条seabo_mxs1402a_result_l记录
	 * 
	 * @param nodeid 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public SeaboMxs1402aResultL getSeaboMxs1402aResultL(Long nodeid) {
		return seaboMxs1402aResultLMapper.select(nodeid);
	}

	/**
	 * 根据查询对象查询seabo_mxs1402a_result_l结果列表
	 * 
	 * @return  seabo_mxs1402a_result_l记录列表
	 */
	public List<SeaboMxs1402aResultL> getSeaboMxs1402aResultLs(SeaboMxs1402aResultLQB queryBean) {
		return seaboMxs1402aResultLMapper.selectList(queryBean);
	}

	/**
	 * 查询所有seabo_mxs1402a_result_l记录
	 * 
	 * @return 所有 seabo_mxs1402a_result_l记录
	 */
	public List<SeaboMxs1402aResultL> getAllSeaboMxs1402aResultLs() {
		return seaboMxs1402aResultLMapper.selectList(null);
	}

	/**
	 * 分页查询所有seabo_mxs1402a_result_l记录
	 * 
	 * @return 当前页的 seabo_mxs1402a_result_l记录
	 */
	public PagingResultBean<List<SeaboMxs1402aResultL>> getAllSeaboMxs1402aResultLsPaging(PagingQueryBean<SeaboMxs1402aResultLQB> pagingQueryBean) {
		// 分页查询列表
		List<SeaboMxs1402aResultL> seaboMxs1402aResultLList = seaboMxs1402aResultLMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<SeaboMxs1402aResultL>> result = new PagingResultBean<List<SeaboMxs1402aResultL>>();
		result.setResultList(seaboMxs1402aResultLList);

		// 查询记录数
		Integer count = seaboMxs1402aResultLMapper.selectCount(pagingQueryBean);
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
	public void proc(SeaboMxs1402aResultL SeaboMxs1402aResultL) {
		seaboMxs1402aResultLMapper.proc(SeaboMxs1402aResultL);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxs1402aResultLMapper(SeaboMxs1402aResultLMapper seaboMxs1402aResultLMapper) {
		this.seaboMxs1402aResultLMapper = seaboMxs1402aResultLMapper;
	}
}
