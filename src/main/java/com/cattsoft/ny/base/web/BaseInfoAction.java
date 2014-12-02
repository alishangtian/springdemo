package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.CustomerQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.CustomerService;

/**
 * 
 * 基地信息维护管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class BaseInfoAction extends BaseAction {

	private static final Logger log = Logger.getLogger(BaseInfoAction.class);

	private Base baseInfo;

	private PagingQueryBean<BaseQB> pqb;

	private CustomerService customerService;

	private PagingResultBean<List<Base>> baseInfoResult;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseService baseService;
	private boolean flag;
	private String msg;
	private String baseInfoCodeStr;
	private Long baseInfoId;
	private List<Customer> customerList;
	private BaseQB baseQB;
	private JSONArray jsonArray;
	private String nodeTreeId;
	private Integer level;
	
	private BaseGreenHouseInfoService baseGreenHouseInfoService;

	/**
	 * 主页面
	 */
	@Override
	public String execute() {
		return "jumpBaseInfoAction";
	}

	/**
	 * 返回地图操作页面
	 * 
	 * @Title: getMapInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getMapInfo() {
		return "mapPage";
	}

	/**
	 * 返回绘制区域页面
	 * 
	 * @Title: getDrawPolygonPage
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getDrawPolygonPage() {
		return "polygonPage";
	}

	/**
	 * 返回对话框请求页面
	 * 
	 * @Title: getDialogDemoPage
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getDialogDemoPage() {
		return "dialogDemo";
	}

	/**
	 * 
	 * Description: 分页查询 <br>
	 * 
	 * @return
	 */
	public String getBaseInfoHome() {
		if (pqb == null) {
			pqb = new PagingQueryBean<BaseQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(10);
			pqb.setPagingInfo(pageInfo);
		}
		if (baseQB == null)
			pqb.setQueryBean(new BaseQB());
		else{
			String userId = getStringLoginUserId();
			baseQB.setCustId(Long.parseLong(userId));
			pqb.setQueryBean(baseQB);
		}
			
			
		
		this.baseInfoResult = baseService.getAllBasesPaging(pqb);
		return "getBaseInfo";
	}

	public String saveBaseInfo() {
		this.flag = false;
		if (null == this.baseInfo) {
			return "addProduceWorksInfo";
		}
		try {
			baseInfo.setCtime(new Date());
			BaseQB baseQB = new BaseQB();
			baseQB.setName(baseInfo.getName());
			baseQB.setState("0");
			
			String userId = getStringLoginUserId();
			baseInfo.setCustId(Long.parseLong(userId));
			List<Base> bases = this.baseService.getBases(baseQB);
			if (!(null != bases && bases.size() > 0)) {
				this.baseService.createBase(baseInfo);
				this.flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}
		return "saveBaseInfo";
	}

	public String deleteBaseInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.baseInfoCodeStr)) {
			String[] baseInfoCodes = this.baseInfoCodeStr.split(",");
			for (String baseInfoCode : baseInfoCodes) {
				try {
					Base bii = baseService.getBase(Long.parseLong(baseInfoCode));
					Base bi = new Base();
					if(bii.getState().equals("1")){
						bi.setState("0");
					}else{
						bi.setState("1");
					}
					bi.setId(Long.parseLong(baseInfoCode));
					
					this.baseService.updateBase(bi);
					this.flag = true;
				} catch (Exception e) {
					failCode.append(baseInfoCode).append(" ");
					this.flag = false;
				}
			}
			this.flag = true;
		}
		if (StringUtils.isNotBlank(failCode.toString())) {
			this.flag = false;
			this.msg = "代码为:" + failCode.toString() + "的基地信息删除失败";
		} else {
			this.flag = true;
		}
		return "deleteBaseInfo";
	}

	/**
	 * 
	 * 查看施肥过程信息
	 * 
	 * @return
	 */
	public String viewBaseInfo() {

		this.flag = false;
		if (null == this.baseInfoId) {
			return "jumpBaseInfoAction";
		}

		try {
			baseInfo = this.baseService.getBase(baseInfoId);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
			e.printStackTrace();
		}

		return "viewBaseInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editBaseInfo() {

		this.flag = false;
		if (null == this.baseInfoId) {
			return "jumpBaseInfoAction";
		}

		try {
			baseInfo = this.baseService.getBase(baseInfoId);
			this.flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "editBaseInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateBaseInfo() {

		this.flag = false;
		if (null == this.baseInfo) {
			return "jumpBaseInfoAction";
		}

		try {
			String userId = getStringLoginUserId();
			baseInfo.setCustId(Long.parseLong(userId));
			this.baseService.updateBase(baseInfo);
			this.flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
		}

		return "updaOk";
	}

	/**
	 * 
	 * Description:添加功能 <br>
	 * 
	 * @return
	 */
	public String addBaseInfo() {
		CustomerQB custmerqb = new CustomerQB();
		custmerqb.setState("1");
		this.customerList = customerService.getCustomers(custmerqb);
		return "addBaseInfo";
	}

	/**
	 * 获取基地温室树
	 * 
	 * @return
	 */
	public String getBaseHouseTree() {
		try {
			if (this.nodeTreeId == null) {
				String userId = getStringLoginUserId();
				BaseQB queryBean = new BaseQB();
				queryBean.setCustId(Long.parseLong(userId));
				List<Base> bases = baseService.getBases(queryBean);
				List<BaseGreenHouseTree> trees = new ArrayList<BaseGreenHouseTree>();
				for (Base base : bases) {
					BaseGreenHouseTree treeNode = new BaseGreenHouseTree();
					treeNode.setNodeTreeId(base.getId());
					treeNode.setNodeTreeName(base.getName());
					treeNode.setType(1);
					trees.add(treeNode);
				}
				this.jsonArray = JSONArray.fromObject(trees);
			} else {
				BaseGreenHouseInfoQB query = new BaseGreenHouseInfoQB();
				query.setBaseId(Long.parseLong(this.nodeTreeId));
				List<BaseGreenHouseInfo> bases = this.baseGreenHouseInfoService
						.getBaseGreenHouseInfos(query);
				List<BaseGreenHouseTree> trees = new ArrayList<BaseGreenHouseTree>();
				for (BaseGreenHouseInfo base : bases) {
					BaseGreenHouseTree treeNode = new BaseGreenHouseTree();
					treeNode.setNodeTreeId(base.getId());
					treeNode.setNodeTreeName(base.getName());
					treeNode.setParentId(Long.parseLong(this.nodeTreeId));
					treeNode.setType(2);
					trees.add(treeNode);
				}
				this.jsonArray = JSONArray.fromObject(trees);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "getTree";
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

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public PagingResultBean<List<Base>> getBaseInfoResult() {
		return baseInfoResult;
	}

	public void setBaseInfoResult(PagingResultBean<List<Base>> baseInfoResult) {
		this.baseInfoResult = baseInfoResult;
	}

	public void setBaseInfo(Base baseInfo) {
		this.baseInfo = baseInfo;
	}

	public Base getBaseInfo() {
		return baseInfo;
	}

	public String getBaseInfoCodeStr() {
		return baseInfoCodeStr;
	}

	public void setBaseInfoCodeStr(String baseInfoCodeStr) {
		this.baseInfoCodeStr = baseInfoCodeStr;
	}

	public Long getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(Long baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(String nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public PagingQueryBean<BaseQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<BaseQB> pqb) {
		this.pqb = pqb;
	}

	public BaseQB getBaseQB() {
		return baseQB;
	}

	public void setBaseQB(BaseQB baseQB) {
		this.baseQB = baseQB;
	}

	public BaseService getBaseService() {
		return baseService;
	}

}
