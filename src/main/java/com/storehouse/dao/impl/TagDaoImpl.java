package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.TagDao;
import com.storehouse.entity.Tag;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签分类数据访问层的实现类
 *
 * @author nicole
 */
public class TagDaoImpl extends BaseDao implements TagDao {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 通过构造传conn值过来
     */
    public TagDaoImpl(Connection conn) {
        super(conn);
    }

    /**
     * 添加标签分类
     *
     * @param tag 标签分类实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addTag(Tag tag) {
        //编写SQL语句
        String sql = "insert into tag(tag_name,topic_count,tag_category_id ) values(?,?,?)";
        return this.executeUpdate(sql, tag.getTagName(), tag.getTopicCount(), tag.getTagCategoryId());
    }

    /**
     * 根据标签分类的编号删除信息
     *
     * @param tagId 标签分类编号
     * @return 返回受影响的行数
     */
    @Override
    public int delTagByTagId(int tagId) {
        //编写SQL语句
        String sql = "delete from tag where tag_id=?";
        return this.executeUpdate(sql, tagId);
    }

    /**
     * 修改标签分类信息列表
     *
     * @param tag 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateTag(Tag tag) {
        //编写SQL语句
        String sql = "update tag set tag_name=?,topic_count=?,tag_category_id =? where tag_id=?";
        return this.executeUpdate(sql, tag.getTagName(), tag.getTopicCount(), tag.getTagCategoryId(), tag.getTagId());
    }

    /**
     * 查询所有标签分类信息
     *
     * @return list集合列表
     */
    @Override
    public List<Tag> getTagAll() {
        List<Tag> list = new ArrayList<>();
        ResultSet rs = null;
        //编写查询语句
        String sql = "select * from tag";
        try {
            //执行
            rs = this.executeQuery(sql);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Tag tag = new Tag();
                tag.setTagId(rs.getInt("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
                tag.setTopicCount(rs.getInt("topic_count"));
                tag.setTagCategoryId(rs.getInt("tag_category_id"));
                //将内容添加到集合中
                list.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        //最后返回list集合
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
        ResultSet rs = null;
        String sql = "select * from tag where tag_id=?";
        Tag tag = null;
        try {
            rs = this.executeQuery(sql, tagId);
            if (rs.next()) {
                tag = new Tag();
                tag.setTagId(rs.getInt("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
                tag.setTopicCount(rs.getInt("topic_count"));
                tag.setTagCategoryId(rs.getInt("tag_category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
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
        ResultSet rs = null;
        String sql = "select * from tag where tag_id=?";
        Tag tag = null;
        try {
            rs = this.executeQuery(sql, tagId);
            if (rs.next()) {
                tag = new Tag();
                tag.setTagId(rs.getInt("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return tag;
    }
}
