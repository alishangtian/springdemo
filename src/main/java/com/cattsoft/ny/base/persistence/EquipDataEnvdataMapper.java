/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataEnvdataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipDataEnvdataMapper {
	void insert(EquipDataEnvdata equipDataEnvdata);

	void delete(Long dataId);

	void update(EquipDataEnvdata equipDataEnvdata);

	EquipDataEnvdata select(Long dataId);

	List<EquipDataEnvdata> selectList(EquipDataEnvdataQB queryBean);
	List<EquipDataEnvdata> selectListTime(EquipDataEnvdataQB queryBean);
	
	List<EquipDataEnvdataInfo> selectListInfo(Long typeid);
	
	List<EquipDataEnvdata> selectPage(PagingQueryBean<EquipDataEnvdataQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipDataEnvdataQB> pagingQueryBean);

	Object proc(EquipDataEnvdata equipDataEnvdata);
	
	List<EquipDataEnvdata> maxEnvdata();
	
}