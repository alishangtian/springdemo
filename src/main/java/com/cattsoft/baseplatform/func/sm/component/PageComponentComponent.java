package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.PageComponent;
import com.cattsoft.baseplatform.func.sm.persistence.PageComponentMapper;

/**
 * 用户主页定制：功能组件。
 * 
 * @author wangcl
 */
public class PageComponentComponent {
	private PageComponentMapper pageComponentMapper;

	public void setPageComponentMapper(PageComponentMapper pageComponentMapper) {
		this.pageComponentMapper = pageComponentMapper;
	}

	public Integer createPageComponent(PageComponent pageComponent) {
		pageComponentMapper.insert(pageComponent);
		return pageComponent.getPageComponentId();
	}

	public void updatePageComponent(PageComponent pageComponent) {
		pageComponentMapper.update(pageComponent);
	}

	public void removePageComponent(Integer pageComponentId) {
		pageComponentMapper.delete(pageComponentId);
	}

	public PageComponent getPageComponent(Integer pageComponentId) {
		return pageComponentMapper.select(pageComponentId);
	}

	public List<PageComponent> getAllPageComponents() {
		return pageComponentMapper.selectAll();
	}
}
