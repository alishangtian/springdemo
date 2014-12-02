/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface InsercticidesWorksInfoMapper {
	void insert(InsercticidesWorksInfo insercticidesWorksInfo);

	void delete(Long id);

	void update(InsercticidesWorksInfo insercticidesWorksInfo);

	InsercticidesWorksInfo select(Long id);

	List<InsercticidesWorksInfo> selectList(InsercticidesWorksInfoQB queryBean);

	List<InsercticidesWorksInfo> selectPage(PagingQueryBean<InsercticidesWorksInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<InsercticidesWorksInfoQB> pagingQueryBean);

	Object proc(InsercticidesWorksInfo insercticidesWorksInfo);
	
}