package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.ctrl.RoleAllocUserCtrl;
import com.cattsoft.baseplatform.func.sm.entity.query.SysRoleQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.service.SysRoleService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 角色管理
 * 
 * @author zhangweiqiang
 * 
 */
public class RoleManagerAction extends PagingSupportAction implements SessionAware {
	private static final long serialVersionUID = -2310740309823803544L;
	private SysRoleService ideaRoleService;
	private PagingResultBean<List<SysRole>> sysRoleQueryResult;
	private PagingResultBean<List<RoleAllocUserCtrl>> roleAllocUserList;
	private JSONArray funcAuthList;
	private boolean result;
	private String msg;
	private static String AJAXSUCCESS = "ajaxsuccess";
	private Map<String, Object> session;
	
	private SysUserQuery sysUserQuery;
	
	private PagingQueryBean<SysUserQuery> pqb;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public PagingResultBean<List<RoleAllocUserCtrl>> getRoleAllocUserList() {
		return roleAllocUserList;
	}

	public void setRoleAllocUserList(PagingResultBean<List<RoleAllocUserCtrl>> roleAllocUserList) {
		this.roleAllocUserList = roleAllocUserList;
	}

	public PagingResultBean<List<SysRole>> getSysRoleQueryResult() {
		return sysRoleQueryResult;
	}

	public void setSysRoleQueryResult(PagingResultBean<List<SysRole>> sysRoleQueryResult) {
		this.sysRoleQueryResult = sysRoleQueryResult;
	}

	public void setIdeaRoleService(SysRoleService ideaRoleService) {
		this.ideaRoleService = ideaRoleService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public JSONArray getFuncAuthList() {
		return funcAuthList;
	}

	public void setFuncAuthList(JSONArray funcAuthList) {
		this.funcAuthList = funcAuthList;
	}

	/**
	 * 查询角色列表
	 * 
	 * @return
	 */
	public String getSysRolePaging() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysRoleQuery sysRoleQuery = new SysRoleQuery();
		sysRoleQuery.setRoleName(request.getParameter("roleName"));
		PagingQueryBean<SysRoleQuery> sysRoleQueryPage = buildPagingQueryBean(request, sysRoleQuery);
		sysRoleQueryResult = ideaRoleService.getSysRolePaging(sysRoleQueryPage);
		return "getSysRolePaging";
	}

	/**
	 * 查询角色详细信息
	 * 
	 * @return
	 */
	public String loadSysRolePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		SysRole sysRole = ideaRoleService.getSysRole(roleId);
		request.setAttribute("sysRole", sysRole);
		return "loadSysRolePage";
	}

	/**
	 * 加载角色详细信息页面
	 * 
	 * @return
	 */
	public String loadSysRoleDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		if (roleId == -1) {
			request.setAttribute("sysRole", new SysRole());
		} else {
			request.setAttribute("sysRole", ideaRoleService.getSysRole(roleId));
		}
		return "loadSysRoleDetail";
	}

	/**
	 * 加载功能树
	 * 
	 * @return
	 */
	public String loadFuncInfoPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<FuncTree> funcTreeList = ideaRoleService.loadFuncTree();
		for (FuncTree funcTree : funcTreeList) {
			if (funcTree.getParentId() == null) {
				funcTree.setParentId(-1l);
			}
		}
		request.setAttribute("funcTreeList", funcTreeList);
		return "loadFuncInfoPage";
	}

	/**
	 * 加载角色在指定功能下的详细信息
	 * 
	 * @return
	 */
	public String loadFuncAuth() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		long nodeTreeId = NumberUtils.toLong(request.getParameter("nodeTreeId"), -1);
		funcAuthList = ideaRoleService.loadFuncAuth(sysUser.getUserId(), roleId, nodeTreeId);
		return "loadFuncAuth";
	}

	/**
	 * 加载角色的用户授权页面
	 * 
	 * @return
	 */
	public String loadSysUserQueryPage() {
		return "loadSysUserQueryPage";
	}

	/**
	 * 查询系统用户列表
	 * 
	 * @return
	 */
	public String getSysUserPaging() {
		if(pqb == null){
			pqb = new PagingQueryBean<SysUserQuery>();
			PagingInfo pInfo = new PagingInfo();
			pInfo.setPageNum(1);
			pInfo.setPageRows(10);
			pqb.setPagingInfo(pInfo);
		}
		pqb.setQueryBean(sysUserQuery);
		roleAllocUserList = ideaRoleService.getSysUserPaging(pqb);
		return "getSysUserPaging";
	}

	/**
	 * 新建角色
	 * 
	 * @return
	 */
	public String doCreateSysRole() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleName = request.getParameter("new_roleName");
		String roleDesc = request.getParameter("new_roleDesc");
		long roleId = ideaRoleService.createSysRole(roleName, roleDesc);
		result = true;
		msg = String.valueOf(roleId);
		return AJAXSUCCESS;
	}

	/**
	 * 更新角色信息
	 * 
	 * @return
	 */
	public String doModifySysRole() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleName = request.getParameter("new_roleName");
		String roleDesc = request.getParameter("new_roleDesc");
		long roleId = NumberUtils.toLong(request.getParameter("new_roleId"), -1);
		ideaRoleService.modifySysRole(roleId, roleName, roleDesc);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 */
	public String removeSysRole() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		ideaRoleService.removeSysRole(roleId);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 保存角色功能授权
	 * 
	 * @return
	 */
	public String saveRoleFuncAuth() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String funcAuthInfo = request.getParameter("funcAuthInfos");
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		ideaRoleService.saveRoleFuncAuth(roleId, JSONArray.fromObject(funcAuthInfo));
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 保存用户角色授权信息
	 * 
	 * @return
	 */
	public String saveRoleUserAuth() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String funcAuthInfo = request.getParameter("retakeUsers");
		String grantUsers = request.getParameter("grantUsers");
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		ideaRoleService
				.saveRoleUserAuth(roleId, toLongArray(funcAuthInfo), toLongArray(grantUsers));
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 将json数组字符串转成long类型的list（jsonarray只能转成int类型的）
	 * 
	 * @param arrString
	 * @return
	 */
	public List<Long> toLongArray(String arrString) {
		List<Long> list = new ArrayList<Long>();
		JSONArray array = JSONArray.fromObject(arrString);
		for (Object object : array) {
			list.add(NumberUtils.toLong(String.valueOf(object), -1));
		}
		return list;
	}

	/**
	 * 浏览角色在指定功能下的详细信息
	 * 
	 * @return
	 */
	public String viewFuncAuth() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long roleId = NumberUtils.toLong(request.getParameter("roleId"), -1);
		long nodeTreeId = NumberUtils.toLong(request.getParameter("nodeTreeId"), -1);
		funcAuthList = ideaRoleService.viewRoleFuncAuth(roleId, nodeTreeId);
		return "viewFuncAuth";
	}

	public SysUserQuery getSysUserQuery() {
		return sysUserQuery;
	}

	public void setSysUserQuery(SysUserQuery sysUserQuery) {
		this.sysUserQuery = sysUserQuery;
	}

	public PagingQueryBean<SysUserQuery> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<SysUserQuery> pqb) {
		this.pqb = pqb;
	}
}
