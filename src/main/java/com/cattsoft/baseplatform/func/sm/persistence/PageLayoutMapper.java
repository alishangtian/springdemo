package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.PageLayout;

/**
 * PAGE_LAYOUT
 * 
 * @author wangcl
 */
public interface PageLayoutMapper {
	void insert(PageLayout pageLayout);

	void delete(Integer pageLayoutId);

	void update(PageLayout pageLayout);

	PageLayout select(Integer pageLayoutId);

	List<PageLayout> selectAll();
}
