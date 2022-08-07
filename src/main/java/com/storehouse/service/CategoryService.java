package com.storehouse.service;

import com.storehouse.entity.Category;

import java.util.List;

/**
 *  板块Service接口
 */
public interface CategoryService {
    /**
     * 添加
     *
     * @param category 实体类对象
     * @return 受影响的行数
     */
    int addCategory(Category category);

    /**
     * 删除
     *
     * @param category 根据id编号删除
     * @return 返回受影响的行数
     */
    int delCategory(int category);

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    int updateCategory(Category category);

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    List<Category> queryCategory();

    /**
     * 下拉框显示所有板块
     */
    List<Category> queryCategoryForSelect();

    /**
     * 返回单个板块信息
     */
    List<Category> queryCategoryForId(String categoryId);
}
