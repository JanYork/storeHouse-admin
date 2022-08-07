package com.storehouse.service.impl;

import com.storehouse.dao.TopicDao;
import com.storehouse.dao.impl.TopicDaoImpl;
import com.storehouse.entity.Topic;
import com.storehouse.service.TopicService;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * 帖子业务逻辑层的实现类
 *
 * @author nicole
 */
public class TopicServiceImpl implements TopicService {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 添加帖子
     *
     * @param topic 帖子实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addTopic(Topic topic) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int result = new TopicDaoImpl(conn).addTopic(topic);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 根据帖子的编号删除信息
     *
     * @param topicId 帖子编号
     * @return 返回受影响的行数
     */
    @Override
    public int delTopicByTopicId(int topicId) {
        int result;
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        try {
            //创建数据访问层对象
            TopicDao topicDao = new TopicDaoImpl(conn);
            result = topicDao.delTopicByTopicId(topicId);
        } finally {
            BaseUtils.closeAll(null, null, conn);
        }
        return result;
    }

    /**
     * 修改帖子信息列表
     *
     * @param topic 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateTopic(Topic topic) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int result = new TopicDaoImpl(conn).updateTopic(topic);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 查询所有帖子信息
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicAll() {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的查询所有信息的方法
        List<Topic> list = new TopicDaoImpl(conn).getTopicAll();
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按topicId查询的方法
        Topic topic = new TopicDaoImpl(conn).getTopicByTopicId(topicId);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return topic;
    }

    /**
     * 得到所有贴子的总记录数 实现分页
     *
     * @param category 板块id
     * @return 主题对象
     */
    @Override
    public int getTopicCount(int... category) {
        Connection conn = BaseUtils.getConnection();
        int count = new TopicDaoImpl(conn).getTopicCount(category);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }


    /**
     * 根据浏览量查询所有贴子信息列表
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicByBrowse(int currentPage, int pageSize, String... category) {
        Connection conn = BaseUtils.getConnection();
        List<Topic> list = new TopicDaoImpl(conn).getTopicByBrowse(currentPage, pageSize, category);
        BaseUtils.closeAll(null, null, conn);
        return list;
    }

    /**
     * 根据点赞量查询所有贴子信息列表
     *
     * @return list集合列表
     */
    @Override
    public List<Topic> getTopicByThumbsUp(int currentPage, int pageSize, String... category) {
        Connection conn = BaseUtils.getConnection();
        List<Topic> list = new TopicDaoImpl(conn).getTopicByThumbsUp(currentPage, pageSize, category);
        BaseUtils.closeAll(null, null, conn);
        return list;
    }

    /**
     * 根据板块分类，分页得到贴子信息
     *
     * @param currentPage 当前页码
     * @param pageSize    每页记录数
     * @return 主题对象
     */
    @Override
    public List<Topic> getTopicByPage(int currentPage, int pageSize, String... category) {
        Connection conn = BaseUtils.getConnection();
        List<Topic> list = new TopicDaoImpl(conn).getTopicByPage(currentPage, pageSize, category);
        BaseUtils.closeAll(null, null, conn);
        return list;
    }


}
