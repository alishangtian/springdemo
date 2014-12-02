package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.EquipDataTypeCommon;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipNetTopologyService;
import com.cattsoft.ny.base.service.EquipSensorsGhouseService;

/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class GreenHouseAndPointAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private BaseService baseService;
	private EquipSensorsGhouseService equipSensorsGhouseService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private EquipInfoService equipInfoService;
	private List<EquipInfo> equipInfosList;
	private JSONArray greenHousePointList;
	private boolean flag;
	private String msg;
	private List<BaseGreenHouseTree> synTreeNodes;
	private BaseGreenHouseTree baseGreenHouseTree;
	private PagingResultBean<List<EquipControlInfo>> equipControlInfoResult;
	private PagingQueryBean<EquipControlInfoQB> query;
	private EquipNetTopologyService equipNetTopologyService;
	private PagingQueryBean<EquipNetTopologyQB> equipNetQuery;
	private PagingResultBean<List<EquipNetTopology>> equipNetTopologyResult;

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			EquipInfoQB equipInfoQB = new EquipInfoQB();
			equipInfosList = equipInfoService.getEquipInfos(equipInfoQB);
			request.setAttribute("baseGreenHouseTreeList",
					GetBaseGreenHouseTree());
		} catch (Exception e) {
		}

		return "jumpGreenHouseAndPointManager";
	}

	/**
	 * 获取控制设备端口信息列表
	 * 
	 * @Title: getEquipControlInfoquery
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getEquipControlInfoQuery() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nodeId = request.getParameter("nodeId");
		EquipControlInfoQB equipInfoQB = new EquipControlInfoQB();
		if (StringUtils.isNotBlank(nodeId)) {
			equipInfoQB.setEquipId(nodeId);
		}
		/* 查询所有满足条件的列表 */
		query = buildPagingQueryBean(request, equipInfoQB);
		List<EquipControlInfo> datas = equipInfoService.seletePage(query);
		Integer count = equipInfoService.selectCount(query);
		equipControlInfoResult = new PagingResultBean<List<EquipControlInfo>>();
		equipControlInfoResult.setResultList(datas);
		PagingInfo pi = query.getPagingInfo();
		pi.setTotalRows(count);
		equipControlInfoResult.setPagingInfo(pi);
		return "equipControlInfoResult";
	}

	/**
	 * 获取节点下的控制器分页数据
	 * 
	 * @Title: getEquipControlInfoquery
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getNodeControlInfoQuery() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nodeId = request.getParameter("nodeId");
		EquipNetTopologyQB equipNetTopologyQB = new EquipNetTopologyQB();
		if (StringUtils.isNotBlank(nodeId)) {
			equipNetTopologyQB.setParentNodeId(Long.parseLong(nodeId));
		}
		equipNetQuery = buildPagingQueryBean(request, equipNetTopologyQB);
		this.setEquipNetTopologyResult(this.equipNetTopologyService
				.getAllEquipNetTopologysPaging(equipNetQuery));
		return "equipNetTopologyResult";
	}

	/**
	 * 获取空闲的控制器列表
	 * 
	 * @Title: getFreeControlEquip
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getFreeControlEquip() {
		EquipInfoQB query = new EquipInfoQB();
		query.setCustId(getStringLoginUserId());
		query.setType(EquipDataTypeCommon.controller);
		List<EquipInfo> equips = this.equipInfoService
				.findFreeControlEquips(query);
		this.equipInfosList = equips;
		greenHousePointList = JSONArray.fromObject(equipInfosList);
		return "viewEquips";
	}

	/**
	 * 保存节点和控制器的拓扑关系
	 * 
	 * @Title: saveNodeControlInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String saveNodeControlInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String port = request.getParameter("port");
			String equipId = request.getParameter("equipId");
			String nodeId = request.getParameter("nodeId");
			if (StringUtils.isNotBlank(port) && StringUtils.isNotBlank(equipId)
					&& StringUtils.isNotBlank(nodeId)) {
				Integer portValue = Integer.parseInt(port);
				EquipNetTopologyQB query = new EquipNetTopologyQB();
				query.setParentPort(portValue);
				query.setParentNodeId(Long.parseLong(nodeId));
				List<EquipNetTopology> datas = this.equipNetTopologyService
						.findEquipNetTopologys(query);
				if (null != datas && datas.size() > 0) {
					this.flag = false;
					this.msg = "端口重复";
				} else {
					EquipNetTopology info = new EquipNetTopology();
					info.setEquipInfoId(Long.parseLong(equipId));
					info.setParentNodeId(Long.parseLong(nodeId));
					info.setParentPort(portValue);
					info.setNodeOrder(1);
					info.setNodeDeep(0);
					Long id = this.equipNetTopologyService
							.createEquipNetTopology(info);
					this.flag = true;
					this.msg = id + "";
				}
			} else {
				this.flag = false;
				this.msg = "请补全信息";
			}
		} catch (Exception e) {
			this.flag = false;
			this.msg = "exception";
		}
		return "saveOk";
	}

	/**
	 * 删除一条控制器设备端口信息
	 * 
	 * @Title: deleteControlPortInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String deleteControlPortInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			this.equipInfoService.deleteOneEquipControlInfo(Long.parseLong(id));
		} catch (Exception e) {
			this.flag = false;
		}
		this.flag = true;
		return "saveOk";
	}

	/**
	 * 删除一条节点设备端口信息
	 * 
	 * @Title: deleteNodePortInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String deleteNodePortInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			this.equipNetTopologyService.removeEquipNetTopology(Long
					.parseLong(id));
		} catch (Exception e) {
			this.flag = false;
		}
		this.flag = true;
		return "saveOk";
	}

	/**
	 * 保存控制器端口信息
	 * 
	 * @Title: saveControlPortInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String saveControlPortInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String name = request.getParameter("name");
			String port = request.getParameter("port");
			String equipId = request.getParameter("equipId");
			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(port)
					&& StringUtils.isNotBlank(equipId)) {
				EquipControlInfoQB query = new EquipControlInfoQB();
				query.setEquipId(equipId);
				query.setPort(Long.parseLong(port));
				List<EquipControlInfo> results = this.equipInfoService
						.findEquipControlInfo(query);
				if (results != null && results.size() > 0) {
					this.flag = false;
					this.msg = "端口重复";
				} else {
					EquipControlInfo info = new EquipControlInfo();
					info.setEquipId(equipId);
					info.setName(name);
					info.setPort(Long.parseLong(port));
					info.setType(Long.parseLong(EquipDataTypeCommon.controller));
					this.equipInfoService.insertOneEquipControlInfo(info);
					this.flag = true;
				}
			} else {
				this.flag = false;
				this.msg = "bad request！";
			}
		} catch (Exception e) {
			this.flag = false;
			this.msg = "exception occured!";
		}
		return "saveOk";
	}

	/**
	 * 
	 * Description:通过温室查找节点，并设置选中状态 <br>
	 * 
	 * @return
	 */
	public String getEquipByHouseId() {
		/**
		 * 1，-10代表网关 -20代表气象站 其他代表温室 2，根据类型不同，获取不同的设备 3，获取被当前基地所用的设备和其他基地未用的设备
		 * 4，返回到前台
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		Long greenHouseId = NumberUtils.toLong(
				request.getParameter("greenHouseId"), -1);
		Long baseId = NumberUtils.toLong(request.getParameter("baseId"), -1);
		Integer range = NumberUtils.toInt(request.getParameter("range"), -1);// 0全部：1可用的
		String equipName = request.getParameter("equipName");
		if (greenHouseId != -1 && baseId != -1) {
			// 获取未用的和被此基地用的设备 被此基地用的设备：（网关和气象站：基地id和设备类型）（节点：基地id，设备类型，温室id）
			// 未被此基地用且未被其他基地用的设备：设备列表比对
			EquipInfoQB equipInfoQB = new EquipInfoQB();
			EquipSensorsGhouseQB equipSensorsGhouseQB = new EquipSensorsGhouseQB();
			equipInfoQB.setState("1");
			String type = "";
			if (greenHouseId == -10l) {
				type = EquipDataTypeCommon.gateway;
				equipSensorsGhouseQB.setBaseId(baseId);
				equipInfoQB.setBaseId(baseId.toString());
			} else if (greenHouseId == -20l) {
				type = EquipDataTypeCommon.qxstation;
				equipSensorsGhouseQB.setBaseId(baseId);
				equipInfoQB.setBaseId(baseId.toString());
			} else {
				type = EquipDataTypeCommon.node;
				equipSensorsGhouseQB.setHouseId(greenHouseId);
				equipInfoQB.setHouseId(greenHouseId.toString());
			}
			equipInfoQB.setType(type);
			equipInfoQB.setCustId(getStringLoginUserId());
			if (StringUtils.isNotBlank(equipName)) {
				equipInfoQB.setName(equipName);
			}
			if (range == 1) {
				equipInfosList = equipInfoService
						.getUsefulEquipInfos(equipInfoQB);
			} else {
				equipInfoQB.setBaseId(null);
				equipInfoQB.setHouseId(null);
				equipInfosList = equipInfoService.getEquipInfos(equipInfoQB);
			}
			equipSensorsGhouseQB.setType(type);
			equipSensorsGhouseQB.setCustId(getLongLoginUserId());
			List<EquipSensorsGhouse> equipSensorsGhouses = equipSensorsGhouseService
					.getEquipSensorsGhouses(equipSensorsGhouseQB);
			for (EquipInfo equipInfo : equipInfosList) {
				for (EquipSensorsGhouse equipSensorsGhouse : equipSensorsGhouses) {
					if (equipInfo.getId().equals(
							equipSensorsGhouse.getEquipInfoId())) {
						equipInfo.setFlag(true);
						break;
					}
				}
			}
			greenHousePointList = JSONArray.fromObject(equipInfosList);
		}
		return "viewEquips";
	}

	/**
	 * 保存基地温室节点关系（设备维护）
	 * 
	 * @Title: saveGreenHousePoinInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String saveGreenHousePoinInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long greenHouseId = NumberUtils.toLong(
				request.getParameter("greenHouseId"), -1);
		long baseId = NumberUtils.toLong(request.getParameter("baseId"), -1);
		String equipType = request.getParameter("equipType");
		String custId = request.getParameter("custId");
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		EquipSensorsGhouse equipSensorsGhouse;
		String equipIds = request.getParameter("equipIds");
		if (StringUtils.isNotBlank(equipIds)) {
			String[] domainCodes = equipIds.split(",");
			List<String> postIds = Arrays.asList(domainCodes);
			EquipSensorsGhouseQB queryB = new EquipSensorsGhouseQB();
			if (greenHouseId == -10l) {
				queryB.setBaseId(baseId);
				queryB.setType(EquipDataTypeCommon.gateway);
			} else if (greenHouseId == -20l) {
				queryB.setBaseId(baseId);
				queryB.setType(EquipDataTypeCommon.qxstation);
			} else {
				queryB.setHouseId(greenHouseId);
				queryB.setType(EquipDataTypeCommon.node);
			}
			List<EquipSensorsGhouse> oldDatas = this.equipSensorsGhouseService
					.getEquipSensorsGhouses(queryB);
			List<String> oldIds = new ArrayList<String>();
			for (EquipSensorsGhouse house : oldDatas) {
				oldIds.add(house.getEquipInfoId().toString());
			}
			List<String> newIds = new ArrayList<String>();
			List<String> deleteIds = new ArrayList<String>();
			for (String str : postIds) {
				if (!oldIds.contains(str)) {
					newIds.add(str);
				}
			}
			for (String str : oldIds) {
				if (!postIds.contains(str)) {
					deleteIds.add(str);
				}
			}
			for (String str : newIds) {
				try {
					equipSensorsGhouseService.removeEquipSensorsByEquipId(Long
							.parseLong(str));
					equipSensorsGhouse = new EquipSensorsGhouse();
					equipSensorsGhouse.setEquipInfoId(Long.parseLong(str));
					if (greenHouseId == -10l || greenHouseId == -20l) {
						equipSensorsGhouse.setBaseId(baseId);
					} else {
						equipSensorsGhouse.setHouseId(greenHouseId);
					}
					equipSensorsGhouse.setType(equipType);
					equipSensorsGhouse.setCustId(Long.parseLong(custId));
					equipSensorsGhouseService
							.createEquipSensorsGhouse(equipSensorsGhouse);
				} catch (Exception e) {
					e.printStackTrace();
					failCode.append(str).append(" ");
				}
			}
			for (String str : deleteIds) {
				this.equipSensorsGhouseService.removeEquipSensorsByEquipId(Long
						.parseLong(str));
			}
		} else {
			// 获取原本的中间数据，如果不为空就全部删除，如果为空就不管
			EquipSensorsGhouseQB queryB = new EquipSensorsGhouseQB();
			if (greenHouseId == -10l) {
				queryB.setBaseId(baseId);
				queryB.setType(EquipDataTypeCommon.gateway);
			} else if (greenHouseId == -20l) {
				queryB.setBaseId(baseId);
				queryB.setType(EquipDataTypeCommon.qxstation);
			} else {
				queryB.setHouseId(greenHouseId);
				queryB.setType(EquipDataTypeCommon.node);
			}
			List<EquipSensorsGhouse> gHouses = this.equipSensorsGhouseService
					.getEquipSensorsGhouses(queryB);
			if (null != gHouses && gHouses.size() > 0) {
				for (EquipSensorsGhouse esg : gHouses) {
					this.equipSensorsGhouseService.removeEquipSensorsGhouse(esg
							.getId());
				}
			}
		}
		if (StringUtils.isNotBlank(failCode.toString())) {
			this.flag = false;
			this.msg = "代码为:" + failCode.toString() + "的值域添加失败";
		} else {
			this.flag = true;
		}
		return "saveOk";

	}

	/**
	 * 
	 * Description: 获取左侧树方法 <br>
	 * 
	 * @return
	 */
	public List<BaseGreenHouseTree> GetBaseGreenHouseTree() {
		BaseQB baseQB = new BaseQB();
		baseQB.setCustId(getLongLoginUserId());
		List<Base> baseAll = this.baseService.getBases(baseQB);
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseInfoQB greenHouseQB = null;
		BaseGreenHouseTree baseGreenHouseTree = null;
		for (Base base : baseAll) {
			// 添加基地
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeId(base.getId());
			baseGreenHouseTree.setNodeTreeName(base.getName());
			baseGreenHouseTree.setParentId(-1l);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;

			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeName("网关");
			baseGreenHouseTree.setParentId(base.getId());
			baseGreenHouseTree.setNodeTreeId(-10l);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeName("气象站");
			baseGreenHouseTree.setNodeTreeId(-20l);
			baseGreenHouseTree.setParentId(base.getId());
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;
			// 拼查询条件
			greenHouseQB = new BaseGreenHouseInfoQB();
			greenHouseQB.setBaseId(base.getId());

			List<BaseGreenHouseInfo> greenHouseAll = baseGreenHouseInfoService
					.getBaseGreenHouseInfos(greenHouseQB);
			if (greenHouseAll != null) {
				for (BaseGreenHouseInfo greenHouse : greenHouseAll) {
					// 添加温室信息
					baseGreenHouseTree = new BaseGreenHouseTree();
					baseGreenHouseTree.setNodeTreeId(greenHouse.getId());
					baseGreenHouseTree.setNodeTreeName(greenHouse.getName());
					baseGreenHouseTree.setParentId(base.getId());
					baseGreenHouseTreeList.add(baseGreenHouseTree);
				}
			}
		}
		return baseGreenHouseTreeList;
	}

	/**
	 * 控制设备管理页面
	 * 
	 * @Title: controlEquipManager
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String controlEquipManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("controlTree", getControlEquipInfoTree());
		return "controlEquipManager";
	}

	/**
	 * 获取控制设备管理左侧树
	 * 
	 * @Title: getControlEquipInfoTree
	 * @author Mao Xiaobing
	 * @return List<BaseGreenHouseTree> 返回类型
	 * @throws
	 */
	public List<BaseGreenHouseTree> getControlEquipInfoTree() {
		List<BaseGreenHouseTree> tree = new ArrayList<BaseGreenHouseTree>();
		EquipInfoQB query = new EquipInfoQB();
		query.setType(EquipDataTypeCommon.node);
		query.setCustId(getStringLoginUserId());
		List<EquipInfo> nodes = this.equipInfoService.getEquipInfos(query);
		BaseGreenHouseTree treeNode = new BaseGreenHouseTree();
		treeNode.setNodeTreeId(0l);
		treeNode.setNodeTreeName("所有节点");
		treeNode.setType(0);
		tree.add(treeNode);
		for (EquipInfo ei : nodes) {
			BaseGreenHouseTree eiNode = new BaseGreenHouseTree();
			eiNode.setNodeTreeId(ei.getId());
			eiNode.setNodeTreeName(ei.getName());
			eiNode.setParentId(0l);
			eiNode.setType(1);
			tree.add(eiNode);

			EquipNetTopologyQB queryQB = new EquipNetTopologyQB();
			queryQB.setParentNodeId(ei.getId());
			List<EquipNetTopology> eqbs = this.equipNetTopologyService
					.getEquipNetTopologys(queryQB);
			for (EquipNetTopology equipNetTopology : eqbs) {
				BaseGreenHouseTree controlNode = new BaseGreenHouseTree();
				controlNode.setNodeTreeId(equipNetTopology.getEquipInfoId());
				controlNode.setType(2);
				controlNode.setParentId(equipNetTopology.getParentNodeId());
				controlNode.setNodeTreeName(equipNetTopology.getName());
				tree.add(controlNode);
			}
		}
		return tree;
	}

	public String synLoadTree() {
		synTreeNodes = new ArrayList<BaseGreenHouseTree>();
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String nodeTreeId = request.getParameter("nodeTreeId");
			if (null == nodeTreeId) {
				BaseGreenHouseTree bght = new BaseGreenHouseTree();
				bght.setIsVirtualNode("1");
				bght.setNodeTreeId(0l);
				bght.setNodeTreeName("所有基地");
				bght.setParentId(null);
				this.synTreeNodes.add(bght);
			}
		} catch (Exception e) {
		}
		return "synLoadTree";
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}

	public void setEquipInfosList(List<EquipInfo> equipInfosList) {
		this.equipInfosList = equipInfosList;
	}

	public List<EquipInfo> getEquipInfosList() {
		return equipInfosList;
	}

	public JSONArray getGreenHousePointList() {
		return greenHousePointList;
	}

	public void setGreenHousePointList(JSONArray greenHousePointList) {
		this.greenHousePointList = greenHousePointList;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setEquipSensorsGhouseService(
			EquipSensorsGhouseService equipSensorsGhouseService) {
		this.equipSensorsGhouseService = equipSensorsGhouseService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public List<BaseGreenHouseTree> getSynTreeNodes() {
		return synTreeNodes;
	}

	public void setSynTreeNodes(List<BaseGreenHouseTree> synTreeNodes) {
		this.synTreeNodes = synTreeNodes;
	}

	public BaseGreenHouseTree getBaseGreenHouseTree() {
		return baseGreenHouseTree;
	}

	public void setBaseGreenHouseTree(BaseGreenHouseTree baseGreenHouseTree) {
		this.baseGreenHouseTree = baseGreenHouseTree;
	}

	public PagingResultBean<List<EquipControlInfo>> getEquipControlInfoResult() {
		return equipControlInfoResult;
	}

	public void setEquipControlInfoResult(
			PagingResultBean<List<EquipControlInfo>> equipControlInfoResult) {
		this.equipControlInfoResult = equipControlInfoResult;
	}

	public PagingQueryBean<EquipControlInfoQB> getQuery() {
		return query;
	}

	public void setQuery(PagingQueryBean<EquipControlInfoQB> query) {
		this.query = query;
	}

	/**
	 * @return the equipNetTopologyService
	 */
	public EquipNetTopologyService getEquipNetTopologyService() {
		return equipNetTopologyService;
	}

	/**
	 * @param equipNetTopologyService
	 *            the equipNetTopologyService to set
	 */
	public void setEquipNetTopologyService(
			EquipNetTopologyService equipNetTopologyService) {
		this.equipNetTopologyService = equipNetTopologyService;
	}

	/**
	 * @return the equipNetTopologyResult
	 */
	public PagingResultBean<List<EquipNetTopology>> getEquipNetTopologyResult() {
		return equipNetTopologyResult;
	}

	/**
	 * @param equipNetTopologyResult
	 *            the equipNetTopologyResult to set
	 */
	public void setEquipNetTopologyResult(
			PagingResultBean<List<EquipNetTopology>> equipNetTopologyResult) {
		this.equipNetTopologyResult = equipNetTopologyResult;
	}

}
