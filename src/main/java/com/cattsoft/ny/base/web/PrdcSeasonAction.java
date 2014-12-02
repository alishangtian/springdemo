package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.web.CommonUtil;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.OperationWorksInfo;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.PrdcThresholdInfo;
import com.cattsoft.ny.base.entity.ProduceAccident;
import com.cattsoft.ny.base.entity.ProduceWorksInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.OperationWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.entity.querybean.ProduceAccidentQB;
import com.cattsoft.ny.base.entity.querybean.ProduceWorksInfoQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.InsercticidesWorksInfoService;
import com.cattsoft.ny.base.service.OperationWorksInfoService;
import com.cattsoft.ny.base.service.PickWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;
import com.cattsoft.ny.base.service.PrdcThresholdInfoService;
import com.cattsoft.ny.base.service.ProduceSeasonService;
import com.cattsoft.ny.base.service.ProduceThresholdInfoService;
import com.cattsoft.ny.base.service.ProduceWorksInfoService;

/**
 * 
 * 种植季管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class PrdcSeasonAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Boolean flag = false;
	private BaseService baseService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private PrdcSeasonService prdcSeasonService;
	private ProduceSeasonService produceSeasonService;
	private List<PrdcSeason> prdcSeasons;
	private PrdcSeason prdcSeason;
	private String houseName;
	private String PTIName;
	private PagingQueryBean<PrdcSeasonQB> pqb;
	private PagingResultBean<List<PrdcSeason>> prdcSeasonsInfoResult;
	private ProduceThresholdInfoService produceThresholdInfoService;
	private List<PrdcThresholdInfo> ptilist;
	private PrdcSeasonQB prdcSeasonQB;
	private List<BaseGreenHouseTree> baseAndHouseTree;
	private PrdcThresholdInfoService prdcThresholdInfoService;
	private ProduceAccident produceAccident;
	private PagingResultBean<List<ProduceAccident>> produceAccidentInfoResult;
	private Long id;
	private List<PrdcSeason> produceSeasonListajax;
	private String crops;
	private ProduceWorksInfoService produceWorksInfoService;
	private PickWorksInfoService pickWorksInfoService;
	private OperationWorksInfoService operationWorksInfoService;
	private InsercticidesWorksInfoService insercticidesWorksInfoService;
	private boolean prdcSeasonFlag;
	private Date BTime;
	private Date ETime;
	private boolean prdcSeasionTimeFlag;
	public String execute() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("baseGreenHouseTreeList",
					getBaseGreenHouseTree());
		} catch (Exception e) {
		}
		return "jumpPrdcSeasonManager";
	}

	/**
	 * 生产事故监测页面
	 * 
	 * @return
	 */
	public String produceAccidentManager() {
		this.baseAndHouseTree = getBaseGreenHouseTree();
		return "produceAccidentManager";
	}

	/**
	 * 查询生产事故列表
	 * 
	 * @return
	 */
	public String queryProduceAccidents() {
		ProduceAccidentQB queryBean = new ProduceAccidentQB();
		if (this.produceAccident != null) {
			queryBean.setDetail(this.produceAccident.getDetail());
			queryBean.setEndTime(this.produceAccident.getEndTime());
			queryBean.setStartTime(this.produceAccident.getStartTime());
			queryBean.setHouseId(this.produceAccident.getHouseId());
		}
		PagingQueryBean<ProduceAccidentQB> pagingQueryBean = buildPagingQueryBean(
				ServletActionContext.getRequest(), queryBean);
		this.produceAccidentInfoResult = this.produceSeasonService
				.findPageProduceAccident(pagingQueryBean);
		return "produceAccidentInfoResult";
	}

	/**
	 * 跳转新增种植季界面准备数据
	 */
	public String createPrdcSeasonURL() {
		houseName = baseGreenHouseInfoService.getBaseGreenHouseInfo(
				prdcSeason.getHouseId()).getName();
		ptilist = prdcThresholdInfoService.getAllPrdcThresholdInfos();
		
		PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
		prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
		prdcSeasonQB.setState("1");
		produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		
		return "createPrdcSeasonURL";
	}

	/**
	 * 新增种植季
	 */
	public String createPrdcSeason() {

		try {
			prdcSeason.setState("1");
			prdcSeason.setCreateTime(new Date());
			prdcSeasonService.createPrdcSeason(prdcSeason);
			this.flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}
		return "createPrdcSeason";
	}

	/**
	 * 跳转修改种植季界面准备数据
	 */
	public String updatePrdcSeasonURL() {
		prdcSeason = prdcSeasonService.getPrdcSeason(prdcSeason.getId());
		ptilist = prdcThresholdInfoService.getAllPrdcThresholdInfos();
		PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
		prdcSeasonQB.setHouseId(prdcSeason.getHouseId());
		prdcSeasonQB.setState("1");
		produceSeasonListajax = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
		
		return "updatePrdcSeasonURL";
	}

	/**
	 * 修改种植季信息
	 */
	public String updatePrdcSeason() {
		try {
			prdcSeason.setCreateTime(new Date());
			prdcSeasonService.updatePrdcSeason(prdcSeason);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		return "updatePrdcSeason";
	}

	/**
	 * 作废种植季信息
	 */
	public String updatePrdcSeason2Unable() {
		try {
			prdcSeason = prdcSeasonService.getPrdcSeason(prdcSeason.getId());
			prdcSeason.setState("0");
			prdcSeasonService.updatePrdcSeason(prdcSeason);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		return "updatePrdcSeason2Unable";
	}

	/**
	 * 根据种植季ID删除种植季信息
	 */
	public String removePrdcSeasonById() {
		try {
			prdcSeasonService.removePrdcSeason(prdcSeason.getId());
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		return "removePrdcSeasonById";
	}

	/**
	 * 根据种植季ID获取种植季信息
	 */
	public String getPrdcSeasonById() {
		prdcSeason = prdcSeasonService.getPrdcSeason(prdcSeason.getId());
		PrdcThresholdInfo  ptii= prdcThresholdInfoService.getPrdcThresholdInfo(
				prdcSeason.getThresholdInfoId());
		if(ptii==null){
			PTIName="";
		}else{
			PTIName=ptii.getName();
		}
		
		
		return "getPrdcSeasonById";
	}

	/**
	 * 根据种植季属性组合keyValue 获取种植季信息列表
	 */
	public String getPrdcSeasonsByQueryBean() {
		if (pqb == null) {
			pqb = new PagingQueryBean<PrdcSeasonQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if (prdcSeasonQB == null)
			pqb.setQueryBean(new PrdcSeasonQB());
		else {
			if ("".equals(prdcSeasonQB.getName())) {
				prdcSeasonQB.setName(null);
			}
			prdcSeasonQB.setState("1");
			pqb.setQueryBean(prdcSeasonQB);
		}
		try {
			this.prdcSeasonsInfoResult = prdcSeasonService
					.getAllPrdcSeasonsPaging(pqb);
			for (PrdcSeason ps : this.prdcSeasonsInfoResult.getResultList()) {
				ps.setBDate(CommonUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						ps.getBeginTime()));
				ps.setEDate(CommonUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						ps.getEndTime()));
				ps.setCDate(CommonUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						ps.getCreateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "getPrdcSeasonsByQueryBean";
	}

	/**
	 * 获取所有prdcSeason
	 * 
	 */
	public String getAllPrdcSeasons() {
		prdcSeasons = prdcSeasonService.getAllPrdcSeasons();
		return "getAllPrdcSeasons";
	}

	public List<BaseGreenHouseTree> getBaseGreenHouseTree() {
		String userId = getStringLoginUserId();
		BaseQB queryBean = new BaseQB();
		queryBean.setCustId(Long.parseLong(userId));
		List<Base> baseAll = baseService.getBases(queryBean);
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseInfoQB baseGreenHouseInfoQB = null;
		BaseGreenHouseTree baseGreenHouseTree = null;
		for (Base base : baseAll) {
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeId(base.getId());
			baseGreenHouseTree.setNodeTreeName(base.getName());
			baseGreenHouseTree.setParentId(-1l);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;
			baseGreenHouseInfoQB = new BaseGreenHouseInfoQB();
			baseGreenHouseInfoQB.setBaseId(base.getId());
			List<BaseGreenHouseInfo> greenHouseAll = baseGreenHouseInfoService
					.getBaseGreenHouseInfos(baseGreenHouseInfoQB);
			if (greenHouseAll != null) {
				for (BaseGreenHouseInfo baseGreenHouseInfo : greenHouseAll) {
					baseGreenHouseTree = new BaseGreenHouseTree();
					baseGreenHouseTree
							.setNodeTreeId(baseGreenHouseInfo.getId());
					baseGreenHouseTree.setNodeTreeName(baseGreenHouseInfo
							.getName());
					baseGreenHouseTree.setParentId(base.getId());
					baseGreenHouseTreeList.add(baseGreenHouseTree);
				}
			}
		}
		return baseGreenHouseTreeList;
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
	
	public String getCropsById(){

		try {
			crops = prdcSeasonService.getPrdcSeason(id).getCrops();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "getCropsById";
	}
	/**
	 * 查找关联种植季
	 * @return
	 */
	public String findPrdcSeasonRelation(){
		Long prdcSeasonId = this.prdcSeason.getId();
		
		ProduceWorksInfoQB produceWorksInfoQB = new ProduceWorksInfoQB();
		produceWorksInfoQB.setProduceSeasonId(prdcSeasonId);
		List<ProduceWorksInfo> pwi = produceWorksInfoService.getProduceWorksInfos(produceWorksInfoQB);
		
		PickWorksInfoQB pickWorksInfoQB = new PickWorksInfoQB();
		pickWorksInfoQB.setProduceSeasonId(prdcSeasonId);
		List<PickWorksInfo> pickWorksInfo = pickWorksInfoService.getPickWorksInfos(pickWorksInfoQB);
		
		OperationWorksInfoQB OperationWorksInfoQB = new OperationWorksInfoQB();
		OperationWorksInfoQB.setProduceSeasonId(prdcSeasonId);
		List<OperationWorksInfo> operationWorksInfo = operationWorksInfoService.getOperationWorksInfos(OperationWorksInfoQB);
		
		InsercticidesWorksInfoQB insercticidesWorksInfoQB = new InsercticidesWorksInfoQB();
		insercticidesWorksInfoQB.setProduceSeasonId(prdcSeasonId);
		List<InsercticidesWorksInfo> insercticidesWorksInfo = insercticidesWorksInfoService.getInsercticidesWorksInfos(insercticidesWorksInfoQB);
		prdcSeasonFlag = (pwi.size()==0)&&(pickWorksInfo.size()==0)&&(operationWorksInfo.size()==0)&&(insercticidesWorksInfo.size()==0);
		return "findPrdcSeasonRelation";
	}
	//插入种植季时不能在已有的种植季时间范围内
	public String findPrdcSeasonByTime(){
		try {
			PrdcSeasonQB prdcSeasonQB = new PrdcSeasonQB();
			prdcSeasonQB.setBeginTime(BTime);
			prdcSeasonQB.setEndTime(ETime);
			List<PrdcSeason> pList = prdcSeasonService.getPrdcSeasons(prdcSeasonQB);
			System.out.println(pList.size());
			if(pList.size()<1)prdcSeasionTimeFlag=false;
			else prdcSeasionTimeFlag=true;
			System.out.println(prdcSeasionTimeFlag);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "findPrdcSeasonByTime";
	}
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public PrdcSeasonService getPrdcSeasonService() {
		return prdcSeasonService;
	}

	public void setPrdcSeasonService(PrdcSeasonService prdcSeasonService) {
		this.prdcSeasonService = prdcSeasonService;
	}

	public List<PrdcSeason> getPrdcSeasons() {
		return prdcSeasons;
	}

	public void setPrdcSeasons(List<PrdcSeason> prdcSeasons) {
		this.prdcSeasons = prdcSeasons;
	}

	public PrdcSeason getPrdcSeason() {
		return prdcSeason;
	}

	public void setPrdcSeason(PrdcSeason prdcSeason) {
		this.prdcSeason = prdcSeason;
	}

	public PagingResultBean<List<PrdcSeason>> getPrdcSeasonsInfoResult() {
		return prdcSeasonsInfoResult;
	}

	public void setPrdcSeasonsInfoResult(
			PagingResultBean<List<PrdcSeason>> prdcSeasonsInfoResult) {
		this.prdcSeasonsInfoResult = prdcSeasonsInfoResult;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public PagingQueryBean<PrdcSeasonQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<PrdcSeasonQB> pqb) {
		this.pqb = pqb;
	}

	public ProduceThresholdInfoService getProduceThresholdInfoService() {
		return produceThresholdInfoService;
	}

	public void setProduceThresholdInfoService(
			ProduceThresholdInfoService produceThresholdInfoService) {
		this.produceThresholdInfoService = produceThresholdInfoService;
	}

	public String getPTIName() {
		return PTIName;
	}

	public void setPTIName(String pTIName) {
		PTIName = pTIName;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public PrdcSeasonQB getPrdcSeasonQB() {
		return prdcSeasonQB;
	}

	public void setPrdcSeasonQB(PrdcSeasonQB prdcSeasonQB) {
		this.prdcSeasonQB = prdcSeasonQB;
	}

	public List<BaseGreenHouseTree> getBaseAndHouseTree() {
		return baseAndHouseTree;
	}

	public void setBaseAndHouseTree(List<BaseGreenHouseTree> baseAndHouseTree) {
		this.baseAndHouseTree = baseAndHouseTree;
	}

	public List<PrdcThresholdInfo> getPtilist() {
		return ptilist;
	}

	public void setPtilist(List<PrdcThresholdInfo> ptilist) {
		this.ptilist = ptilist;
	}

	public PrdcThresholdInfoService getPrdcThresholdInfoService() {
		return prdcThresholdInfoService;
	}

	public void setPrdcThresholdInfoService(
			PrdcThresholdInfoService prdcThresholdInfoService) {
		this.prdcThresholdInfoService = prdcThresholdInfoService;
	}

	public ProduceAccident getProduceAccident() {
		return produceAccident;
	}

	public void setProduceAccident(ProduceAccident produceAccident) {
		this.produceAccident = produceAccident;
	}

	public ProduceSeasonService getProduceSeasonService() {
		return produceSeasonService;
	}

	public void setProduceSeasonService(
			ProduceSeasonService produceSeasonService) {
		this.produceSeasonService = produceSeasonService;
	}

	public PagingResultBean<List<ProduceAccident>> getProduceAccidentInfoResult() {
		return produceAccidentInfoResult;
	}

	public void setProduceAccidentInfoResult(
			PagingResultBean<List<ProduceAccident>> produceAccidentInfoResult) {
		this.produceAccidentInfoResult = produceAccidentInfoResult;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PrdcSeason> getProduceSeasonListajax() {
		return produceSeasonListajax;
	}

	public void setProduceSeasonListajax(List<PrdcSeason> produceSeasonListajax) {
		this.produceSeasonListajax = produceSeasonListajax;
	}

	public void setCrops(String crops) {
		this.crops = crops;
	}

	public String getCrops() {
		return crops;
	}

	public ProduceWorksInfoService getProduceWorksInfoService() {
		return produceWorksInfoService;
	}

	public void setProduceWorksInfoService(
			ProduceWorksInfoService produceWorksInfoService) {
		this.produceWorksInfoService = produceWorksInfoService;
	}

	public PickWorksInfoService getPickWorksInfoService() {
		return pickWorksInfoService;
	}

	public void setPickWorksInfoService(PickWorksInfoService pickWorksInfoService) {
		this.pickWorksInfoService = pickWorksInfoService;
	}

	public OperationWorksInfoService getOperationWorksInfoService() {
		return operationWorksInfoService;
	}

	public void setOperationWorksInfoService(
			OperationWorksInfoService operationWorksInfoService) {
		this.operationWorksInfoService = operationWorksInfoService;
	}

	public InsercticidesWorksInfoService getInsercticidesWorksInfoService() {
		return insercticidesWorksInfoService;
	}

	public void setInsercticidesWorksInfoService(
			InsercticidesWorksInfoService insercticidesWorksInfoService) {
		this.insercticidesWorksInfoService = insercticidesWorksInfoService;
	}

	public boolean isPrdcSeasonFlag() {
		return prdcSeasonFlag;
	}

	public void setPrdcSeasonFlag(boolean prdcSeasonFlag) {
		this.prdcSeasonFlag = prdcSeasonFlag;
	}

	public Date getBTime() {
		return BTime;
	}

	public void setBTime(Date bTime) {
		BTime = bTime;
	}

	public Date getETime() {
		return ETime;
	}

	public void setETime(Date eTime) {
		ETime = eTime;
	}

	public boolean isPrdcSeasionTimeFlag() {
		return prdcSeasionTimeFlag;
	}

	public void setPrdcSeasionTimeFlag(boolean prdcSeasionTimeFlag) {
		this.prdcSeasionTimeFlag = prdcSeasionTimeFlag;
	}

}
