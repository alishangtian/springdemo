package com.cattsoft.ny.base.web;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.CropsResult;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.cropAmount;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.entity.querybean.PickWorkInfoDataQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
import com.cattsoft.ny.base.service.BaseService;
import com.cattsoft.ny.base.service.PickWorksInfoService;
import com.cattsoft.ny.base.service.PrdcSeasonService;

public class StatisticsAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PickWorksInfoService pickWorksInfoService;
	private PickWorksInfo pickWorksInfo;
	private String beginTime;
	private String endTime;
	private List<PickWorksInfo> pickWorksInfoList;
	private Base base;
	private BaseService baseService;
	private List<Base> allBaseList;
	private String jsonData;
	private BaseGreenHouseInfoService baseGreenHouseInfoService;
	private PrdcSeasonService prdcSeasonService;
	private String prdcSeasonListJson;
	private String cropAmountJson;
	private String id;
	private String houseCropJson;
	private String hl;
	private String cl;

	
	public String execute() {
		try{
			BaseQB baseQB = new BaseQB();
			baseQB.setState("0");
			allBaseList = baseService.getBases(baseQB);
			
			HttpServletRequest request = ServletActionContext.getRequest();
			

			request.setAttribute("begin_Time", beginTime);
			request.setAttribute("end_Time", endTime);
		}catch(Exception e){
		}
	
		return "jumpstatisticsAction";
	}
	
	public String queryPickData(){
		return "queryPickData";
	}
	
	@SuppressWarnings("null")
	public String getDateForStatistics(){
		Long baseId = null;
		PickWorkInfoDataQB pickWorkInfoDataQB = new PickWorkInfoDataQB();
		pickWorkInfoDataQB.setId(null);
		System.out.println("beginTime:"+beginTime);
		System.out.println("endTime:"+endTime);
		try{
			Calendar c = Calendar.getInstance(); 
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime));
			Date beginDate = new Date(c.getTimeInMillis()); 
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			Date endDate = new Date(c.getTimeInMillis()); 
			pickWorkInfoDataQB.setBeginTime(beginDate);
			pickWorkInfoDataQB.setEndTime(endDate);	
		}catch(Exception e){
		}
		List<CropsResult> cr = pickWorksInfoService.queryCropsResult(pickWorkInfoDataQB);
		for(CropsResult d : cr){
			System.out.println(d.getCrops());
		}
		
		Test(cr);
		
		

		return "getDateForStatistics";
	}
	
	public void Test(List<CropsResult> cr){
		//查询所有基地
		BaseQB baseQB = new BaseQB();
		baseQB.setState("0");
		allBaseList = baseService.getBases(baseQB);
		JSONArray json = JSONArray.fromObject(allBaseList);
		jsonData=json.toString();
	//查询所有作物
		List<PrdcSeason> allPrdcSeasonList = prdcSeasonService.getAllPrdcSeasons();
		prdcSeasonListJson=JSONArray.fromObject(allPrdcSeasonList).toString();
		
		
		List<cropAmount> cropAmountList = new ArrayList<cropAmount>();		
		for(CropsResult sresult : cr){
			cropAmount ca = new cropAmount();
			ca.setName(sresult.getCrops());
			double[] d=new double[5];	
			int i=0;
				for(Base base : allBaseList){
					if(base.getName().equals(sresult.getBaseName())){
						if(sresult.getAmounts()==null){
							d[i]=0.0;
							continue;
						}
						d[i]=sresult.getAmounts();	
					}
					i++;
			}
			ca.setData(d);
			ca.setType("bar");
			cropAmountList.add(ca);		
		}
		cropAmountJson=JSONArray.fromObject(cropAmountList).toString();
	}
	
	public String getGreenHouseDateForStatistics(){
		try{
			PickWorkInfoDataQB pickWorkInfoDataQB = new PickWorkInfoDataQB();
			pickWorkInfoDataQB.setId(Long.parseLong(id));
			try{
				Calendar c = Calendar.getInstance(); 
				c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime));
				Date beginDate = new Date(c.getTimeInMillis()); 
				c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
				Date endDate = new Date(c.getTimeInMillis()); 
				pickWorkInfoDataQB.setBeginTime(beginDate);
				pickWorkInfoDataQB.setEndTime(endDate);	
			}catch(Exception e){
			}
			List<CropsResult> cr = pickWorksInfoService.queryCropsResult(pickWorkInfoDataQB);
			List<String> houseList = new ArrayList<String>();
			List<String> cropsList = new ArrayList<String>();
			List<cropAmount> cropAmountList = new ArrayList<cropAmount>();
			for(CropsResult cre : cr){
				if(cre.getBaseName()!=null&&cre.getCrops()!=null){
					houseList.add(cre.getHouseName());
					cropsList.add(cre.getCrops());
				}
				
			}
			HashSet hSeth  =   new  HashSet(houseList);
			HashSet hSetc  =   new  HashSet(cropsList);
			houseList.clear();
			cropsList.clear();
			houseList.addAll(hSeth);
			cropsList.addAll(hSetc);
			hl = JSONArray.fromObject(houseList).toString();
			cl = JSONArray.fromObject(cropsList).toString();
			for(CropsResult cre : cr){
				
				cropAmount ca = new cropAmount();
				ca.setName(cre.getCrops());
				double[] d=new double[houseList.size()];	
				int i=0;
					for(String house : houseList){
						if(house.equals(cre.getHouseName())){
							if(cre.getAmounts()==null){
								d[i]=0.0;
								continue;
							}
							d[i]=cre.getAmounts();	
						}
						i++;
				}
				ca.setData(d);
				ca.setType("bar");
				cropAmountList.add(ca);
			}
			
			houseCropJson=JSONArray.fromObject(cropAmountList).toString();
		}catch(Exception e){
		}
		return "getGreenHouseDateForStatistics";
	}
	

	public PickWorksInfoService getPickWorksInfoService() {
		return pickWorksInfoService;
	}

	public void setPickWorksInfoService(PickWorksInfoService pickWorksInfoService) {
		this.pickWorksInfoService = pickWorksInfoService;
	}
	
	public PickWorksInfo getPickWorksInfo() {
		return pickWorksInfo;
	}

	public void setPickWorksInfo(PickWorksInfo pickWorksInfo) {
		this.pickWorksInfo = pickWorksInfo;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<PickWorksInfo> getPickWorksInfoList() {
		return pickWorksInfoList;
	}

	public void setPickWorksInfoList(List<PickWorksInfo> pickWorksInfoList) {
		this.pickWorksInfoList = pickWorksInfoList;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public List<Base> getAllBaseList() {
		return allBaseList;
	}

	public void setAllBaseList(List<Base> allBaseList) {
		this.allBaseList = allBaseList;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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

	public String getPrdcSeasonListJson() {
		return prdcSeasonListJson;
	}

	public void setPrdcSeasonListJson(String prdcSeasonListJson) {
		this.prdcSeasonListJson = prdcSeasonListJson;
	}

	public String getCropAmountJson() {
		return cropAmountJson;
	}

	public void setCropAmountJson(String cropAmountJson) {
		this.cropAmountJson = cropAmountJson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseCropJson() {
		return houseCropJson;
	}

	public void setHouseCropJson(String houseCropJson) {
		this.houseCropJson = houseCropJson;
	}

	public String getHl() {
		return hl;
	}

	public void setHl(String hl) {
		this.hl = hl;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}


	
}
