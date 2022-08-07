package com.storehouse.service.impl;

import com.storehouse.dao.ConfigDao;
import com.storehouse.dao.impl.ConfigDaoImpl;
import com.storehouse.entity.Config;
import com.storehouse.service.ConfigService;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 设置接口
 */
public class ConfigServiceImpl implements ConfigService {
    /**
     * 添加
     *
     * @param config 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addConfig(Config config) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {

            ConfigDao cd = new ConfigDaoImpl(conn);
            jg = cd.addConfig(config);

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
     * @param cid 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delConfig(int cid) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {

            ConfigDao cd = new ConfigDaoImpl(conn);
            jg = cd.delConfig(cid);

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
    public int updateConfig(Config config) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {

            ConfigDao cd = new ConfigDaoImpl(conn);
            jg = cd.updateConfig(config);

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
    public List<Config> queryConfig() {
        Connection conn = BaseUtils.getConnection();
        List<Config> list = new ConfigDaoImpl(conn).queryConfig();
        BaseUtils.closeAll(null, null, conn);
        return list;
    }
}
