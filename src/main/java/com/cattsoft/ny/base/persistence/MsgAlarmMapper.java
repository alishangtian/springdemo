/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
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
@MyBatisRepository
public interface MsgAlarmMapper {
	void insert(MsgAlarm msgAlarm);

	void delete(Long alarmId);

	void update(MsgAlarm msgAlarm);

	MsgAlarm select(Long alarmId);

	List<MsgAlarm> selectList(MsgAlarmQB queryBean);

	List<MsgAlarm> selectPage(PagingQueryBean<MsgAlarmQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<MsgAlarmQB> pagingQueryBean);

	Object proc(MsgAlarm msgAlarm);
	
}