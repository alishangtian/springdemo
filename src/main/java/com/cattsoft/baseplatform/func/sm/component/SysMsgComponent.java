package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.SysMsg;
import com.cattsoft.baseplatform.func.sm.persistence.SysMsgMapper;

/**
 * 系统消息组件类
 */
public class SysMsgComponent {

	private SysMsgMapper sysMsgMapper;

	public void setSysMsgMapper(SysMsgMapper sysMsgMapper) {
		this.sysMsgMapper = sysMsgMapper;
	}

	/**
	 * 分页查询系统消息
	 * 
	 * @param qb
	 * @return
	 */
	public List<SysMsg> getAllSysMsg() {
		return sysMsgMapper.selectAllSysMsg();
	}

	/**
	 * 根据Id查询系统消息
	 * 
	 * @param sysMsgId
	 * @return
	 */
	public SysMsg getSysMsgById(Long sysMsgId) {
		return sysMsgMapper.selectSysMsgById(sysMsgId);
	}

	/**
	 * 添加系统消息
	 * 
	 * @param sysMsg
	 */
	public void addSysMsg(SysMsg sysMsg) {
		sysMsgMapper.insertSysMsg(sysMsg);
	}

	/**
	 * 删除系统消息
	 * 
	 * @param sysMsgId
	 */
	public void deleteSysMsg(Long sysMsgId) {
		sysMsgMapper.deleteSysMsg(sysMsgId);
	}

	/**
	 * 修改系统消息
	 * 
	 * @param sysMsg
	 */
	public void editSysMsg(SysMsg sysMsg) {
		sysMsgMapper.updateSysMsg(sysMsg);
	}
}
