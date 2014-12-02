/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface BaseGreenHouseInfoMapper {
	void insert(BaseGreenHouseInfo baseGreenHouseInfo);

	void delete(Long id);

	void update(BaseGreenHouseInfo baseGreenHouseInfo);

	BaseGreenHouseInfo select(Long id);

	List<BaseGreenHouseInfo> selectList(BaseGreenHouseInfoQB queryBean);

	List<BaseGreenHouseInfo> selectPage(PagingQueryBean<BaseGreenHouseInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<BaseGreenHouseInfoQB> pagingQueryBean);

	Object proc(BaseGreenHouseInfo baseGreenHouseInfo);
	
}