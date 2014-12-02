package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;
import com.cattsoft.baseplatform.func.sm.entity.query.UserFuncAuthQuery;
import com.cattsoft.baseplatform.func.sm.persistence.FunctionMapper;
import com.cattsoft.baseplatform.func.sm.persistence.SysUserMapper;

public class FunctionComponent {

	private FunctionMapper functionMapper;

	private SysUserMapper sysUserMapper;

	public void setFunctionMapper(FunctionMapper functionMapper) {
		this.functionMapper = functionMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	/**
	 * 初始化功能目录结构
	 * 
	 * @return
	 */
	public List<FuncTree> initFuncTree() {
		return this.functionMapper.selectListFuncTree();
	}

	/**
	 * 获取功能目录下的功能点及功能点对应的功能操作
	 * 
	 * @param nodeTreeId
	 * @return
	 */
	public List<FuncNode> getFuncNodeList(Long nodeTreeId) {
		return this.functionMapper.selectListFuncNode(nodeTreeId);
	}

	/**
	 * 获取直属子节点
	 * 
	 * @param nodeTreeId
	 * @return
	 */
	public List<FuncTree> getChildrenFuncTree(Long nodeTreeId) {
		return this.functionMapper.selectChildrenFuncTree(nodeTreeId);
	}

	/**
	 * 获取用户的所有快捷方式
	 * 
	 * @param userId
	 * @return
	 */
	public JSONArray getFavorMenu(Long userId) {
		List<FuncNode> funcs = this.sysUserMapper.selectUserShortcut(userId);
		JSONArray jsonArray = new JSONArray();
		for (FuncNode func : funcs) {
			JSONObject funcJSON = new JSONObject();
			funcJSON.put("isParent", false);
			funcJSON.put("menuId", func.getFuncNodeId().toString());
			funcJSON.put("menuName", func.getFuncNodeName());
			funcJSON.put("menuUrl", func.getFuncNodeUrl());
			jsonArray.add(funcJSON);
		}
		return jsonArray;
	}

	/**
	 * 获取2级菜单的子菜单
	 * 
	 * @param userId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray getSubMenu(Long userId, Long nodeTreeId) {
		// 获取二级菜单的所有子菜单
		JSONArray jsonArray = new JSONArray();
		List<FuncTree> children = this.getChildrenFuncTree(nodeTreeId);
		// 子目录
		for (FuncTree child : children) {
			jsonArray.add(this.buildSubMenuJSON(userId, child));
		}
		// 直属功能节点
		List<FuncNode> funcs = this.getUserAuthFuncs(userId, nodeTreeId);
		for (FuncNode func : funcs) {
			JSONObject funcJSON = new JSONObject();
			funcJSON.put("isParent", false);
			funcJSON.put("menuId", func.getFuncNodeId().toString());
			funcJSON.put("menuName", func.getFuncNodeName());
			funcJSON.put("menuUrl", func.getFuncNodeUrl());
			jsonArray.add(funcJSON);
		}
		return jsonArray;
	}

	/**
	 * 获取用户在某功能目录下已授权的功能点
	 * 
	 * @param userId
	 * @param nodeTreeId
	 * @return
	 */
	private List<FuncNode> getUserAuthFuncs(Long userId, Long nodeTreeId) {
		UserFuncAuthQuery userFuncAuthQuery = new UserFuncAuthQuery();
		userFuncAuthQuery.setUserId(userId);
		userFuncAuthQuery.setNodeTreeId(nodeTreeId);
		return this.sysUserMapper.selectUserAuthFuncs(userFuncAuthQuery);
	}

	private JSONObject buildSubMenuJSON(Long userId, FuncTree funcTree) {
		JSONObject json = new JSONObject();
		json.put("isParent", true);
		json.put("menuId", funcTree.getNodeTreeId().toString());
		json.put("menuName", funcTree.getNodeTreeName());
		json.put("menuUrl", "");
		// 判断目录节点是否存在子目录节点
		List<FuncTree> children = this.getChildrenFuncTree(funcTree.getNodeTreeId());
		if (children.isEmpty()) {
			// 获取功能目录下的功能
			JSONArray subMenu = new JSONArray();
			List<FuncNode> funcs = this.getUserAuthFuncs(userId, funcTree.getNodeTreeId());
			for (FuncNode func : funcs) {
				JSONObject funcJSON = new JSONObject();
				funcJSON.put("isParent", false);
				funcJSON.put("menuId", func.getFuncNodeId().toString());
				funcJSON.put("menuName", func.getFuncNodeName());
				funcJSON.put("menuUrl", func.getFuncNodeUrl());
				subMenu.add(funcJSON);
			}
			json.put("subMenu", subMenu);
		} else {
			// 获取子目录
			JSONArray subMenu = new JSONArray();
			for (FuncTree child : children) {
				JSONObject childJSON = this.buildSubMenuJSON(userId, child);
				subMenu.add(childJSON);
			}
			json.put("subMenu", subMenu);
		}
		return json;
	}

	/**
	 * 获取主界面顶层的1、2级功能菜单
	 * 
	 * @return
	 */
	public JSONArray getTopMenu() {
		JSONArray result = new JSONArray();
		// 1.获取一级菜单:父节点为0
		List<FuncTree> first = this.getChildrenFuncTree(0L);
		for (FuncTree funcTree : first) {
			JSONObject json = new JSONObject();
			json.put("isParent", true);
			json.put("menuId", funcTree.getNodeTreeId().toString());
			json.put("menuName", funcTree.getNodeTreeName());
			json.put("menuUrl", "");
			JSONArray subMenu = new JSONArray();
			// 2.获取对应的二级菜单
			List<FuncTree> children = this.getChildrenFuncTree(funcTree.getNodeTreeId());
			for (FuncTree child : children) {
				JSONObject childJSON = new JSONObject();
				childJSON.put("isParent", true);
				childJSON.put("menuId", child.getNodeTreeId().toString());
				childJSON.put("menuName", child.getNodeTreeName());
				childJSON.put("menuUrl", "");
				childJSON.put("subMenu", new JSONArray());
				subMenu.add(childJSON);
			}
			json.put("subMenu", subMenu);
			result.add(json);
		}
		return result;
	}
}
