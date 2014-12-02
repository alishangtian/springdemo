package com.cattsoft.ny.base.web;

import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.service.BulletinService;

/**
 * 公告操作类
 */
public class BulletinAction extends BaseAction {
	private BulletinService bulletinService;
	private Bulletin bulletin;
	private Long bulletinId;
	private static final long serialVersionUID = 1L;

	public String viewBulletin() {
		this.bulletin = this.bulletinService.getBulletin(bulletinId);
		return "viewBulletin";
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public Long getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}
}
