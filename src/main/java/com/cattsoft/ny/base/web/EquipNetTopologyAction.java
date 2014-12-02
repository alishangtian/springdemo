package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipNetTopologyService;
import com.cattsoft.ny.base.service.EquipSensorsGhouseService;

/**
 * 
 * 设备网络拓扑action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class EquipNetTopologyAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private EquipSensorsGhouseService equipSensorsGhouseService;
	private EquipSensorsGhouseQB equipSensorsGhouseQB;
	private BaseService baseService;
	private EquipInfo equipInfo;
	private EquipInfoService equipInfoService;
	private EquipNetTopologyService equipNetTopologyService;
	private EquipNetTopology equipNetTopology;
	private PagingResultBean<List<EquipNetTopology>> equipNetTopologyResult;
	private PagingQueryBean<EquipNetTopologyQB> pqb;
	private String parentId;
	private List<EquipInfo> eiList;
	private boolean flag;
	private String msg;
	private String equipNetTopologyCodeStr;

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setAttribute("eList", GetEquipNetTopology());
		} catch (Exception e) {
		}
		return "jumpproduceWorksInfoManager";
	}

	/**
	 * 获取拓扑树
	 * 
	 * @return
	 */
	public List<EquipNetTopology> GetEquipNetTopology() {
		List<Base> baseAll = baseService.getAllBases();
		List<EquipNetTopology> equipNetTopologyList = new ArrayList<EquipNetTopology>();
		EquipNetTopology equipNetTopology = null;
		for (Base base : baseAll) {
			equipNetTopology = new EquipNetTopology();
			equipNetTopology.setId(base.getId());
			equipNetTopology.setEquipInfoId(base.getId());
			equipNetTopology.setParentNodeId(-1L);
			equipNetTopology.setName(base.getName());
			equipNetTopologyList.add(equipNetTopology);
			getEquipTree(equipNetTopologyList, base.getId());// 获取网关
		}

		return equipNetTopologyList;
	}

	/**
	 * 获取拓扑子树
	 * 
	 * @param equipNetTopologyList
	 * @param pId
	 */
	public void getEquipTree(List<EquipNetTopology> equipNetTopologyList,
			Long pId) {
		EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
		queryBean.setParentNodeId(pId);
		List<EquipNetTopology> entList = equipNetTopologyService
				.getEquipNetTopologys(queryBean);
		for (EquipNetTopology ent : entList) {
			ent.setName(equipInfoService.getEquipInfo(ent.getEquipInfoId())
					.getName());
			equipNetTopologyList.add(ent);
			getEquipTree(equipNetTopologyList, ent.getEquipInfoId());
		}
	}

	/**
	 * 查询拓扑
	 * 
	 * @return
	 */
	public String getENTopology() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// 查询所有满足条件的列表
		pqb = new PagingQueryBean<EquipNetTopologyQB>();
		EquipNetTopologyQB equipNetTopologyQB = new EquipNetTopologyQB();
		System.out.println(parentId);

		EquipNetTopology en = equipNetTopologyService.getEquipNetTopology(Long
				.parseLong(parentId));
		if (en == null) {
			equipNetTopologyQB.setParentNodeId(Long.parseLong(parentId));
		} else {
			equipNetTopologyQB.setParentNodeId(en.getEquipInfoId());
		}

		pqb = buildPagingQueryBean(request, equipNetTopologyQB);
		this.equipNetTopologyResult = equipNetTopologyService
				.getAllEquipNetTopologysPaging(pqb);
		return "getENTopology";
	}

	/**
	 * 添加拓扑
	 * 
	 * @return
	 */
	public String addEquipNetTopology() {
		System.out.println(parentId);
		EquipNetTopology topology = equipNetTopologyService
				.getEquipNetTopology(Long.parseLong(parentId));
		try {
			EquipInfo ei = equipInfoService.getEquipInfo(topology
					.getEquipInfoId());
			String type = null;
			System.out.println("111111111111111");
			System.out.println(ei.getName());
			type = String.valueOf(Integer.parseInt(ei.getType()) + 1);
			System.out.println(type);
			EquipInfoQB eiQB = new EquipInfoQB();
			System.out.println(type);
			eiQB.setType(type);
			eiList = equipInfoService.getEquipInfosOther(eiQB);

		} catch (Exception e) {
			System.out.println("22222222222");
			EquipInfoQB eiQB = new EquipInfoQB();
			eiQB.setType("0");
			eiList = equipInfoService.getEquipInfosOther(eiQB);
		}

		return "addEquipNetTopology";
	}

	/**
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveEquipNetTopology() {
		equipNetTopology.setEquipInfoId(equipNetTopology.getId());
		EquipNetTopology eid = equipNetTopologyService.getEquipNetTopology(Long
				.parseLong(parentId));
		if (eid == null) {
			equipNetTopology.setParentNodeId(Long.parseLong(parentId));
		} else {
			equipNetTopology.setParentNodeId(eid.getEquipInfoId());
		}

		equipNetTopology.setNodeOrder(1);
		equipNetTopology.setType("0");
		// this.flag = false;
		if (null == this.equipNetTopology) {
			return "addProduceWorksInfo";
		}

		try {
			this.equipNetTopologyService
					.createEquipNetTopology(equipNetTopology);

			// this.flag = true;
		} catch (Exception e) {
			// this.flag = false;
		}

		return "saveEquipNetTopology";
	}

	/**
	 * '
	 * 
	 * 删除节点
	 * 
	 * @return
	 */
	public String deleteEquipNetTopology() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.equipNetTopologyCodeStr)) {
			String[] domainCodes = this.equipNetTopologyCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.equipNetTopologyService.removeEquipNetTopology(Long
							.parseLong(domainCode2));
					this.flag = true;
				} catch (Exception e) {
					failCode.append(domainCode2).append(" ");
					this.flag = false;
				}
			}
			this.flag = true;
		}
		if (StringUtils.isNotBlank(failCode.toString())) {
			this.flag = false;
			this.msg = "代码为:" + failCode.toString() + "的值域删除失败";
		} else {
			this.flag = true;
		}
		return "deleteEquipNetTopology";
	}

	public PagingResultBean<List<EquipNetTopology>> getEquipNetTopologyResult() {
		return equipNetTopologyResult;
	}

	public void setEquipNetTopologyResult(
			PagingResultBean<List<EquipNetTopology>> equipNetTopologyResult) {
		this.equipNetTopologyResult = equipNetTopologyResult;
	}

	public PagingQueryBean<EquipNetTopologyQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<EquipNetTopologyQB> pqb) {
		this.pqb = pqb;
	}

	public EquipNetTopology getEquipNetTopology() {
		return equipNetTopology;
	}

	public void setEquipNetTopology(EquipNetTopology equipNetTopology) {
		this.equipNetTopology = equipNetTopology;
	}

	public EquipNetTopologyService getEquipNetTopologyService() {
		return equipNetTopologyService;
	}

	public void setEquipNetTopologyService(
			EquipNetTopologyService equipNetTopologyService) {
		this.equipNetTopologyService = equipNetTopologyService;
	}

	public EquipInfoService getEquipInfoService() {
		return equipInfoService;
	}

	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}

	public EquipSensorsGhouseQB getEquipSensorsGhouseQB() {
		return equipSensorsGhouseQB;
	}

	public void setEquipSensorsGhouseQB(
			EquipSensorsGhouseQB equipSensorsGhouseQB) {
		this.equipSensorsGhouseQB = equipSensorsGhouseQB;
	}

	public EquipInfo getEquipInfo() {
		return equipInfo;
	}

	public void setEquipInfo(EquipInfo equipInfo) {
		this.equipInfo = equipInfo;
	}

	public EquipSensorsGhouseService getEquipSensorsGhouseService() {
		return equipSensorsGhouseService;
	}

	public void setEquipSensorsGhouseService(
			EquipSensorsGhouseService equipSensorsGhouseService) {
		this.equipSensorsGhouseService = equipSensorsGhouseService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<EquipInfo> getEiList() {
		return eiList;
	}

	public void setEiList(List<EquipInfo> eiList) {
		this.eiList = eiList;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEquipNetTopologyCodeStr() {
		return equipNetTopologyCodeStr;
	}

	public void setEquipNetTopologyCodeStr(String equipNetTopologyCodeStr) {
		this.equipNetTopologyCodeStr = equipNetTopologyCodeStr;
	}

}