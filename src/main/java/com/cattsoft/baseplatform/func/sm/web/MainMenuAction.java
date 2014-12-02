package com.cattsoft.baseplatform.func.sm.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.service.FunctionService;
import com.cattsoft.baseplatform.func.sm.service.SysUserService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

public class MainMenuAction extends PagingSupportAction implements SessionAware {

	private static final long serialVersionUID = -91218675949045889L;
	private FunctionService functionService;
	private SysUserService ideaUserService;
	private long menuId;
	private String menuList;
	private Map<String, Object> session;

	protected static String AJAXSUCCESS = "ajaxsuccess";
	protected boolean result;
	protected String msg;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public void setIdeaUserService(SysUserService ideaUserService) {
		this.ideaUserService = ideaUserService;
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	@Override
	public String execute() {

		return SUCCESS;
	}

	public String getTopMenu() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		// JSONArray result = functionService.getTopMenu();
		JSONArray result = sysUser.getUserMenu();
		menuList = result.toString();
		return "getTopMenu";
	}

	/**
	 * 获取子菜单
	 * 
	 * @return
	 */
	public String getSubMenu() {
		// 与登录页面集成后，从session中获取登录用户信息
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		// JSONArray result = functionService.getSubMenu(sysUser.getUserId(),
		// menuId);
		// menuList = result.toString();
		boolean match = false;
		JSONArray userMenu = sysUser.getUserMenu();
		for (int index = 0; index < userMenu.size(); index++) {
			JSONObject menuLevel1 = userMenu.getJSONObject(index);
			if (menuLevel1.containsKey("subMenu")) {
				JSONArray menuLevel2 = menuLevel1.getJSONArray("subMenu");
				for (int subIndex = 0; subIndex < menuLevel2.size(); subIndex++) {
					if (menuLevel2.getJSONObject(subIndex).getString("menuId")
							.equals(String.valueOf(this.menuId))) {
						this.menuList = menuLevel2.getJSONObject(subIndex).getJSONArray("subMenu")
								.toString();
						match = true;
						break;
					}
				}
			}
			if (match) {
				break;
			}
		}
		return "getSubMenu";
	}

	/**
	 * 获取快捷方式
	 * 
	 * @return
	 */
	public String getFavourMenu() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		JSONArray result = functionService.getFavorMenu(sysUser.getUserId());
		menuList = result.toString();
		return "getFavourMenu";
	}

	/**
	 * 添加快捷方式
	 * 
	 * @return
	 */
	public String doCreateUserShortcut() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		long funcNodeId = NumberUtils.toLong(request.getParameter("menuId"));
		msg = String.valueOf(ideaUserService.createUserShortcut(sysUser.getUserId(), funcNodeId));
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 删除快捷方式
	 * 
	 * @return
	 */
	public String removeUserShortcut() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		long funcNodeId = NumberUtils.toLong(request.getParameter("menuId"));
		ideaUserService.removeUserShortcut(sysUser.getUserId(), funcNodeId);
		result = true;
		return AJAXSUCCESS;
	}

}
