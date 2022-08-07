package com.storehouse.service.impl;

import com.storehouse.dao.CategoryDao;
import com.storehouse.dao.impl.CategoryDaoImpl;
import com.storehouse.entity.Category;
import com.storehouse.service.CategoryService;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 分类板块相关接口
 */
public class CategoryServiceImpl implements CategoryService {
    /**
     * 添加
     *
     * @param category 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addCategory(Category category) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            CategoryDao cd = new CategoryDaoImpl(conn);
            jg = cd.addCategory(category);
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
     * @param category 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delCategory(int category) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            CategoryDao cd = new CategoryDaoImpl(conn);
            jg = cd.delCategory(category);
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
    public int updateCategory(Category category) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {
            CategoryDao cd = new CategoryDaoImpl(conn);
            jg = cd.updateCategory(category);
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
    public List<Category> queryCategory() {
        Connection con = BaseUtils.getConnection();
        List<Category> list = new CategoryDaoImpl(con).queryCategory();
        BaseUtils.closeAll(null, null, con);
        return list;
    }

    /**
     * 下拉框显示所有板块
     */
    @Override
    public List<Category> queryCategoryForSelect() {
        Connection con = BaseUtils.getConnection();
        List<Category> list = new CategoryDaoImpl(con).queryCategoryForSelect();
        BaseUtils.closeAll(null, null, con);
        return list;
    }

    /**
     * 返回单个板块信息
     */
    @Override
    public List<Category> queryCategoryForId(String categoryId) {
        Connection con = BaseUtils.getConnection();
        List<Category> list = new CategoryDaoImpl(con).queryCategoryForId(categoryId);
        BaseUtils.closeAll(null, null, con);
        return list;
    }
}
