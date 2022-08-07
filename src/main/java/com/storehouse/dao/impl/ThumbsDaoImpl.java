package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.ThumbsDao;
import com.storehouse.entity.Thumbs;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 点赞表的操作Dao层实现类
 */
public class ThumbsDaoImpl extends BaseDao implements ThumbsDao {
    /**
     * 通过构造方法传con值
     */
    public ThumbsDaoImpl(Connection con) {
        super(con);
    }

    /**
     * 添加
     *
     * @param thumbs 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addThumbs(Thumbs thumbs) {
        String sql = "insert into thumbs(thumbs_uid ,thumbs_topic_id ) values(?,?)";
        return this.executeUpdate(sql, thumbs.getThumbsUid(), thumbs.getThumbsTopicld());
    }

    /**
     * 删除
     *
     * @param thumbsId 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delThumbs(int thumbsId) {
        String sql = "delete from thumbs where thumbs_id = ?";
        return this.executeUpdate(sql, thumbsId);
    }

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateThumbs(Thumbs thumbs) {
        String sql = "update thumbs set thumbs_uid=?,thumbs_topicId=? where thumbs_id=?";
        return this.executeUpdate(sql, thumbs.getThumbsUid(), thumbs.getThumbsTopicld(), thumbs.getThumbsId());
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Thumbs> queryThumbs() {
        ResultSet rs = null;
        List<Thumbs> list = new ArrayList<>();

        //创建sql语句
        String sql = "select * from thumbs";
        try {
            rs = this.executeQuery(sql);
            while (rs.next()) {
                Thumbs thumbs = new Thumbs();
                thumbs.setThumbsId(rs.getInt("thumbs_id"));
                thumbs.setThumbsUid(rs.getInt("thumbs_uid"));
                thumbs.setThumbsTopicld(rs.getInt("thumbs_topicId"));
                //存入list集合
                list.add(thumbs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }

    /**
     * 查询是否存在对应uid与文章id的点赞记录
     *
     * @param uid       用户id
     * @param articleId 文章id
     * @return 返回数量
     */
    @Override
    public int ThumbsByUidAndArticleId(int uid, int articleId) {
        ResultSet rs;
        Thumbs thumbs = new Thumbs();
        //创建sql语句
        String sql = "select thumbs_id from thumbs where thumbs_uid=? and thumbs_topic_id=?";
        //将查询出来的数量返回
        rs = this.executeQuery(sql, uid, articleId);
        try {
            if (rs.next()) {
                thumbs.setThumbsId(rs.getInt("thumbs_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thumbs.getThumbsId();
    }
}
