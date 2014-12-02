package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.exception.ServiceException;
import com.cattsoft.baseplatform.func.sm.component.PageComponentComponent;
import com.cattsoft.baseplatform.func.sm.component.PageLayoutComponent;
import com.cattsoft.baseplatform.func.sm.component.UserPageSettingComponent;
import com.cattsoft.baseplatform.func.sm.entity.PageComponent;
import com.cattsoft.baseplatform.func.sm.entity.PageLayout;
import com.cattsoft.baseplatform.func.sm.entity.UserPageSetting;

/**
 * 主页定制后台服务的实现。
 * 
 * @author wangcl
 */
@Transactional
public class PageSettingServiceImpl implements PageSettingService {
	private static final Long DEFAULT_USER_ID = 0L;

	private UserPageSettingComponent userPageSettingComponent;
	private PageLayoutComponent pageLayoutComponent;
	private PageComponentComponent pageComponentComponent;

	public void setUserPageSettingComponent(UserPageSettingComponent userPageSettingComponent) {
		this.userPageSettingComponent = userPageSettingComponent;
	}

	public void setPageLayoutComponent(PageLayoutComponent pageLayoutComponent) {
		this.pageLayoutComponent = pageLayoutComponent;
	}

	public void setPageComponentComponent(PageComponentComponent pageComponentComponent) {
		this.pageComponentComponent = pageComponentComponent;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PageComponent> getPageComponents() {
		return pageComponentComponent.getAllPageComponents();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PageLayout> getPageLayouts() {
		return pageLayoutComponent.getAllPageLayouts();
	}

	@Override
	@Transactional(readOnly = true)
	public UserPageSetting getPageSetting(Long userId) {
		UserPageSetting setting = userPageSettingComponent.getUserPageSetting(userId);
		if (setting == null) {
			setting = userPageSettingComponent.getUserPageSetting(DEFAULT_USER_ID);
		}
		return setting;
	}

	@Override
	public void savePageSetting(UserPageSetting userPageSetting) {
		if (userPageSetting == null || userPageSetting.getUserId() == null) {
			throw new ServiceException("[缺少用户设定参数]");
		}

		if (userPageSettingComponent.getUserPageSetting(userPageSetting.getUserId()) == null) {
			userPageSettingComponent.createUserPageSetting(userPageSetting);
		} else {
			userPageSettingComponent.updateUserPageSetting(userPageSetting);
		}
	}

}
