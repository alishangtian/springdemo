/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.querybean.EquipDataTypeQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface EquipDataTypeMapper {
	void insert(EquipDataType equipDataType);

	void delete(Long id);

	void update(EquipDataType equipDataType);

	EquipDataType select(Long id);

	List<EquipDataType> selectList(EquipDataTypeQB queryBean);

	List<EquipDataType> selectPage(PagingQueryBean<EquipDataTypeQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<EquipDataTypeQB> pagingQueryBean);

	Object proc(EquipDataType equipDataType);
	
	List<EquipDataEnvdataTypeInfo> selectListDataInfo(Long equipId);
	
}