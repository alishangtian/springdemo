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
import com.cattsoft.ny.base.entity.MsgType;
import com.cattsoft.ny.base.entity.querybean.MsgTypeQB;
import com.cattsoft.ny.base.persistence.MsgTypeMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class MsgTypeComponent {
	
	@Autowired
	private MsgTypeMapper msgTypeMapper;

	/**
	 * 插入msg_type一条记录
	 * 
	 * @param msgType 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createMsgType(MsgType msgType) {
		msgTypeMapper.insert(msgType);
		return msgType.getId();
	}

	/**
	 * 根据主键删除指定的msg_type记录
	 * 
	 * @param id 主键值
	 */
	public void removeMsgType(Long id) {
		msgTypeMapper.delete(id);
	}

	/**
	 * 更新指定的msg_type记录
	 * 
	 * @param msgType
	 */
	public void updateMsgType(MsgType msgType) {
		msgTypeMapper.update(msgType);
	}

	/**
	 * 根据主键查询一条msg_type记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public MsgType getMsgType(Long id) {
		return msgTypeMapper.select(id);
	}

	/**
	 * 根据查询对象查询msg_type结果列表
	 * 
	 * @return  msg_type记录列表
	 */
	public List<MsgType> getMsgTypes(MsgTypeQB queryBean) {
		return msgTypeMapper.selectList(queryBean);
	}

	/**
	 * 查询所有msg_type记录
	 * 
	 * @return 所有 msg_type记录
	 */
	public List<MsgType> getAllMsgTypes() {
		return msgTypeMapper.selectList(null);
	}

	/**
	 * 分页查询所有msg_type记录
	 * 
	 * @return 当前页的 msg_type记录
	 */
	public PagingResultBean<List<MsgType>> getAllMsgTypesPaging(PagingQueryBean<MsgTypeQB> pagingQueryBean) {
		// 分页查询列表
		List<MsgType> msgTypeList = msgTypeMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<MsgType>> result = new PagingResultBean<List<MsgType>>();
		result.setResultList(msgTypeList);

		// 查询记录数
		Integer count = msgTypeMapper.selectCount(pagingQueryBean);
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
	public void proc(MsgType MsgType) {
		msgTypeMapper.proc(MsgType);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setMsgTypeMapper(MsgTypeMapper msgTypeMapper) {
		this.msgTypeMapper = msgTypeMapper;
	}
}
