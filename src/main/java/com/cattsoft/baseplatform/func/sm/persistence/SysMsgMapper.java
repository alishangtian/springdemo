package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.SysMsg;

/**
 * 系统消息映射类
 */
public interface SysMsgMapper {
	/**
	 * 分页查询系统消息
	 * 
	 * @param qb
	 * @return
	 */
	List<SysMsg> selectAllSysMsg();

	/**
	 * 根据Id查询系统消息
	 * 
	 * @param sysMsgId
	 * @return
	 */
	SysMsg selectSysMsgById(Long sysMsgId);

	/**
	 * 添加系统消息
	 * 
	 * @param sysMsg
	 */
	void insertSysMsg(SysMsg sysMsg);

	/**
	 * 删除系统消息
	 * 
	 * @param sysMsgId
	 */
	void deleteSysMsg(Long sysMsgId);

	/**
	 * 修改系统消息
	 * 
	 * @param sysMsg
	 */
	void updateSysMsg(SysMsg sysMsg);
}
