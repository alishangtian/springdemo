package com.cattsoft.baseplatform.func.sm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.service.StaffService;
import com.cattsoft.baseplatform.func.sm.service.SysUserService;
import com.cattsoft.baseplatform.func.sm.util.DateUtils;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 系统用户管理
 * 
 * @author zhangweiqiang
 * 
 */
public class SysUserManagerAction extends PagingSupportAction implements SessionAware {
	private static final long serialVersionUID = -4510024503699263861L;
	private final static String STAFF_TYPE_NEW = "n";
	private SysUserService ideaUserService;
	private StaffService staffService;

	private PagingResultBean<List<SysUser>> sysUserQueryResult;
	private String roleAuth;
	private JSONArray funcAuthList;
	private List<FuncNode> userFuncInfos;
	private boolean result;
	private String msg;
	private Map<String, Object> session;
	private SysUser sysUser;
	
	private SysUserQuery sysUserQuery;
	
	private PagingQueryBean<SysUserQuery> pqb;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
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

	public List<FuncNode> getUserFuncInfos() {
		return userFuncInfos;
	}

	public void setUserFuncInfos(List<FuncNode> userFuncInfos) {
		this.userFuncInfos = userFuncInfos;
	}

	public String getRoleAuth() {
		return roleAuth;
	}

	public void setRoleAuth(String roleAuth) {
		this.roleAuth = roleAuth;
	}

	public PagingResultBean<List<SysUser>> getSysUserQueryResult() {
		return sysUserQueryResult;
	}

	public void setSysUserQueryResult(PagingResultBean<List<SysUser>> sysUserQueryResult) {
		this.sysUserQueryResult = sysUserQueryResult;
	}

	public void setIdeaUserService(SysUserService ideaUserService) {
		this.ideaUserService = ideaUserService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
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
		sysUserQueryResult = ideaUserService.getSysUserPaging(pqb);// mockData();
		return "getSysUserPaging";
	}

	/**
	 * 获取系统用户详细信息
	 * 
	 * @return
	 */
	public String getSysUserDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		this.sysUser = ideaUserService.getSysUser(userId);// mockSysUserData();
		// request.setAttribute("sysUser", sysUser);
		return "sysUserInfo";
	}

	/**
	 * 加载角色权限页面
	 * 
	 * @return
	 */
	public String loadRoleAuthPage() {
		loadFuncTreeList();
		return "loadRoleAuthPage";
	}

	/**
	 * 加载角色权限数据
	 * 
	 * @return
	 */
	public String loadRoleAuth() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		JSONArray roleInfo = ideaUserService.loadRoleAuth(sysUser.getUserId(), userId);// mockRoleAuthData();
		if (roleInfo == null) {
			roleAuth = "";
		} else {
			roleAuth = roleInfo.toString();
		}
		return "loadRoleAuth";
	}

	/**
	 * 加载功能权限页面
	 * 
	 * @return
	 */
	public String loadFuncInfoPage() {
		loadFuncTreeList();
		return "loadFuncInfoPage";
	}

	/**
	 * 加载权限树
	 */
	private void loadFuncTreeList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<FuncTree> funcTreeList = ideaUserService.loadFuncTree();
		for (FuncTree funcTree : funcTreeList) {
			if (funcTree.getParentId() == null) {
				funcTree.setParentId(-1l);
			}
		}
		request.setAttribute("funcTreeList", funcTreeList);
	}

	/**
	 * 加载功能权限数据
	 * 
	 * @return
	 */
	public String loadFuncAuth() {
		SysUser sysUser = (SysUser) session.get(SysConstants.LOGIN_USER);
		HttpServletRequest request = ServletActionContext.getRequest();
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		long nodeTreeId = NumberUtils.toLong(request.getParameter("nodeTreeId"), -1);
		funcAuthList = ideaUserService.loadFuncAuth(sysUser.getUserId(), userId, nodeTreeId);// mockFuncAuth();
		return "loadFuncAuth";
	}

	/**
	 * 加载用户功能权限视图页面
	 * 
	 * @return
	 */
	public String loadUserFuncInfosPage() {
		return "getUserFuncInfosPage";
	}

	/**
	 * 加载用户权限视图
	 * 
	 * @return
	 */
	public String loadUserFuncInfos() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		userFuncInfos = ideaUserService.getUserFuncInfos(userId);// mockUserFuncInfos();
		return "getUserFuncInfos";
	}

	/**
	 * 新建用户提交前验证登录名称
	 * 
	 * @return
	 */
	public String valiNewLoginName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> requestMap = buildSysUserData(request);
		SysUser sysUser = (SysUser) requestMap.get("sysUser");
		if (ideaUserService.loginNameBeenUsed(sysUser.getLoginName())) {
			result = false;
			msg = "登录名称存在，请重新输入";
			return "valiNewLoginName";
		}
		result = true;
		return "valiNewLoginName";
	}

	/**
	 * 新建用户
	 * 
	 * @return
	 */
	public String doCreateSysUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> requestMap = buildSysUserData(request);
		SysUser sysUser = (SysUser) requestMap.get("sysUser");
		if (ideaUserService.loginNameBeenUsed(sysUser.getLoginName())) {
			result = false;
			msg = "登录名称存在，请重新输入";
			return "doCreateSysUser";
		}

		if (sysUser.getStaff() != null) {
			/* 新建员工:验证员工代码重复 */
			if (this.staffService.staffCodeBeenUsed(sysUser.getStaff().getStaffCode())) {
				result = false;
				msg = "员工代码存在，请重新输入";
				return "doCreateSysUser";
			}
		}
		long userId = ideaUserService.createSysUser(sysUser,
				String.valueOf(requestMap.get("ownerType")),
				String.valueOf(requestMap.get("ownerId")), sysUser.getStaff());
		result = true;
		msg = String.valueOf(userId);
		return "doCreateSysUser";
	}

	/**
	 * 构建用户数据
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> buildSysUserData(HttpServletRequest request) {
		SysUser sysUser = new SysUser();
		String ownerType = request.getParameter("new_ownerType");
		String ownerId = "";
		sysUser.setLoginName(request.getParameter("new_loginName"));
		sysUser.setUserExpTime(DateUtils.parseDataDb(request.getParameter("new_userExpTime")));
		if (SysConstants.SysUser.OwnerType.DEPT.equals(ownerType)) {// 部门
			ownerId = request.getParameter("new_dept_deptId_hid");
		} else {
			String staffType = request.getParameter("new_staff_type");
			if (STAFF_TYPE_NEW.equals(staffType)) {// 新员工
				Staff staff = new Staff();
				staff.setDeptId(NumberUtils.toLong(request.getParameter("new_add_deptId_hid"), -1));
				staff.setStaffName(request.getParameter("new_add_staffName"));
				staff.setMobileNbr(request.getParameter("new_add_mobileNbr"));
				staff.setFixNbr(request.getParameter("new_add_fixNbr"));
				staff.setEmail(request.getParameter("new_add_email"));
				staff.setStaffCode(request.getParameter("new_staffCode"));
				sysUser.setStaff(staff);
			} else {// 在职员工
				ownerId = request.getParameter("new_oldStaffId");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysUser", sysUser);
		map.put("ownerType", ownerType);
		map.put("ownerId", ownerId);
		return map;
	}

	/**
	 * 保存用户失效时间
	 * 
	 * @return
	 */
	public String doModifyUserExpTime() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userExpTime = request.getParameter("userExpTime");
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		ideaUserService.modifyUserExpTime(userId, DateUtils.parseDataDb(userExpTime));
		result = true;
		return "doModifyUserExpTime";
	}

	/**
	 * 保存角色信息
	 * 
	 * @return
	 */
	public String doSaveUserRoleAuth() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleAuthInfo = request.getParameter("roleAuthInfos");
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		ideaUserService.saveUserRoleAuth(userId, JSONArray.fromObject(roleAuthInfo));
		result = true;
		return "doSaveUserRoleAuth";
	}

	/**
	 * 保存功能信息
	 * 
	 * @return
	 */
	public String doSaveUserFuncAuth() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String funcAuthInfo = request.getParameter("funcAuthInfos");
		long userId = NumberUtils.toLong(request.getParameter("userId"), -1);
		ideaUserService.saveUserFuncAuth(userId, JSONArray.fromObject(funcAuthInfo));
		result = true;
		return "doSaveUserFuncAuth";
	}

	/**
	 * 修改用户失效时间
	 * 
	 * @return
	 */
	public String doChangeUserExpTime() {
		return "changeUserExpTime";
	}

	/**
	 * 新增用户页面
	 * 
	 * @return
	 */
	public String initAddNewUserPage() {
		return "addnewuser";
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