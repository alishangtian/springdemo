/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
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
public interface MsgTypeService {
	/**
	 * 增加msg_type信息
	 * 
	 * @param msgType msg_type信息
	 *
	 * @return msg_type标识
	 */
	Long createMsgType(MsgType msgType);

	/**
	 * 修改msg_type信息
	 * 
	 * @param msgType msg_type信息
	 */
	void updateMsgType(MsgType msgType);

	/**
	 * 删除msg_type信息
	 * 
	 * @param id msg_type标识
	 */
	void removeMsgType(Long id);

	/**
	 * 获取msg_type信息
	 * 
	 * @param id msg_type标识
	 * @return msg_type信息
	 */
	MsgType getMsgType(Long id);

	/**
	 * 获取所有msg_type
	 * 
	 * @return 所有msg_type信息的列表
	 */
	List<MsgType> getAllMsgTypes();
	
	/**
	 * 根据查询对象查询msg_type结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  msg_type记录列表
	 */
	List<MsgType> getMsgTypes(MsgTypeQB queryBean);

	/**
	 * 分页获取msg_type列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页msg_type列表
	 */
	PagingResultBean<List<MsgType>> getAllMsgTypesPaging(PagingQueryBean<MsgTypeQB> pagingQueryBean);
}
