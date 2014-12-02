package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.PageComponent;

/**
 * PAGE_COMPONENT
 * 
 * @author wangcl
 */
public interface PageComponentMapper {
	void insert(PageComponent pageComponent);

	void delete(Integer pageComponentId);

	void update(PageComponent pageComponent);

	PageComponent select(Integer pageComponentId);

	List<PageComponent> selectAll();
}
