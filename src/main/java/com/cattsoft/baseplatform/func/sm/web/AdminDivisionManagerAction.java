package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.func.sm.entity.AdminDivision;
import com.cattsoft.baseplatform.func.sm.service.AdminDivisionService;
import com.cattsoft.baseplatform.web.action.Action;

/**
 * 行政区划管理
 * 
 * @author wanghuacun
 * 
 */
public class AdminDivisionManagerAction extends Action {
	private static final long serialVersionUID = -6776851689195480187L;

	private AdminDivisionService adminDivisionService;

	private String divisionId;

	private List<AdminDivision> adminDivisions = new ArrayList<AdminDivision>();

	private AdminDivision adminDivisionObj;

	private boolean flag;
	private String msg;

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 跳转到行政区划管理页面
	 * 
	 * @return
	 */
	public String jumpAdminDivisionManager() {
		return "jumpAdminDivisionManager";
	}

	/**
	 * 获取行政区划数据
	 * 
	 * @return
	 */
	public String getAdminDivision() {
		if (StringUtils.isNotBlank(divisionId)) {
			adminDivisions = adminDivisionService.getSubDivisions(Long.valueOf(divisionId));
		} else {
			AdminDivision adminDivision = adminDivisionService.getRootDivision();
			adminDivisions.add(adminDivision);
		}
		return "getAdminDivision";
	}

	/**
	 * 修改AdminDivision
	 * 
	 * @return
	 */
	public String editAdminDivision() {
		flag = false;
		if (adminDivisionObj != null) {
			try {
				AdminDivision adminDivision = adminDivisionService
						.getDivisionByCode(adminDivisionObj.getDivisionCode());
				if (adminDivision != null
						&& !adminDivision.getDivisionId().equals(adminDivisionObj.getDivisionId())) {
					msg = "更新失败：行政区划代码存在，请重新输入";
				} else {
					adminDivisionService.modifyDivision(adminDivisionObj);
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			}
		}
		return "editAdminDivision";
	}

	/**
	 * 删除AdminDivision
	 * 
	 * @return
	 */
	public String deleteAdminDivision() {
		try {
			if (StringUtils.isNotBlank(divisionId)) {
				adminDivisionService.removeDivision(Long.valueOf(divisionId));
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return "deleteAdminDivision";
	}

	/**
	 * 添加AdminDivision
	 * 
	 * @return
	 */
	public String addAdminDivision() {
		flag = false;
		if (adminDivisionObj != null) {
			try {
				if (adminDivisionService.getDivisionByCode(adminDivisionObj.getDivisionCode()) != null) {
					msg = "添加失败：行政区划代码存在，请重新输入";
				} else {
					adminDivisionService.createDivision(adminDivisionObj);
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			}
		}
		return "addAdminDivision";
	}

	public AdminDivisionService getAdminDivisionService() {
		return adminDivisionService;
	}

	public void setAdminDivisionService(AdminDivisionService adminDivisionService) {
		this.adminDivisionService = adminDivisionService;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public List<AdminDivision> getAdminDivisions() {
		return adminDivisions;
	}

	public void setAdminDivisions(List<AdminDivision> adminDivisions) {
		this.adminDivisions = adminDivisions;
	}

	public AdminDivision getAdminDivisionObj() {
		return adminDivisionObj;
	}

	public void setAdminDivisionObj(AdminDivision adminDivisionObj) {
		this.adminDivisionObj = adminDivisionObj;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}