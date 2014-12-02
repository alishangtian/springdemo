package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.func.sm.component.FunctionComponent;
import com.cattsoft.baseplatform.func.sm.entity.FuncNode;
import com.cattsoft.baseplatform.func.sm.entity.FuncTree;

@Transactional
public class FunctionServiceImpl implements FunctionService {
	private FunctionComponent functionComponent;

	public void setFunctionComponent(FunctionComponent functionComponent) {
		this.functionComponent = functionComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public List<FuncTree> initFuncTree() {
		return this.functionComponent.initFuncTree();
	}

	@Transactional(readOnly = true)
	@Override
	public List<FuncNode> getFuncNodeList(Long nodeTreeId) {
		return this.functionComponent.getFuncNodeList(nodeTreeId);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray getTopMenu() {
		return this.functionComponent.getTopMenu();
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray getSubMenu(Long userId, Long nodeTreeId) {
		return this.functionComponent.getSubMenu(userId, nodeTreeId);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray getFavorMenu(Long userId) {
		return this.functionComponent.getFavorMenu(userId);
	}

}
