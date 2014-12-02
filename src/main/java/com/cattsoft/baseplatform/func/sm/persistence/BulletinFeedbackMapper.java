package com.cattsoft.baseplatform.func.sm.persistence;

import com.cattsoft.baseplatform.func.sm.entity.BulletinFeedback;

/**
 * 公告发布结果反馈映射类
 */
public interface BulletinFeedbackMapper {
	/**
	 * 新增公告发布结果反馈信息
	 * @param bulletinFeedback 公告发布结果反馈信息
	 */
	void insert(BulletinFeedback bulletinFeedback);
	/**
	 * 查看公告发布结果反馈信息
	 * @param bulletinFeedback 公告发布结果反馈信息
	 * @return BulletinFeedback 公告发布结果反馈信息
	 */
	BulletinFeedback selectByFeedback(BulletinFeedback bulletinFeedback);
	/**
	 * 删除公告发布结果反馈信息
	 * @param bulletinFeedback 公告发布结果反馈信息
	 */
	void deleteByFeedback(BulletinFeedback bulletinFeedback);
}
