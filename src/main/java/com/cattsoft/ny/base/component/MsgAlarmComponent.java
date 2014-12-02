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
import com.cattsoft.ny.base.entity.MsgAlarm;
import com.cattsoft.ny.base.entity.querybean.MsgAlarmQB;
import com.cattsoft.ny.base.persistence.MsgAlarmMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class MsgAlarmComponent {
	
	@Autowired
	private MsgAlarmMapper msgAlarmMapper;

	/**
	 * 插入msg_alarm一条记录
	 * 
	 * @param msgAlarm 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createMsgAlarm(MsgAlarm msgAlarm) {
		msgAlarmMapper.insert(msgAlarm);
		return msgAlarm.getAlarmId();
	}

	/**
	 * 根据主键删除指定的msg_alarm记录
	 * 
	 * @param alarmId 主键值
	 */
	public void removeMsgAlarm(Long alarmId) {
		msgAlarmMapper.delete(alarmId);
	}

	/**
	 * 更新指定的msg_alarm记录
	 * 
	 * @param msgAlarm
	 */
	public void updateMsgAlarm(MsgAlarm msgAlarm) {
		msgAlarmMapper.update(msgAlarm);
	}

	/**
	 * 根据主键查询一条msg_alarm记录
	 * 
	 * @param alarmId 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public MsgAlarm getMsgAlarm(Long alarmId) {
		return msgAlarmMapper.select(alarmId);
	}

	/**
	 * 根据查询对象查询msg_alarm结果列表
	 * 
	 * @return  msg_alarm记录列表
	 */
	public List<MsgAlarm> getMsgAlarms(MsgAlarmQB queryBean) {
		return msgAlarmMapper.selectList(queryBean);
	}

	/**
	 * 查询所有msg_alarm记录
	 * 
	 * @return 所有 msg_alarm记录
	 */
	public List<MsgAlarm> getAllMsgAlarms() {
		return msgAlarmMapper.selectList(null);
	}

	/**
	 * 分页查询所有msg_alarm记录
	 * 
	 * @return 当前页的 msg_alarm记录
	 */
	public PagingResultBean<List<MsgAlarm>> getAllMsgAlarmsPaging(PagingQueryBean<MsgAlarmQB> pagingQueryBean) {
		// 分页查询列表
		List<MsgAlarm> msgAlarmList = msgAlarmMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<MsgAlarm>> result = new PagingResultBean<List<MsgAlarm>>();
		result.setResultList(msgAlarmList);

		// 查询记录数
		Integer count = msgAlarmMapper.selectCount(pagingQueryBean);
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
	public void proc(MsgAlarm MsgAlarm) {
		msgAlarmMapper.proc(MsgAlarm);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setMsgAlarmMapper(MsgAlarmMapper msgAlarmMapper) {
		this.msgAlarmMapper = msgAlarmMapper;
	}
}
