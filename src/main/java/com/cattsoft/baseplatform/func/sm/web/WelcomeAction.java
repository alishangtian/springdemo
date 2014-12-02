package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.func.sm.entity.PageComponent;
import com.cattsoft.baseplatform.func.sm.entity.PageLayout;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.UserPageSetting;
import com.cattsoft.baseplatform.func.sm.service.PageSettingService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

/**
 * 主页功能
 * 
 * @author zhangweiqiang
 * 
 */
public class WelcomeAction extends AjaxSupportAction implements SessionAware {
	private static final long serialVersionUID = 2026674253584923603L;
	private String layOut;
	private PageSettingService pageSettingService;
	private List<PageLayout> pageLayoutList;
	private List<PageComponent> pageComponentList;
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<PageComponent> getPageComponentList() {
		return pageComponentList;
	}

	public void setPageComponentList(List<PageComponent> pageComponentList) {
		this.pageComponentList = pageComponentList;
	}

	public List<PageLayout> getPageLayoutList() {
		return pageLayoutList;
	}

	public void setPageLayoutList(List<PageLayout> pageLayoutList) {
		this.pageLayoutList = pageLayoutList;
	}

	public PageSettingService getPageSettingService() {
		return pageSettingService;
	}

	public void setPageSettingService(PageSettingService pageSettingService) {
		this.pageSettingService = pageSettingService;
	}

	public String getLayOut() {
		return layOut;
	}

	public void setLayOut(String layOut) {
		this.layOut = layOut;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 获取布局
	 * 
	 * @return
	 */
	public String doGetLayOut() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		UserPageSetting userPageSetting = pageSettingService
				.getPageSetting(sysUser.getUserId());
		layOut = userPageSetting.getSetting();
		// layOut =
		// "[{\"width\":\"50%\",\"panel\":[{\"title\":\"一，功能组件一\",\"url\":\"\"},{\"title\":\"一，功能组件二\",\"url\":\"\"}]},{\"width\":\"49%\",\"panel\":[{\"title\":\"二，功能组件三\",\"url\":\"\"},{\"title\":\"二，功能组件四\",\"url\":\"\"}]}]";
		return "doGetLayOut";
	}

	/**
	 * 保存布局
	 * 
	 * @return
	 */
	public String savePageSetting() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		UserPageSetting userPageSetting = new UserPageSetting();
		userPageSetting.setUserId(sysUser.getUserId());
		userPageSetting.setSetting(request.getParameter("layOut"));
		pageSettingService.savePageSetting(userPageSetting);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 获取所有页面布局的列表。
	 * 
	 * @return
	 */
	public String doGetPageLayouts() {
		pageLayoutList = pageSettingService.getPageLayouts();
		return "doGetPageLayouts";
	}

	/**
	 * 获取所有功能组件的列表。
	 * 
	 * @return
	 */
	public String doGetPageComponents() {
		pageComponentList = pageSettingService.getPageComponents();
		return "doGetPageComponents";
	}

}