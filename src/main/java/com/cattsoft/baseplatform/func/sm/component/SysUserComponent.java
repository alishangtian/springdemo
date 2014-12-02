package com.cattsoft.baseplatform.func.sm.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.exception.AuthenticationException;
import com.cattsoft.baseplatform.core.exception.ComponentException;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.entity.FuncItem;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.UserFuncAlloc;
import com.cattsoft.baseplatform.func.sm.entity.UserFuncItemAlloc;
import com.cattsoft.baseplatform.func.sm.entity.UserOwnerRela;
import com.cattsoft.baseplatform.func.sm.entity.UserRoleAlloc;
import com.cattsoft.baseplatform.func.sm.entity.UserShortcut;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.UserRoleAuthQuery;
import com.cattsoft.baseplatform.func.sm.persistence.FunctionMapper;
import com.cattsoft.baseplatform.func.sm.persistence.StaffMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysDeptMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysRoleMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysUserMapper;
import com.cattsoft.baseplatform.func.sm.persistence.UserFuncAllocMapper;
import com.cattsoft.baseplatform.func.sm.persistence.UserFuncItemAllocMapper;
import com.cattsoft.baseplatform.func.sm.persistence.UserOwnerRelaMapper;
import com.cattsoft.baseplatform.func.sm.persistence.UserRoleAllocMapper;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.func.sm.util.UserMenuComparator;

public class SysUserComponent {
	public static String MENU_BRANCH_PREFIX = "B";
	public static String MENU_LEAF_PREFIX = "L";

	private SysUserMapper sysUserMapper;
	private UserRoleAllocMapper userRoleAllocMapper;
	private UserFuncAllocMapper userFuncAllocMapper;
	private UserFuncItemAllocMapper userFuncItemAllocMapper;
	private StaffMapper staffMapper;
	private SysDeptMapper sysDeptMapper;
	private SysRoleMapper sysRoleMapper;
	private FunctionMapper functionMapper;
	private UserOwnerRelaMapper userOwnerRelaMapper;

	public void setUserOwnerRelaMapper(UserOwnerRelaMapper userOwnerRelaMapper) {
		this.userOwnerRelaMapper = userOwnerRelaMapper;
	}

	public void setFunctionMapper(FunctionMapper functionMapper) {
		this.functionMapper = functionMapper;
	}

	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	public void setStaffMapper(StaffMapper staffMapper) {
		this.staffMapper = staffMapper;
	}

	public void setSysDeptMapper(SysDeptMapper sysDeptMapper) {
		this.sysDeptMapper = sysDeptMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	public void setUserRoleAllocMapper(UserRoleAllocMapper userRoleAllocMapper) {
		this.userRoleAllocMapper = userRoleAllocMapper;
	}

	public void setUserFuncAllocMapper(UserFuncAllocMapper userFuncAllocMapper) {
		this.userFuncAllocMapper = userFuncAllocMapper;
	}

	public void setUserFuncItemAllocMapper(UserFuncItemAllocMapper userFuncItemAllocMapper) {
		this.userFuncItemAllocMapper = userFuncItemAllocMapper;
	}

	/**
	 * 依据用户所有者类型、用户所有者名称、用户名称查询满足条件的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysUser>> getSysUserPaging(PagingQueryBean<SysUserQuery> qb) {
		if (SysConstants.SysUser.OwnerType.DEPT.equals(qb.getQueryBean().getOwnerType())) {
			return this.getSysDeptUserPaging(qb);
		} else if (SysConstants.SysUser.OwnerType.STAFF.equals(qb.getQueryBean().getOwnerType())) {
			return this.getStaffUserPaging(qb);
		} else {
			PagingResultBean<List<SysUser>> result = new PagingResultBean<List<SysUser>>();
			result.setResultList(new ArrayList<SysUser>());
			qb.getPagingInfo().setTotalRows(0);
			result.setPagingInfo(qb.getPagingInfo());
			return result;
		}
	}

	/**
	 * 依据用户登录名称、部门名称查询所有者为部门的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	private PagingResultBean<List<SysUser>> getSysDeptUserPaging(PagingQueryBean<SysUserQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<SysUser>> result = new PagingResultBean<List<SysUser>>();
		result.setResultList(this.sysUserMapper.selectPageDeptUser(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.sysUserMapper.selectCountDeptUser(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 依据用户登录名称、员工名称查询所有者为员工的用户列表
	 * 
	 * @param qb
	 * @return
	 */
	private PagingResultBean<List<SysUser>> getStaffUserPaging(PagingQueryBean<SysUserQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<SysUser>> result = new PagingResultBean<List<SysUser>>();
		result.setResultList(this.sysUserMapper.selectPageStaffUser(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.sysUserMapper.selectCountStaffUser(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 创建系统用户
	 * 
	 * @param sysUser
	 * @param ownerType
	 * @param ownerId
	 * @param staff
	 *            :只有新建员工才传此参数，否则传null
	 * @return
	 */
	public Long createSysUser(SysUser sysUser, String ownerType, String ownerId, Staff staff) {
		this.sysUserMapper.insert(sysUser);
		if (null != staff) {
			this.staffMapper.insert(staff);
			ownerId = staff.getStaffId().toString();
			ownerType = SysConstants.SysUser.OwnerType.STAFF;
		}
		this.createUserOwnerRela(sysUser.getUserId(), ownerType, ownerId);
		return sysUser.getUserId();
	}

	private void createUserOwnerRela(Long userId, String ownerType, String ownerId) {
		UserOwnerRela userOwnerRela = new UserOwnerRela();
		userOwnerRela.setUserId(userId);
		userOwnerRela.setOwnerType(ownerType);
		userOwnerRela.setOwnerId(ownerId);
		userOwnerRela.setSts(SysConstants.Status.STS_A);
		Timestamp time = DateUtil.getCurrentTimestamp();
		userOwnerRela.setStsTime(time);
		userOwnerRela.setCreateTime(time);
		this.userOwnerRelaMapper.insert(userOwnerRela);
	}

	/**
	 * 加载系统用户及用户对应的使用者信息
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getSysUser(Long userId) {
		SysUser sysUser = this.sysUserMapper.selectOne(userId);
		this.setSysUserOwnerInfo(sysUser);
		return sysUser;
	}

	private void setSysUserOwnerInfo(SysUser sysUser) {
		// 判断用户的使用者/所有者类型
		UserOwnerRela userOwnerRela = this.userOwnerRelaMapper
				.getUserOwnerType(sysUser.getUserId());
		if (SysConstants.SysUser.OwnerType.DEPT.equals(userOwnerRela.getOwnerType())) {
			// 使用者为部门，则将部门基本信息返回
			sysUser.setSysDept(this.sysDeptMapper.selectOne(Long.valueOf(userOwnerRela.getOwnerId())));
		} else if (SysConstants.SysUser.OwnerType.STAFF.equals(userOwnerRela.getOwnerType())) {
			// 使用者为员工，则将员工基本信息返回
			Staff staff = this.staffMapper.selectOne(Long.valueOf(userOwnerRela.getOwnerId()));
			if (staff != null && staff.getDeptId() != null) {
				staff.setSysDept(this.sysDeptMapper.selectOne(staff.getDeptId()));
			}
			sysUser.setStaff(staff);
		}
	}

	/**
	 * 依据用户登录名称获取唯一的在用、未失效用户信息，如能匹配到多个用户，则说明用户数据有误，返回null
	 * 
	 * @param loginName
	 * @return
	 */
	public SysUser getSysUserByName(String loginName) {
		List<SysUser> sysUsers = this.sysUserMapper.selectListByName(loginName);
		SysUser retSysUser = null;
		Timestamp time = DateUtil.getCurrentTimestamp();
		int index = 0;
		for (SysUser sysUser : sysUsers) {
			// 用户失效时间未过
			if (sysUser.getUserExpTime().after(time)) {
				retSysUser = sysUser;
				index++;
			}
		}
		if (index == 1) {
			this.setSysUserOwnerInfo(retSysUser);
			return retSysUser;
		}
		return null;
	}

	/**
	 * 判断登录是否被未注销的用户使用
	 * 
	 * @param loginName
	 * @return
	 */
	public boolean loginNameBeenUsed(String loginName) {
		// 依据用户登录名称查询所有未注销的用户，如列表为空则返回false，否则说明登录名称被使用，返回true
		return !(this.sysUserMapper.selectListByName(loginName).isEmpty());
	}

	/**
	 * 更改用户的失效时间
	 * 
	 * @param userId
	 * @param userExpTime
	 */
	public void modifyUserExpTime(Long userId, Timestamp userExpTime) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setUserExpTime(userExpTime);
		this.sysUserMapper.update(sysUser);
	}

	/**
	 * 注销系统用户
	 * 
	 * @param userId
	 */
	public void removeSysUser(Long userId) {
		// 1、系统用户基本信息
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setSts(SysConstants.Status.STS_P);
		this.sysUserMapper.update(sysUser);
		// 2、角色授权信息
		this.userRoleAllocMapper.destroyUser(userId);
		// 3、功能操作授权信息
		this.userFuncItemAllocMapper.destroyUser(userId);
		// 4、功能授权信息
		this.userFuncAllocMapper.destroyUser(userId);
		// 5.用户的所有者关系数据
		this.userOwnerRelaMapper.destroyUser(userId);
	}

	/**
	 * 用户密码修改
	 * 
	 * @param userId
	 *            ：用户标识
	 * @param password
	 *            ：加密后的原密码
	 * @param newPassword
	 *            ：加密后的新密码
	 */
	public void modifyPassword(Long userId, String password, String newPassword) {
		SysUser sysUser = this.sysUserMapper.selectOne(userId);
		if (SysConstants.Status.STS_P.equals(sysUser.getSts())) {
			throw new ComponentException("用户已注销，无法进行密码修改操作");
		}
		if (DateUtil.getCurrentTimestamp().after(sysUser.getUserExpTime())) {
			throw new ComponentException("用户已过期，无法进行密码修改操作");
		}
		// 1、验证原密码是否正确
		if (!password.equals(sysUser.getPassword())) {
			throw new ComponentException("原密码不正确");
		}
		// 2、验证新密码与旧密码是否相同，新密码不能与原密码相同在前台提前验证
		if (newPassword.equals(sysUser.getOldPwd())) {
			throw new ComponentException("新密码不能与前2次密码相同");
		}
		// 3、验证通过，新密码入库
		sysUser.setOldPwd(password);
		sysUser.setPassword(newPassword);
		Timestamp time = DateUtil.getCurrentTimestamp();
		sysUser.setPwdChgTime(time);
		sysUser.setPwdExpTime(DateUtil.addMonth(time, 3));
		this.sysUserMapper.update(sysUser);
	}

	/**
	 * 用户密码重置
	 * 
	 * @param userId
	 */
	public void resetPassword(Long userId, String password) {
		SysUser sysUser = this.sysUserMapper.selectOne(userId);
		if (SysConstants.Status.STS_P.equals(sysUser.getSts())) {
			throw new ComponentException("用户已注销，无法进行密码重置操作");
		}
		if (DateUtil.getCurrentTimestamp().after(sysUser.getUserExpTime())) {
			throw new ComponentException("用户已过期，无法进行密码重置操作");
		}
		// 旧密码更新为原密码
		sysUser.setOldPwd(sysUser.getPassword());
		// 新密码更新为默认密码：密文
		/* sysUser.setPassword(SysConstants.INIT_PASSWORD); */
		sysUser.setPassword(password);
		Timestamp time = DateUtil.getCurrentTimestamp();
		sysUser.setPwdChgTime(time);
		sysUser.setPwdExpTime(DateUtil.addMonth(time, 3));
		this.sysUserMapper.update(sysUser);
	}

	/**
	 * 加载系统用户的角色权限信息
	 * 
	 * @param operUserId
	 *            ：执行授权操作的用户（登录用户）
	 * @param userId
	 *            ：被授权的用户
	 * @return
	 */
	public JSONArray loadRoleAuth(Long operUserId, Long userId) {
		// 判断执行授权操作的用户
		SysUser sysUser = this.sysUserMapper.selectOne(operUserId);
		List<SysRole> optionSysRoles = null;
		if (SysConstants.SysUser.UserType.SUPER.equals(sysUser.getUserType())) {
			// 超级用户，在授权界面下执行的是角色分配工作，可分配角色来源于角色信息表(IDEA_ROLE)
			optionSysRoles = this.sysRoleMapper.selectAll();
		} else {
			// 普通用户，在授权界面下执行的是角色委派工作，可委派的角色来源执行授权操作的员工自己的已有权限角色
			optionSysRoles = this.userRoleAllocMapper.selectDesignateRoles(operUserId);
		}
		optionSysRoles = (null == optionSysRoles) ? new ArrayList<SysRole>() : optionSysRoles;
		// 被授权用户已分配的角色
		List<UserRoleAlloc> authRoles = this.userRoleAllocMapper.selectAllocRoles(userId);
		// 组织本方法返回的JSONArray
		JSONArray retRoleInfos = new JSONArray();
		for (SysRole sysRole : optionSysRoles) {
			JSONObject roleInfo = new JSONObject();
			roleInfo.put(SysConstants.AuthJsonKey.ID, sysRole.getRoleId());
			roleInfo.put(SysConstants.AuthJsonKey.TEXT, sysRole.getRoleName());
			roleInfo.put(SysConstants.AuthJsonKey.CHECKED, false);
			roleInfo.put(SysConstants.AuthJsonKey.DESIGNATE, SysConstants.NO);
			// 修正：checked、designate节点
			for (UserRoleAlloc authRole : authRoles) {
				if (authRole.getRoleId().equals(sysRole.getRoleId())) {
					roleInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
					roleInfo.put(SysConstants.AuthJsonKey.DESIGNATE, authRole.getDesignate());
					break;
				}
			}
			retRoleInfos.add(roleInfo);
		}

		return retRoleInfos;
	}

	/**
	 * 保存用户的角色授权信息
	 * 
	 * @param userId
	 * @param roleAuthInfos
	 */
	public void saveUserRoleAuth(Long userId, JSONArray roleAuthInfos) {
		for (int index = 0, size = roleAuthInfos.size(); index < size; index++) {
			JSONObject roleAuthInfo = roleAuthInfos.getJSONObject(index);
			// 判断checked
			boolean checked = roleAuthInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED);
			Long roleId = roleAuthInfo.getLong(SysConstants.AuthJsonKey.ID);
			if (checked) {// 为用户授予该角色权限
				this.removeUserRoleAuth(userId, roleId);
				this.createUserRoleAuth(userId, roleId,
						roleAuthInfo.getString(SysConstants.AuthJsonKey.DESIGNATE));
			} else {// 取消用户的角色权限
				this.removeUserRoleAuth(userId, roleId);
			}
		}
	}

	/**
	 * 加载用户的功能及功能操作权限信息
	 * 
	 * @param operUserId
	 *            ：执行授权操作的用户（登录用户）
	 * @param userId
	 *            ：被授权的用户
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray loadFuncAuth(Long operUserId, Long userId, Long nodeTreeId) {
		// 判断执行授权操作的用户
		SysUser sysUser = this.sysUserMapper.selectOne(operUserId);
		// 组织界面可进行授权操作的功能、功能操作信息
		List<FuncNode> optionFuncs = null;
		UserFuncAuthQuery userFuncAuthQuery = null;
		if (SysConstants.SysUser.UserType.SUPER.equals(sysUser.getUserType())) {
			// 超级用户，在授权界面下执行的是功能及功能操作的分配工作，可分配功能及功能操作及来源于功能点定义表(func_node)、功能操作定义表(func_item)
			optionFuncs = this.functionMapper.selectListFuncNode(nodeTreeId);
		} else {
			// 普通用户，在授权界面下执行的是功能及功能操作委派工作，可委派的功能及功能操作来源执行授权操作的员工自己的已有权限功能及功能操作
			userFuncAuthQuery = new UserFuncAuthQuery();
			userFuncAuthQuery.setUserId(operUserId);
			userFuncAuthQuery.setNodeTreeId(nodeTreeId);
			userFuncAuthQuery.setDesignate(SysConstants.YES);
			userFuncAuthQuery.setFuncNodeId(null);
			optionFuncs = this.sysUserMapper.selectUserAuthFuncs(userFuncAuthQuery);
			for (FuncNode func : optionFuncs) {
				// 查询用户在当前功能下的可委派的功能操作
				userFuncAuthQuery.setFuncNodeId(func.getFuncNodeId());
				func.setFuncItemList(this.sysUserMapper.selectUserAuthFuncItems(userFuncAuthQuery));
			}
		}

		// 读取被授权用户已有的功能及功能操作权限信息
		userFuncAuthQuery = new UserFuncAuthQuery();
		userFuncAuthQuery.setUserId(userId);
		userFuncAuthQuery.setNodeTreeId(nodeTreeId);
		List<UserFuncAlloc> authFuncs = this.userFuncAllocMapper
				.selectAllocFuncs(userFuncAuthQuery);

		// 组织本方法返回的JSONArray
		JSONArray retFuncInfos = new JSONArray();
		for (FuncNode funcNode : optionFuncs) {
			JSONObject funcInfo = new JSONObject();
			funcInfo.put(SysConstants.AuthJsonKey.ID, funcNode.getFuncNodeId());
			funcInfo.put(SysConstants.AuthJsonKey.TEXT, funcNode.getFuncNodeName());
			funcInfo.put(SysConstants.AuthJsonKey.CHECKED, false);
			funcInfo.put(SysConstants.AuthJsonKey.DESIGNATE, SysConstants.NO);
			// 修正功能的check、designate节点
			for (UserFuncAlloc authFunc : authFuncs) {
				if (authFunc.getFuncNodeId().equals(funcNode.getFuncNodeId())) {
					funcInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
					funcInfo.put(SysConstants.AuthJsonKey.DESIGNATE, authFunc.getDesignate());
				}
			}
			// 读取用户在该功能下的已授权的功能操作列表
			userFuncAuthQuery.setUserId(userId);
			userFuncAuthQuery.setFuncNodeId(funcNode.getFuncNodeId());
			List<UserFuncItemAlloc> authFuncItems = this.userFuncItemAllocMapper
					.selectAllocFuncItems(userFuncAuthQuery);
			JSONArray funcItemInfos = new JSONArray();
			for (FuncItem funcItem : funcNode.getFuncItemList()) {
				JSONObject funcItemInfo = new JSONObject();
				funcItemInfo.put(SysConstants.AuthJsonKey.ID, funcItem.getFuncItemId());
				funcItemInfo.put(SysConstants.AuthJsonKey.TEXT, funcItem.getFuncItemName());
				funcItemInfo.put(SysConstants.AuthJsonKey.CHECKED, false);
				funcItemInfo.put(SysConstants.AuthJsonKey.DESIGNATE, SysConstants.NO);
				// 修正功能操作的checked、designate节点
				for (UserFuncItemAlloc authFuncItem : authFuncItems) {
					// 功能被授权的前提下，功能操作才会被授权
					if (funcInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED)
							&& authFuncItem.getFuncItemId().equals(funcItem.getFuncItemId())) {
						funcItemInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
						funcItemInfo.put(SysConstants.AuthJsonKey.DESIGNATE,
								authFuncItem.getDesignate());
					}
				}
				funcItemInfos.add(funcItemInfo);
			}
			funcInfo.put(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS, funcItemInfos);
			retFuncInfos.add(funcInfo);
		}
		return retFuncInfos;
	}

	/**
	 * 收回用户的角色授权信息
	 * 
	 * @param userId
	 * @param roleId
	 */
	private void removeUserRoleAuth(Long userId, Long roleId) {
		UserRoleAuthQuery userRoleAuthQuery = new UserRoleAuthQuery();
		userRoleAuthQuery.setRoleId(roleId);
		userRoleAuthQuery.setUserId(userId);
		this.userRoleAllocMapper.retake(userRoleAuthQuery);
	}

	/**
	 * 新增用户的角色授权信息
	 * 
	 * @param userId
	 * @param roleId
	 * @param designate
	 */
	private void createUserRoleAuth(Long userId, Long roleId, String designate) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		UserRoleAlloc userRoleAlloc = new UserRoleAlloc();
		userRoleAlloc.setUserId(userId);
		userRoleAlloc.setRoleId(roleId);
		userRoleAlloc.setDesignate(designate);
		userRoleAlloc.setSts(SysConstants.Status.STS_A);
		userRoleAlloc.setStsTime(time);
		userRoleAlloc.setCreateTime(time);
		this.userRoleAllocMapper.insert(userRoleAlloc);
	}

	/**
	 * 保存用户的功能授权信息
	 * 
	 * @param userId
	 *            :用户标识
	 * @param funcAuthInfos
	 *            :前台提交的授权结果
	 */
	public void saveUserFuncAuth(Long userId, JSONArray funcAuthInfos) {
		for (int index = 0, size = funcAuthInfos.size(); index < size; index++) {
			JSONObject funcAuthInfo = funcAuthInfos.getJSONObject(index);
			// 判断checked
			boolean checked = funcAuthInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED);
			Long funcNodeId = funcAuthInfo.getLong(SysConstants.AuthJsonKey.ID);
			if (checked) {// 授予用户功能权限
				UserFuncAlloc userFuncAlloc = new UserFuncAlloc();
				userFuncAlloc.setUserId(userId);
				userFuncAlloc.setFuncNodeId(funcNodeId);
				userFuncAlloc = this.userFuncAllocMapper.selectAllocFunc(userFuncAlloc);
				// 判断是新授权还是保留授权
				if (null == userFuncAlloc) {
					// 新授权的功能，则功能操作也全部是新授权
					UserFuncAlloc newFuncAuth = new UserFuncAlloc();
					newFuncAuth.setUserId(userId);
					newFuncAuth.setFuncNodeId(funcNodeId);
					newFuncAuth.setDesignate(funcAuthInfo
							.getString(SysConstants.AuthJsonKey.DESIGNATE));
					Long userFuncAllocId = this.createUserFuncAuth(newFuncAuth);
					// 解析授权的功能操作
					if (funcAuthInfo.containsKey(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS)) {
						JSONArray funcItemInfos = funcAuthInfo
								.getJSONArray(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS);
						List<UserFuncItemAlloc> funcItemAllocList = new ArrayList<UserFuncItemAlloc>();
						for (int itemIndex = 0; itemIndex < funcItemInfos.size(); itemIndex++) {
							JSONObject funcItemInfo = funcItemInfos.getJSONObject(itemIndex);
							Long funcItemId = funcItemInfo.getLong(SysConstants.AuthJsonKey.ID);
							if (funcItemInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED)) {
								// 当前功能操作被授权
								UserFuncItemAlloc funcItemAlloc = new UserFuncItemAlloc();
								funcItemAlloc.setFuncItemId(funcItemId);
								funcItemAlloc.setDesignate(funcItemInfo
										.getString(SysConstants.AuthJsonKey.DESIGNATE));
								funcItemAllocList.add(funcItemAlloc);
							}
						}
						this.createUserFuncItemAuth(userFuncAllocId, funcItemAllocList);
					}
				} else {
					// 保留授权，但是可能变更委派标志信息
					String designate = funcAuthInfo.getString(SysConstants.AuthJsonKey.DESIGNATE);
					if (!userFuncAlloc.getDesignate().equals(designate)) {
						// 委派标识发生变更
						userFuncAlloc.setDesignate(designate);
						this.userFuncAllocMapper.modifyDesignate(userFuncAlloc);
					}
					// 解析授权的功能操作：只能对前台提交的需要取消授权的功能操作进行授权取消操作
					JSONArray funcItemInfos = funcAuthInfo
							.getJSONArray(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS);
					List<UserFuncItemAlloc> funcItemAllocList = new ArrayList<UserFuncItemAlloc>();
					for (int itemIndex = 0; itemIndex < funcItemInfos.size(); itemIndex++) {
						JSONObject funcItemInfo = funcItemInfos.getJSONObject(itemIndex);
						Long funcItemId = funcItemInfo.getLong(SysConstants.AuthJsonKey.ID);
						// 分析：最终功能操作授权依旧存在，但可能变更委派标志，为简化处理采取先删再增，最终功能操作授权不存在，直接删除
						// 结论：不论最终授权是否存在，都先删除原授权信息；如最终授权存在，再新增
						this.retakeFuncItemAuth(userId, funcNodeId, funcItemId);
						if (funcItemInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED)) {
							// 组织新增对象
							UserFuncItemAlloc funcItemAlloc = new UserFuncItemAlloc();
							funcItemAlloc.setFuncItemId(funcItemId);
							funcItemAlloc.setDesignate(funcItemInfo
									.getString(SysConstants.AuthJsonKey.DESIGNATE));
							funcItemAllocList.add(funcItemAlloc);
						}
					}
					// 最终授权存在的功能操作
					this.createUserFuncItemAuth(userFuncAlloc.getUserFuncAllocId(),
							funcItemAllocList);
				}
			} else {// 取消用户的功能权限
				UserFuncAuthQuery userFuncAuthQuery = new UserFuncAuthQuery();
				userFuncAuthQuery.setUserId(userId);
				userFuncAuthQuery.setFuncNodeId(funcNodeId);
				this.userFuncItemAllocMapper.retake(userFuncAuthQuery);
				this.userFuncAllocMapper.retake(userFuncAuthQuery);
			}
		}
	}

	/**
	 * 收回用户的某个具体的功能操作权限
	 * 
	 * @param userId
	 * @param funcNodeId
	 * @param funcItemId
	 */
	private void retakeFuncItemAuth(Long userId, Long funcNodeId, Long funcItemId) {
		UserFuncAuthQuery userFuncAuthQuery = new UserFuncAuthQuery();
		userFuncAuthQuery.setUserId(userId);
		userFuncAuthQuery.setFuncNodeId(funcNodeId);
		userFuncAuthQuery.setFuncItemId(funcItemId);
		this.userFuncItemAllocMapper.retake(userFuncAuthQuery);
	}

	/**
	 * 新增用户的功能授权信息
	 * 
	 * @param userId
	 * @param userFuncAlloc
	 *            :只需要userId、funcNodeId、designate信息
	 */
	private Long createUserFuncAuth(UserFuncAlloc userFuncAlloc) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		userFuncAlloc.setSts("A");
		userFuncAlloc.setStsTime(time);
		userFuncAlloc.setCreateTime(time);
		this.userFuncAllocMapper.insert(userFuncAlloc);
		// 主键返回，用于授予功能操作权限时的外键字段
		return userFuncAlloc.getUserFuncAllocId();
	}

	/**
	 * 授予功能下的功能操作权限
	 * 
	 * @param userFuncAllocId
	 * @param funcItemInfos
	 *            :只需要funcItemId、designate信息
	 */
	private void createUserFuncItemAuth(Long userFuncAllocId, List<UserFuncItemAlloc> funcItemInfos) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		for (UserFuncItemAlloc funcItemInfo : funcItemInfos) {
			funcItemInfo.setUserFuncAllocId(userFuncAllocId);
			funcItemInfo.setSts("A");
			funcItemInfo.setStsTime(time);
			funcItemInfo.setCreateTime(time);
			this.userFuncItemAllocMapper.insert(funcItemInfo);
		}
	}

	/**
	 * 用户认证
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 * @throws AuthenticationException
	 */
	public SysUser authenticate(String loginName, String password) throws AuthenticationException {
		// 依据唯一的登录名称查询在用的未注销的用户信息
		List<SysUser> sysUsers = this.sysUserMapper.selectListByName(loginName);
		Timestamp time = DateUtil.getCurrentTimestamp();
		int index = 0;
		for (SysUser sysUser : sysUsers) {
			// 用户失效时间未过，且用户密码匹配
			if (sysUser.getUserExpTime().after(time) && sysUser.getPassword().equals(password)) {
				index++;
			}
		}
		if (index == 0) {
			throw new AuthenticationException("用户名或密码不正确，或用户失效时间已过");
		} else if (index > 1) {
			throw new AuthenticationException("数据异常：用户名、密码匹配的用户不唯一");
		} else {
			SysUser retSysUser = sysUsers.get(0);
			if (retSysUser.getPwdExpTime().before(DateUtil.getCurrentTimestamp())) {
				throw new AuthenticationException("用户密码已过期");
			} else {
				this.setSysUserOwnerInfo(retSysUser);
				// 用户功能及功能操作授权信息
				retSysUser.setFuncList(this.getUserFuncInfos(retSysUser.getUserId()));
				// 用户功能菜单信息
				retSysUser.setUserMenu(this.getUserMenu(retSysUser.getUserId()));
				return retSysUser;
			}
		}
	}

	/**
	 * 获取用户的授权的功能及功能操作集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<FuncNode> getUserFuncInfos(Long userId) {
		UserFuncAuthQuery userFuncAuthQuery = new UserFuncAuthQuery();
		userFuncAuthQuery.setUserId(userId);
		userFuncAuthQuery.setNodeTreeId(null);
		userFuncAuthQuery.setDesignate(null);
		userFuncAuthQuery.setFuncNodeId(null);
		List<FuncNode> results = this.sysUserMapper.selectUserAuthFuncs(userFuncAuthQuery);
		for (FuncNode result : results) {
			userFuncAuthQuery.setFuncNodeId(result.getFuncNodeId());
			// 查询用户在当前功能下的可委派的功能操作
			result.setFuncItemList(this.sysUserMapper.selectUserAuthFuncItems(userFuncAuthQuery));
		}
		return results;
	}

	/**
	 * 获取用户功能菜单数据
	 * 
	 * @param userId
	 * @return
	 */
	public JSONArray getUserMenu(Long userId) {
		Map<String, JSONObject> branch = this.constructBranch();
		Map<String, JSONObject> leaf = this.constructLeaf(userId);
		// 所有需要显示的节点：叶子及叶子的所有上级节点
		JSONArray showNodes = new JSONArray();
		// 叶子节点的所有上级节点集合
		Set<String> branchSet = new HashSet<String>();
		for (Entry<String, JSONObject> entry : leaf.entrySet()) {
			if (branch.containsKey(entry.getValue().getString("pid"))) {
				// 叶节点需要显示
				showNodes.add(entry.getValue());
				// 递归获取叶节点的所有上级节点
				parent(branch, entry.getValue().getString("pid"), branchSet);
			}
		}
		// 需要显示的上级节点
		for (String key : branchSet) {
			showNodes.add(branch.get(key));
		}
		// 构建用户菜单树
		JSONArray userMenu = this.buildUserMenuTree(showNodes);
		// 移除构建树时添加的JSON节点数据，并不是服务调用方需要的数据
		String[] properties = { "pid", "id", "displayOrder" };
		this.removeUserMenuProperty(userMenu, properties);
		return userMenu;
	}

	/**
	 * 查找某叶子节点的所有上级节点
	 * 
	 * @param parents
	 * @param key
	 * @param parentSet
	 */
	private void parent(Map<String, JSONObject> parents, String key, Set<String> parentSet) {
		if (!parentSet.contains(key) && parents.containsKey(key)) {
			parentSet.add(key);
			for (Entry<String, JSONObject> entry : parents.entrySet()) {
				if (entry.getValue().getString("id").equals(parents.get(key).getString("pid"))) {
					parent(parents, entry.getValue().getString("id"), parentSet);
				}
			}
		}
	}

	/**
	 * 构建用户菜单树
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONArray buildUserMenuTree(JSONArray data) {
		JSONArray roots = new JSONArray();

		for (int index = 0; index < data.size(); index++) {
			JSONObject json = data.getJSONObject(index);
			if (StringUtils.isBlank(json.getString("pid"))
					|| (MENU_BRANCH_PREFIX + 0).equals(json.getString("pid"))) {
				JSONObject root = new JSONObject();
				root.put("isParent", json.getBoolean("isParent"));
				root.put("menuId", json.getString("menuId"));
				root.put("menuName", json.getString("menuName"));
				root.put("menuUrl", json.getString("menuUrl"));
				root.put("id", json.getString("id"));
				root.put("displayOrder", json.getString("displayOrder"));
				root.put("pid", json.getString("pid"));
				root.put("subMenu", children(root, data));
				roots.add(root);
			}
		}
		Collections.sort(roots, new UserMenuComparator());
		return roots;
	}

	/**
	 * 获取节点的下级节点
	 * 
	 * @param pNode
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONArray children(JSONObject pNode, JSONArray data) {
		JSONArray subMenu = new JSONArray();
		for (int index = 0; index < data.size(); index++) {
			JSONObject json = data.getJSONObject(index);
			if (pNode.getString("id").equals(json.get("pid"))) {
				JSONObject child = new JSONObject();
				child.put("isParent", json.getBoolean("isParent"));
				child.put("menuId", json.getString("menuId"));
				child.put("menuName", json.getString("menuName"));
				child.put("menuUrl", json.getString("menuUrl"));
				child.put("id", json.getString("id"));
				child.put("displayOrder", json.getString("displayOrder"));
				child.put("pid", json.getString("pid"));
				child.put("subMenu", children(child, data));
				subMenu.add(child);
			}
		}

		Collections.sort(subMenu, new UserMenuComparator());
		return subMenu;
	}

	/**
	 * 移除JSON数据中指定节点
	 * 
	 * @param menu
	 * @param keys
	 */
	private void removeUserMenuProperty(JSONArray menu, String[] keys) {
		for (int index = 0; index < menu.size(); index++) {
			for (String key : keys) {
				if (menu.getJSONObject(index).containsKey(key)) {
					menu.getJSONObject(index).remove(key);
				}
			}
			if (menu.getJSONObject(index).containsKey("subMenu")) {
				removeUserMenuProperty(menu.getJSONObject(index).getJSONArray("subMenu"), keys);
			}
		}
	}

	private Map<String, JSONObject> constructBranch() {
		Map<String, JSONObject> branch = new HashMap<String, JSONObject>();

		List<FuncTree> trees = this.functionMapper.selectListFuncTree();
		for (FuncTree tree : trees) {
			// 功能目录根节点，不构建
			if (tree.getNodeTreeId().equals(0L)) {
				continue;
			}
			JSONObject json = new JSONObject();
			json.put("id", MENU_BRANCH_PREFIX + tree.getNodeTreeId());
			json.put("pid", MENU_BRANCH_PREFIX + tree.getParentId());
			json.put("isParent", true);
			json.put("menuId", tree.getNodeTreeId());
			json.put("menuName", tree.getNodeTreeName());
			json.put("menuUrl", "");
			json.put("displayOrder", tree.getDisplayOrder());
			// json.put("subMenu", new JSONArray());
			branch.put(MENU_BRANCH_PREFIX + tree.getNodeTreeId(), json);
		}

		return branch;
	}

	private Map<String, JSONObject> constructLeaf(Long userId) {
		Map<String, JSONObject> leaf = new HashMap<String, JSONObject>();

		UserFuncAuthQuery userFuncAuthQuery = new UserFuncAuthQuery();
		userFuncAuthQuery.setUserId(userId);
		List<FuncNode> funcs = this.sysUserMapper.selectUserAuthFuncs(userFuncAuthQuery);
		for (FuncNode func : funcs) {
			JSONObject json = new JSONObject();
			json.put("id", MENU_LEAF_PREFIX + func.getFuncNodeId());
			json.put("pid", MENU_BRANCH_PREFIX + func.getNodeTreeId());
			json.put("isParent", false);
			json.put("menuId", func.getFuncNodeId());
			json.put("menuName", func.getFuncNodeName());
			json.put("menuUrl", func.getFuncNodeUrl());
			json.put("displayOrder", func.getDisplayOrder());
			leaf.put(MENU_LEAF_PREFIX + func.getFuncNodeId(), json);
		}

		return leaf;
	}

	/**
	 * 新增用户的功能快捷方式
	 * 
	 * @param userId
	 * @param funcNodeId
	 * @return
	 */
	public Long createUserShortcut(Long userId, Long funcNodeId) {
		UserShortcut userShortcut = new UserShortcut();
		userShortcut.setUserId(userId);
		userShortcut.setFuncNodeId(funcNodeId);
		userShortcut.setOrderNum(100L);
		userShortcut.setCreateTime(DateUtil.getCurrentTimestamp());
		this.sysUserMapper.insertShortcut(userShortcut);
		return userShortcut.getShortcutId();
	}

	/**
	 * 删除用户的功能快捷方式
	 * 
	 * @param userId
	 * @param funcNodeId
	 * @return
	 */
	public void removeUserShortcut(Long userId, Long funcNodeId) {
		UserShortcut userShortcut = new UserShortcut();
		userShortcut.setUserId(userId);
		userShortcut.setFuncNodeId(funcNodeId);
		this.sysUserMapper.deleteShortcut(userShortcut);
	}

}
