package com.storehouse.dao;

import com.storehouse.entity.Config;

import java.util.List;

/**
 * 后台管理接口
 */
public interface ConfigDao {
    /**
     * 添加
     *
     * @param config 实体类对象
     * @return 受影响的行数
     */
    int addConfig(Config config);

    /**
     * 删除
     *
     * @param cid 根据id编号删除
     * @return 返回受影响的行数
     */
    int delConfig(int cid);

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    int updateConfig(Config config);

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    List<Config> queryConfig();
}
