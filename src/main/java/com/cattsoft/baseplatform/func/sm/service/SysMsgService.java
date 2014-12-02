package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.SysMsg;

/**
 * 系统消息服务接口类
 */
public interface SysMsgService {
	/**
	 * 分页查询系统消息
	 * 
	 * @param qb
	 * @return
	 */
	List<SysMsg> getAllSysMsg();

	/**
	 * 根据Id查询系统消息
	 * 
	 * @param sysMsgId
	 * @return
	 */
	public SysMsg getSysMsgById(Long sysMsgId);

	/**
	 * 添加系统消息
	 * 
	 * @param sysMsg
	 */
	public void addSysMsg(SysMsg sysMsg);

	/**
	 * 删除系统消息
	 * 
	 * @param sysMsgId
	 */
	public void deleteSysMsg(Long sysMsgId);

	/**
	 * 修改系统消息
	 * 
	 * @param sysMsg
	 */
	public void editSysMsg(SysMsg sysMsg);

}
