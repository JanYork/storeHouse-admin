package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.CommentDao;
import com.storehouse.entity.Comment;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论表的操作Dao层实现类
 */
public class CommentDaoImpl extends BaseDao implements CommentDao {
    /**
     * 通过构造方法传con值
     *
     * @param con 参数
     */
    public CommentDaoImpl(Connection con) {
        super(con);
    }

    /**
     * 添加
     *
     * @param comment 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addComment(Comment comment) {
        String sql = "insert into comment(content,comment_uid,comment_topic_id,comment_time,comment_ip,comment_equipment) values(?,?,?,?,?,?)";
        return this.executeUpdate(sql, comment.getContent(), comment.getCommentUid(), comment.getCommentTopicId(), comment.getCommentTime(), comment.getCommentIp(), comment.getCommentEquipment());
    }

    /**
     * 删除
     *
     * @param commentId 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delComment(int commentId) {
        String sql = "delete from comment where comment_id=?";
        return this.executeUpdate(sql, commentId);
    }


    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateComment(Comment comment) {
        String sql = "update comment set content=?,comment_uid=?,comment_topic_id=?,comment_time=?,comment_ip=?,comment_equipment=? where comment_id=?";
        return this.executeUpdate(sql, comment.getContent(), comment.getCommentUid(), comment.getCommentTopicId(), comment.getCommentTime()
                , comment.getCommentIp(), comment.getCommentEquipment(), comment.getCommentId());
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Comment> queryComment() {
        ResultSet rs = null;
        List<Comment> list = new ArrayList<>();
        Comment comment;
        //创建sql语句
        String sql = "select * from comment";
        try {
            rs = this.executeQuery(sql);
            while (rs.next()) {
                comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setContent(rs.getString("content"));
                comment.setCommentUid(rs.getInt("comment_uid"));
                comment.setCommentTopicId(rs.getInt("comment_topic_id"));
                comment.setCommentTime(rs.getDate("comment_time"));
                comment.setCommentIp(rs.getString("comment_ip"));
                comment.setCommentEquipment(rs.getString("comment_equipment"));
                //存入list集合
                list.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }
}
