package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.CategoryDao;
import com.storehouse.entity.Category;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 板块表的操作Dao层实现类
 *
 * @author JanYork
 */
public class CategoryDaoImpl extends BaseDao implements CategoryDao {

    /**
     * 通过构造方法传con值
     */
    public CategoryDaoImpl(Connection con) {
        super(con);
    }

    /**
     * 添加
     *
     * @param category 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addCategory(Category category) {
        String sql = "insert into category(category_title,topic_count,comment_count,description,category_logo) values(?,?,?,?,?)";
        return this.executeUpdate(sql, category.getCategoryTitle(), category.getTopicCount(), category.getCommentCount()
                , category.getDescription(), category.getCategoryLogo());
    }

    /**
     * 删除
     *
     * @param categoryId 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delCategory(int categoryId) {
        String sql = "delete from category where category_id=?";
        return this.executeUpdate(sql, categoryId);
    }

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateCategory(Category category) {
        String sql = "update category set category_title=?,topic_count=?,comment_count=?,description=?,category_logo=? where category_id=?";
        return this.executeUpdate(sql, category.getCategoryTitle(), category.getTopicCount()
                , category.getCommentCount(), category.getDescription(), category.getCategoryLogo(), category.getCategoryId());
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Category> queryCategory() {
        ResultSet rs = null;
        List<Category> list = new ArrayList<>();
        Category category;
        String sql = "select * from category";
        try {
            rs = this.executeQuery(sql);
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryTitle(rs.getString("category_title"));
                category.setTopicCount(rs.getInt("topic_count"));
                category.setCommentCount(rs.getInt("comment_count"));
                category.setDescription(rs.getString("description"));
                category.setCategoryLogo(rs.getString("category_logo"));
                //存入list集合
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }

    /**
     * 下拉框显示所有板块
     */
    @Override
    public List<Category> queryCategoryForSelect() {
        ResultSet rs = null;
        List<Category> list = new ArrayList<>();
        Category category;
        String sql = "SELECT * FROM category where category_id = ?";
        try {
            rs = this.executeQuery(sql);
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryTitle(rs.getString("category_title"));
                //存入list集合
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }


    /**
     * 返回单个板块信息
     */
    @Override
    public List<Category> queryCategoryForId(String categoryId) {
        ResultSet rs = null;
        List<Category> list = new ArrayList<>();
        Category category;
        String sql = "SELECT * FROM category where category_id = ?";
        try {
            rs = this.executeQuery(sql, categoryId);
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryTitle(rs.getString("category_title"));
                category.setTopicCount(rs.getInt("topic_count"));
                category.setCommentCount(rs.getInt("comment_count"));
                category.setDescription(rs.getString("description"));
                category.setCategoryLogo(rs.getString("category_logo"));
                //存入list集合
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }
}
