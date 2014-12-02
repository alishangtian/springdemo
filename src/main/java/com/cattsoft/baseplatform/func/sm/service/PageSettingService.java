package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.PageComponent;
import com.cattsoft.baseplatform.func.sm.entity.PageLayout;
import com.cattsoft.baseplatform.func.sm.entity.UserPageSetting;

/**
 * 主页定制后台服务。
 * 
 * @author wangcl
 */
public interface PageSettingService {
	/**
	 * 获取所有功能组件的列表。
	 * 
	 * @return 所有的功能组件
	 */
	public List<PageComponent> getPageComponents();

	/**
	 * 获取所有页面布局的列表。
	 * 
	 * @return 所有的页面布局
	 */
	public List<PageLayout> getPageLayouts();

	/**
	 * 获取指定用户的主页设定。
	 * 
	 * @return 用户设定信息
	 */
	public UserPageSetting getPageSetting(Long userId);

	/**
	 * 保存用户主页设置。
	 * 
	 * @param userPageSetting
	 *            用户主页设置信息
	 */
	public void savePageSetting(UserPageSetting userPageSetting);

}
