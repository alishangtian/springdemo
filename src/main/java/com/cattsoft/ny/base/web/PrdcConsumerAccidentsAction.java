package com.cattsoft.ny.base.web;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.PrdcConsumerAccidents;
import com.cattsoft.ny.base.entity.querybean.PrdcConsumerAccidentsQB;
import com.cattsoft.ny.base.service.PrdcConsumerAccidentsService;

public class PrdcConsumerAccidentsAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private PrdcConsumerAccidentsQB pcaQB;
	private PagingQueryBean<PrdcConsumerAccidentsQB> pqb;
	private PagingResultBean<List<PrdcConsumerAccidents>> prdcConsAccResult;
	private PrdcConsumerAccidentsService  prdcConsAccService;
	public String execute() {
		return "prdcConsumerAccidentsInfo";
	}
	
	/**
	 * 查询
	 */
	public String  selectPrdcConsAccInfo(){
		try{
		if(pqb == null){
			pqb = new PagingQueryBean<PrdcConsumerAccidentsQB>();
			PagingInfo pageInfo = new PagingInfo();
			pageInfo.setPageNum(1);
			pageInfo.setPageRows(8);
			pqb.setPagingInfo(pageInfo);
		}
		if(pcaQB == null){
			pqb.setQueryBean(new PrdcConsumerAccidentsQB());
		}else{
			pcaQB.setState(pcaQB.getState().equals("")?null:pcaQB.getState());
			pqb.setQueryBean(pcaQB);
		}
		this.prdcConsAccResult = prdcConsAccService.getAllPrdcConsumerAccidentssPaging(pqb);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return "selectPrdcConsAccInfo";
	}
	/**
	 * 弹出新增页面
	 * @return
	 */
	public String createPrdcConsAccInfo(){
		return "createPrdcConsAccInfo";
	}
	

	public PrdcConsumerAccidentsQB getPcaQB() {
		return pcaQB;
	}

	public void setPcaQB(PrdcConsumerAccidentsQB pcaQB) {
		this.pcaQB = pcaQB;
	}

	public PagingQueryBean<PrdcConsumerAccidentsQB> getPqb() {
		return pqb;
	}

	public void setPqb(PagingQueryBean<PrdcConsumerAccidentsQB> pqb) {
		this.pqb = pqb;
	}

	public PagingResultBean<List<PrdcConsumerAccidents>> getPrdcConsAccResult() {
		return prdcConsAccResult;
	}

	public void setPrdcConsAccResult(
			PagingResultBean<List<PrdcConsumerAccidents>> prdcConsAccResult) {
		this.prdcConsAccResult = prdcConsAccResult;
	}

	public PrdcConsumerAccidentsService getPrdcConsAccService() {
		return prdcConsAccService;
	}

	public void setPrdcConsAccService(
			PrdcConsumerAccidentsService prdcConsAccService) {
		this.prdcConsAccService = prdcConsAccService;
	}
	
	
	
}
