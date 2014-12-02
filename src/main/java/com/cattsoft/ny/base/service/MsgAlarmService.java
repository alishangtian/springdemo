/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.MsgAlarm;
import com.cattsoft.ny.base.entity.querybean.MsgAlarmQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface MsgAlarmService {
	/**
	 * 增加msg_alarm信息
	 * 
	 * @param msgAlarm msg_alarm信息
	 *
	 * @return msg_alarm标识
	 */
	Long createMsgAlarm(MsgAlarm msgAlarm);

	/**
	 * 修改msg_alarm信息
	 * 
	 * @param msgAlarm msg_alarm信息
	 */
	void updateMsgAlarm(MsgAlarm msgAlarm);

	/**
	 * 删除msg_alarm信息
	 * 
	 * @param alarmId msg_alarm标识
	 */
	void removeMsgAlarm(Long alarmId);

	/**
	 * 获取msg_alarm信息
	 * 
	 * @param alarmId msg_alarm标识
	 * @return msg_alarm信息
	 */
	MsgAlarm getMsgAlarm(Long alarmId);

	/**
	 * 获取所有msg_alarm
	 * 
	 * @return 所有msg_alarm信息的列表
	 */
	List<MsgAlarm> getAllMsgAlarms();
	
	/**
	 * 根据查询对象查询msg_alarm结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  msg_alarm记录列表
	 */
	List<MsgAlarm> getMsgAlarms(MsgAlarmQB queryBean);

	/**
	 * 分页获取msg_alarm列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页msg_alarm列表
	 */
	PagingResultBean<List<MsgAlarm>> getAllMsgAlarmsPaging(PagingQueryBean<MsgAlarmQB> pagingQueryBean);
}
