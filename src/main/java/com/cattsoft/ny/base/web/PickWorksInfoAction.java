package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.PickWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;

/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class PickWorksInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PickWorksInfoService pickWorksInfoService;
	private BaseService baseService;
	
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	//private ProduceSeasonService produceSeasonService;
	private PickWorksInfo pickWorksInfo;
	private PickWorksInfoQB pickWorksInfoQB;
	private PagingResultBean<List<PickWorksInfo>> pickWorksInfoResult;
	private PagingQueryBean<PickWorksInfoQB> pqb;
	private String pickWorksInfoCodeStr;
	private boolean flag;
	private String msg;
	private Long pickWorksInfoId;
	private PrdcSeasonService prdcSeasonService;
	private Long GreenHouseId;
	private PrdcSeason prdcSeason;
	private DomainService domainService;
	private List<PrdcSeason> produceSeasonList;
	private List<PrdcSeason> produceSeasonListajax;
	private String abc;
	private List<DomainValue> domainValues;
	private Long houseId;
	private DomainValue domainV;
	private String typeName;
	private String seasonId;
	private String scropsajax;
	private Long id;
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			produceSeasonList = prdcSeasonService.getAllPrdcSeasons();
			 request.setAttribute("baseGreenHouseTreeList",
			 GetBaseGreenHouseTree());
			
		}
		catch (Exception e) {
				e.printStackTrace();
		}

		return "jumppickWorksInfoManager";
	}

	public void setPickWorksInfoService(PickWorksInfoService pickWorksInfoService) {
		this.pickWorksInfoService = pickWorksInfoService;
	}

	/**
	 * 获取PickWorksInfo
	 * 
	 * @return
	 */
	public String getPicksWorksInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(pqb == null){
			pqb = new PagingQueryBean<PickWorksInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if(pickWorksInfoQB == null){
			PickWorksInfoQB pickQB = new PickWorksInfoQB();
			pickQB.setUserId(((SysUser) request.getSession().getAttribute("LOGIN_USER")).getUserId());
			 pqb.setQueryBean(pickQB);
		}
		else{
			pickWorksInfoQB.setUserId(((SysUser) request.getSession().getAttribute("LOGIN_USER")).getUserId());	
			 pqb.setQueryBean(pickWorksInfoQB);
		}
		this.pickWorksInfoResult = pickWorksInfoService.getAllPickWorksInfosPaging(pqb);
		return "getPickWorksInfo";
	}

	/**
	 * 添加PickWorksInfo
	 * 
	 * @return
	 */
	public String addPickWorksInfo() {
		PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
		prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
		prdcSeasonQB.setState("1");
		produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		domainValues = domainService.getDomainValues((long) 89);
		houseId=prdcSeason.getHouseId();
		
		return "addPickWorksInfo";
	}

	public List<BaseGreenHouseTree> GetBaseGreenHouseTree() {
		String userId = getStringLoginUserId();
		BaseQB queryBean = new BaseQB();
		queryBean.setCustId(Long.parseLong(userId));
		List<Base> baseAll = baseService.getBases(queryBean);
//		List<Base> baseAll = baseService.getAllBases();
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseInfoQB baseGreenHouseInfoQB = null;
		BaseGreenHouseTree baseGreenHouseTree = null;
		for (Base base : baseAll) {

			// 添加基地
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeId(base.getId());
			baseGreenHouseTree.setNodeTreeName(base.getName());
			baseGreenHouseTree.setParentId(-1l);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;

			// 拼查询条件
			baseGreenHouseInfoQB = new BaseGreenHouseInfoQB();
			baseGreenHouseInfoQB.setBaseId(base.getId());

			List<BaseGreenHouseInfo> greenHouseAll = baseGreenHouseInfoService.getBaseGreenHouseInfos(baseGreenHouseInfoQB);
			if (greenHouseAll != null) {

				for (BaseGreenHouseInfo baseGreenHouseInfo : greenHouseAll) {
					// 添加温室信息
					baseGreenHouseTree = new BaseGreenHouseTree();
					baseGreenHouseTree.setNodeTreeId(baseGreenHouseInfo.getId());
					baseGreenHouseTree.setNodeTreeName(baseGreenHouseInfo.getName());
					baseGreenHouseTree.setParentId(base.getId());
					baseGreenHouseTreeList.add(baseGreenHouseTree);
				}

			}

		}
		return baseGreenHouseTreeList;

	}

	/**
	 * 删除PickWorksInfo
	 * 
	 * @return
	 */
	public String deletePickWorksInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.pickWorksInfoCodeStr)) {
			String[] domainCodes = this.pickWorksInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.pickWorksInfoService.removePickWorksInfo(Long.parseLong(domainCode2));
					this.flag = true;
				}
				catch (Exception e) {
					failCode.append(domainCode2).append(" ");
					this.flag = false;
				}
			}
			this.flag = true;
		}
		if (StringUtils.isNotBlank(failCode.toString())) {
			this.flag = false;
			this.msg = "代码为:" + failCode.toString() + "的值域删除失败";
		}
		else {
			this.flag = true;
		}
		return "deletePickWorksInfo";
	}

	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String savePickWorksInfo() {
		pickWorksInfo.setHouseId(houseId);
		//domainService.getDomainValues(domainId)
		this.flag = false;
		if (null == this.pickWorksInfo) {
			return "addPickWorksInfo";
		}

		try {
			this.pickWorksInfoService.createPickWorksInfo(pickWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "savePickWorksInfo";
	}

	/**
	 * 
	 * 查看施肥过程信息
	 * 
	 * @return
	 */
	public String viewPickWorksInfo() {
		
		this.flag = false;
		if (null == this.pickWorksInfoId) {
			return "addPickWorksInfo";
		}

		try {
			pickWorksInfo = this.pickWorksInfoService.getPickWorksInfo(pickWorksInfoId);
			System.out.println(pickWorksInfo.getProduceSeasonId());
			prdcSeason = prdcSeasonService.getPrdcSeason(pickWorksInfo.getProduceSeasonId());
			domainValues = domainService.getDomainValues(89L);
			for(DomainValue dv :domainValues){
				if(pickWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "viewPickWorksInfo";
	}
	

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editPickWorksInfo() {
		this.flag = false;
		if (null == this.pickWorksInfoId) {
			return "addPickWorksInfo";
		}

		try {
			
			pickWorksInfo = this.pickWorksInfoService.getPickWorksInfo(pickWorksInfoId);
			
			prdcSeason = prdcSeasonService.getPrdcSeason(pickWorksInfo.getProduceSeasonId());
			
//			***********************************
			
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setHouseId(pickWorksInfo.getHouseId());
			prdcSeasonQB.setState("1");
			prdcSeason = prdcSeasonService.getPrdcSeason(pickWorksInfo.getProduceSeasonId());
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
//			***********************************
			domainValues = domainService.getDomainValues(89L);
			for(DomainValue dv :domainValues){
				if(pickWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "editPickWorksInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updatePickWorksInfo() {
		
		this.flag = false;
		if (null == this.pickWorksInfo) {
			return "addPickWorksInfo";
		}

		try {
			this.pickWorksInfoService.updatePickWorksInfo(pickWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "updaOk";
	}
	
//	public String getPrdcSeasonByGreenHouse(){
//		PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
//		prdcSeasonQB.setHouseId(GreenHouseId);
//		prdcSeasonQB.setState("1");
//		produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
//		JSONArray json = JSONArray.fromObject(produceSeasonListajax);
//		abc=json.toString();
//		return "getPrdcSeasonByGreenHouse";
//	}
	
	public String getPrdcSeasonByGreenHouse(){
		if(id != null){
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setState("1");
			prdcSeasonQB.setHouseId(id);
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		}
		return "getPrdcSeasonByGreenHouse";
	}
	
	/**
	 * 根据种植季查询作物
	 * @return
	 */
	public String getCropBySeason(){
		try {
			PrdcSeason prdcSeasonScrop = prdcSeasonService.getPrdcSeason(Long.parseLong(seasonId));
			scropsajax = prdcSeasonScrop.getCrops();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "getCropBySeason";
	}
	public PickWorksInfo getPickWorksInfo() {
		return pickWorksInfo;
	}

	public void setPickWorksInfo(PickWorksInfo pickWorksInfo) {
		this.pickWorksInfo = pickWorksInfo;
	}

	public PagingResultBean<List<PickWorksInfo>> getPickWorksInfoResult() {
		return pickWorksInfoResult;
	}

	public void setPickWorksInfoResult(PagingResultBean<List<PickWorksInfo>> pickWorksInfoResult) {
		this.pickWorksInfoResult = pickWorksInfoResult;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getPickWorksInfoCodeStr() {
		return pickWorksInfoCodeStr;
	}

	public void setPickWorksInfoCodeStr(String pickWorksInfoCodeStr) {
		this.pickWorksInfoCodeStr = pickWorksInfoCodeStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getPickWorksInfoId() {
		return pickWorksInfoId;
	}

	public void setPickWorksInfoId(Long pickWorksInfoId) {
		this.pickWorksInfoId = pickWorksInfoId;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

//	public ProduceSeasonService getProduceSeasonService() {
//		return produceSeasonService;
//	}
//
//	public void setProduceSeasonService(ProduceSeasonService produceSeasonService) {
//		this.produceSeasonService = produceSeasonService;
//	}

	public PickWorksInfoService getPickWorksInfoService() {
		return pickWorksInfoService;
	}

	public PagingQueryBean<PickWorksInfoQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<PickWorksInfoQB> pqb) {
		this.pqb = pqb;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public PrdcSeasonService getPrdcSeasonService() {
		return prdcSeasonService;
	}

	public void setPrdcSeasonService(PrdcSeasonService prdcSeasonService) {
		this.prdcSeasonService = prdcSeasonService;
	}

	public Long getGreenHouseId() {
		return GreenHouseId;
	}

	public void setGreenHouseId(Long greenHouseId) {
		GreenHouseId = greenHouseId;
	}

	public List<PrdcSeason> getProduceSeasonList() {
		return produceSeasonList;
	}

	public void setProduceSeasonList(List<PrdcSeason> produceSeasonList) {
		this.produceSeasonList = produceSeasonList;
		
	}

	public List<PrdcSeason> getProduceSeasonListajax() {
		return produceSeasonListajax;
	}

	public void setProduceSeasonListajax(List<PrdcSeason> produceSeasonListajax) {
		this.produceSeasonListajax = produceSeasonListajax;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public PrdcSeason getPrdcSeason() {
		return prdcSeason;
	}

	public void setPrdcSeason(PrdcSeason prdcSeason) {
		this.prdcSeason = prdcSeason;
	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public List<DomainValue> getDomainValues() {
		return domainValues;
	}

	public void setDomainValues(List<DomainValue> domainValues) {
		this.domainValues = domainValues;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public DomainValue getDomainV() {
		return domainV;
	}

	public void setDomainV(DomainValue domainV) {
		this.domainV = domainV;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public PickWorksInfoQB getPickWorksInfoQB() {
		return pickWorksInfoQB;
	}

	public void setPickWorksInfoQB(PickWorksInfoQB pickWorksInfoQB) {
		this.pickWorksInfoQB = pickWorksInfoQB;
	}

	public String getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(String seasonId) {
		this.seasonId = seasonId;
	}

	public String getScropsajax() {
		return scropsajax;
	}

	public void setScropsajax(String scropsajax) {
		this.scropsajax = scropsajax;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}






}

