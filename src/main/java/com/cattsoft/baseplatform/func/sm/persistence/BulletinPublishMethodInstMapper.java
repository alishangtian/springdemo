package com.cattsoft.baseplatform.func.sm.persistence;

import com.cattsoft.baseplatform.func.sm.entity.BulletinPublishMethodInst;

/**
 * 公告信息发布方式映射类
 */
public interface BulletinPublishMethodInstMapper {
	/**
	 * 新增公告信息发布方式信息
	 * @param bulletinPublishMethodInst 公告信息发布方式信息
	 */
	void insert(BulletinPublishMethodInst bulletinPublishMethodInst);
	/**
	 * 修改公告信息发布方式信息
	 * @param bulletinPublishMethodInst 公告信息发布方式信息
	 */
	void update(BulletinPublishMethodInst bulletinPublishMethodInst);
	/**
	 * 查看公告信息发布方式信息
	 * @param bulletinId 公告信息id
	 * @return BulletinPublishMethodInst 公告信息发布方式信息
	 */
	BulletinPublishMethodInst selectByBulletinId(Long bulletinId);
	/**
	 * 删除公告信息发布方式信息
	 * @param bulletinId 公告信息id
	 */
	void deleteByBulletinId(long bulletinId);
	/**
	 * 修改状态删除公告信息发布方式信息
	 * @param bulletinId 公告信息id
	 */
	void deleteBySts(long bulletinId);
}
