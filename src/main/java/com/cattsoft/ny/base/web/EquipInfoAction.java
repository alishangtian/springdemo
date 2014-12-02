package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.DomainValue;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.service.DomainService;
import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.service.BulletinService;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.EquipAttribute;
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.EquipType;
import com.cattsoft.ny.base.entity.EquipTypeTree;
import com.cattsoft.ny.base.entity.MsgAlarm;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.CustomerQB;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.entity.querybean.EquipTypeQB;
import com.cattsoft.ny.base.entity.querybean.MsgAlarmQB;
import com.cattsoft.ny.base.entity.querybean.NewsQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.CustomerService;
import com.cattsoft.ny.base.service.EquipDataTypeService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipSensorsGhouseService;
import com.cattsoft.ny.base.service.MsgAlarmService;
import com.cattsoft.ny.base.service.OperationWorksInfoService;
import com.cattsoft.ny.base.service.PickWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;
import com.cattsoft.ny.base.service.ProduceSeasonService;

/**
 * 设备操作录入
 * 
 * @author
 * 
 */
public class EquipInfoAction extends BaseAction {
	private static final Logger log = Logger.getLogger(BaseInfoAction.class);
	private static final long serialVersionUID = 1L;
	private String msg;
	private boolean flag;
	private BaseService baseService;
	private DomainService domainService;
	private ProduceSeasonService produceSeasonService;
	private PrdcSeasonService prdcSeasonService;
	private EquipInfoService equipInfoService;
	private PagingResultBean<List<EquipInfo>> equipInfoResult;
	private PagingQueryBean<EquipInfoQB> pqb;
	private PagingResultBean<List<EquipType>> equipTypeResult;
	private PagingQueryBean<EquipTypeQB> pqbe;
	private String equipInfoCodeStr;
	private String domainId_equipAttribute_Str = "90";
	private String domainId_equipFactory_Str = "91";
	private Long equipInfoId;
	private EquipInfo equipInfo;
	private EquipType equipType;
	private List<EquipType> equipTypes;
	private CustomerService customerService;
	private EquipSensorsGhouseService equipSensorsGhouseService;
	private String datas;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private JSONArray baseOrGreenHouseList;
	private OperationWorksInfoService operationWorksInfoService;
	private String newsListJson;
	private List<News> newsList;
	private List<Bulletin> bulletinList;
	private List<MsgAlarm> msgAlarmList;
	private List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo;
	private EquipDataTypeService equipDataTypeService;
	private PickWorksInfoService pickWorksInfoService;
	private Long pid;
	private BulletinService bulletinService;
	private MsgAlarmService msgAlarmService;

	public PrdcSeasonService getPrdcSeasonService() {
		return prdcSeasonService;
	}

	public void setPrdcSeasonService(PrdcSeasonService prdcSeasonService) {
		this.prdcSeasonService = prdcSeasonService;
	}

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setAttribute("baseGreenHouseTreeList", GetCustomerTree());
		} catch (Exception e) {
		}
		return "jumpEquipInfo";
	}

	/**
	 * 登陆成功主页
	 * 
	 * @Title: welcomePage
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String welcomePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("baseGreenHouseTree", getBaseGreenHouseTree());
		getNews();
		for (BaseGreenHouseTree house : getBaseGreenHouseTree()) {
			if ("2".equals(house.getType() + "")) {

				if (!StringUtils.isEmpty(house.getNodeTreeId() + "")) {
					// 得到 温室下面的 设备编号 如果类型类型为节点的 设备编号
					EquipSensorsGhouseQB esgQb = new EquipSensorsGhouseQB();
					esgQb.setHouseId(Long.parseLong(house.getNodeTreeId() + ""));
					List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService
							.getEquipSensorsGhouses(esgQb);
					if (null != esgsinfo && esgsinfo.size() > 0) {
						for (int i = 0; i < esgsinfo.size(); i++) {
							EquipInfo EquipInfo = equipInfoService
									.getEquipInfo(esgsinfo.get(i)
											.getEquipInfoId());
							if ("3".equals(EquipInfo.getType())) {
								// request.setAttribute("EquipDTIList",equipDataTypeService.getEquipDataEnvdataTypeInfo(EquipInfo.getId()));
								equipDataEnvdataTypeInfo = equipDataTypeService
										.getEquipDataEnvdataTypeInfo(EquipInfo
												.getId());
								break;
							}
						}
					}
				}
			}
		}
		// getNews();
		return "welcome";
	}

	public void getNews() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			NewsQB newQB = new NewsQB();
			newQB.setUserId(Long.parseLong(((SysUser) request.getSession()
					.getAttribute("LOGIN_USER")).getLoginUserId()));
			newsList = pickWorksInfoService.getNews(newQB);
			bulletinList = bulletinService.getAllBulletins();
			MsgAlarmQB msgQB = new MsgAlarmQB();
			msgQB.setCustId(Long.parseLong(((SysUser) request.getSession()
					.getAttribute("LOGIN_USER")).getLoginUserId()));
			msgAlarmList = msgAlarmService.getMsgAlarms(msgQB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getOneNews() {
		NewsQB nQB = new NewsQB();
		nQB.setPickId(pid);
		newsList = pickWorksInfoService.getNews(nQB);
		return "getOneNews";
	}

	/**
	 * 获取所有的基地信息
	 * 
	 * @Title: getAllBaseInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getAllBaseInfo() {
		BaseQB query = new BaseQB();
		query.setCustId(getLongLoginUserId());
		List<Base> bases = this.baseService.getBases(query);
		this.baseOrGreenHouseList = JSONArray.fromObject(bases);
		return "viewBaseOrGreenHouse";
	}

	/**
	 * 获取单个基地信息
	 * 
	 * @Title: getOneBaseInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getOneBaseInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Base base = this.baseService.getBase(Long.parseLong(request
					.getParameter("baseId")));
			this.baseOrGreenHouseList = JSONArray.fromObject(base);
		} catch (Exception e) {
		}
		return "viewBaseOrGreenHouse";
	}

	/**
	 * 获取单个温室信息
	 * 
	 * @Title: getOneGreenHouseInfo
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String getOneGreenHouseInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String houseId = request.getParameter("houseId");
			BaseGreenHouseInfo house = this.baseGreenHouseInfoService
					.getBaseGreenHouseInfo(Long.parseLong(houseId));
			this.baseOrGreenHouseList = JSONArray.fromObject(house);
		} catch (Exception e) {
		}
		return "viewBaseOrGreenHouse";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String getEquipInfoquery() {
		this.equipInfo = null == this.equipInfo ? new EquipInfo()
				: this.equipInfo;
		HttpServletRequest request = ServletActionContext.getRequest();
		/* 查询所有满足条件的列表 */
		pqb = new PagingQueryBean<EquipInfoQB>();
		EquipInfoQB equipInfoQB = new EquipInfoQB();
		equipInfoQB.setState("1");
		equipInfoQB.setName("".equals(equipInfo.getName()) ? null : equipInfo
				.getName());
		equipInfoQB.setModel("".equals(equipInfo.getModel()) ? null : equipInfo
				.getModel());
		// pqb.setQueryBean(produceWorksInfoQB);
		pqb = buildPagingQueryBean(request, equipInfoQB);
		this.equipInfoResult = equipInfoService.getAllEquipInfosPaging(pqb);
		return "getEquipInfoq";
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String addEquipInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("baseGreenHouseTreeList", GetCustomerTree());
		Long domainId_equipAttribute = NumberUtils
				.toLong(domainId_equipAttribute_Str);
		Long domainId_equipFactory = NumberUtils
				.toLong(domainId_equipFactory_Str);
		List<DomainValue> equipAttributes = this.domainService
				.getDomainValues(domainId_equipAttribute);
		List<DomainValue> equipFactorys = this.domainService
				.getDomainValues(domainId_equipFactory);
		EquipType et = new EquipType();
		et.setLevel(0);
		List<EquipType> ets = this.equipInfoService.selectEquipTypes(et);
		request.setAttribute("values", equipAttributes);
		request.setAttribute("factorys", equipFactorys);
		request.setAttribute("ets", ets);
		return "addEquipInfo";
	}

	/**
	 * 
	 * 添加保存
	 * 
	 * @return
	 */
	public String saveEquipInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.flag = false;
		if (null == this.equipInfo) {
			return "addEquipInfo";
		}
		try {
			equipInfo.setState("1");
			long eqid = this.equipInfoService.createEquipInfo(equipInfo);
			Long domainId = NumberUtils.toLong(domainId_equipAttribute_Str);
			List<DomainValue> values = this.domainService
					.getDomainValues(domainId);
			for (DomainValue domainValue : values) {
				EquipAttribute ea = new EquipAttribute();
				ea.setEquipId(eqid);
				String name = domainValue.getValueName();
				String valueCode = domainValue.getValueCode();
				String value = request.getParameter(valueCode);
				ea.setName(name);
				ea.setValue(value);
				this.equipInfoService.createEquipAttribute(ea);
			}
			/*
			 * EquipSensorsGhouse esg = new EquipSensorsGhouse();
			 * esg.setCustId(Long.parseLong(equipInfo.getCustId()));
			 * esg.setEquipInfoId(eqid); esg.setType("1");// 设备
			 * this.equipSensorsGhouseService.createEquipSensorsGhouse(esg);
			 */
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}

		return "saveEquipInfo";
	}

	/**
	 * 删除deleteEquipInfo
	 * 
	 * @return
	 */
	public String deleteEquipInfo() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.equipInfoCodeStr)) {
			String[] domainCodes = this.equipInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					this.equipInfoService.removeEquipInfo(Long
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
		return "deleteEquipInfo";
	}

	/**
	 * 
	 * 查看设备信息
	 * 
	 * @return
	 */
	public String viewEquipInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.flag = false;
		if (null == this.equipInfoId) {
			return "addEquipInfo";
		}
		try {
			equipInfo = this.equipInfoService.getEquipInfo(equipInfoId);
			Long type = Long.parseLong(equipInfo.getType());
			EquipType et = this.equipInfoService.getOneEquipType(type);
			equipInfo.setType(et.getType());
			Long domainId = NumberUtils.toLong(domainId_equipAttribute_Str);
			List<DomainValue> values = this.domainService
					.getDomainValues(domainId);
			List<EquipAttribute> showValues = new ArrayList<EquipAttribute>();
			for (DomainValue dv : values) {
				String valueName = dv.getValueName();
				Long eqid = equipInfo.getId();
				EquipAttribute ea = new EquipAttribute();
				ea.setEquipId(eqid);
				ea.setName(valueName);
				List<EquipAttribute> eas = this.equipInfoService
						.getEquipAttributes(ea);
				if (null != eas && eas.size() > 0) {
					showValues.add(eas.get(0));
				}
			}
			request.setAttribute("showValues", showValues);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		// baseInfoList();
		return "viewEquipInfo";
	}

	/**
	 * 
	 * 编辑页面
	 * 
	 * @return
	 */
	public String editEquipInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		CustomerQB custmerqb = new CustomerQB();
		custmerqb.setState("1");
		List<Customer> customerList = customerService.getCustomers(custmerqb);
		EquipSensorsGhouseQB equipsensorsghouseqb = new EquipSensorsGhouseQB();
		equipsensorsghouseqb.setEquipInfoId(equipInfoId);
		List<EquipSensorsGhouse> esglist = equipSensorsGhouseService
				.getEquipSensorsGhouses(equipsensorsghouseqb);
		EquipSensorsGhouse es = new EquipSensorsGhouse();
		if (null != esglist) {
			for (int i = 0; i < esglist.size(); i++) {
				es = esglist.get(i);
			}
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("esglist", es);
		this.flag = false;
		if (null == this.equipInfoId) {
			return "addEquipInfo";
		}
		try {
			equipInfo = this.equipInfoService.getEquipInfo(equipInfoId);
			Long domainId = NumberUtils.toLong(domainId_equipAttribute_Str);
			List<DomainValue> values = this.domainService
					.getDomainValues(domainId);
			for (DomainValue dv : values) {
				String valueName = dv.getValueName();
				Long eqid = equipInfo.getId();
				EquipAttribute ea = new EquipAttribute();
				ea.setEquipId(eqid);
				ea.setName(valueName);
				List<EquipAttribute> eas = this.equipInfoService
						.getEquipAttributes(ea);
				if (null != eas && eas.size() > 0) {
					dv.setDomainCode(eas.get(0).getValue());
				}
			}
			Long typeId = Long.parseLong(equipInfo.getType());
			EquipType et = this.equipInfoService.getOneEquipType(typeId);
			equipInfo.setType(et.getType());
			request.setAttribute("values", values);
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		return "editEquipInfo";
	}

	/**
	 * 
	 * 编辑保存
	 * 
	 * @return
	 */
	public String updateEquipInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.flag = false;
		if (null == this.equipInfo) {
			return "addEquipInfo";
		}
		try {
			this.equipInfoService.updateEquipInfo(equipInfo);
			// 更新中间表
			Long domainId = NumberUtils.toLong(domainId_equipAttribute_Str);
			List<DomainValue> values = this.domainService
					.getDomainValues(domainId);
			for (DomainValue domainValue : values) {
				EquipAttribute ea = new EquipAttribute();
				ea.setEquipId(equipInfo.getId());
				String name = domainValue.getValueName();
				String valueCode = domainValue.getValueCode();
				String value = request.getParameter(valueCode);
				ea.setName(name);
				ea.setValue(value);
				EquipAttribute oldEquip = this.equipInfoService
						.getEquipAttributeByEqidAndName(ea);
				if (oldEquip != null) {
					this.equipInfoService.updateEquipAttribute(ea);
				} else {
					this.equipInfoService.createEquipAttribute(ea);
				}
			}
			/*
			 * EquipSensorsGhouse esginfo = new EquipSensorsGhouse();
			 * esginfo.setId(Long.parseLong(esgid));
			 * esginfo.setCustId(Long.parseLong(custid));
			 * equipSensorsGhouseService.updateEquipSensorsGhouse(esginfo);
			 */
			this.flag = true;
		} catch (Exception e) {
			this.flag = false;
		}
		return "updaOk";
	}

	/**
	 * 客户设备列表联动
	 * 
	 * @return
	 */
	public String forwardEquipInfo() {
		this.equipInfo = null == this.equipInfo ? new EquipInfo()
				: this.equipInfo;
		HttpServletRequest request = ServletActionContext.getRequest();
		/* 查询所有满足条件的列表 */
		pqb = new PagingQueryBean<EquipInfoQB>();
		EquipInfoQB equipInfoQB = new EquipInfoQB();
		equipInfoQB.setState("1");
		equipInfoQB.setName("".equals(equipInfo.getName()) ? null : equipInfo
				.getName());
		equipInfoQB.setModel("".equals(equipInfo.getModel()) ? null : equipInfo
				.getModel());
		equipInfoQB.setCustId("".equals(equipInfo.getCustId()) ? null
				: equipInfo.getCustId());
		// pqb.setQueryBean(produceWorksInfoQB);
		pqb = buildPagingQueryBean(request, equipInfoQB);
		this.equipInfoResult = equipInfoService.getUserEquipInfosPaging(pqb);

		return "getEquipInfoq";
	}

	/**
	 * 作废 设备信息
	 */
	public String updateEquipInfoUnable() {
		StringBuilder failCode = new StringBuilder();
		this.flag = false;
		if (StringUtils.isNotBlank(this.equipInfoCodeStr)) {
			String[] domainCodes = this.equipInfoCodeStr.split(",");
			for (String domainCode2 : domainCodes) {
				try {
					equipInfo = this.equipInfoService.getEquipInfo(Long
							.parseLong(domainCode2));
					equipInfo.setState("0");
					equipInfoService.updateEquipInfo(equipInfo);
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
		return "deleteEquipInfo";
	}

	/**
	 * 基地 信息
	 */
	public void baseInfoList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Base> baseAll = baseService.getAllBases();
		request.setAttribute("baseInfoList", baseAll);
	}

	/**
	 * 树基地
	 * 
	 */
	public List<BaseGreenHouseTree> GetBaseInfo() {
		List<Base> baseAll = baseService.getAllBases();
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseTree baseGreenHouseTree = null;
		for (Base base : baseAll) {

			// 添加基地
			baseGreenHouseTree = new BaseGreenHouseTree();
			baseGreenHouseTree.setNodeTreeId(base.getId());
			baseGreenHouseTree.setNodeTreeName(base.getName());
			baseGreenHouseTree.setParentId(-1l);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;
		}
		return baseGreenHouseTreeList;
	}

	/**
	 * 树基地 温室
	 * 
	 * @return
	 */
	public List<BaseGreenHouseTree> GetBaseGreenHouseTree() {
		List<Base> baseAll = baseService.getAllBases();
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

			// 拼查询条件
			greenHouseQB = new BaseGreenHouseInfoQB();
			greenHouseQB.setBaseId(base.getId());

			List<BaseGreenHouseInfo> greenHouseAll = this.baseGreenHouseInfoService
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
	 * 设备类型管理页面
	 * 
	 * @Title: equipTypeManager
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String equipTypeManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<EquipTypeTree> etts = getEquipTypeTree();
		request.setAttribute("etts", etts);
		return "equipTypeManager";
	}

	/**
	 * 客户 树
	 * 
	 * @return
	 */
	public List<BaseGreenHouseTree> GetCustomerTree() {
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseTree baseGreenHouseTree = null;
		baseGreenHouseTree = new BaseGreenHouseTree();
		baseGreenHouseTree.setNodeTreeId(2l);
		baseGreenHouseTree.setNodeTreeName("所有客户");
		baseGreenHouseTree.setParentId(-1l);
		baseGreenHouseTreeList.add(baseGreenHouseTree);
		baseGreenHouseTree = null;
		CustomerQB custmerqb = new CustomerQB();
		custmerqb.setState("1");
		List<Customer> customerList = customerService.getCustomers(custmerqb);
		if (customerList != null) {
			for (Customer customer : customerList) {
				baseGreenHouseTree = new BaseGreenHouseTree();
				baseGreenHouseTree.setNodeTreeId(customer.getCustId());
				baseGreenHouseTree.setNodeTreeName(customer.getName());
				baseGreenHouseTree.setParentId(2l);
				baseGreenHouseTreeList.add(baseGreenHouseTree);
			}
		}
		return baseGreenHouseTreeList;
	}

	/**
	 * 获取设备类型树
	 * 
	 * @Title: getEquipTypeTree
	 * @author Mao Xiaobing
	 * @return List<BaseGreenHouseTree> 返回类型
	 * @throws
	 */
	public List<EquipTypeTree> getEquipTypeTree() {
		List<EquipTypeTree> etts = new ArrayList<EquipTypeTree>();
		List<EquipType> ets = this.equipInfoService.getAllEquipTypes();
		EquipTypeTree pTree = new EquipTypeTree();
		pTree.setNodeTreeId(-1l);
		pTree.setNodeTreeName("设备");
		pTree.setLevel(0l);
		etts.add(pTree);
		for (EquipType et : ets) {
			EquipTypeTree ett = new EquipTypeTree();
			ett.setLevel(Long.parseLong(et.getLevel() + "") + 1);
			ett.setNodeTreeId(et.getId());
			ett.setParentId(et.getParentId());
			ett.setNodeTreeName(et.getType());
			etts.add(ett);
		}
		return etts;
	}

	/**
	 * 跳转到设备类型新增页面
	 * 
	 * @Title: toAddEquipTypePage
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String toAddEquipTypePage() {
		return "toAddEquipTypePage";
	}

	/**
	 * 保存设备类型
	 * 
	 * @Title: saveEquipType
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String saveEquipType() {
		if (null != this.equipType) {
			String type = equipType.getType();
			if (StringUtils.isNotBlank(type)
					&& null == this.equipInfoService
							.findOneEquipType(equipType)) {
				this.equipInfoService.saveOneEquipType(equipType);
			}
		}
		return "saveEquipTypeOk";
	}

	/**
	 * 加载设备类型表格数据
	 * 
	 * @Title: loadEquipTypes
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String loadEquipTypes() {
		this.equipType = null == this.equipType ? new EquipType()
				: this.equipType;
		HttpServletRequest request = ServletActionContext.getRequest();
		pqbe = new PagingQueryBean<EquipTypeQB>();
		EquipTypeQB equipTypeQB = new EquipTypeQB();
		equipTypeQB.setLevel(equipType.getLevel());
		equipTypeQB.setParentId(equipType.getParentId());
		pqbe = buildPagingQueryBean(request, equipTypeQB);
		this.equipTypeResult = equipInfoService.getPagingEquipTypes(pqbe);
		return "getEquipTypeq";
	}

	/**
	 * 加载下一级设备类型
	 * 
	 * @Title: loadNextEquipTypes
	 * @author Mao Xiaobing
	 * @return String 返回类型
	 * @throws
	 */
	public String loadNextEquipTypes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pId = request.getParameter("pId");
		if (StringUtils.isNotBlank(pId)) {
			try {
				Long parentId = Long.parseLong(pId);
				this.equipTypes = this.equipInfoService
						.getEquipTypesByPiD(parentId);
				JSONArray json = JSONArray.fromObject(this.equipTypes);
				this.datas = json.toString();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return "echoForLoadNextEquipTypes";
	}

	/**
	 * 获取基地和温室树
	 * 
	 * @Title: GetBaseGreenHouseTree
	 * @author Mao Xiaobing
	 * @return List<BaseGreenHouseTree> 返回类型
	 * @throws
	 */
	public List<BaseGreenHouseTree> getBaseGreenHouseTree() {
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
			baseGreenHouseTree.setType(1);
			baseGreenHouseTreeList.add(baseGreenHouseTree);
			baseGreenHouseTree = null;

			// 拼查询条件
			greenHouseQB = new BaseGreenHouseInfoQB();
			greenHouseQB.setBaseId(base.getId());
			List<BaseGreenHouseInfo> greenHouseAll = baseGreenHouseInfoService
					.getBaseGreenHouseInfos(greenHouseQB);
			if (greenHouseAll != null) {
				for (BaseGreenHouseInfo greenHouse : greenHouseAll) {
					baseGreenHouseTree = new BaseGreenHouseTree();
					baseGreenHouseTree.setNodeTreeId(greenHouse.getId());
					baseGreenHouseTree.setNodeTreeName(greenHouse.getName());
					baseGreenHouseTree.setParentId(base.getId());
					baseGreenHouseTree.setType(2);
					baseGreenHouseTreeList.add(baseGreenHouseTree);
				}
			}
		}
		return baseGreenHouseTreeList;
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

	public ProduceSeasonService getProduceSeasonService() {
		return produceSeasonService;
	}

	public void setProduceSeasonService(
			ProduceSeasonService produceSeasonService) {
		this.produceSeasonService = produceSeasonService;
	}

	public EquipInfoService getEquipInfoService() {
		return equipInfoService;
	}

	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}

	public String getEquipInfoCodeStr() {
		return equipInfoCodeStr;
	}

	public void setEquipInfoCodeStr(String equipInfoCodeStr) {
		this.equipInfoCodeStr = equipInfoCodeStr;
	}

	public Long getEquipInfoId() {
		return equipInfoId;
	}

	public void setEquipInfoId(Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}

	public EquipInfo getEquipInfo() {
		return equipInfo;
	}

	public void setEquipInfo(EquipInfo equipInfo) {
		this.equipInfo = equipInfo;
	}

	public PagingResultBean<List<EquipInfo>> getEquipInfoResult() {
		return equipInfoResult;
	}

	public void setEquipInfoResult(
			PagingResultBean<List<EquipInfo>> equipInfoResult) {
		this.equipInfoResult = equipInfoResult;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public EquipSensorsGhouseService getEquipSensorsGhouseService() {
		return equipSensorsGhouseService;
	}

	public void setEquipSensorsGhouseService(
			EquipSensorsGhouseService equipSensorsGhouseService) {
		this.equipSensorsGhouseService = equipSensorsGhouseService;
	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public PagingResultBean<List<EquipType>> getEquipTypeResult() {
		return equipTypeResult;
	}

	public void setEquipTypeResult(
			PagingResultBean<List<EquipType>> equipTypeResult) {
		this.equipTypeResult = equipTypeResult;
	}

	public EquipType getEquipType() {
		return equipType;
	}

	public void setEquipType(EquipType equipType) {
		this.equipType = equipType;
	}

	public List<EquipType> getEquipTypes() {
		return equipTypes;
	}

	public void setEquipTypes(List<EquipType> equipTypes) {
		this.equipTypes = equipTypes;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public BaseGreenHouseInfoService getBaseGreenHouseInfoService() {
		return baseGreenHouseInfoService;
	}

	public void setBaseGreenHouseInfoService(
			BaseGreenHouseInfoService baseGreenHouseInfoService) {
		this.baseGreenHouseInfoService = baseGreenHouseInfoService;
	}

	public JSONArray getBaseOrGreenHouseList() {
		return baseOrGreenHouseList;
	}

	public void setBaseOrGreenHouseList(JSONArray baseOrGreenHouseList) {
		this.baseOrGreenHouseList = baseOrGreenHouseList;
	}

	public OperationWorksInfoService getOperationWorksInfoService() {
		return operationWorksInfoService;
	}

	public void setOperationWorksInfoService(
			OperationWorksInfoService operationWorksInfoService) {
		this.operationWorksInfoService = operationWorksInfoService;
	}

	public String getNewsListJson() {
		return newsListJson;
	}

	public void setNewsListJson(String newsListJson) {
		this.newsListJson = newsListJson;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo() {
		return equipDataEnvdataTypeInfo;
	}

	public void setEquipDataEnvdataTypeInfo(
			List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo) {
		this.equipDataEnvdataTypeInfo = equipDataEnvdataTypeInfo;
	}

	public EquipDataTypeService getEquipDataTypeService() {
		return equipDataTypeService;
	}

	public void setEquipDataTypeService(
			EquipDataTypeService equipDataTypeService) {
		this.equipDataTypeService = equipDataTypeService;
	}

	public PagingQueryBean<EquipInfoQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<EquipInfoQB> pqb) {
		this.pqb = pqb;
	}

	public PagingQueryBean<EquipTypeQB> getPqbe() {
		return pqbe;
	}

	public void setPqbe(PagingQueryBean<EquipTypeQB> pqbe) {
		this.pqbe = pqbe;
	}

	public String getDomainId_equipAttribute_Str() {
		return domainId_equipAttribute_Str;
	}

	public void setDomainId_equipAttribute_Str(
			String domainId_equipAttribute_Str) {
		this.domainId_equipAttribute_Str = domainId_equipAttribute_Str;
	}

	public String getDomainId_equipFactory_Str() {
		return domainId_equipFactory_Str;
	}

	public void setDomainId_equipFactory_Str(String domainId_equipFactory_Str) {
		this.domainId_equipFactory_Str = domainId_equipFactory_Str;
	}

	public PickWorksInfoService getPickWorksInfoService() {
		return pickWorksInfoService;
	}

	public void setPickWorksInfoService(
			PickWorksInfoService pickWorksInfoService) {
		this.pickWorksInfoService = pickWorksInfoService;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public List<Bulletin> getBulletinList() {
		return bulletinList;
	}

	public void setBulletinList(List<Bulletin> bulletinList) {
		this.bulletinList = bulletinList;
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public List<MsgAlarm> getMsgAlarmList() {
		return msgAlarmList;
	}

	public void setMsgAlarmList(List<MsgAlarm> msgAlarmList) {
		this.msgAlarmList = msgAlarmList;
	}

	public MsgAlarmService getMsgAlarmService() {
		return msgAlarmService;
	}

	public void setMsgAlarmService(MsgAlarmService msgAlarmService) {
		this.msgAlarmService = msgAlarmService;
	}

}
