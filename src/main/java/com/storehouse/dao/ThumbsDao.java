package com.storehouse.dao;

import com.storehouse.entity.Thumbs;

import java.util.List;

/**
 * 创建点赞相关接口
 */
public interface ThumbsDao {
    /**
     * 添加
     *
     * @param thumbs 实体类对象
     * @return 受影响的行数
     */
    int addThumbs(Thumbs thumbs);

    /**
     * 删除
     *
     * @param thumbsId 根据id编号删除
     * @return 返回受影响的行数
     */
    int delThumbs(int thumbsId);

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    int updateThumbs(Thumbs thumbs);

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    List<Thumbs> queryThumbs();

    /**
     * 查询是否存在对应uid与文章id的点赞记录
     * @param uid 用户id
     * @param articleId 文章id
     * @return 返回数量
     */
    int ThumbsByUidAndArticleId(int uid,int articleId);
}
