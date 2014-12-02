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
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.persistence.BaseMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class BaseComponent {
	
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 插入base一条记录
	 * 
	 * @param base 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createBase(Base base) {
		baseMapper.insert(base);
		return base.getId();
	}

	/**
	 * 根据主键删除指定的base记录
	 * 
	 * @param id 主键值
	 */
	public void removeBase(Long id) {
		baseMapper.delete(id);
	}

	/**
	 * 更新指定的base记录
	 * 
	 * @param base
	 */
	public void updateBase(Base base) {
		baseMapper.update(base);
	}

	/**
	 * 根据主键查询一条base记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public Base getBase(Long id) {
		return baseMapper.select(id);
	}

	/**
	 * 根据查询对象查询base结果列表
	 * 
	 * @return  base记录列表
	 */
	public List<Base> getBases(BaseQB queryBean) {
		return baseMapper.selectList(queryBean);
	}

	/**
	 * 查询所有base记录
	 * 
	 * @return 所有 base记录
	 */
	public List<Base> getAllBases() {
		return baseMapper.selectList(null);
	}

	/**
	 * 分页查询所有base记录
	 * 
	 * @return 当前页的 base记录
	 */
	public PagingResultBean<List<Base>> getAllBasesPaging(PagingQueryBean<BaseQB> pagingQueryBean) {
		// 分页查询列表
		List<Base> baseList = baseMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<Base>> result = new PagingResultBean<List<Base>>();
		result.setResultList(baseList);

		// 查询记录数
		Integer count = baseMapper.selectCount(pagingQueryBean);
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
	public void proc(Base Base) {
		baseMapper.proc(Base);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}
}
