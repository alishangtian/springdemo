/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.SeaboMxs1402aResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1402aResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface SeaboMxs1402aResultLMapper {
	void insert(SeaboMxs1402aResultL seaboMxs1402aResultL);

	void delete(Long nodeid);

	void update(SeaboMxs1402aResultL seaboMxs1402aResultL);

	SeaboMxs1402aResultL select(Long nodeid);

	List<SeaboMxs1402aResultL> selectList(SeaboMxs1402aResultLQB queryBean);

	List<SeaboMxs1402aResultL> selectPage(PagingQueryBean<SeaboMxs1402aResultLQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<SeaboMxs1402aResultLQB> pagingQueryBean);

	Object proc(SeaboMxs1402aResultL seaboMxs1402aResultL);
	
}