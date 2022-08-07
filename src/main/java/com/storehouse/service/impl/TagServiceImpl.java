package com.storehouse.service.impl;

import com.storehouse.dao.TagDao;
import com.storehouse.dao.TopicDao;
import com.storehouse.dao.impl.TagDaoImpl;
import com.storehouse.dao.impl.TopicDaoImpl;
import com.storehouse.entity.Tag;
import com.storehouse.entity.Topic;
import com.storehouse.service.TagService;
import com.storehouse.utils.BaseUtils;
import javafx.scene.control.TableColumn;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 帖子实体类业务逻辑层的实现类
 *
 * @author nicole
 */
public class TagServiceImpl implements TagService {
    private static Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 添加标签分类
     *
     * @param tag 标签分类实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addTag(Tag tag) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int result = new TagDaoImpl(conn).addTag(tag);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 根据标签分类的编号删除信息
     *
     * @param tagId 标签分类编号
     * @return 返回受影响的行数
     */
    @Override
    public int delTagByTagId(int tagId) {
        int result = 0;
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //创建数据访问层对象
        TagDao tagDao = new TagDaoImpl(conn);
        result = tagDao.delTagByTagId(tagId);
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 修改标签分类信息列表
     *
     * @param tag 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateTag(Tag tag) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int result = new TagDaoImpl(conn).updateTag(tag);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 查询所有标签分类信息
     *
     * @return list集合列表
     */
    @Override
    public List<Tag> getTagAll() {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的查询所有信息的方法
        List<Tag> list = new TagDaoImpl(conn).getTagAll();
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return list;
    }

    /**
     * 据id编号查询详情信息
     *
     * @param tagId 标签ID
     * @return Tag实体类对象
     */
    @Override
    public Tag getTagByTagId(int tagId) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按tagId查询的方法
        Tag tag = new TagDaoImpl(conn).getTagByTagId(tagId);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return tag;
    }

    /**
     * 根据标签分类的编号查询详情信息
     *
     * @param tagId 标签分类编号
     * @return Tag实体类对象
     */
    @Override
    public Tag getTagByTagIdName(int tagId) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按tagId查询的方法
        Tag tag = new TagDaoImpl(conn).getTagByTagId(tagId);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return tag;
    }
}
