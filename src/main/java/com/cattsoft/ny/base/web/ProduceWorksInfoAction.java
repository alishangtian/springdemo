package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.ProduceWorksInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.entity.querybean.ProduceWorksInfoQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.PrdcSeasonService;
import com.cattsoft.ny.base.service.ProduceWorksInfoService;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;


/**
 * 
 * 施肥管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class ProduceWorksInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log  = Logger.getLogger(ProduceWorksInfoAction.class);
	
	private ProduceWorksInfoService produceWorksInfoService;
	private BaseService baseService;
	//private ProduceSeasonService produceSeasonService;
	private PrdcSeasonService prdcSeasonService;
	private ProduceWorksInfo produceWorksInfo;
	private PagingResultBean<List<ProduceWorksInfo>> produceWorksInfoResult;
	private PagingResultBean<List<PrdcSeason>> prdcSeasonsInfoResult;
	private PagingQueryBean<ProduceWorksInfoQB> pqb;
	private ProduceWorksInfoQB pwQB;
	private List<PrdcSeason> PrdcSeasonList;
	private String produceWorksInfoCodeStr;
	private boolean flag;
	private String msg;
	private Long produceWorksInfoId;
	private List<PrdcSeason> produceSeasonList;
	private List<PrdcSeason> produceSeasonListajax;
	private DomainService domainService;
	private List<DomainValue> values;
	private String abc;
	private PrdcSeason prdcSeason;
	private Long id;
	private String custId;
	private List<Base> baseLists;
	private Long houseId;
	//private GreenHouseService greenHouseService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private Long GreenHouseId;
	private String typeName;
	/**
	 * 组织机构集合
	 */
	private List<BaseGreenHouseTree> sysOrgList;
	
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			//produceWorksInfo.getProduceSeasonId();
			//produceSeasonList = prdcSeasonService.getAllPrdcSeasons();
			//request.setAttribute("produceSeasonList", produceSeasonList);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return "jumpproduceWorksInfoManager";
	}

	public String findBaseHouseTree() {
		if(custId !=null) return "treeData";
		
		if(id==null){
			try {
				String userId = getStringLoginUserId();
				BaseQB queryBean = new BaseQB();
				queryBean.setCustId(Long.parseLong(userId));
				baseLists = baseService.getBases(queryBean);
				for(int i=0;i<baseLists.size();i++){
					baseLists.get(i).setParent(true);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			baseLists = new ArrayList<Base>();
			BaseGreenHouseInfoQB bgQB = new BaseGreenHouseInfoQB();
			bgQB.setBaseId(id);
			List<BaseGreenHouseInfo> ghLists = baseGreenHouseInfoService.getBaseGreenHouseInfos(bgQB);
			if(ghLists!=null && ghLists.size()>0){
				for(int i=0;i<ghLists.size();i++ ){
					Base oneBase = new Base();
					oneBase.setId(ghLists.get(i).getId());
					oneBase.setName(ghLists.get(i).getName());
					oneBase.setParent(false);
					baseLists.add(oneBase);
				}
			}
		}
		return "treeData";

	}

	/**
	 * 获取ProduceWorksInfo
	 * 
	 * @return
	 */
	public String getProdWorksInfo() {
		if(pqb == null){
			pqb = new PagingQueryBean<ProduceWorksInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if(pwQB == null) pqb.setQueryBean(new ProduceWorksInfoQB());
		else {
			pqb.setQueryBean(pwQB);
		}
		try{
			this.produceWorksInfoResult = produceWorksInfoService.getAllProduceWorksInfosPaging(pqb);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "getProdWorksInfo";
	}

	/**
	 * 添加ProduceWorksInfo
	 * 
	 * @return
	 */
	public String addProduceWorksInfo() {
		try {
			System.out.println(prdcSeason.getHouseId());
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
			prdcSeasonQB.setState("1");
			PrdcSeasonList = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			houseId=prdcSeason.getHouseId();
			values = domainService.getDomainValues(88L);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addProduceWorksInfo";
	}



	/**
	 * 删除ProduceWorksInfo
	 * 
	 * @return
	 */
	public String deleteProduceWorksInfo() {
		
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.produceWorksInfoCodeStr)) {
			String[] domainCodes = this.produceWorksInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.produceWorksInfoService.removeProduceWorksInfo(Long.parseLong(domainCode2));
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
		return "deleteProduceWorksInfo";
	}

	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveProduceWorksInfo() {

		this.flag = false;
		if (null == this.produceWorksInfo) {
			return "addProduceWorksInfo";
		}

		try {
			
			produceWorksInfo.setUserId(1L);
			this.produceWorksInfoService.createProduceWorksInfo(produceWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			this.flag = false;
		}

		return "saveProduceWorksInfo";
	}

	/**
	 * 
	 * 查看施肥过程信息
	 * 
	 * @return
	 */
	public String viewProduceWorksInfo() {

		this.flag = false;
		if (null == this.produceWorksInfoId) {
			return "addProduceWorksInfo";
		}

		try {
			produceWorksInfo = this.produceWorksInfoService.getProduceWorksInfo(produceWorksInfoId);
			this.flag = true;
			
			prdcSeason = prdcSeasonService.getPrdcSeason(produceWorksInfo.getProduceSeasonId());
			values = domainService.getDomainValues(88L);
			for(DomainValue dv :values){
				if(produceWorksInfo.getType().equals(dv.getValueCode())){
					typeName=dv.getValueName();
					break;
				}
			}
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "viewProduceWorksInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editProduceWorksInfo() {

		this.flag = false;
		if (null == this.produceWorksInfoId) {
			return "addProduceWorksInfo";
		}

		try {
			produceWorksInfo = this.produceWorksInfoService.getProduceWorksInfo(produceWorksInfoId);
			this.flag = true;
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setState("1");
			prdcSeasonQB.setHouseId(produceWorksInfo.getHouseId());
			prdcSeason = prdcSeasonService.getPrdcSeason(produceWorksInfo.getProduceSeasonId());
			PrdcSeasonList = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			values = domainService.getDomainValues(88L);
		}
		catch (Exception e) {
			this.flag = false;
		}

		return "editProduceWorksInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateProduceWorksInfo() {

		this.flag = false;
		if (null == this.produceWorksInfo) {
			return "addProduceWorksInfo";
		}

		try {
			this.produceWorksInfoService.updateProduceWorksInfo(produceWorksInfo);
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
			e.printStackTrace();
		}

		return "updaOk";
	}
	public String getPrdcSeasonByGreenHouse(){
		if(id != null){
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setState("1");
			prdcSeasonQB.setHouseId(id);
			produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		}
		return "getPrdcSeasonByGreenHouse";
	}

	public ProduceWorksInfo getProduceWorksInfo() {
		return produceWorksInfo;
	}

	public void setProduceWorksInfo(ProduceWorksInfo produceWorksInfo) {
		this.produceWorksInfo = produceWorksInfo;
	}

	public PagingResultBean<List<ProduceWorksInfo>> getProduceWorksInfoResult() {
		return produceWorksInfoResult;
	}

	public void setProduceWorksInfoResult(PagingResultBean<List<ProduceWorksInfo>> produceWorksInfoResult) {
		this.produceWorksInfoResult = produceWorksInfoResult;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getProduceWorksInfoCodeStr() {
		return produceWorksInfoCodeStr;
	}

	public void setProduceWorksInfoCodeStr(String produceWorksInfoCodeStr) {
		this.produceWorksInfoCodeStr = produceWorksInfoCodeStr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getProduceWorksInfoId() {
		return produceWorksInfoId;
	}

	public void setProduceWorksInfoId(Long produceWorksInfoId) {
		this.produceWorksInfoId = produceWorksInfoId;
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
	

	public List<BaseGreenHouseTree> getSysOrgList() {
		return sysOrgList;
	}

	public PrdcSeasonService getPrdcSeasonService() {
		return prdcSeasonService;
	}

	public void setPrdcSeasonService(PrdcSeasonService prdcSeasonService) {
		this.prdcSeasonService = prdcSeasonService;
	}

	public void setSysOrgList(List<BaseGreenHouseTree> sysOrgList) {
		this.sysOrgList = sysOrgList;
	}

	public PagingQueryBean<ProduceWorksInfoQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<ProduceWorksInfoQB> pqb) {
		this.pqb = pqb;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public ProduceWorksInfoService getProduceWorksInfoService() {
		return produceWorksInfoService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public List<PrdcSeason> getProduceSeasonList() {
		return produceSeasonList;
	}

	public void setProduceSeasonList(List<PrdcSeason> produceSeasonList) {
		this.produceSeasonList = produceSeasonList;
	}

	public Long getGreenHouseId() {
		return GreenHouseId;
	}

	public void setGreenHouseId(Long greenHouseId) {
		GreenHouseId = greenHouseId;
	}

	public List<PrdcSeason> getProduceSeasonListajax() {
		return produceSeasonListajax;
	}

	public void setProduceSeasonListajax(List<PrdcSeason> produceSeasonListajax) {
		this.produceSeasonListajax = produceSeasonListajax;
	}

	public PagingResultBean<List<PrdcSeason>> getPrdcSeasonsInfoResult() {
		return prdcSeasonsInfoResult;
	}

	public void setPrdcSeasonsInfoResult(
			PagingResultBean<List<PrdcSeason>> prdcSeasonsInfoResult) {
		this.prdcSeasonsInfoResult = prdcSeasonsInfoResult;
	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public List<DomainValue> getValues() {
		return values;
	}

	public void setValues(List<DomainValue> values) {
		this.values = values;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public List<Base> getBaseLists() {
		return baseLists;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setProduceWorksInfoService(ProduceWorksInfoService produceWorksInfoService) {
		this.produceWorksInfoService = produceWorksInfoService;
	}

	public ProduceWorksInfoQB getPwQB() {
		return pwQB;
	}

	public void setPwQB(ProduceWorksInfoQB pwQB) {
		this.pwQB = pwQB;
	}

	public List<PrdcSeason> getPrdcSeasonList() {
		return PrdcSeasonList;
	}

	public void setPrdcSeasonList(List<PrdcSeason> prdcSeasonList) {
		PrdcSeasonList = prdcSeasonList;
	}

	public Long getId() {
		return id;
	}

	public String getCustId() {
		return custId;
	}

	public void setBaseLists(List<Base> baseLists) {
		this.baseLists = baseLists;
	}

	public PrdcSeason getPrdcSeason() {
		return prdcSeason;
	}

	public void setPrdcSeason(PrdcSeason prdcSeason) {
		this.prdcSeason = prdcSeason;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
}
