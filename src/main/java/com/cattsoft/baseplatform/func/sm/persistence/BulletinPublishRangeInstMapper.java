package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.BulletinPublishRangeInst;

/**
 * 公告信息发布范围映射类
 */
public interface BulletinPublishRangeInstMapper {
	/**
	 * 新增公告信息发布范围信息
	 * @param bulletinPublishRangeInst 公告信息发布范围信息
	 */
	void insert(BulletinPublishRangeInst bulletinPublishRangeInst);
	/**
	 * 查看公告信息发布范围信息
	 * @param bulletinId 公告信息id
	 * @return List<BulletinPublishRangeInst> 公告信息发布范围信息集合
	 */
	List<BulletinPublishRangeInst> selectByBulletinId(Long bulletinId);
	/**
	 * 删除公告信息发布范围信息
	 * @param bulletinId 公告信息id
	 */
	void deleteByBulletinId(long bulletinId);
	/**
	 * 修改状态删除公告信息发布范围信息
	 * @param bulletinId 公告信息id
	 */
	void deleteBySts(long bulletinId);
}
