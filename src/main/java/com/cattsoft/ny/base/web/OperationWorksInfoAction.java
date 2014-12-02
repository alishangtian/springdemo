package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.OperationWorksInfo;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.OperationWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.OperationWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;

/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class OperationWorksInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OperationWorksInfoService operationWorksInfoService;
	private BaseService baseService;
	
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	//private ProduceSeasonService produceSeasonService;
	private OperationWorksInfo operationWorksInfo;
	private PagingResultBean<List<OperationWorksInfo>> operationWorksInfoResult;
	private PagingQueryBean<OperationWorksInfoQB> pqb;
	private String operationWorksInfoCodeStr;
	private boolean flag;
	private String msg;
	private Long operationWorksInfoId;
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
	private Long id;
	private OperationWorksInfoQB operationWorksInfoQB;
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			produceSeasonList = prdcSeasonService.getAllPrdcSeasons();
			 request.setAttribute("baseGreenHouseTreeList",
			 GetBaseGreenHouseTree());
			
		}
		catch (Exception e) {
		}

		return "jumpoperationWorksInfoManager";
	}

	public void setOperationWorksInfoService(OperationWorksInfoService operationWorksInfoService) {
		this.operationWorksInfoService = operationWorksInfoService;
	}

	/**
	 * 获取OperationWorksInfo
	 * 
	 * @return
	 */
	public String getOptnWorksInfo() {
		if(pqb == null){
			pqb = new PagingQueryBean<OperationWorksInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if(operationWorksInfoQB == null) pqb.setQueryBean(new OperationWorksInfoQB());
		else pqb.setQueryBean(operationWorksInfoQB);
		this.operationWorksInfoResult = operationWorksInfoService.getAllOperationWorksInfosPaging(pqb);
		return "getOptnWorksInfo";
	}

	/**
	 * 添加OperationWorksInfo
	 * 
	 * @return
	 */
	public String addOperationWorksInfo() {
		PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
		prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
		prdcSeasonQB.setState("1");
		produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		domainValues = domainService.getDomainValues((long) 87);
		for(DomainValue dv :domainValues){
			System.out.println(dv.getValueName());
		}
		houseId=prdcSeason.getHouseId();
		
		return "addOperationWorksInfo";
	}

	public List<BaseGreenHouseTree> GetBaseGreenHouseTree() {
		
		String userId = getStringLoginUserId();
		BaseQB queryBean = new BaseQB();
		queryBean.setCustId(Long.parseLong(userId));
		List<Base> baseAll = baseService.getBases(queryBean);
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
	 * 删除OperationWorksInfo
	 * 
	 * @return
	 */
	public String deleteOperationWorksInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.operationWorksInfoCodeStr)) {
			String[] domainCodes = this.operationWorksInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.operationWorksInfoService.removeOperationWorksInfo(Long.parseLong(domainCode2));
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
		return "deleteOperationWorksInfo";
	}

	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveOperationWorksInfo() {
		operationWorksInfo.setHouseId(houseId);
		//domainService.getDomainValues(domainId)
		this.flag = false;
		if (null == this.operationWorksInfo) {
			return "addOperationWorksInfo";
		}

		try {
			if(operationWorksInfo.getAmount().equals("")){
				operationWorksInfo.setAmount(null);
			}
			this.operationWorksInfoService.createOperationWorksInfo(operationWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "saveOperationWorksInfo";
	}

	/**
	 * 
	 * 查看施肥过程信息
	 * 
	 * @return
	 */
	public String viewOperationWorksInfo() {
		
		this.flag = false;
		if (null == this.operationWorksInfoId) {
			return "addOperationWorksInfo";
		}

		try {
			operationWorksInfo = this.operationWorksInfoService.getOperationWorksInfo(operationWorksInfoId);
			System.out.println(operationWorksInfo.getProduceSeasonId());
			prdcSeason = prdcSeasonService.getPrdcSeason(operationWorksInfo.getProduceSeasonId());
			domainValues = domainService.getDomainValues(87L);
			for(DomainValue dv :domainValues){
				if(operationWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "viewOperationWorksInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editOperationWorksInfo() {
		this.flag = false;
		if (null == this.operationWorksInfoId) {
			return "addOperationWorksInfo";
		}

		try {
			
			operationWorksInfo = this.operationWorksInfoService.getOperationWorksInfo(operationWorksInfoId);
			System.out.println(operationWorksInfo.getProduceSeasonId());
			prdcSeason = prdcSeasonService.getPrdcSeason(operationWorksInfo.getProduceSeasonId());
			
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setHouseId(operationWorksInfo.getHouseId());
			prdcSeasonQB.setState("1");
			prdcSeason = prdcSeasonService.getPrdcSeason(operationWorksInfo.getProduceSeasonId());
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			
			
			domainValues = domainService.getDomainValues(87L);
			for(DomainValue dv :domainValues){
				if(operationWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "editOperationWorksInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateOperationWorksInfo() {
		
		this.flag = false;
		if (null == this.operationWorksInfo) {
			return "addOperationWorksInfo";
		}

		try {
			this.operationWorksInfoService.updateOperationWorksInfo(operationWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
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
//		System.out.println(json.toString());
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

	public OperationWorksInfo getOperationWorksInfo() {
		return operationWorksInfo;
	}

	public void setOperationWorksInfo(OperationWorksInfo operationWorksInfo) {
		this.operationWorksInfo = operationWorksInfo;
	}

	public PagingResultBean<List<OperationWorksInfo>> getOperationWorksInfoResult() {
		return operationWorksInfoResult;
	}

	public void setOperationWorksInfoResult(PagingResultBean<List<OperationWorksInfo>> operationWorksInfoResult) {
		this.operationWorksInfoResult = operationWorksInfoResult;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getOperationWorksInfoCodeStr() {
		return operationWorksInfoCodeStr;
	}

	public void setOperationWorksInfoCodeStr(String operationWorksInfoCodeStr) {
		this.operationWorksInfoCodeStr = operationWorksInfoCodeStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getOperationWorksInfoId() {
		return operationWorksInfoId;
	}

	public void setOperationWorksInfoId(Long operationWorksInfoId) {
		this.operationWorksInfoId = operationWorksInfoId;
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

	public OperationWorksInfoService getOperationWorksInfoService() {
		return operationWorksInfoService;
	}

	public PagingQueryBean<OperationWorksInfoQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<OperationWorksInfoQB> pqb) {
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

	public OperationWorksInfoQB getOperationWorksInfoQB() {
		return operationWorksInfoQB;
	}

	public void setOperationWorksInfoQB(OperationWorksInfoQB operationWorksInfoQB) {
		this.operationWorksInfoQB = operationWorksInfoQB;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}






}
