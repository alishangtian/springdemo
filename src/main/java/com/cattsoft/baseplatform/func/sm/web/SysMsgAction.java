package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.SysMsg;
import com.cattsoft.baseplatform.func.sm.service.SysMsgService;

/**
 * 系统消息Action类
 */
public class SysMsgAction {
	private static final long serialVersionUID = 7860732831955161934L;

	private SysMsgService sysMsgService;

	private List<SysMsg> sysMsgList;

	private boolean resultFlag;

	public SysMsgAction() {
	}

	/**
	 * 查询所有系统消息
	 * 
	 * @return
	 */
	public String getAllSysMsg() {
		try {
			sysMsgList = sysMsgService.getAllSysMsg();
			resultFlag = true;
		} catch (Exception e) {
			resultFlag = false;
		}

		return "getAllSysMsg";
	}

	public SysMsgService getSysMsgService() {
		return sysMsgService;
	}

	public void setSysMsgService(SysMsgService sysMsgService) {
		this.sysMsgService = sysMsgService;
	}

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public List<SysMsg> getSysMsgList() {
		return sysMsgList;
	}

	public void setSysMsgList(List<SysMsg> sysMsgList) {
		this.sysMsgList = sysMsgList;
	}

}
