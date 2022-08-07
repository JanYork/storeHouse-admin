package com.storehouse.dao;

import com.storehouse.entity.Tag;

import java.util.List;

/**
 * 标签数据访问层的接口
 *
 * @author nicole
 */
public interface TagDao {
    /**
     * 添加标签分类
     *
     * @param tag 标签分类实体类
     * @return 返回受影响的行数
     */
    int addTag(Tag tag);

    /**
     * 根据标签分类的编号删除信息
     *
     * @param tagId 标签分类编号
     * @return 返回受影响的行数
     */
    int delTagByTagId(int tagId);

    /**
     * 修改标签分类信息列表
     *
     * @param tag 实体类对象
     * @return 返回受影响的行数
     */
    int updateTag(Tag tag);

    /**
     * 查询所有标签分类信息
     *
     * @return list集合列表
     */
    List<Tag> getTagAll();

    /**
     * 据id编号查询详情信息
     *
     * @param tagId 标签ID
     * @return Tag实体类对象
     */
    Tag getTagByTagId(int tagId);

    /**
     * 根据标签分类的编号查询详情信息
     *
     * @param tagId 标签分类编号
     * @return Tag实体类对象
     */
    Tag getTagByTagIdName(int tagId);
}
