package com.cattsoft.baseplatform.func.sm.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.entity.FuncItem;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.RoleFuncAlloc;
import com.cattsoft.baseplatform.func.sm.entity.RoleFuncItemAlloc;
import com.cattsoft.baseplatform.func.sm.entity.SysRole;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.UserRoleAlloc;
import com.cattsoft.baseplatform.func.sm.entity.query.RoleFuncAuthQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysRoleQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.SysUserQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;
import com.cattsoft.baseplatform.func.sm.entity.query.UserRoleAuthQuery;
import com.cattsoft.baseplatform.func.sm.persistence.FunctionMapper;
import com.cattsoft.baseplatform.func.sm.persistence.RoleFuncAllocMapper;
import com.cattsoft.baseplatform.func.sm.persistence.RoleFuncItemAllocMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysRoleMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysUserMapper;
import com.cattsoft.baseplatform.func.sm.persistence.UserRoleAllocMapper;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

public class SysRoleComponent {

	private SysRoleMapper sysRoleMapper;
	private RoleFuncAllocMapper roleFuncAllocMapper;
	private RoleFuncItemAllocMapper roleFuncItemAllocMapper;
	private UserRoleAllocMapper userRoleAllocMapper;
	private FunctionMapper functionMapper;
	private SysUserMapper sysUserMapper;

	public void setUserRoleAllocMapper(UserRoleAllocMapper userRoleAllocMapper) {
		this.userRoleAllocMapper = userRoleAllocMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	public void setFunctionMapper(FunctionMapper functionMapper) {
		this.functionMapper = functionMapper;
	}

	public void setRoleFuncAllocMapper(RoleFuncAllocMapper roleFuncAllocMapper) {
		this.roleFuncAllocMapper = roleFuncAllocMapper;
	}

	public void setRoleFuncItemAllocMapper(RoleFuncItemAllocMapper roleFuncItemAllocMapper) {
		this.roleFuncItemAllocMapper = roleFuncItemAllocMapper;
	}

	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	/**
	 * 依据角色名称，模糊查询满足条件的角色列表
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysRole>> getSysRolePaging(PagingQueryBean<SysRoleQuery> qb) {
		// 分页查询列表
		PagingResultBean<List<SysRole>> result = new PagingResultBean<List<SysRole>>();
		result.setResultList(this.sysRoleMapper.selectPageSysRole(qb));
		// 查询记录数
		qb.getPagingInfo().setTotalRows(this.sysRoleMapper.selectCountSysRole(qb));
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 创建角色
	 * 
	 * @param sysRole
	 * @return
	 */
	public Long createSysRole(SysRole sysRole) {
		this.sysRoleMapper.insert(sysRole);
		return sysRole.getRoleId();
	}

	/**
	 * 获取角色详细信息
	 * 
	 * @param roleId
	 * @return
	 */
	public SysRole getSysRole(Long roleId) {
		return this.sysRoleMapper.selectOne(roleId);
	}

	/**
	 * 角色信息更新
	 * 
	 * @param sysRole
	 */
	public void updateSysRole(SysRole sysRole) {
		this.sysRoleMapper.update(sysRole);
	}

	/**
	 * 角色注销：注销角色信息、角色授权信息
	 * 
	 * @param roleId
	 */
	public void removeSysRole(Long roleId) {
		// 1、角色信息
		SysRole sysRole = new SysRole();
		sysRole.setRoleId(roleId);
		sysRole.setSts(SysConstants.Status.STS_P);
		this.sysRoleMapper.update(sysRole);
		// 2、用户授权信息
		this.userRoleAllocMapper.destroyRole(roleId);
		// 3、功能操作权限信息
		this.roleFuncItemAllocMapper.destroyRole(roleId);
		// 4、功能权限信息
		this.roleFuncAllocMapper.destroyRole(roleId);
	}

	/**
	 * 新增角色的指定功能及功能操作授权信息
	 * 
	 * @param roleFuncAlloc
	 *            :只需要roleId、funcNodeId、designate信息
	 */
	private Long createRoleFuncAuth(RoleFuncAlloc roleFuncAlloc) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		roleFuncAlloc.setSts(SysConstants.Status.STS_A);
		roleFuncAlloc.setStsTime(time);
		roleFuncAlloc.setCreateTime(time);
		this.roleFuncAllocMapper.insert(roleFuncAlloc);
		// 主键返回，用于授予功能操作权限时的外键字段
		return roleFuncAlloc.getRoleFuncAllocId();
	}

	/**
	 * 授予功能下的功能操作权限
	 * 
	 * @param roleFuncAllocId
	 * @param funcItemInfos
	 *            :只需要funcItemId、designate信息
	 */
	private void createRoleFuncItemAuth(Long roleFuncAllocId, List<RoleFuncItemAlloc> funcItemInfos) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		for (RoleFuncItemAlloc funcItemInfo : funcItemInfos) {
			funcItemInfo.setRoleFuncAllocId(roleFuncAllocId);
			funcItemInfo.setSts(SysConstants.Status.STS_A);
			funcItemInfo.setStsTime(time);
			funcItemInfo.setCreateTime(time);
			this.roleFuncItemAllocMapper.insert(funcItemInfo);
		}
	}

	/**
	 * 收回角色的某个具体的功能操作权限
	 * 
	 * @param roleId
	 * @param funcNodeId
	 * @param funcItemId
	 */
	private void retakeFuncItemAuth(Long roleId, Long funcNodeId, Long funcItemId) {
		RoleFuncAuthQuery roleFuncAuthQuery = new RoleFuncAuthQuery();
		roleFuncAuthQuery.setRoleId(roleId);
		roleFuncAuthQuery.setFuncNodeId(funcNodeId);
		roleFuncAuthQuery.setFuncItemId(funcItemId);
		this.roleFuncItemAllocMapper.retake(roleFuncAuthQuery);
	}

	/**
	 * 收回角色的用户授权信息
	 * 
	 * @param roleId
	 * @param userId
	 */
	private void removeRoleUserAuth(Long roleId, Long userId) {
		UserRoleAuthQuery userRoleAuthQuery = new UserRoleAuthQuery();
		userRoleAuthQuery.setRoleId(roleId);
		userRoleAuthQuery.setUserId(userId);
		this.userRoleAllocMapper.retake(userRoleAuthQuery);
	}

	/**
	 * 新增角色的用户授权信息
	 * 
	 * @param roleId
	 * @param userId
	 */
	private void createRoleUserAuth(Long roleId, Long userId) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		UserRoleAlloc userRoleAlloc = new UserRoleAlloc();
		userRoleAlloc.setUserId(userId);
		userRoleAlloc.setRoleId(roleId);
		userRoleAlloc.setSts("A");
		userRoleAlloc.setStsTime(time);
		userRoleAlloc.setCreateTime(time);
		// 角色管理功能简单处理，用户获得的此角色都默认可委派
		userRoleAlloc.setDesignate(SysConstants.YES);
		this.userRoleAllocMapper.insert(userRoleAlloc);
	}

	/**
	 * 保存角色的用户授权信息
	 * 
	 * @param roleId
	 *            ：角色标识
	 * @param retakeUsers
	 *            ：取消授权的用户
	 * @param grantUsers
	 *            ：授权的用户
	 */
	public void saveRoleUserAuth(Long roleId, List<Long> retakeUsers, List<Long> grantUsers) {
		for (Long userId : retakeUsers) {
			this.removeRoleUserAuth(roleId, userId);
		}
		for (Long userId : grantUsers) {
			this.removeRoleUserAuth(roleId, userId);
			this.createRoleUserAuth(roleId, userId);
		}
	}

	/**
	 * 角色管理>>功能授权：加载角色的授权功能信息
	 * 
	 * @param operUserId
	 * @param roleId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray loadFuncAuth(Long operUserId, Long roleId, Long nodeTreeId) {
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
			optionFuncs = this.sysUserMapper.selectUserAuthFuncs(userFuncAuthQuery);
			for (FuncNode optionFunc : optionFuncs) {
				userFuncAuthQuery.setFuncNodeId(optionFunc.getFuncNodeId());
				// 查询用户在当前功能下的可委派的功能操作
				optionFunc.setFuncItemList(this.sysUserMapper
						.selectUserAuthFuncItems(userFuncAuthQuery));
			}
		}

		// 读取被授权角色已有的功能及功能操作权限信息
		RoleFuncAuthQuery roleFuncAuthQuery = new RoleFuncAuthQuery();
		roleFuncAuthQuery.setRoleId(roleId);
		roleFuncAuthQuery.setNodeTreeId(nodeTreeId);
		List<RoleFuncAlloc> authFuncs = this.roleFuncAllocMapper
				.selectAllocFuncs(roleFuncAuthQuery);

		// 组织本方法返回的JSONArray
		JSONArray retFuncInfos = new JSONArray();
		for (FuncNode funcNode : optionFuncs) {
			JSONObject funcInfo = new JSONObject();
			funcInfo.put(SysConstants.AuthJsonKey.ID, funcNode.getFuncNodeId());
			funcInfo.put(SysConstants.AuthJsonKey.TEXT, funcNode.getFuncNodeName());
			funcInfo.put(SysConstants.AuthJsonKey.CHECKED, false);
			funcInfo.put(SysConstants.AuthJsonKey.DESIGNATE, SysConstants.NO);
			// 修正功能的check、designate节点
			for (RoleFuncAlloc authFunc : authFuncs) {
				if (authFunc.getFuncNodeId().equals(funcNode.getFuncNodeId())) {
					funcInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
					funcInfo.put(SysConstants.AuthJsonKey.DESIGNATE, authFunc.getDesignate());
				}
			}
			// 读取角色在该功能下的已授权的功能操作列表
			roleFuncAuthQuery.setRoleId(roleId);
			roleFuncAuthQuery.setFuncNodeId(funcNode.getFuncNodeId());
			List<RoleFuncItemAlloc> authFuncItems = this.roleFuncItemAllocMapper
					.selectAllocFuncItems(roleFuncAuthQuery);
			JSONArray funcItemInfos = new JSONArray();
			for (FuncItem funcItem : funcNode.getFuncItemList()) {
				JSONObject funcItemInfo = new JSONObject();
				funcItemInfo.put(SysConstants.AuthJsonKey.ID, funcItem.getFuncItemId());
				funcItemInfo.put(SysConstants.AuthJsonKey.TEXT, funcItem.getFuncItemName());
				funcItemInfo.put(SysConstants.AuthJsonKey.CHECKED, false);
				funcItemInfo.put(SysConstants.AuthJsonKey.DESIGNATE, SysConstants.NO);
				// 修正功能操作的checked、designate节点
				for (RoleFuncItemAlloc authFuncItem : authFuncItems) {
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
	 * 保存角色的功能授权信息
	 * 
	 * @param roleId
	 *            :角色标识
	 * @param funcAuthInfos
	 *            :前台提交的授权结果
	 */
	public void saveRoleFuncAuth(Long roleId, JSONArray funcAuthInfos) {
		for (int index = 0, size = funcAuthInfos.size(); index < size; index++) {
			JSONObject funcAuthInfo = funcAuthInfos.getJSONObject(index);
			// 判断checked
			boolean checked = funcAuthInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED);
			Long funcNodeId = funcAuthInfo.getLong(SysConstants.AuthJsonKey.ID);
			if (checked) {// 授予用户功能权限
				RoleFuncAlloc roleFuncAlloc = new RoleFuncAlloc();
				roleFuncAlloc.setRoleId(roleId);
				roleFuncAlloc.setFuncNodeId(funcNodeId);
				roleFuncAlloc = this.roleFuncAllocMapper.selectAllocFunc(roleFuncAlloc);
				// 判断是新授权还是保留授权
				if (null == roleFuncAlloc) {
					// 新授权的功能，则功能操作也全部是新授权
					RoleFuncAlloc newFuncAuth = new RoleFuncAlloc();
					newFuncAuth.setRoleId(roleId);
					newFuncAuth.setFuncNodeId(funcNodeId);
					newFuncAuth.setDesignate(funcAuthInfo
							.getString(SysConstants.AuthJsonKey.DESIGNATE));
					Long roleFuncAllocId = this.createRoleFuncAuth(newFuncAuth);
					// 解析授权的功能操作
					if (funcAuthInfo.containsKey(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS)) {
						JSONArray funcItemInfos = funcAuthInfo
								.getJSONArray(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS);
						List<RoleFuncItemAlloc> funcItemAllocList = new ArrayList<RoleFuncItemAlloc>();
						for (int itemIndex = 0; itemIndex < funcItemInfos.size(); itemIndex++) {
							JSONObject funcItemInfo = funcItemInfos.getJSONObject(itemIndex);
							Long funcItemId = funcItemInfo.getLong(SysConstants.AuthJsonKey.ID);
							if (funcItemInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED)) {
								// 当前功能操作被授权
								RoleFuncItemAlloc funcItemAlloc = new RoleFuncItemAlloc();
								funcItemAlloc.setFuncItemId(funcItemId);
								funcItemAlloc.setDesignate(funcItemInfo
										.getString(SysConstants.AuthJsonKey.DESIGNATE));
								funcItemAllocList.add(funcItemAlloc);
							}
						}
						this.createRoleFuncItemAuth(roleFuncAllocId, funcItemAllocList);
					}
				} else {
					// 保留授权，但是可能变更委派标志信息
					String designate = funcAuthInfo.getString(SysConstants.AuthJsonKey.DESIGNATE);
					if (!roleFuncAlloc.getDesignate().equals(designate)) {
						// 委派标识发生变更
						roleFuncAlloc.setDesignate(designate);
						this.roleFuncAllocMapper.modifyDesignate(roleFuncAlloc);
					}
					// 解析授权的功能操作：只能对前台提交的需要取消授权的功能操作进行授权取消操作
					JSONArray funcItemInfos = funcAuthInfo
							.getJSONArray(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS);
					List<RoleFuncItemAlloc> funcItemAllocList = new ArrayList<RoleFuncItemAlloc>();
					for (int itemIndex = 0; itemIndex < funcItemInfos.size(); itemIndex++) {
						JSONObject funcItemInfo = funcItemInfos.getJSONObject(itemIndex);
						Long funcItemId = funcItemInfo.getLong(SysConstants.AuthJsonKey.ID);
						// 分析：最终功能操作授权依旧存在，但可能变更委派标志，为简化处理采取先删再增，最终功能操作授权不存在，直接删除
						// 结论：不论最终授权是否存在，都先删除原授权信息；如最终授权存在，再新增
						this.retakeFuncItemAuth(roleId, funcNodeId, funcItemId);
						if (funcItemInfo.getBoolean(SysConstants.AuthJsonKey.CHECKED)) {
							// 组织新增对象
							RoleFuncItemAlloc funcItemAlloc = new RoleFuncItemAlloc();
							funcItemAlloc.setFuncItemId(funcItemId);
							funcItemAlloc.setDesignate(funcItemInfo
									.getString(SysConstants.AuthJsonKey.DESIGNATE));
							funcItemAllocList.add(funcItemAlloc);
						}
					}
					// 最终授权存在的功能操作
					this.createRoleFuncItemAuth(roleFuncAlloc.getRoleFuncAllocId(),
							funcItemAllocList);
				}
			} else {// 取消用户的功能权限
				RoleFuncAuthQuery roleFuncAuthQuery = new RoleFuncAuthQuery();
				roleFuncAuthQuery.setRoleId(roleId);
				roleFuncAuthQuery.setFuncNodeId(funcNodeId);
				this.roleFuncItemAllocMapper.retake(roleFuncAuthQuery);
				this.roleFuncAllocMapper.retake(roleFuncAuthQuery);
			}
		}
	}

	/**
	 * 查询所有已关联的系统用户信息
	 * 
	 * @param qb
	 * @return
	 */
	public PagingResultBean<List<SysUser>> getAuthSysUserPaging(PagingQueryBean<SysUserQuery> qb) {
		PagingResultBean<List<SysUser>> result = new PagingResultBean<List<SysUser>>();
		if (SysConstants.SysUser.OwnerType.DEPT.equals(qb.getQueryBean().getOwnerType())) {
			result.setResultList(this.sysRoleMapper.selectPageDeptUser(qb));
			qb.getPagingInfo().setTotalRows(this.sysRoleMapper.selectCountDeptUser(qb));
		} else if (SysConstants.SysUser.OwnerType.STAFF.equals(qb.getQueryBean().getOwnerType())) {
			result.setResultList(this.sysRoleMapper.selectPageStaffUser(qb));
			qb.getPagingInfo().setTotalRows(this.sysRoleMapper.selectCountStaffUser(qb));
		}
		result.setPagingInfo(qb.getPagingInfo());
		return result;
	}

	/**
	 * 角色与用户的授权关系是否存在
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public boolean roleAuthUser(Long roleId, Long userId) {
		UserRoleAlloc userRoleAlloc = new UserRoleAlloc();
		userRoleAlloc.setUserId(userId);
		userRoleAlloc.setRoleId(roleId);
		userRoleAlloc = this.userRoleAllocMapper.selectUserRoleAlloc(userRoleAlloc);
		return null == userRoleAlloc ? false : true;
	}

	/**
	 * 浏览角色的功能权限
	 * 
	 * @param roleId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray viewRoleFuncAuth(Long roleId, Long nodeTreeId) {
		JSONArray retFuncInfos = new JSONArray();
		List<FuncNode> optionFuncs = this.functionMapper.selectListFuncNode(nodeTreeId);
		RoleFuncAuthQuery roleFuncAuthQuery = new RoleFuncAuthQuery();
		roleFuncAuthQuery.setRoleId(roleId);
		roleFuncAuthQuery.setNodeTreeId(nodeTreeId);
		List<RoleFuncAlloc> authFuncs = this.roleFuncAllocMapper
				.selectAllocFuncs(roleFuncAuthQuery);
		for (FuncNode funcNode : optionFuncs) {
			for (RoleFuncAlloc authFunc : authFuncs) {
				if (authFunc.getFuncNodeId().equals(funcNode.getFuncNodeId())) {
					// 浏览时，只显示已授权的功能
					JSONObject funcInfo = new JSONObject();
					funcInfo.put(SysConstants.AuthJsonKey.ID, funcNode.getFuncNodeId());
					funcInfo.put(SysConstants.AuthJsonKey.TEXT, funcNode.getFuncNodeName());
					funcInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
					funcInfo.put(SysConstants.AuthJsonKey.DESIGNATE, authFunc.getDesignate());

					// 读取角色在该功能下的已授权的功能操作列表
					roleFuncAuthQuery.setRoleId(roleId);
					roleFuncAuthQuery.setFuncNodeId(funcNode.getFuncNodeId());
					List<RoleFuncItemAlloc> authFuncItems = this.roleFuncItemAllocMapper
							.selectAllocFuncItems(roleFuncAuthQuery);
					JSONArray funcItemInfos = new JSONArray();
					for (FuncItem funcItem : funcNode.getFuncItemList()) {
						for (RoleFuncItemAlloc authFuncItem : authFuncItems) {
							if (authFuncItem.getFuncItemId().equals(funcItem.getFuncItemId())) {
								JSONObject funcItemInfo = new JSONObject();
								funcItemInfo.put(SysConstants.AuthJsonKey.ID,
										funcItem.getFuncItemId());
								funcItemInfo.put(SysConstants.AuthJsonKey.TEXT,
										funcItem.getFuncItemName());
								funcItemInfo.put(SysConstants.AuthJsonKey.CHECKED, true);
								funcItemInfo.put(SysConstants.AuthJsonKey.DESIGNATE,
										authFuncItem.getDesignate());
								funcItemInfos.add(funcItemInfo);
								break;
							}
						}
					}
					funcInfo.put(SysConstants.AuthJsonKey.FUNC_ITEM_INFOS, funcItemInfos);
					retFuncInfos.add(funcInfo);
					break;
				}
			}
		}
		return retFuncInfos;
	}
}
