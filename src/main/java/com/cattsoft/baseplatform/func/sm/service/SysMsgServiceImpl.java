package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.func.sm.component.SysMsgComponent;
import com.cattsoft.baseplatform.func.sm.entity.SysMsg;

/**
 * 系统消息服务类
 */
@Transactional
public class SysMsgServiceImpl implements SysMsgService {
	private SysMsgComponent sysMsgComponent;

	public void setSysMsgComponent(SysMsgComponent sysMsgComponent) {
		this.sysMsgComponent = sysMsgComponent;
	}

	/**
	 * 分页查询系统消息
	 */
	@Override
	public List<SysMsg> getAllSysMsg() {
		return sysMsgComponent.getAllSysMsg();
	}

	/**
	 * 根据Id查询系统消息
	 */
	@Override
	public SysMsg getSysMsgById(Long sysMsgId) {
		return sysMsgComponent.getSysMsgById(sysMsgId);
	}

	/**
	 * 添加系统消息
	 */
	@Override
	public void addSysMsg(SysMsg sysMsg) {
		sysMsgComponent.addSysMsg(sysMsg);
	}

	/**
	 * 删除系统消息
	 */
	@Override
	public void deleteSysMsg(Long sysMsgId) {
		sysMsgComponent.deleteSysMsg(sysMsgId);
	}

	/**
	 * 修改系统消息
	 */
	@Override
	public void editSysMsg(SysMsg sysMsg) {
		sysMsgComponent.editSysMsg(sysMsg);
	}
}
