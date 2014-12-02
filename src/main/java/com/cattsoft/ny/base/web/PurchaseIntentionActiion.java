package com.cattsoft.ny.base.web;

public class PurchaseIntentionActiion extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String execute(){
		return "jumpPurchaseIntention";
	}
	public String viewPurchaseIntention(){
		
		return "viewPurchaseIntention";
	}
}
