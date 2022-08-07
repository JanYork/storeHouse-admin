package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.ConfigDao;
import com.storehouse.entity.Config;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置表的操作Dao层实现类
 */
public class ConfigDaoImpl extends BaseDao implements ConfigDao {
    /**
     * 通过构造方法传con值
     */
    public ConfigDaoImpl(Connection con) {
        super(con);
    }

    /**
     * 添加
     *
     * @param config 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addConfig(Config config) {
        String sql = "insert into config(smtp_eamil,smtp_pwd,baidu_app_key,baidu_secret_key,geetest_id,geetest_key) values(?,?,?,?,?,?)";
        return this.executeUpdate(sql, config.getSmtpEamil(), config.getSmtpPwd(), config.getBaduAppKey()
                , config.getBaiduSecretKey(), config.getGeetestId(), config.getGeetestKey());
    }

    /**
     * 删除
     *
     * @param cid 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delConfig(int cid) {
        String sql = "delete from config where cid=?";
        return this.executeUpdate(sql, cid);
    }

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateConfig(Config config) {
        String sql = "update config set smtp_eamil=?,smtp_pwd=?,baidu_app_key=?,baidu_secret_key=?,geetest_id=?,geetest_key=? where cid=?";
        return this.executeUpdate(sql, config.getSmtpEamil(), config.getSmtpPwd(), config.getBaduAppKey()
                , config.getBaiduSecretKey(), config.getGeetestId(), config.getGeetestKey(), config.getCid());
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Config> queryConfig() {
        ResultSet rs = null;
        List<Config> list = new ArrayList<>();
        Config config;
        String sql = "select * from config";
        try {
            rs = this.executeQuery(sql);
            while (rs.next()) {
                config = new Config();
                config.setCid(rs.getInt("cid"));
                config.setSmtpEamil(rs.getString("smtp_eamil"));
                config.setSmtpPwd(rs.getString("smtp_pwd"));
                config.setBaduAppKey(rs.getString("baidu_app_key"));
                config.setBaiduSecretKey(rs.getString("baidu_secret_key"));
                config.setGeetestId(rs.getString("geetest_id"));
                config.setGeetestKey(rs.getString("geetest_key"));
                //存入list集合
                list.add(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return list;
    }
}
