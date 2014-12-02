/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.MsgType;
import com.cattsoft.ny.base.entity.querybean.MsgTypeQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface MsgTypeMapper {
	void insert(MsgType msgType);

	void delete(Long id);

	void update(MsgType msgType);

	MsgType select(Long id);

	List<MsgType> selectList(MsgTypeQB queryBean);

	List<MsgType> selectPage(PagingQueryBean<MsgTypeQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<MsgTypeQB> pagingQueryBean);

	Object proc(MsgType msgType);
	
}