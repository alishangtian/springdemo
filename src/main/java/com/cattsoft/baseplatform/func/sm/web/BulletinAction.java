package com.cattsoft.baseplatform.func.sm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.BulletinQB;
import com.cattsoft.baseplatform.func.sm.service.BulletinService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.CrudAction;

/**
 * 公告操作类
 */
public class BulletinAction extends CrudAction<Bulletin> {
	private static final long serialVersionUID = -2572173029598613352L;
	@Autowired
	private BulletinService bulletinService;
	private Bulletin bulletin;
	private PagingQueryBean<BulletinQB> pqb = new PagingQueryBean<BulletinQB>();
	private PagingResultBean<List<Bulletin>> prb;
	private List<Bulletin> ownerBulletinList;
	private String rangeStr;
	private String viewRangeStr;
	private Long id;
	private Boolean flag;
	private String msg;
	private String publishTime;
	private String revokeTime;
	private String keyword;
	private String bulletinTitle;
	private String bulletinContentStr;
	private String priority;

	/**
	 * 构造函数
	 */
	public BulletinAction() {
		BulletinQB qb = new BulletinQB();
		pqb.setQueryBean(qb);
		PagingInfo pinfo = new PagingInfo();
		pqb.setPagingInfo(pinfo);
	}

	/**
	 * 显示新增界面
	 */
	@Override
	public String prepareCreate() throws Exception {
		return "prepareCreate";
	}

	/**
	 * 显示发布界面
	 */
	public String preparePublish() throws Exception {
		return "preparePublish";
	}

	/**
	 * 显示部门界面
	 */
	public String prepareDept() throws Exception {
		return "prepareDept";
	}

	/**
	 * 显示角色界面
	 */
	public String prepareRole() throws Exception {
		return "prepareRole";
	}

	/**
	 * 显示用户界面
	 */
	public String prepareUser() throws Exception {
		return "prepareUser";
	}

	/**
	 * 显示变更界面
	 */
	@Override
	public String prepareUpdate() {
		return "prepareUpdate";
	}

	/**
	 * 显示编辑界面
	 */
	public String prepareEdit() {
		this.bulletin = this.bulletinService.getBulletin(this.id);
		return "prepareEdit";
	}

	/**
	 * 首页显示查看界面
	 */
	public String prepareView() {
		return "prepareView";
	}

	/**
	 * 新增操作
	 */
	@Override
	public String create() {
		if (null == this.bulletin) {
			this.bulletin = new Bulletin();
		}
		try {
			SysUser sysUser = (SysUser) getSession().get(
					SysConstants.LOGIN_USER);
			if (sysUser != null) {
				bulletin.setUserId(sysUser.getUserId());
			}
			this.bulletin.setBulletinTitle(this.bulletinTitle);
			this.bulletin.setKeyword(this.keyword);
			this.bulletin.setCreateTime(new Date());
			this.bulletin.setPriority(this.priority);
			this.bulletin.setPublishTime(formatDate(this.publishTime));
			this.bulletin.setRevokeTime(formatDate(this.revokeTime));
			this.bulletin.setSts(sysUser.getSts());
			this.bulletin.setStsTime(new Date());
			this.bulletin.setBulletinContent(this.bulletinContentStr);
			bulletinService.createBulletin(bulletin);
			this.flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.flag = false;
			this.msg = "新增失败";
		}
		return "ajaxsuccess";
	}

	/**
	 * 变更操作
	 */
	@Override
	public String update() throws Exception {
		try {
			SysUser sysUser = (SysUser) getSession().get(
					SysConstants.LOGIN_USER);
			if (sysUser != null) {
				bulletin.setUserId(sysUser.getUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT;
	}

	/**
	 * 编辑操作
	 */
	public String edit() throws Exception {
		try {
			this.initBulletinRangeByRangeStr();
			SysUser sysUser = (SysUser) getSession().get(
					SysConstants.LOGIN_USER);
			if (sysUser != null) {
				bulletin.setUserId(sysUser.getUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT;
	}

	/**
	 * 查看操作
	 */
	@Override
	public String view() throws Exception {
		this.bulletin = this.bulletinService.getBulletin(this.id);
		return VIEW;
	}

	/**
	 * 公告所有者查看操作
	 */
	public String ownerView() throws Exception {
		return "ownerView";
	}

	/**
	 * 公告所有者查看操作
	 */
	public String loginView() throws Exception {
		return "loginView";
	}

	/**
	 * 列表操作
	 */
	@Override
	public String list() throws Exception {
		if (StringUtils.isNotBlank(this.keyword)) {
			this.pqb.getQueryBean().setKeyword(this.keyword);
		}
		setPrb(bulletinService.getAllBulletinsPaging(pqb));
		for (Bulletin bulletin : this.prb.getResultList()) {
			if (StringUtils.isNotBlank(bulletin.getBulletinContent())) {
				bulletin.setBulletinContentStr(CommonUtil
						.removeHtmlTag(bulletin.getBulletinContent()));
			}
		}
		return SUCCESS;
	}

	/**
	 * 用户查看公告操作
	 */
	public String ownerList() throws Exception {
		return "ownerList";
	}

	/**
	 * 用户查看全部公告操作
	 */
	public String ownerAllList() throws Exception {
		return "ownerAllList";
	}

	/**
	 * 首页查看公告操作
	 */
	public String loginBulletinList() throws Exception {
		return SUCCESS;
	}

	/**
	 * 删除操作
	 */
	@Override
	public String remove() throws Exception {
		this.bulletinService.removeBulletin(this.id);
		return DEFAULT;
	}

	/**
	 * 默认操作跳到列表
	 */
	public String defaultList() throws Exception {
		return "bulletinList";
	}

	/**
	 * 初始化公告范围实体数据
	 */
	private void initBulletinRangeByRangeStr() {

	}

	@Override
	public Bulletin getModel() {
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PagingResultBean<List<Bulletin>> getPrb() {
		return prb;
	}

	public void setPrb(PagingResultBean<List<Bulletin>> prb) {
		this.prb = prb;
	}

	public String getRangeStr() {
		return rangeStr;
	}

	public void setRangeStr(String rangeStr) {
		this.rangeStr = rangeStr;
	}

	public String getViewRangeStr() {
		return viewRangeStr;
	}

	public void setViewRangeStr(String viewRangeStr) {
		this.viewRangeStr = viewRangeStr;
	}

	public List<Bulletin> getOwnerBulletinList() {
		return ownerBulletinList;
	}

	public void setOwnerBulletinList(List<Bulletin> ownerBulletinList) {
		this.ownerBulletinList = ownerBulletinList;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getRevokeTime() {
		return revokeTime;
	}

	public void setRevokeTime(String revokeTime) {
		this.revokeTime = revokeTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private Date formatDate(String dateString) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formater.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getBulletinTitle() {
		return bulletinTitle;
	}

	public void setBulletinTitle(String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}

	public String getBulletinContentStr() {
		return bulletinContentStr;
	}

	public void setBulletinContentStr(String bulletinContentStr) {
		this.bulletinContentStr = bulletinContentStr;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
