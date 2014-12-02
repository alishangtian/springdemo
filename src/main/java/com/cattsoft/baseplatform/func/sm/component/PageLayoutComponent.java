package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.PageLayout;
import com.cattsoft.baseplatform.func.sm.persistence.PageLayoutMapper;

/**
 * 用户主页定制：页面布局。
 * 
 * @author wangcl
 */
public class PageLayoutComponent {
	private PageLayoutMapper pageLayoutMapper;

	public void setPageLayoutMapper(PageLayoutMapper pageLayoutMapper) {
		this.pageLayoutMapper = pageLayoutMapper;
	}

	public Integer createPageLayout(PageLayout pageLayout) {
		pageLayoutMapper.insert(pageLayout);
		return pageLayout.getPageLayoutId();
	}

	public void updatePageLayout(PageLayout pageLayout) {
		pageLayoutMapper.update(pageLayout);
	}

	public void removePageLayout(Integer pageLayoutId) {
		pageLayoutMapper.delete(pageLayoutId);
	}

	public PageLayout getPageLayout(Integer pageLayoutId) {
		return pageLayoutMapper.select(pageLayoutId);
	}

	public List<PageLayout> getAllPageLayouts() {
		return pageLayoutMapper.selectAll();
	}

}
