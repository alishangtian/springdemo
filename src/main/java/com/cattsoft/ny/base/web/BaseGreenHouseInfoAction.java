package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.GreenHouseService;
import com.cattsoft.ny.base.service.ProduceSeasonService;

/**
 * 采摘操作录入
 * 
 * @author 
 * 
 */
public class BaseGreenHouseInfoAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String msg;
	private boolean flag;
	private BaseService baseService;
	private ProduceSeasonService produceSeasonService;
	private GreenHouseService greenHouseService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private PagingResultBean<List<BaseGreenHouseInfo>> baseGreenHouseInfoResult;
	private BaseGreenHouseInfo baseGreenHouseInfo;
	private PagingQueryBean<BaseGreenHouseInfoQB> pqb;
	private String baseGreenHouseInfoCodeStr;
	private Long baseGreenHouseInfoId;
	private Base b;
	private String baseId;
	private BaseGreenHouseInfoQB baseGreenHouseInfoQB;
    private List<BaseGreenHouseTree> baseGreenHouseTreeList;
    private String levelTree;
	@Override
	public String execute() {
 	HttpServletRequest request = ServletActionContext.getRequest();
		return "jumpBaseGreenHouseInfo";
	}
	/**
	 * 查询
	 * @return
	 */
	public String getBaseGreenHouseInfoquery() {
		if(pqb == null){
			pqb = new PagingQueryBean<BaseGreenHouseInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if(baseGreenHouseInfoQB == null) pqb.setQueryBean(new BaseGreenHouseInfoQB());
		else{
			baseGreenHouseInfoQB.setCrops(null);
			pqb.setQueryBean(baseGreenHouseInfoQB);
		} 
		
		this.baseGreenHouseInfoResult = baseGreenHouseInfoService.getAllBaseGreenHouseInfosPaging(pqb);
		return "getBaseGreenHouseInfoq";
		
	}
	
	
	/**
	 * 新增
	 * @return
	 */
	public String addBaseGreenHouseInfo() {
		System.out.println("baseId");
		try{
			b = baseService.getBase(Long.parseLong(baseId));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//baseInfoList();
//		HttpServletRequest request = ServletActionContext.getRequest();
//		try {
//			//List<ProduceSeason> produceSeasonList = produceSeasonService.getAllProduceSeasons();
//			//request.setAttribute("produceSeasonList", produceSeasonList);
//			request.setAttribute("baseGreenHouseTreeList", GetbaseGreenHouseTreeList());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		} 
		return "addBaseGreenHouseInfo";
	}
	
 
	
	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveBaseGreenHouseInfo() {
		
		this.flag = false;
		if (null == this.baseGreenHouseInfo) {
			return "addBaseGreenHouseInfo";
		}
		try {
			baseGreenHouseInfo.setCtime(new Date());
			this.baseGreenHouseInfoService.createBaseGreenHouseInfo(baseGreenHouseInfo);
			this.flag = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "savebaseGreenHouseInfo";
	}
	
	
	
	/**
	 * 删除baseGreenHouseInfo 
	 * 
	 * @return
	 */
	public String deleteBaseGreenHouseInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.baseGreenHouseInfoCodeStr)) {
			String[] domainCodes = this.baseGreenHouseInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.baseGreenHouseInfoService.removeBaseGreenHouseInfo(Long.parseLong(domainCode2));
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
		return "deleteBaseGreenHouseInfo";
	}
	/**
	 * 
	 * 查看温室信息
	 * 
	 * @return
	 */
	public String viewBaseGreenHouseInfo() {

		this.flag = false;
		if (null == this.baseGreenHouseInfoId) {
			return "addBaseGreenHouseInfo";
		}
		try {
			baseGreenHouseInfo = this.baseGreenHouseInfoService.getBaseGreenHouseInfo(baseGreenHouseInfoId);
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}
		baseInfoList();
		return "viewBaseGreenHouseInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editBaseGreenHouseInfo() {

		this.flag = false;
		if (null == this.baseGreenHouseInfoId) {
			return "addBaseGreenHouseInfo";
		}
		try {
			baseGreenHouseInfo = this.baseGreenHouseInfoService.getBaseGreenHouseInfo(baseGreenHouseInfoId);
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}
		baseInfoList();
		return "editBaseGreenHouseInfo";
	}
	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateBaseGreenHouseInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		baseGreenHouseInfo.setBaseId(Long.parseLong(request.getParameter("baseId")));
		this.flag = false;
		if (null == this.baseGreenHouseInfo) {
			return "addBaseGreenHouseInfo";
		}
		try {

			String userId = getStringLoginUserId();
			BaseQB queryBean = new BaseQB();
			queryBean.setCustId(Long.parseLong(userId));
			
			this.baseGreenHouseInfoService.updateBaseGreenHouseInfo(baseGreenHouseInfo);
			this.flag = true;
		}
		catch (Exception e) {
			this.flag = false;
		}
		return "updaOk";
	}
	/**
	 * 基地 信息
	 */
	public void baseInfoList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Base> baseAll = baseService.getAllBases();
		request.setAttribute("baseInfoList", baseAll);
	}
 
	
	/**
	 * 所有基地 树
	 * @return
	 */
	public String GetbaseGreenHouseTreeList() {
		BaseGreenHouseTree baseGreenHouseTree = null;
		if(StringUtils.isEmpty(levelTree)){
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeId(1L);
			baseGreenHouseTree.setNodeTreeName("所有基地");
			baseGreenHouseTree.setParent(true);
			baseGreenHouseTree.setLevelTree("0");
			baseGreenHouseTreeList.add(baseGreenHouseTree);
		}
		if("0".equals(levelTree)){
			String userId = getStringLoginUserId();
			BaseQB queryBean = new BaseQB();
			queryBean.setCustId(Long.parseLong(userId));
			queryBean.setState("0");
			List<Base> baseAll = baseService.getBases(queryBean);
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
			for(Base base : baseAll){ 
				baseGreenHouseTree = new BaseGreenHouseTree();
				baseGreenHouseTree.setNodeTreeId(base.getId());
				baseGreenHouseTree.setNodeTreeName(base.getName());
				baseGreenHouseTree.setParent(true);
				baseGreenHouseTree.setLevelTree("1");
				baseGreenHouseTreeList.add(baseGreenHouseTree);
			  }
		}
		if("1".equals(levelTree)){
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		}
		return "getbaseGreenHouseTreeList";
	}
	
	public BaseGreenHouseInfo getBaseGreenHouseInfo() {
		return baseGreenHouseInfo;
	}
	public void setBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo) {
		this.baseGreenHouseInfo = baseGreenHouseInfo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public BaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	public GreenHouseService getGreenHouseService() {
		return greenHouseService;
	}
	public void setGreenHouseService(GreenHouseService greenHouseService) {
		this.greenHouseService = greenHouseService;
	}
	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}
	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}
	public PagingResultBean<List<BaseGreenHouseInfo>> getBaseGreenHouseInfoResult() {
		return baseGreenHouseInfoResult;
	}
	public void setBaseGreenHouseInfoResult(
			PagingResultBean<List<BaseGreenHouseInfo>> baseGreenHouseInfoResult) {
		this.baseGreenHouseInfoResult = baseGreenHouseInfoResult;
	}

	public String getBaseGreenHouseInfoCodeStr() {
		return baseGreenHouseInfoCodeStr;
	}
	public void setBaseGreenHouseInfoCodeStr(String baseGreenHouseInfoCodeStr) {
		this.baseGreenHouseInfoCodeStr = baseGreenHouseInfoCodeStr;
	}
	public Long getBaseGreenHouseInfoId() {
		return baseGreenHouseInfoId;
	}
	public void setBaseGreenHouseInfoId(Long baseGreenHouseInfoId) {
		this.baseGreenHouseInfoId = baseGreenHouseInfoId;
	}
	public ProduceSeasonService getProduceSeasonService() {
		return produceSeasonService;
	}
	public void setProduceSeasonService(ProduceSeasonService produceSeasonService) {
		this.produceSeasonService = produceSeasonService;
	}
	public PagingQueryBean<BaseGreenHouseInfoQB> getPqb() {
		return pqb;
	}
	public void setPqb(PagingQueryBean<BaseGreenHouseInfoQB> pqb) {
		this.pqb = pqb;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public Base getB() {
		return b;
	}
	public void setB(Base b) {
		this.b = b;
	}
	public BaseGreenHouseInfoQB getBaseGreenHouseInfoQB() {
		return baseGreenHouseInfoQB;
	}
	public void setBaseGreenHouseInfoQB(BaseGreenHouseInfoQB baseGreenHouseInfoQB) {
		this.baseGreenHouseInfoQB = baseGreenHouseInfoQB;
	}
	public List<BaseGreenHouseTree> getBaseGreenHouseTreeList() {
		return baseGreenHouseTreeList;
	}
	public void setBaseGreenHouseTreeList(
			List<BaseGreenHouseTree> baseGreenHouseTreeList) {
		this.baseGreenHouseTreeList = baseGreenHouseTreeList;
	}
	public String getLevelTree() {
		return levelTree;
	}
	public void setLevelTree(String levelTree) {
		this.levelTree = levelTree;
	}

  
	
}
