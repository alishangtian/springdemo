package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;

public interface FunctionMapper {
	/**
	 * 获取所有的功能目录信息
	 * 
	 * @return
	 */
	List<FuncTree> selectListFuncTree();

	/**
	 * 获取某功能目录下的所有功能点及功能点的功能操作信息
	 * 
	 * @param nodeTreeId
	 * @return
	 */
	List<FuncNode> selectListFuncNode(Long nodeTreeId);

	/**
	 * 获取直属子节点
	 * 
	 * @return
	 */
	List<FuncTree> selectChildrenFuncTree(Long nodeTreeId);

}
