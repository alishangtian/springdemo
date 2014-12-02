/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
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
@MyBatisRepository
public interface PortalcfgMapper {
	void insert(Portalcfg portalcfg);

	void delete(Long portalId);

	void update(Portalcfg portalcfg);

	Portalcfg select(Long portalId);

	List<Portalcfg> selectList(PortalcfgQB queryBean);

	List<Portalcfg> selectPage(PagingQueryBean<PortalcfgQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<PortalcfgQB> pagingQueryBean);

	Object proc(Portalcfg portalcfg);
	
}