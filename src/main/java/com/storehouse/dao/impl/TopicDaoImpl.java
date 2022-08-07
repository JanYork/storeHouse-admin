package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.TopicDao;
import com.storehouse.entity.Topic;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子数据访问层的实现类
 *
 * @author nicole
 */
public class TopicDaoImpl extends BaseDao implements TopicDao {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 通过构造传conn值过来
     */
    public TopicDaoImpl(Connection conn) {
        super(conn);
    }


    /**
     * 添加帖子
     *
     * @param topic 帖子实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addTopic(Topic topic) {
        //编写SQL语句
        String sql = "insert into topic(title,topic_content,comment_count,topic_time,topic_uid,topic_category_id," +
                "topic_tag_id,browse_count,thumbs_up) values(?,?,?,?,?,?,?,?,?)";
        return this.executeUpdate(sql, topic.getTitle(), topic.getTopicContent(), topic.getCommentCount(), topic.getTopicTime(),
                topic.getTopicUid(), topic.getTopicCategoryId(), topic.getTopicTagId(), topic.getBrowseCount(), topic.getThumbsUp());
    }

    /**
     * 根据帖子的编号删除信息
     *
     * @param topicId 帖子编号
     * @return 返回受影响的行数
     */
    @Override
    public int delTopicByTopicId(int topicId) {
        //编写SQL语句
        String sql = "delete from topic where topic_id=?";
        return this.executeUpdate(sql, topicId);
    }

    /**
     * 修改帖子信息列表
     *
     * @param topic 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateTopic(Topic topic) {
        //编写SQL语句
        String sql = "update topic set title=?,topic_content=?,comment_count=?,topic_time=?,topic_uid=?," +
                "topic_category_id=?,topic_tag_id=?,browse_count=?,thumbs_up=? where topic_id=?";
        return this.executeUpdate(sql, topic.getTitle(), topic.getTopicContent(), topic.getCommentCount(), topic.getTopicTime(),
                topic.getTopicUid(), topic.getTopicCategoryId(), topic.getTopicTagId(), topic.getBrowseCount(), topic.getThumbsUp(), topic.getTopicId());
    }

    /**
     * 查询所有帖子信息
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicAll() {
        List<Topic> list = new ArrayList<>();
        ResultSet rs = null;
        //编写查询语句
        String sql = "select * from topic";
        try {
            //执行
            rs = this.executeQuery(sql);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Topic topic = new Topic();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setTopicContent(rs.getString("topic_content"));
                topic.setCommentCount(rs.getInt("comment_count"));
                topic.setTopicTime(rs.getTimestamp("topic_time"));
                topic.setTopicUid(rs.getInt("topic_uid"));
                topic.setTopicCategoryId(rs.getInt("topic_category_id"));
                topic.setTopicTagId(rs.getInt("topic_tag_id"));
                topic.setBrowseCount(rs.getInt("browse_count"));
                topic.setThumbsUp(rs.getInt("thumbs_up"));
                //把值存到list集合中
                list.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        //返回list集合
        return list;
    }

    /**
     * 据id编号查询详情信息
     *
     * @param topicId 标签ID
     * @return Topic实体类对象
     */
    @Override
    public Topic getTopicByTopicId(int topicId) {
        ResultSet rs = null;
        String sql = "select * from topic where topic_id=?";
        Topic topic = null;
        try {
            rs = this.executeQuery(sql, topicId);
            if (rs.next()) {
                //创建实体类对象
                topic = new Topic();
                //设置值
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setTopicContent(rs.getString("topic_content"));
                topic.setCommentCount(rs.getInt("comment_count"));
                topic.setTopicTime(rs.getTimestamp("topic_time"));
                topic.setTopicUid(rs.getInt("topic_uid"));
                topic.setTopicCategoryId(rs.getInt("topic_category_id"));
                topic.setTopicTagId(rs.getInt("topic_tag_id"));
                topic.setBrowseCount(rs.getInt("browse_count"));
                topic.setThumbsUp(rs.getInt("thumbs_up"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return topic;
    }

    /**
     * 根据浏览量查询所有贴子信息列表
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicByBrowse(int currentPage, int pageSize, String... category) {
        List<Topic> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql;
            if (category.length == 0) {
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t, userInfo u, category c, tag tag where t.topic_uid = u.uid and c.category_id = t.topic_category_id and t.topic_tag_id = tag.tag_id ORDER BY thumbs_up DESC LIMIT ?,?";
            } else {
                String categoryId = category[0];
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t,userInfo u,category c,tag tag where t.topic_uid = u.uid and tag.tag_id = t.topic_tag_id and c.category_id = t.topic_category_id and t.topic_category_id = " + categoryId + " ORDER BY thumbs_up DESC LIMIT ?,?";
            }
            //执行
            rs = this.executeQuery(sql, (currentPage - 1) * pageSize, pageSize);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Topic topic = new Topic();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setTopicContent(rs.getString("topic_content"));
                topic.setCommentCount(rs.getInt("comment_count"));
                topic.setTopicTime(rs.getTimestamp("topic_time"));
                topic.setTopicUid(rs.getInt("topic_uid"));
                topic.setTopicCategoryId(rs.getInt("topic_category_id"));
                topic.setTopicTagId(rs.getInt("topic_tag_id"));
                topic.setBrowseCount(rs.getInt("browse_count"));
                topic.setThumbsUp(rs.getInt("thumbs_up"));
                topic.setPicPath(rs.getString("avatar_address"));
                topic.setLogo(rs.getString("category_logo"));
                topic.setTagName(rs.getString("tag_name"));
                //把值存到list集合中
                list.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }

    /**
     * 根据点赞量查询所有贴子信息列表
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicByThumbsUp(int currentPage, int pageSize, String... category) {
        List<Topic> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql;
            if (category.length == 0) {
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t, userInfo u, category c, tag tag where t.topic_uid = u.uid and c.category_id = t.topic_category_id and t.topic_tag_id = tag.tag_id ORDER BY browse_count DESC LIMIT ?,?";
            } else {
                String categoryId = category[0];
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t,userInfo u,category c,tag tag where t.topic_uid = u.uid and tag.tag_id = t.topic_tag_id and c.category_id = t.topic_category_id and t.topic_category_id = " + categoryId + " ORDER BY browse_count DESC LIMIT ?,?";
            }
            //执行sql
            rs = this.executeQuery(sql, (currentPage - 1) * pageSize, pageSize);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Topic topic = new Topic();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setTopicContent(rs.getString("topic_content"));
                topic.setCommentCount(rs.getInt("comment_count"));
                topic.setTopicTime(rs.getTimestamp("topic_time"));
                topic.setTopicUid(rs.getInt("topic_uid"));
                topic.setTopicCategoryId(rs.getInt("topic_category_id"));
                topic.setTopicTagId(rs.getInt("topic_tag_id"));
                topic.setBrowseCount(rs.getInt("browse_count"));
                topic.setThumbsUp(rs.getInt("thumbs_up"));
                topic.setPicPath(rs.getString("avatar_address"));
                topic.setLogo(rs.getString("category_logo"));
                topic.setTagName(rs.getString("tag_name"));
                //把值存到list集合中
                list.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }

    /**
     * 得到所有贴子的总记录数 实现分页
     *
     * @param category 板块id
     * @return 主题对象
     */
    @Override
    public int getTopicCount(int... category) {
        int count = 0;
        ResultSet rs = null;
        try {
            String sql;
            if (category.length == 0) {
                sql = "SELECT COUNT(*) FROM topic";
            } else {
                int categoryId = category[0];
                sql = "SELECT COUNT(*) FROM topic WHERE topic_category_id = (SELECT " + categoryId + " FROM category)";
            }
            //执行sql
            rs = this.executeQuery(sql);
            //遍历循环数据
            while (rs.next()) {
                //获取总记录数
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        return count;
    }


    /**
     * 根据板块分页得到贴子所有贴子信息
     *
     * @param currentPage 当前页码
     * @param pageSize    每页记录数
     * @return 主题对象
     */
    @Override
    public List<Topic> getTopicByPage(int currentPage, int pageSize, String... category) {
        List<Topic> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            String sql;
            //如果category为空，则查询所有贴子信息
            if (category.length == 0) {
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t, userInfo u, category c, tag tag where t.topic_uid = u.uid and c.category_id = t.topic_category_id and t.topic_tag_id = tag.tag_id ORDER BY topic_time DESC LIMIT ?,?";
            } else {
                String categoryId = category[0];
//                sql = "SELECT * FROM topic WHERE topic_category_id IN (SELECT ? FROM category) ORDER BY topic_time DESC LIMIT ?,?";
                //将categoryId使用concat到第一个?位置
                sql = "SELECT t.*, u.avatar_address, c.category_logo, tag.tag_name FROM topic t,userInfo u,category c,tag tag where t.topic_uid = u.uid and tag.tag_id = t.topic_tag_id and c.category_id = t.topic_category_id and t.topic_category_id = " + categoryId + " ORDER BY topic_time DESC LIMIT ?,?";
            }
            //执行
            rs = this.executeQuery(sql, (currentPage - 1) * pageSize, pageSize);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Topic topic = new Topic();
                topic.setTopicId(rs.getInt("topic_id"));
                topic.setTitle(rs.getString("title"));
                topic.setTopicContent(rs.getString("topic_content"));
                topic.setCommentCount(rs.getInt("comment_count"));
                topic.setTopicTime(rs.getTimestamp("topic_time"));
                topic.setTopicUid(rs.getInt("topic_uid"));
                topic.setTopicCategoryId(rs.getInt("topic_category_id"));
                topic.setTopicTagId(rs.getInt("topic_tag_id"));
                topic.setBrowseCount(rs.getInt("browse_count"));
                topic.setThumbsUp(rs.getInt("thumbs_up"));
                topic.setPicPath(rs.getString("avatar_address"));
                topic.setLogo(rs.getString("category_logo"));
                topic.setTagName(rs.getString("tag_name"));
                //把值存到list集合中
                list.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }

}
