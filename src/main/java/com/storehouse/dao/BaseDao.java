package com.storehouse.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 基础数据访问层的实现类
 *
 * @author JanYork
 */
public class BaseDao {
    private final Connection con;
    /**
     * 创建Logger对象，用于记录日志
     */
    private static final Logger LOG = Logger.getLogger(BaseDao.class);

    /**
     * 通过构造方法传con值
     */
    public BaseDao(Connection con) {
        this.con = con;
    }

    /**
     * 增删改操作
     *
     * @param sql    sql语句
     * @param params 存数据,可以不存
     * @return 返回受影响的行数
     */
    public int executeUpdate(String sql, Object... params) {
        //定义返回值
        int result = 0;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                result = ps.executeUpdate();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            LOG.error("执行增删改操作失败！------>sql语句：" + sql);
            LOG.error("执行增删改操作失败！------>错误：" + exception.getMessage());
        }
        LOG.info("返回参数------》" + result);
        return result;
    }

    /**
     * 通用查询方法
     *
     * @param sql    查询语句
     * @param params 参数列表
     * @return ResultSet对象
     */
    public ResultSet executeQuery(String sql, Object... params) {
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            //执行查询方法
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}
