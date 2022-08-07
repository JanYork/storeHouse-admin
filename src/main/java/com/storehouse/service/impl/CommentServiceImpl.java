package com.storehouse.service.impl;

import com.storehouse.dao.impl.CommentDaoImpl;
import com.storehouse.entity.Comment;
import com.storehouse.service.CommentService;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 用户评论相关接口
 */
public class CommentServiceImpl implements CommentService {

    /**
     * 添加
     *
     * @param comment 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addComment(Comment comment) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            jg = new CommentDaoImpl(conn).addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(null, null, conn);
        }
        return jg;
    }

    /**
     * 删除
     *
     * @param commentId 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delComment(int commentId) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            jg = new CommentDaoImpl(conn).delComment(commentId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(null, null, conn);
        }
        return jg;
    }

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateComment(Comment comment) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            jg = new CommentDaoImpl(conn).updateComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(null, null, conn);
        }
        return jg;
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Comment> queryComment() {
        Connection conn = BaseUtils.getConnection();
        List<Comment> list = new CommentDaoImpl(conn).queryComment();
        BaseUtils.closeAll(null, null, conn);
        return list;
    }
}
