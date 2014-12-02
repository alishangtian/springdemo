/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Portalcfg;
import com.cattsoft.ny.base.entity.querybean.PortalcfgQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface PortalcfgService {
	/**
	 * 增加portalcfg信息
	 * 
	 * @param portalcfg portalcfg信息
	 *
	 * @return portalcfg标识
	 */
	Long createPortalcfg(Portalcfg portalcfg);

	/**
	 * 修改portalcfg信息
	 * 
	 * @param portalcfg portalcfg信息
	 */
	void updatePortalcfg(Portalcfg portalcfg);

	/**
	 * 删除portalcfg信息
	 * 
	 * @param portalId portalcfg标识
	 */
	void removePortalcfg(Long portalId);

	/**
	 * 获取portalcfg信息
	 * 
	 * @param portalId portalcfg标识
	 * @return portalcfg信息
	 */
	Portalcfg getPortalcfg(Long portalId);

	/**
	 * 获取所有portalcfg
	 * 
	 * @return 所有portalcfg信息的列表
	 */
	List<Portalcfg> getAllPortalcfgs();
	
	/**
	 * 根据查询对象查询portalcfg结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  portalcfg记录列表
	 */
	List<Portalcfg> getPortalcfgs(PortalcfgQB queryBean);

	/**
	 * 分页获取portalcfg列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页portalcfg列表
	 */
	PagingResultBean<List<Portalcfg>> getAllPortalcfgsPaging(PagingQueryBean<PortalcfgQB> pagingQueryBean);
}
