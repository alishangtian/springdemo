/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface BaseService {
	/**
	 * 增加base信息
	 * 
	 * @param base base信息
	 *
	 * @return base标识
	 */
	Long createBase(Base base);

	/**
	 * 修改base信息
	 * 
	 * @param base base信息
	 */
	void updateBase(Base base);

	/**
	 * 删除base信息
	 * 
	 * @param id base标识
	 */
	void removeBase(Long id);

	/**
	 * 获取base信息
	 * 
	 * @param id base标识
	 * @return base信息
	 */
	Base getBase(Long id);

	/**
	 * 获取所有base
	 * 
	 * @return 所有base信息的列表
	 */
	List<Base> getAllBases();
	
	/**
	 * 根据查询对象查询base结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  base记录列表
	 */
	List<Base> getBases(BaseQB queryBean);

	/**
	 * 分页获取base列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页base列表
	 */
	PagingResultBean<List<Base>> getAllBasesPaging(PagingQueryBean<BaseQB> pagingQueryBean);
}
