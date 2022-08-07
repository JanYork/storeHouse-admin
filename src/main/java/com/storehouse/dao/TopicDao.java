package com.storehouse.dao;

import com.storehouse.entity.Topic;

import java.util.List;

/**
 * 发帖数据访问层的接口
 *
 * @author nicole
 */
public interface TopicDao {
    /**
     * 添加帖子
     *
     * @param topic 帖子实体类
     * @return 返回受影响的行数
     */
    int addTopic(Topic topic);

    /**
     * 根据帖子的编号删除信息
     *
     * @param topicId 帖子编号
     * @return 返回受影响的行数
     */
    int delTopicByTopicId(int topicId);

    /**
     * 修改帖子信息列表
     *
     * @param topic 实体类对象
     * @return 返回受影响的行数
     */
    int updateTopic(Topic topic);

    /**
     * 查询所有帖子信息
     *
     * @return list集合列表
     */
    List<Topic> getTopicAll();

    /**
     * 据id编号查询详情信息
     *
     * @param topicId 标签ID
     * @return Topic实体类对象
     */
    Topic getTopicByTopicId(int topicId);

    /**
     * 根据浏览量分页查询所有贴子信息列表
     *
     * @return list集合列表
     */
    List<Topic> getTopicByBrowse(int currentPage, int pageSize, String... category);

    /**
     * 根据点赞量分页查询所有贴子信息列表
     *
     * @return list集合列表
     */
    List<Topic> getTopicByThumbsUp(int currentPage, int pageSize, String... category);

    /**
     * 得到所有贴子的总记录数 实现分页
     *
     * @return 主题对象
     */
    int getTopicCount(int... category);

    /**
     * 初始化分页显示所有贴子信息列表
     *
     * @param currentPage 当前页码
     * @param pageSize    每页记录数
     * @return 主题对象
     */
    List<Topic> getTopicByPage(int currentPage, int pageSize, String... category);


}
