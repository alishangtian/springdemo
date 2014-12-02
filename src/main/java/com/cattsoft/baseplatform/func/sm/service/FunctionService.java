package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;

public interface FunctionService {
	/**
	 * 初始化功能目录结构
	 * 
	 * @return
	 */
	public List<FuncTree> initFuncTree();

	/**
	 * 获取功能目录下的功能点及功能点对应的功能操作
	 * 
	 * @param nodeTreeId
	 * @return
	 */
	public List<FuncNode> getFuncNodeList(Long nodeTreeId);

	/**
	 * 获取主界面顶层的1、2级功能菜单
	 * 
	 * @return
	 */
	public JSONArray getTopMenu();

	/**
	 * 获取2级菜单的子菜单
	 * 
	 * @param userId
	 * @param nodeTreeId
	 * @return
	 */
	public JSONArray getSubMenu(Long userId, Long nodeTreeId);

	/**
	 * 获取用户的所有快捷方式
	 * 
	 * @param userId
	 * @return
	 */
	public JSONArray getFavorMenu(Long userId);
}
