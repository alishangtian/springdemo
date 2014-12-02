package com.cattsoft.ny.base.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfoList;
import com.cattsoft.ny.base.entity.BaseGreenHouseTree;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.EquipControlInfoH;
import com.cattsoft.ny.base.entity.EquipControlList;
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipDataTypeCommon;
import com.cattsoft.ny.base.entity.EquipDateTypeListInfo;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.GreenHouse;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.ProduceThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoHQB;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.entity.querybean.GreenHouseQB;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.EquipControlInfoHService;
import com.cattsoft.ny.base.service.EquipDataEnvdataService;
import com.cattsoft.ny.base.service.EquipDataTypeService;
import com.cattsoft.ny.base.service.EquipInfoService;
import com.cattsoft.ny.base.service.EquipNetTopologyService;
import com.cattsoft.ny.base.service.EquipSensorsGhouseService;
import com.cattsoft.ny.base.service.GreenHouseService;
import com.cattsoft.ny.base.service.PrdcSeasonService;
import com.cattsoft.ny.base.service.ProduceThresholdInfoService;
import com.cattsoft.ny.core.HttpRequestUtil;

/**
 * 
 * 种植季管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class GreedHoseClickAction extends BaseAction {

	/**
	 * 
	 */
	private BaseService baseService;
	//private GreenHouseService greenHouseService;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private PrdcSeasonService prdcSeasonService;
	private List<PrdcSeason> prdcSeasons;
	private PrdcSeason prdcSeason;
	private String houseName;
	private String PTIName;
	private PagingQueryBean<PrdcSeasonQB> cqb;
	private PagingResultBean<List<PrdcSeason>> prdcSeasonsInfoResult;
	private ProduceThresholdInfoService produceThresholdInfoService;
	private EquipSensorsGhouseService equipSensorsGhouseService;
	private EquipDataTypeService equipDataTypeService;
	private EquipDataEnvdataService equipDataEnvdataService;
	private EquipInfoService equipInfoService;
	private List<ProduceThresholdInfo> ptilist;
	private JSONArray equipDataEnvdataTypeInfoList;
	private List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo;
	private List<EquipDateTypeListInfo> edataETypeInfo;
	private List<EquipControlList> equipControlList;
	private List<EquipControlInfo> equipControlInfoList;
	private GreenHouseService greenHouseService;
	private List<BaseGreenHouseTree> sysOrgList;
	private EquipNetTopologyService equipNetTopologyService;
    private Long nodeTreeId;
    private String levelTree;
    private List<BaseGreenHouseTree> baseGreenHouseTreeList;
	private String testArrayD;
	private String testArrayX;
    private Integer hour=1;
    private PagingQueryBean<EquipDataEnvdataQB> pqb;
    private PagingQueryBean<BaseGreenHouseInfoQB> pqbi;
    private PagingQueryBean<EquipControlInfoHQB> ecpqb;
    private BaseGreenHouseInfoQB baseGreenHouseInfoQB;
	private PagingResultBean<List<BaseGreenHouseInfo>> baseGreenHouseInfoResult;
	private PagingResultBean<List<BaseGreenHouseInfoList>> baseInfoResultList;
    private PagingResultBean<List<EquipDataEnvdata>> DataInfoResult;
    private PagingResultBean<List<EquipControlInfoH>> econInfoHResult;
    private EquipControlInfoHService equipControlInfoHService;
	private EquipDataEnvdataQB equipDataEnvdataQB;
	private String equipMM;
	private String tid;
	private String flag="0";
	private String baseName;
	private String crops;
	private Map<String,String> listBase;
	public String execute() {
		edataETypeInfo= new ArrayList<EquipDateTypeListInfo>();
		equipControlList= new ArrayList<EquipControlList>();
		try{
			//得到一个基地 下面的一个温室 的设备
			List<Base> base = this.findBaseUserAll();
			if(null!=base&&base.size()>0){
				BaseGreenHouseInfoQB bghi= new BaseGreenHouseInfoQB();
				bghi.setBaseId(base.get(0).getId());
				//根据基地id获得 所有 温室信息
				List<BaseGreenHouseInfo> listbgh = baseGreenHouseInfoService.getBaseGreenHouseInfos(bghi);
				if(!listbgh.isEmpty()){
					EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
					esgQb.setHouseId(listbgh.get(0).getId());
					this.findBaseHoseName(listbgh.get(0).getId());
					//根据温室id 获得 温室下面的设备
					List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
					for(EquipSensorsGhouse listesg:esgsinfo){
						EquipInfo equipInfo = equipInfoService.getEquipInfo(listesg.getEquipInfoId());
						//如果是节点 3 
						if("3".equals(equipInfo.getType()+"")){
							//查询（设备）设备网络拓扑关系 父节点
							EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
							queryBean.setParentNodeId(equipInfo.getId());
							List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
							//如果是空 则是传感器
							if(listEq.isEmpty()){
								equipDataEnvdataTypeInfo=equipDataTypeService.getEquipDataEnvdataTypeInfo(listesg.getEquipInfoId());
								EquipDateTypeListInfo	edtliList = new EquipDateTypeListInfo();
								edtliList.setEquipDataEnvdataTypeInfo(equipDataEnvdataTypeInfo);
								edtliList.setEquipInfoName(equipInfo.getName());
								edataETypeInfo.add(edtliList);
							}else{
								for (EquipNetTopology equipNetTopology : listEq) {
									EquipInfo eip = equipInfoService.getEquipInfo(equipNetTopology.getParentNodeId());
									EquipInfo ei = equipInfoService.getEquipInfo(equipNetTopology.getEquipInfoId());
									if("6".equals(ei.getType())){
										EquipControlInfoQB ec = new EquipControlInfoQB();
										ec.setEquipId(ei.getId()+"");
										equipControlInfoList=equipInfoService.findEquipControlInfo(ec);
										EquipControlList ecl = new EquipControlList();
										ecl.setEquipControlInfoList(equipControlInfoList);
										ecl.setControName(ei.getName());
										//查询（设备）设备网络拓扑关系 父节点
									/*	EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
										queryBean.setEquipInfoId(equipInfo.getId());
										List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
										if(null!=listEq && listEq.size()>0){
											if(listEq.get(0).getParentNodeId()!=null){
												EquipInfo e = equipInfoService.getEquipInfo(listEq.get(0).getParentNodeId());
												if(null != e)	
													ecl.setParentName(e.getName());
											}
										}*/
										ecl.setParentName(eip.getName());
										equipControlList.add(ecl);
									}
								}
							}
						}
						
					}
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "GreedHoseClickInfo";
	}
	
	/**
	 * 根据 设备id 查询具体传感器值
	 */
	public String selectEquipData(){
		edataETypeInfo= new ArrayList<EquipDateTypeListInfo>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String equipId = request.getParameter("equipId");
		String houseId = request.getParameter("houseId");
		if(!StringUtils.isEmpty(houseId)){
			//得到 温室下面的 设备编号 如果类型类型为节点的 设备编号
			EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
			esgQb.setHouseId(Long.parseLong(houseId));
			List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
			if(null!=esgsinfo&&esgsinfo.size()>0){
				for(int i=0;i<esgsinfo.size();i++){
					 EquipInfo EquipInfo = equipInfoService.getEquipInfo(esgsinfo.get(i).getEquipInfoId());
					if("3".equals(EquipInfo.getType())){
						//查询（设备）设备网络拓扑关系 父节点
				    	EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
				    	queryBean.setParentNodeId(EquipInfo.getId());
						List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
						if(listEq.isEmpty()){
							equipDataEnvdataTypeInfo=equipDataTypeService.getEquipDataEnvdataTypeInfo(EquipInfo.getId());
							EquipDateTypeListInfo	edtliList = new EquipDateTypeListInfo();
							edtliList.setEquipDataEnvdataTypeInfo(equipDataEnvdataTypeInfo);
							edtliList.setEquipInfoName(EquipInfo.getName());
							edataETypeInfo.add(edtliList);
						}
					}
				}	
			}	
		}else{
			//判端（设备）设备网络拓扑关系equip_net_topology 是否有据如果有 则查设备类型 为 控制器的 设备否则查
			equipDataEnvdataTypeInfo = equipDataTypeService.getEquipDataEnvdataTypeInfo(Long.parseLong(equipId));
			EquipInfo EquipInfo = equipInfoService.getEquipInfo(Long.parseLong(equipId));
			EquipDateTypeListInfo 	edtliList = new EquipDateTypeListInfo();
			edtliList.setEquipDataEnvdataTypeInfo(equipDataEnvdataTypeInfo);
			edtliList.setEquipInfoName(EquipInfo.getName());
			edataETypeInfo.add(edtliList);
		}
		return "selectEquipData";
	}
 /**
  * 6为控制器类型 查询控制器信息
  */
	public String selectControlInfo(){
		equipControlList= new ArrayList<EquipControlList>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String equipId = request.getParameter("equipId");
		String houseId = request.getParameter("houseId");
		if(!StringUtils.isEmpty(houseId)){
			EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
			esgQb.setHouseId(Long.parseLong(houseId));
			List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
			if(null!=esgsinfo&&esgsinfo.size()>0){
				for (EquipSensorsGhouse equipSensorsGhouse : esgsinfo) {
				EquipInfo equipInfo = equipInfoService.getEquipInfo(equipSensorsGhouse.getEquipInfoId());
				if("3".equals(equipInfo.getType())){
					//查询（设备）设备网络拓扑关系 父节点
					EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
					queryBean.setParentNodeId(equipInfo.getId());
					List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
					//如果是空 则是传感器
					if(!listEq.isEmpty()){
						for (EquipNetTopology equipNetTopology : listEq) {
							EquipInfo eip = equipInfoService.getEquipInfo(equipNetTopology.getParentNodeId());
							EquipInfo ei = equipInfoService.getEquipInfo(equipNetTopology.getEquipInfoId());
							if("6".equals(ei.getType())){
								EquipControlInfoQB ec = new EquipControlInfoQB();
								ec.setEquipId(ei.getId()+"");
								equipControlInfoList=equipInfoService.findEquipControlInfo(ec);
								EquipControlList ecl = new EquipControlList();
								ecl.setEquipControlInfoList(equipControlInfoList);
								ecl.setControName(ei.getName());
								ecl.setParentName(eip.getName());
								equipControlList.add(ecl);
							}
						}
					}
				}
						//发送指令 查出 所有控制器下面的设备状态
						//equipHttpMss(equipId);
				}
			}
			
		}else{
			EquipControlList ecl = new EquipControlList();
			//查询（设备）设备网络拓扑关系 父节点
	    	EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
	    	queryBean.setParentNodeId(Long.parseLong(equipId));
			List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
			if(null!=listEq && listEq.size()>0){
				for (EquipNetTopology equipNetTopology : listEq) {
					EquipInfo e = equipInfoService.getEquipInfo(equipNetTopology.getParentNodeId());
					EquipInfo equipInfo = equipInfoService.getEquipInfo(equipNetTopology.getEquipInfoId());
					if("6".equals(equipInfo.getType())){
						EquipControlInfoQB ec = new EquipControlInfoQB();
						ec.setEquipId(equipInfo.getId()+"");
						equipControlInfoList = equipInfoService.findEquipControlInfo(ec);
						ecl.setParentName(e.getName());
						ecl.setControName(equipInfo.getName());
						ecl.setEquipControlInfoList(equipControlInfoList);
					}
				}
			}
			equipControlList.add(ecl);
		}
		return "selectControlInfo";
	}
	/**
	 * 查询控制器操作历史
	 * 功能待完善，控制器操作历史表需要设计，每次点击开关后新增到历史表，
	 * 操作人 开关状态 操作时间 控制器id 控制器名字 控制器端口 控制器父节点id
	 */
	public String queryControlHistory(){
		return "";
	}
	
	/**
	 * 根据 基地 id 查询基地下面的所有温室，在查询温室下面的所有设备 设备分为 传感器 控制器
	 * 
	 */
	public String queryEquipHouseBaseList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String baseId = request.getParameter("baseId");
		String houseId = request.getParameter("houseId");
		if(pqbi == null){
			pqbi = new PagingQueryBean<BaseGreenHouseInfoQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(5);
			pqbi.setPagingInfo(pageInfo);
		}
		  BaseGreenHouseInfoQB  base = new BaseGreenHouseInfoQB();
		  if(StringUtils.isNotEmpty(houseId)){
			  base.setId(Long.parseLong(houseId));  
		  }else{
			  base.setBaseId(Long.parseLong(baseId));
		  }
		  pqbi.setQueryBean(base);
		baseInfoResultList =  new PagingResultBean<List<BaseGreenHouseInfoList>>();
		this.baseGreenHouseInfoResult = baseGreenHouseInfoService.getAllBaseGreenHouseInfosPaging(pqbi);
		baseInfoResultList.setPagingInfo(baseGreenHouseInfoResult.getPagingInfo());
		List<BaseGreenHouseInfoList> infos = new ArrayList<BaseGreenHouseInfoList>();
		for (BaseGreenHouseInfo bghs : baseGreenHouseInfoResult.getResultList()) {
			BaseGreenHouseInfoList bghil = new BaseGreenHouseInfoList();
			bghil.setBaseId(bghs.getBaseId());
			bghil.setHouseId(bghs.getId());
			bghil.setHouseName(bghs.getName());
			EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
			esgQb.setHouseId(bghs.getId());
			EquipInfoQB e = new EquipInfoQB();
			e.setHouseId(bghs.getId()+"");
			e.setType("3");
			Integer i = equipInfoService.cgqCountInfo(e);
			e.setType("6");
			Integer j = equipInfoService.cgqCountInfo(e);
			bghil.setCgqCount(i);
			bghil.setKzqCount(j);
			infos.add(bghil);
		}
		baseInfoResultList.setResultList(infos);
		return "queryEquipHouseBaseList";
	}
	
	/**
	 * 根据设备id 查询 网络拓扑表 是否有数据 如果有则查询控制器，如果没有则查询传感器
	 */
	public String queryEquipNetTopology(){
		try{
			HttpServletRequest request = ServletActionContext.getRequest();
			String equipId = request.getParameter("equipId");
			//查询（设备）设备网络拓扑关系 父节点
			EquipNetTopologyQB queryBean = new EquipNetTopologyQB();
			queryBean.setParentNodeId(Long.parseLong(equipId));
			List<EquipNetTopology> listEq = equipNetTopologyService.getEquipNetTopologys(queryBean);
			if(null!=listEq && listEq.size()>0){
				flag = "1";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "queryEquipNet";
	}
	
	/**
	 * 根据设备id,查询信息并 发起http请求  
	 */
	public void equipHttpMss(String equipId){
		//根据equipId 得到设备信息 得到设备物理id
		EquipInfo eif = equipInfoService.getEquipInfo(Long.parseLong(equipId));
		//根据设备id 查询拓扑结构表 找到 父节点
		EquipNetTopologyQB ent = new EquipNetTopologyQB();
		ent.setEquipInfoId(Long.parseLong(equipId));
		List<EquipNetTopology> entlinfo =  equipNetTopologyService.getEquipNetTopologys(ent);
		EquipInfo eifi = equipInfoService.getEquipInfo( entlinfo.get(0).getParentNodeId());
		//请求http得到数据
		Map<String,String> map = HttpRequestUtil.httpInfoXml(eifi.getDeviceId(), entlinfo.get(0).getParentPort()+"", eif.getDeviceId(), "on", "0");
		for(int i=0;i<equipControlInfoList.size();i++){
			String eport = equipControlInfoList.get(i).getPort().toString();
			if(!map.isEmpty()){
				for(Map.Entry<String, String> entry:map.entrySet()){
					String key = entry.getKey() ;
					key = key.substring(key.length()-1);
					if(eport.equals(key)){
						equipControlInfoList.get(i).setSatuts(entry.getValue());
					}
				}
			}
		}
	}
	
	/**
	 * 根据温室id 查询温室下面的信息
	 */
	public String equipInfoListMss(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String houseId = request.getParameter("houseId");
		//得到 温室下面的 设备编号 如果类型类型为节点的 设备编号
		EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
		esgQb.setHouseId(Long.parseLong(houseId));
		List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
		if(null!=esgsinfo&&esgsinfo.size()>0){
			for(int i=0;i<esgsinfo.size();i++){
				 EquipInfo EquipInfo = equipInfoService.getEquipInfo(esgsinfo.get(i).getEquipInfoId());
				if("3".equals(EquipInfo.getType())){
						//request.setAttribute("EquipDTIList",equipDataTypeService.getEquipDataEnvdataTypeInfo(EquipInfo.getId()));
					equipDataEnvdataTypeInfo=equipDataTypeService.getEquipDataEnvdataTypeInfo(EquipInfo.getId());
				}
				if("6".equals(EquipInfo.getType())){
						EquipControlInfoQB ec = new EquipControlInfoQB();
						ec.setEquipId(EquipInfo.getId()+"");
						//request.setAttribute("equipControlInfoList" , equipInfoService.findEquipControlInfo(ec));
					equipControlInfoList =equipInfoService.findEquipControlInfo(ec);
					equipHttpMss(EquipInfo.getId()+"");
				}
			}
		}
/*		List<BaseGreenHouseTree> listBght = GetBaseGreenHouseTree();
		request.setAttribute("baseGreenHouseTreeList", listBght);*/
		return "equipInfoListMss";
	}
	 
 /**
  * 跳转视频页面
  */
	public String videosInfo(){
		return "videosInfo";
	}
	/**
	 * 查询控制器历史
	 */
	public String equipControlInfoHisList(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String equipId = request.getParameter("equipId");
			if(ecpqb == null){
				ecpqb = new PagingQueryBean<EquipControlInfoHQB>();
				PagingInfo pageInfo = new PagingInfo();
				pageInfo.setPageNum(1);
				pageInfo.setPageRows(10);
				ecpqb.setPagingInfo(pageInfo);
			}
			EquipControlInfoHQB edQB = new EquipControlInfoHQB();
			edQB.setEquipportid(Long.parseLong(equipId));
			ecpqb.setQueryBean(edQB);
			
			this.econInfoHResult = equipControlInfoHService.getAllEquipControlInfoHsPaging(ecpqb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "equipControlInfoHisList";
	}
	
	/**
	 * 查询当前登陆人下面的所有基地
	 * 
	 */
	public List<Base>  findBaseUserAll(){
		try{
			String userId = getStringLoginUserId();
			BaseQB queryBean = new BaseQB();
			queryBean.setCustId(Long.parseLong(userId));
			queryBean.setState("0");
			List<Base> baseAll = baseService.getBases(queryBean);
			return baseAll;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据温室id得到温室名称以及基地名称 和种植季信息
	 */
	public String findBaseHoseName(Long hoseId){
		//得到温室
		BaseGreenHouseInfo bghi = baseGreenHouseInfoService.getBaseGreenHouseInfo(hoseId);
		houseName = bghi.getName();
		//得到基地
		Base base = baseService.getBase(bghi.getBaseId());
		baseName = base.getName();
		//得到种植季信息
		PrdcSeasonQB ps = new PrdcSeasonQB();
		ps.setHouseId(hoseId);
		ps.setBeginTime(new Date());
		ps.setEndTime(new Date());
		ps.setState("1");
		List<PrdcSeason> psList = prdcSeasonService.getPrdcSeasons(ps);
		if(!psList.isEmpty()){
			crops = psList.get(0).getCrops();
		}
		return null;
	}
	/**
	 * 根据温室id得到温室名称以及基地名称 和种植季信息
	 */
	public String ajaxBaseHoseName(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String houseId = request.getParameter("houseId");
		//得到温室
		BaseGreenHouseInfo bghi = baseGreenHouseInfoService.getBaseGreenHouseInfo(Long.parseLong(houseId));
		houseName = bghi.getName();
		//得到基地
		Base base = baseService.getBase(bghi.getBaseId());
		baseName = base.getName();
		//得到种植季信息
		PrdcSeasonQB ps = new PrdcSeasonQB();
		ps.setHouseId(Long.parseLong(houseId));
		ps.setBeginTime(new Date());
		ps.setEndTime(new Date());
		ps.setState("1");
		List<PrdcSeason> psList = prdcSeasonService.getPrdcSeasons(ps);
		if(!psList.isEmpty()){
			crops = psList.get(0).getCrops();
		}
		listBase= new HashMap<String,String>();
		listBase.put("houseName", houseName);
		listBase.put("baseName", baseName);
		listBase.put("crops", crops);
		return "findBaseHoseName";
	}
	
	
	/**
	 * 动态基地树
	 * @return
	 */
	public String findBaseGreenTree(){
		BaseGreenHouseTree baseGreenHouseTree = null;
		if(StringUtils.isEmpty(levelTree)){
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
		}else if (levelTree.equals("1")){
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
			BaseGreenHouseInfoQB bgQB = new BaseGreenHouseInfoQB();
			bgQB.setBaseId(nodeTreeId);
			List<BaseGreenHouseInfo> ghLists = baseGreenHouseInfoService.getBaseGreenHouseInfos(bgQB);
			if(ghLists!=null && ghLists.size()>0){
				for(BaseGreenHouseInfo bshi:ghLists){
					baseGreenHouseTree = new BaseGreenHouseTree();
					baseGreenHouseTree.setNodeTreeId(bshi.getId());
					baseGreenHouseTree.setNodeTreeName(bshi.getName());
					baseGreenHouseTree.setLevelTree("2");
					baseGreenHouseTree.setParent(false);
					baseGreenHouseTreeList.add(baseGreenHouseTree);
				}
			}
		}else if(levelTree.equals("2")){
			//查温室下面的 传感器 控制器
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
			EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
			esgQb.setHouseId(nodeTreeId);
			List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
			if(null!=esgsinfo&&esgsinfo.size()>0){
				for(EquipSensorsGhouse equipSensorsGhouse : esgsinfo) {
					//查询 设备表  
					EquipInfoQB edt = new EquipInfoQB();
					edt.setId(equipSensorsGhouse.getEquipInfoId());
					List<EquipInfo> edtInfo = equipInfoService.getEquipInfos(edt);
					if(null!=edtInfo&&edtInfo.size()>0){
						for(EquipInfo equipInfo : edtInfo) {
							if(EquipDataTypeCommon.node.equals(equipInfo.getType())){
								baseGreenHouseTree = new BaseGreenHouseTree();
								baseGreenHouseTree.setNodeTreeId(equipInfo.getId());
								baseGreenHouseTree.setNodeTreeName(equipInfo.getName());
								baseGreenHouseTree.setParent(false);
								baseGreenHouseTree.setLevelTree("3");
								baseGreenHouseTree.setType(Integer.parseInt(equipInfo.getType()));
								baseGreenHouseTreeList.add(baseGreenHouseTree);
							}
							/*if(EquipDataTypeCommon.controller.equals(equipInfo.getType())){
								baseGreenHouseTree = new BaseGreenHouseTree();
								baseGreenHouseTree.setNodeTreeId(equipInfo.getId());
								baseGreenHouseTree.setNodeTreeName(equipInfo.getName());
								baseGreenHouseTree.setParent(false);
								baseGreenHouseTree.setLevelTree("3");
								baseGreenHouseTree.setType(Integer.parseInt(equipInfo.getType()));
								baseGreenHouseTreeList.add(baseGreenHouseTree);
							}*/
								
						}
					}
				}
			}
		}else if(levelTree.equals("3")){
			baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		}
		return "findBaseGreenTree";
		
	}
	
	
	public List<BaseGreenHouseTree> GetBaseGreenHouseTree() {
		HttpServletRequest request = ServletActionContext.getRequest();
		BaseQB baseqeury = new BaseQB();
//		baseqeury.setCustId(Long.parseLong(((SysUser)request.getSession().getAttribute("LOGIN_USER")).getLoginUserId()));
		List<Base> baseAll = baseService.getBases(baseqeury);
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
					baseGreenHouseTree.setIsEquip("1");
					baseGreenHouseTreeList.add(baseGreenHouseTree);
					baseGreenHouseTree = null;
					//得到 温室下面的 设备编号 如果类型类型为节点的 设备编号
					EquipSensorsGhouseQB esgQb= new EquipSensorsGhouseQB();
					esgQb.setHouseId(baseGreenHouseInfo.getId());
					List<EquipSensorsGhouse> esgsinfo = equipSensorsGhouseService.getEquipSensorsGhouses(esgQb);
					if(null!=esgsinfo&&esgsinfo.size()>0){
						for(EquipSensorsGhouse equipSensorsGhouse : esgsinfo) {
							//查询 设备表  
							EquipInfoQB edt = new EquipInfoQB();
							edt.setId(equipSensorsGhouse.getEquipInfoId());
							List<EquipInfo> edtInfo = equipInfoService.getEquipInfos(edt);
							if(null!=edtInfo&&edtInfo.size()>0){
								for(EquipInfo equipInfo : edtInfo) {
									if(EquipDataTypeCommon.node.equals(equipInfo.getType())){
										baseGreenHouseTree = new BaseGreenHouseTree();
										baseGreenHouseTree.setNodeTreeId(equipInfo.getId());
										baseGreenHouseTree.setNodeTreeName(equipInfo.getName());
										baseGreenHouseTree.setParentId(baseGreenHouseInfo.getId());
										baseGreenHouseTree.setType(Integer.parseInt(equipInfo.getType()));
										baseGreenHouseTreeList.add(baseGreenHouseTree);
										baseGreenHouseTree = null;
									}
									if(EquipDataTypeCommon.controller.equals(equipInfo.getType())){
										baseGreenHouseTree = new BaseGreenHouseTree();
										baseGreenHouseTree.setNodeTreeId(equipInfo.getId());
										baseGreenHouseTree.setNodeTreeName(equipInfo.getName());
										baseGreenHouseTree.setParentId(baseGreenHouseInfo.getId());
										baseGreenHouseTree.setType(Integer.parseInt(equipInfo.getType()));
										baseGreenHouseTreeList.add(baseGreenHouseTree);
										baseGreenHouseTree = null;
									}
										
								}
							}
						}
					}
				}

			}

		}
		return baseGreenHouseTreeList;

	}
	
	public String getGreenHouseTreeData() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("nodeTreeId");
		String isVirtualNode = request.getParameter("isVirtualNode");
		List<BaseGreenHouseTree> baseGreenHouseTreeList = new ArrayList<BaseGreenHouseTree>();
		BaseGreenHouseTree baseGreenHouseTree = new BaseGreenHouseTree();
		if (StringUtils.isBlank(id)) {// 根节点
			// 添加根基地
			List<Base> baseAll = baseService.getAllBases();
			for (Base base : baseAll) {
				// 添加基地
				baseGreenHouseTree = new BaseGreenHouseTree();
				baseGreenHouseTree.setNodeTreeId(base.getId());
				baseGreenHouseTree.setNodeTreeName(base.getName());
				baseGreenHouseTree.setParentId(0l);
				baseGreenHouseTreeList.add(baseGreenHouseTree);
			}
		}
		else {
			if ("0".equals(id)) {
				//查温室
				BaseGreenHouseInfoQB baseGreenHouseInfoQB = new BaseGreenHouseInfoQB();
				baseGreenHouseInfoQB.setBaseId(Long.parseLong(id));
				List<BaseGreenHouseInfo> greenHouseAll = baseGreenHouseInfoService.getBaseGreenHouseInfos(baseGreenHouseInfoQB);
				if (greenHouseAll != null) {
					for (BaseGreenHouseInfo baseGreenHouseInfo : greenHouseAll) {
						// 添加温室信息
						baseGreenHouseTree = new BaseGreenHouseTree();
						baseGreenHouseTree.setNodeTreeId(baseGreenHouseInfo.getId());
						baseGreenHouseTree.setNodeTreeName(baseGreenHouseInfo.getName());
						baseGreenHouseTree.setParentId(Long.parseLong(id));
						baseGreenHouseTree.setIsEquip("1");
						baseGreenHouseTreeList.add(baseGreenHouseTree);
						baseGreenHouseTree = null;
					}
				}
				
			}else {
				if (!"1".equals(isVirtualNode)) {
					// 拼查询条件
					GreenHouseQB greenHouseQB = new GreenHouseQB();
					greenHouseQB.setBaseId(Long.parseLong(id));
					List<GreenHouse> greenHouseAll = greenHouseService.getGreenHouses(greenHouseQB);
					if (greenHouseAll != null) {

						for (GreenHouse greenHouse : greenHouseAll) {
							// 添加温室信息
							baseGreenHouseTree = new BaseGreenHouseTree();
							baseGreenHouseTree.setNodeTreeId(greenHouse.getId());
							baseGreenHouseTree.setNodeTreeName(greenHouse.getName());
							baseGreenHouseTree.setParentId(Long.parseLong(id));
							baseGreenHouseTree.setIsVirtualNode("1");
							baseGreenHouseTreeList.add(baseGreenHouseTree);
						}

					}
				}
		
			}

		}
		sysOrgList = baseGreenHouseTreeList;
		return "getSysOrgData";

	}
	public String getEquipData(){
		
		return "getEquipData";
	}
	public String testAjax(){
		try{
			
			EquipDataEnvdataQB equipDataEnvdataQB = new EquipDataEnvdataQB();
			equipDataEnvdataQB.setEquipDataTypeId(Long.parseLong(tid));//未添加类别
			Date nD = new Date();						//获取当前时间
			Long hmiao = nD.getTime()-3600000*hour;		//当前时间毫秒减去hour小时的毫秒
			Date pmiao = new Date(hmiao);				//将前hour小时的时间转换成毫秒
			equipDataEnvdataQB.setCtime(pmiao);
			List<EquipDataEnvdata> equipDataEnvdataList = equipDataEnvdataService.getEquipDataEnvdatas(equipDataEnvdataQB);
			if(equipDataEnvdataList.size()==0){
				EquipDataEnvdata ede = new EquipDataEnvdata();
				ede.setCtime(new Date());
				ede.setValue(0.0f);
				ede.setDataId(1L);
				ede.setEquipDataTypeId(8L);//未添加类别
				equipDataEnvdataList.add(ede);
			}
			float[] d = new float[equipDataEnvdataList.size()+1];
			for(int i=0;i<d.length;i++){
				d[i]=0.0f;
			}
			String[] xZ = new String[equipDataEnvdataList.size()+1];
			for(int i=0;i<xZ.length;i++){
				xZ[i]="-";
			}
			EquipDataEnvdata equipDataMM = null;
			EquipDataEnvdataQB equipDataEnvdataMMQB = new EquipDataEnvdataQB();
			equipDataEnvdataQB.setEquipDataTypeId(10L);
			List<EquipDataEnvdata> maxMinDataList = equipDataEnvdataService.getMaxMinData(equipDataEnvdataMMQB);
			for(EquipDataEnvdata edev : maxMinDataList){
				equipDataMM = edev;
			}
			if(equipDataEnvdataList.size()!=0){
				
				int i=0;
				int lastM = 0;
				for(EquipDataEnvdata ede : equipDataEnvdataList){
					
					System.out.println(ede.getValue()+"---"+ede.getCtime());
					Date nowDate = new Date();
					Long f = nowDate.getTime()-ede.getCtime().getTime();
					System.out.println(f);
					long ht = f/3600000;
					String timeString="";
					if(ht==0) timeString = (f%3600000)/60000+"分";
					else timeString = f/3600000+"小时"+(f%3600000)/60000+"分";
					System.out.println(f/3600000+"小时");
					System.out.println((f%3600000)/60000+"分");
					
					//xZ[equipDataEnvdataList.size()-i]=timeString;
					lastM=i;
					
					d[i]=ede.getValue();
					d[0]=d[1];
//					d[0]=0;
//					d[i]=ede.getValue();
					
					i++;
				
				}
				xZ[equipDataEnvdataList.size()]="现在";
				//xZ[equipDataEnvdataList.size()]="现在";
		/*		for(int k=0;k<d.length;k++){
					System.out.print(d[k]+",");
				}
				System.out.println("-------------------------------");
				for(int l = 0;l<xZ.length;l++){
					System.out.print(xZ[l]+",");
				}*/
				
				testArrayD=JSONArray.fromObject(d).toString();
				testArrayX=JSONArray.fromObject(xZ).toString();
				equipMM = JSONArray.fromObject(equipDataMM).toString();
			}else{
				testArrayD=JSONArray.fromObject(d).toString();
				testArrayX=JSONArray.fromObject(xZ).toString();
				equipDataMM.setMax(10F);
				equipDataMM.setMin(0F);
				equipMM = JSONArray.fromObject(equipDataMM).toString();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "testAjax";
	}
	public String getEquipDataInfo(){
		try {
			System.out.println(tid);
			if(pqb == null){
				pqb = new PagingQueryBean<EquipDataEnvdataQB>();
				PagingInfo pageInfo = new PagingInfo();
				pageInfo.setPageNum(1);
				pageInfo.setPageRows(10);
				pqb.setPagingInfo(pageInfo);
			}
			EquipDataEnvdataQB edQB = new EquipDataEnvdataQB();
			edQB.setEquipDataTypeId(Long.parseLong(tid));
			Date nD = new Date();						//获取当前时间
			Long hmiao = nD.getTime()-3600000*hour;		//当前时间毫秒减去hour小时的毫秒
			Date pmiao = new Date(hmiao);				//将前hour小时的时间转换成毫秒
			edQB.setCtime(pmiao);
			pqb.setQueryBean(edQB);
			this.DataInfoResult = equipDataEnvdataService.getAllEquipDataEnvdatasPaging(pqb);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "getEquipDataInfo";
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

	public PagingQueryBean<PrdcSeasonQB> getCqb() {
		return cqb;
	}

	public void setCqb(PagingQueryBean<PrdcSeasonQB> cqb) {
		this.cqb = cqb;
	}

	public ProduceThresholdInfoService getProduceThresholdInfoService() {
		return produceThresholdInfoService;
	}

	public void setProduceThresholdInfoService(
			ProduceThresholdInfoService produceThresholdInfoService) {
		this.produceThresholdInfoService = produceThresholdInfoService;
	}

	public List<ProduceThresholdInfo> getPtilist() {
		return ptilist;
	}

	public void setPtilist(List<ProduceThresholdInfo> ptilist) {
		this.ptilist = ptilist;
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

	public EquipSensorsGhouseService getEquipSensorsGhouseService() {
		return equipSensorsGhouseService;
	}

	public void setEquipSensorsGhouseService(
			EquipSensorsGhouseService equipSensorsGhouseService) {
		this.equipSensorsGhouseService = equipSensorsGhouseService;
	}

	public EquipDataTypeService getEquipDataTypeService() {
		return equipDataTypeService;
	}

	public void setEquipDataTypeService(EquipDataTypeService equipDataTypeService) {
		this.equipDataTypeService = equipDataTypeService;
	}

	public EquipDataEnvdataService getEquipDataEnvdataService() {
		return equipDataEnvdataService;
	}

	public void setEquipDataEnvdataService(
			EquipDataEnvdataService equipDataEnvdataService) {
		this.equipDataEnvdataService = equipDataEnvdataService;
	}

	public EquipInfoService getEquipInfoService() {
		return equipInfoService;
	}

	public void setEquipInfoService(EquipInfoService equipInfoService) {
		this.equipInfoService = equipInfoService;
	}

	public JSONArray getEquipDataEnvdataTypeInfoList() {
		return equipDataEnvdataTypeInfoList;
	}

	public void setEquipDataEnvdataTypeInfoList(
			JSONArray equipDataEnvdataTypeInfoList) {
		this.equipDataEnvdataTypeInfoList = equipDataEnvdataTypeInfoList;
	}

	public List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo() {
		return equipDataEnvdataTypeInfo;
	}

	public void setEquipDataEnvdataTypeInfo(
			List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo) {
		this.equipDataEnvdataTypeInfo = equipDataEnvdataTypeInfo;
	}

	public List<EquipControlInfo> getEquipControlInfoList() {
		return equipControlInfoList;
	}

	public void setEquipControlInfoList(List<EquipControlInfo> equipControlInfoList) {
		this.equipControlInfoList = equipControlInfoList;
	}
 
	 
	public void setGreenHouseService(GreenHouseService greenHouseService) {
		this.greenHouseService = greenHouseService;
	}

 
	public void setSysOrgList(List<BaseGreenHouseTree> sysOrgList) {
		this.sysOrgList = sysOrgList;
	}
 
	public EquipNetTopologyService getEquipNetTopologyService() {
		return equipNetTopologyService;
	}

	public void setEquipNetTopologyService(
			EquipNetTopologyService equipNetTopologyService) {
		this.equipNetTopologyService = equipNetTopologyService;
	}

	public Long getNodeTreeId() {
		return nodeTreeId;
	}

	public void setNodeTreeId(Long nodeTreeId) {
		this.nodeTreeId = nodeTreeId;
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

	public String getTestArrayD() {
		return testArrayD;
	}

	public void setTestArrayD(String testArrayD) {
		this.testArrayD = testArrayD;
	}

	public String getTestArrayX() {
		return testArrayX;
	}

	public GreenHouseService getGreenHouseService() {
		return greenHouseService;
	}

	public List<BaseGreenHouseTree> getSysOrgList() {
		return sysOrgList;
	}

	public void setTestArrayX(String testArrayX) {
		this.testArrayX = testArrayX;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public PagingQueryBean<EquipDataEnvdataQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<EquipDataEnvdataQB> pqb) {
		this.pqb = pqb;
	}

	public PagingResultBean<List<EquipDataEnvdata>> getDataInfoResult() {
		return DataInfoResult;
	}

	public void setDataInfoResult(
			PagingResultBean<List<EquipDataEnvdata>> dataInfoResult) {
		DataInfoResult = dataInfoResult;
	}

	public EquipDataEnvdataQB getEquipDataEnvdataQB() {
		return equipDataEnvdataQB;
	}

	public void setEquipDataEnvdataQB(EquipDataEnvdataQB equipDataEnvdataQB) {
		this.equipDataEnvdataQB = equipDataEnvdataQB;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

 
 
	public List<EquipDateTypeListInfo> getEdataETypeInfo() {
		return edataETypeInfo;
	}

	public void setEdataETypeInfo(List<EquipDateTypeListInfo> edataETypeInfo) {
		this.edataETypeInfo = edataETypeInfo;
	}

	public List<EquipControlList> getEquipControlList() {
		return equipControlList;
	}

	public void setEquipControlList(List<EquipControlList> equipControlList) {
		this.equipControlList = equipControlList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getEquipMM() {
		return equipMM;
	}

	public void setEquipMM(String equipMM) {
		this.equipMM = equipMM;
	}

	public PagingQueryBean<BaseGreenHouseInfoQB> getPqbi() {
		return pqbi;
	}

	public void setPqbi(PagingQueryBean<BaseGreenHouseInfoQB> pqbi) {
		this.pqbi = pqbi;
	}

	public BaseGreenHouseInfoQB getBaseGreenHouseInfoQB() {
		return baseGreenHouseInfoQB;
	}

	public void setBaseGreenHouseInfoQB(BaseGreenHouseInfoQB baseGreenHouseInfoQB) {
		this.baseGreenHouseInfoQB = baseGreenHouseInfoQB;
	}

	public PagingResultBean<List<BaseGreenHouseInfo>> getBaseGreenHouseInfoResult() {
		return baseGreenHouseInfoResult;
	}

	public void setBaseGreenHouseInfoResult(
			PagingResultBean<List<BaseGreenHouseInfo>> baseGreenHouseInfoResult) {
		this.baseGreenHouseInfoResult = baseGreenHouseInfoResult;
	}

	public PagingResultBean<List<BaseGreenHouseInfoList>> getBaseInfoResultList() {
		return baseInfoResultList;
	}

	public void setBaseInfoResultList(
			PagingResultBean<List<BaseGreenHouseInfoList>> baseInfoResultList) {
		this.baseInfoResultList = baseInfoResultList;
	}

	public PagingQueryBean<EquipControlInfoHQB> getEcpqb() {
		return ecpqb;
	}

	public void setEcpqb(PagingQueryBean<EquipControlInfoHQB> ecpqb) {
		this.ecpqb = ecpqb;
	}

	public PagingResultBean<List<EquipControlInfoH>> getEconInfoHResult() {
		return econInfoHResult;
	}

	public void setEconInfoHResult(
			PagingResultBean<List<EquipControlInfoH>> econInfoHResult) {
		this.econInfoHResult = econInfoHResult;
	}

	public EquipControlInfoHService getEquipControlInfoHService() {
		return equipControlInfoHService;
	}

	public void setEquipControlInfoHService(
			EquipControlInfoHService equipControlInfoHService) {
		this.equipControlInfoHService = equipControlInfoHService;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getCrops() {
		return crops;
	}

	public void setCrops(String crops) {
		this.crops = crops;
	}

	public Map<String, String> getListBase() {
		return listBase;
	}

	public void setListBase(Map<String, String> listBase) {
		this.listBase = listBase;
	}

	 
 
}
