package com.storehouse.dao;

import com.storehouse.entity.Comment;

import java.util.List;

/**
 * 用户评论相关接口
 */
public interface CommentDao {
    /**
     * 添加
     *
     * @param comment 实体类对象
     * @return 受影响的行数
     */
    int addComment(Comment comment);

    /**
     * 删除
     *
     * @param commentId 根据id编号删除
     * @return 返回受影响的行数
     */
    int delComment(int commentId);

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    int updateComment(Comment comment);

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    List<Comment> queryComment();

}
