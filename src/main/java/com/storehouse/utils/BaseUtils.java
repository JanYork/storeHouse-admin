package com.storehouse.utils;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接池工具类
 *
 * @author JanYork
 */
public class BaseUtils {
    /**
     * 初始化日志对象
     */
    private static final Logger log = Logger.getLogger(BaseUtils.class);

    /**
     * 获取数据库的连接
     *
     * @return Connection对象
     */
    public static Connection getConnection() {
        Connection connection = null;
        //创建Context对象，初使化
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/storeHouse");
            connection = ds.getConnection();
            log.info("获取数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取数据库连接失败！"+e.getMessage());
        }
        return connection;
    }

    /**
     * 关闭数据源
     */
    public static void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (null != rs) {
            try {
                rs.close();
                log.info("关闭数据库结果集成功");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("关闭数据库结果集失败！" + e.getMessage());
            }
        }

        if (null != ps) {
            try {
                ps.close();
                log.info("关闭数据库预编译语句成功");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("关闭数据库预编译语句失败！" + e.getMessage());
            }
        }

        if (null != conn) {
            try {
                conn.close();
                log.info("关闭数据库连接成功");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("关闭数据库连接失败！" + e.getMessage());
            }
        }
    }
}
