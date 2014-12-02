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
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
import com.cattsoft.ny.base.persistence.InsercticidesWorksInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class InsercticidesWorksInfoComponent {
	
	@Autowired
	private InsercticidesWorksInfoMapper insercticidesWorksInfoMapper;

	/**
	 * 插入insercticides_works_info一条记录
	 * 
	 * @param insercticidesWorksInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo) {
		insercticidesWorksInfoMapper.insert(insercticidesWorksInfo);
		return insercticidesWorksInfo.getId();
	}

	/**
	 * 根据主键删除指定的insercticides_works_info记录
	 * 
	 * @param id 主键值
	 */
	public void removeInsercticidesWorksInfo(Long id) {
		insercticidesWorksInfoMapper.delete(id);
	}

	/**
	 * 更新指定的insercticides_works_info记录
	 * 
	 * @param insercticidesWorksInfo
	 */
	public void updateInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo) {
		insercticidesWorksInfoMapper.update(insercticidesWorksInfo);
	}

	/**
	 * 根据主键查询一条insercticides_works_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public InsercticidesWorksInfo getInsercticidesWorksInfo(Long id) {
		return insercticidesWorksInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询insercticides_works_info结果列表
	 * 
	 * @return  insercticides_works_info记录列表
	 */
	public List<InsercticidesWorksInfo> getInsercticidesWorksInfos(InsercticidesWorksInfoQB queryBean) {
		return insercticidesWorksInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有insercticides_works_info记录
	 * 
	 * @return 所有 insercticides_works_info记录
	 */
	public List<InsercticidesWorksInfo> getAllInsercticidesWorksInfos() {
		return insercticidesWorksInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有insercticides_works_info记录
	 * 
	 * @return 当前页的 insercticides_works_info记录
	 */
	public PagingResultBean<List<InsercticidesWorksInfo>> getAllInsercticidesWorksInfosPaging(PagingQueryBean<InsercticidesWorksInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<InsercticidesWorksInfo> insercticidesWorksInfoList = insercticidesWorksInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<InsercticidesWorksInfo>> result = new PagingResultBean<List<InsercticidesWorksInfo>>();
		result.setResultList(insercticidesWorksInfoList);

		// 查询记录数
		Integer count = insercticidesWorksInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(InsercticidesWorksInfo InsercticidesWorksInfo) {
		insercticidesWorksInfoMapper.proc(InsercticidesWorksInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setInsercticidesWorksInfoMapper(InsercticidesWorksInfoMapper insercticidesWorksInfoMapper) {
		this.insercticidesWorksInfoMapper = insercticidesWorksInfoMapper;
	}

	public InsercticidesWorksInfoMapper getInsercticidesWorksInfoMapper() {
		return insercticidesWorksInfoMapper;
	}
}
