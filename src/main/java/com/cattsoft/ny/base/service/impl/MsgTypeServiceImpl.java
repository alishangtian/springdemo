/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.component.MsgTypeComponent;
import com.cattsoft.ny.base.entity.MsgType;
import com.cattsoft.ny.base.entity.querybean.MsgTypeQB;
import com.cattsoft.ny.base.service.MsgTypeService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("msgTypeService")
@Transactional
public class MsgTypeServiceImpl implements MsgTypeService {
	
	Log log = LogFactory.getLog(MsgTypeServiceImpl.class);
	
	@Autowired
	private MsgTypeComponent msgTypeComponent;

	@Override
	public Long createMsgType(MsgType msgType) {
		return msgTypeComponent.createMsgType(msgType);
	}

	@Override
	public void updateMsgType(MsgType msgType) {
		msgTypeComponent.updateMsgType(msgType);
	}

	@Override
	public void removeMsgType(Long id) {
		msgTypeComponent.removeMsgType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public MsgType getMsgType(Long id) {
		return msgTypeComponent.getMsgType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MsgType> getAllMsgTypes() {
		return msgTypeComponent.getAllMsgTypes();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<MsgType> getMsgTypes(MsgTypeQB queryBean) {
		return msgTypeComponent.getMsgTypes(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<MsgType>> getAllMsgTypesPaging(PagingQueryBean<MsgTypeQB> qb) {
		return msgTypeComponent.getAllMsgTypesPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setMsgTypeComponent(MsgTypeComponent msgTypeComponent) {
		this.msgTypeComponent = msgTypeComponent;
	}
}
