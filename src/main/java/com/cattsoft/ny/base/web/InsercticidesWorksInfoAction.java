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
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.InsercticidesWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;

/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class InsercticidesWorksInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InsercticidesWorksInfoService insercticidesWorksInfoService;
	private BaseService baseService;
	
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	//private ProduceSeasonService produceSeasonService;
	private InsercticidesWorksInfo insercticidesWorksInfo;
	private PagingResultBean<List<InsercticidesWorksInfo>> insercticidesWorksInfoResult;
	private PagingQueryBean<InsercticidesWorksInfoQB> pqb;
	private String insercticidesWorksInfoCodeStr;
	private boolean flag;
	private String msg;
	private Long insercticidesWorksInfoId;
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
	private InsercticidesWorksInfoQB insercticidesWorksInfoQB;
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
		}

		return "jumpinsercticidesWorksInfoManager";
	}

	public void setInsercticidesWorksInfoService(InsercticidesWorksInfoService insercticidesWorksInfoService) {
		this.insercticidesWorksInfoService = insercticidesWorksInfoService;
	}

	/**
	 * 获取InsercticidesWorksInfo
	 * 
	 * @return
	 */
	public String getInstWorksInfo() {
		if(pqb == null){
			pqb = new PagingQueryBean<InsercticidesWorksInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if(insercticidesWorksInfoQB == null) pqb.setQueryBean(new InsercticidesWorksInfoQB());
		else pqb.setQueryBean(insercticidesWorksInfoQB);
		this.insercticidesWorksInfoResult = insercticidesWorksInfoService.getAllInsercticidesWorksInfosPaging(pqb);
		return "getinstWorksInfo";
		
	}

	/**
	 * 添加InsercticidesWorksInfo
	 * 
	 * @return
	 */
	public String addInsercticidesWorksInfo() {
		try {
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
			prdcSeasonQB.setState("1");
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			domainValues = domainService.getDomainValues((long) 89);
			houseId=prdcSeason.getHouseId();
			typeName=prdcSeason.getCrops();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addInsercticidesWorksInfo";
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
	 * 删除InsercticidesWorksInfo
	 * 
	 * @return
	 */
	public String deleteInsercticidesWorksInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.insercticidesWorksInfoCodeStr)) {
			String[] domainCodes = this.insercticidesWorksInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.insercticidesWorksInfoService.removeInsercticidesWorksInfo(Long.parseLong(domainCode2));
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
		return "deleteInsercticidesWorksInfo";
	}

	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveInsercticidesWorksInfo() {
		insercticidesWorksInfo.setHouseId(houseId);
		//domainService.getDomainValues(domainId)
		this.flag = false;
		if (null == this.insercticidesWorksInfo) {
			return "addInsercticidesWorksInfo";
		}

		try {
			if(insercticidesWorksInfo.getAmount().equals("")){
				insercticidesWorksInfo.setAmount(null);
			}
			this.insercticidesWorksInfoService.createInsercticidesWorksInfo(insercticidesWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "saveInsercticidesWorksInfo";
	}

	/**
	 * 
	 * 查看施肥过程信息
	 * 
	 * @return
	 */
	public String viewInsercticidesWorksInfo() {
		
		this.flag = false;
		if (null == this.insercticidesWorksInfoId) {
			return "addInsercticidesWorksInfo";
		}

		try {
			insercticidesWorksInfo = this.insercticidesWorksInfoService.getInsercticidesWorksInfo(insercticidesWorksInfoId);
			System.out.println(insercticidesWorksInfo.getProduceSeasonId());
			prdcSeason = prdcSeasonService.getPrdcSeason(insercticidesWorksInfo.getProduceSeasonId());
			domainValues = domainService.getDomainValues(89L);
			for(DomainValue dv :domainValues){
				if(insercticidesWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "viewInsercticidesWorksInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editInsercticidesWorksInfo() {
		this.flag = false;
		if (null == this.insercticidesWorksInfoId) {
			return "addInsercticidesWorksInfo";
		}

		try {
			insercticidesWorksInfo = this.insercticidesWorksInfoService.getInsercticidesWorksInfo(insercticidesWorksInfoId);
			
			System.out.println(insercticidesWorksInfo.getProduceSeasonId());
			prdcSeason = prdcSeasonService.getPrdcSeason(insercticidesWorksInfo.getProduceSeasonId());
			
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setHouseId(insercticidesWorksInfo.getHouseId());
			prdcSeasonQB.setState("1");
			prdcSeason = prdcSeasonService.getPrdcSeason(insercticidesWorksInfo.getProduceSeasonId());
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			
			domainValues = domainService.getDomainValues(89L);
			for(DomainValue dv :domainValues){
				if(insercticidesWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "editInsercticidesWorksInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateInsercticidesWorksInfo() {
		
		this.flag = false;
		if (null == this.insercticidesWorksInfo) {
			return "addInsercticidesWorksInfo";
		}

		try {
			this.insercticidesWorksInfoService.updateInsercticidesWorksInfo(insercticidesWorksInfo);
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
	public InsercticidesWorksInfo getInsercticidesWorksInfo() {
		return insercticidesWorksInfo;
	}

	public void setInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo) {
		this.insercticidesWorksInfo = insercticidesWorksInfo;
	}

	public PagingResultBean<List<InsercticidesWorksInfo>> getInsercticidesWorksInfoResult() {
		return insercticidesWorksInfoResult;
	}

	public void setInsercticidesWorksInfoResult(PagingResultBean<List<InsercticidesWorksInfo>> insercticidesWorksInfoResult) {
		this.insercticidesWorksInfoResult = insercticidesWorksInfoResult;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getInsercticidesWorksInfoCodeStr() {
		return insercticidesWorksInfoCodeStr;
	}

	public void setInsercticidesWorksInfoCodeStr(String insercticidesWorksInfoCodeStr) {
		this.insercticidesWorksInfoCodeStr = insercticidesWorksInfoCodeStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getInsercticidesWorksInfoId() {
		return insercticidesWorksInfoId;
	}

	public void setInsercticidesWorksInfoId(Long insercticidesWorksInfoId) {
		this.insercticidesWorksInfoId = insercticidesWorksInfoId;
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

	public InsercticidesWorksInfoService getInsercticidesWorksInfoService() {
		return insercticidesWorksInfoService;
	}

	public PagingQueryBean<InsercticidesWorksInfoQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<InsercticidesWorksInfoQB> pqb) {
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

	public InsercticidesWorksInfoQB getInsercticidesWorksInfoQB() {
		return insercticidesWorksInfoQB;
	}

	public void setInsercticidesWorksInfoQB(
			InsercticidesWorksInfoQB insercticidesWorksInfoQB) {
		this.insercticidesWorksInfoQB = insercticidesWorksInfoQB;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}






}
