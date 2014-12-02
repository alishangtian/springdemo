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
import com.cattsoft.ny.base.component.MsgAlarmComponent;
import com.cattsoft.ny.base.entity.MsgAlarm;
import com.cattsoft.ny.base.entity.querybean.MsgAlarmQB;
import com.cattsoft.ny.base.service.MsgAlarmService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("msgAlarmService")
@Transactional
public class MsgAlarmServiceImpl implements MsgAlarmService {
	
	Log log = LogFactory.getLog(MsgAlarmServiceImpl.class);
	
	@Autowired
	private MsgAlarmComponent msgAlarmComponent;

	@Override
	public Long createMsgAlarm(MsgAlarm msgAlarm) {
		return msgAlarmComponent.createMsgAlarm(msgAlarm);
	}

	@Override
	public void updateMsgAlarm(MsgAlarm msgAlarm) {
		msgAlarmComponent.updateMsgAlarm(msgAlarm);
	}

	@Override
	public void removeMsgAlarm(Long alarmId) {
		msgAlarmComponent.removeMsgAlarm(alarmId);
	}

	@Transactional(readOnly = true)
	@Override
	public MsgAlarm getMsgAlarm(Long alarmId) {
		return msgAlarmComponent.getMsgAlarm(alarmId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MsgAlarm> getAllMsgAlarms() {
		return msgAlarmComponent.getAllMsgAlarms();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<MsgAlarm> getMsgAlarms(MsgAlarmQB queryBean) {
		return msgAlarmComponent.getMsgAlarms(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<MsgAlarm>> getAllMsgAlarmsPaging(PagingQueryBean<MsgAlarmQB> qb) {
		return msgAlarmComponent.getAllMsgAlarmsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setMsgAlarmComponent(MsgAlarmComponent msgAlarmComponent) {
		this.msgAlarmComponent = msgAlarmComponent;
	}
}
