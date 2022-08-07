package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.UserInfoDao;
import com.storehouse.entity.UserInfo;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问层的实现类
 *
 * @author nicole
 */
public class UserInfoDaoImpl extends BaseDao implements UserInfoDao {
    private static final Logger LOG = Logger.getLogger(BaseUtils.class);

    /**
     * 通过构造传conn值过来
     */
    public UserInfoDaoImpl(Connection conn) {
        super(conn);
    }

    /**
     * 添加用户
     *
     * @param userInfo 用户实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addUsertInfo(UserInfo userInfo) {
        //编写SQL语句
        String sql = "insert into userInfo(account,password,telephone_number,avatar_address,eamil,introduce," +
                "create_time,topic_count,comment_count,nano_id,url,user_state) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        LOG.info("添加用户的SQL语句：" + sql);
        return this.executeUpdate(sql, userInfo.getAccount(), userInfo.getPassword(), userInfo.getTelephoneNumber(),
                userInfo.getAvatarAddress(), userInfo.getEamil(), userInfo.getIntroduce(), userInfo.getCreateTime(),
                userInfo.getTopicCount(), userInfo.getCommentCount(), userInfo.getNanoId(), userInfo.getUrl(), userInfo.getUserState());
    }

    /**
     * 根据用户uid删除信息
     *
     * @param uid 用户编号
     * @return 返回受影响的行数
     */
    @Override
    public int delUsertInfoByUid(int uid) {
        //编写SQL语句
        String sql = "delete from userInfo where uid=?";
        return this.executeUpdate(sql, uid);
    }

    /**
     * 修改用户信息列表
     *
     * @param userInfo 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateUsertInfo(UserInfo userInfo) {
        //编写sql语句
        String sql = "update userInfo set account=?,password=?,telephone_number=?,avatar_address=?,eamil=?," +
                "introduce=?,create_time=?,topic_count=?,comment_count=?,nano_id=?,url=?,user_state=? where uid=?";
        return this.executeUpdate(sql, userInfo.getAccount(), userInfo.getPassword(), userInfo.getTelephoneNumber(),
                userInfo.getAvatarAddress(), userInfo.getEamil(), userInfo.getIntroduce(), userInfo.getCreateTime(),
                userInfo.getTopicCount(), userInfo.getCommentCount(), userInfo.getNanoId(), userInfo.getUrl(),
                userInfo.getUserState(), userInfo.getUid());
    }

    /**
     * 查询所有用户信息
     *
     * @return list集合列表
     */
    @Override
    public List<UserInfo> getUsertInfoAll() {
        List<UserInfo> list = new ArrayList<>();
        ResultSet rs = null;
        //编写查询语句
        String sql = "select * from userInfo";
        try {
            //执行
            rs = this.executeQuery(sql);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
                //将内容添加到集合中
                list.add(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
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
     * @param uid 标签ID
     * @return TUsertInfo实体类对象
     */
    @Override
    public UserInfo getUsertInfoByUid(int uid) {
        ResultSet rs = null;
        String sql = "select * from userInfo where uid=?";
        UserInfo userInfo = null;
        try {
            rs = this.executeQuery(sql, uid);
            if (rs.next()) {
                //创建实体类对象
                userInfo = new UserInfo();
                //设置值
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return userInfo;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param account 用户名
     * @return 用户实体类对象
     */
    @Override
    public UserInfo getUsertInfoByAccount(String account) {
        ResultSet rs = null;
        UserInfo userInfo = null;
        String sql = "select * from userInfo where account=?";
        try {
            rs = this.executeQuery(sql, account);
            if (rs.next()) {
                //创建实体类对象
                userInfo = new UserInfo();
                //设置值
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return userInfo;
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param telephoneNumber 手机号
     * @return 用户实体类对象
     */
    @Override
    public UserInfo getUsertInfoByTelephoneNumber(String telephoneNumber) {
        ResultSet rs = null;
        UserInfo userInfo = null;
        String sql = "select * from userInfo where telephone_number=?";
        try {
            rs = this.executeQuery(sql, telephoneNumber);
            if (rs.next()) {
                //创建实体类对象
                userInfo = new UserInfo();
                //设置值
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return userInfo;
    }

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户实体类对象
     */
    @Override
    public UserInfo getUsertInfoByEmail(String email) {
        ResultSet rs = null;
        UserInfo userInfo = null;
        String sql = "select * from userInfo where eamil=?";
        try {
            rs = this.executeQuery(sql, email);
            if (rs.next()) {
                //创建实体类对象
                userInfo = new UserInfo();
                //设置值
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return userInfo;
    }

    /**
     * 根据nanoId查询用户信息
     *
     * @param nanoId nanoId
     * @return 用户实体类对象
     */
    @Override
    public UserInfo findUidByNanoId(String nanoId) {
        ResultSet rs = null;
        UserInfo userInfo = null;
        String sql = "select * from userInfo where nano_id=?";
        try {
            rs = this.executeQuery(sql, nanoId);
            if (rs.next()) {
                //创建实体类对象
                userInfo = new UserInfo();
                //设置值
                userInfo.setUid(rs.getInt("uid"));
                userInfo.setAccount(rs.getString("account"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setTelephoneNumber(rs.getNString("telephone_number"));
                userInfo.setAvatarAddress(rs.getNString("avatar_address"));
                userInfo.setEamil(rs.getString("eamil"));
                userInfo.setIntroduce(rs.getString("introduce"));
                userInfo.setCreateTime(rs.getDate("create_time"));
                userInfo.setTopicCount(rs.getInt("topic_count"));
                userInfo.setCommentCount(rs.getInt("comment_count"));
                userInfo.setNanoId(rs.getString("nano_id"));
                userInfo.setUrl(rs.getString("url"));
                userInfo.setUserState(rs.getInt("user_state"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return userInfo;
    }

    /**
     * 根据用户ID查询用户头像
     *
     * @param uid 用户ID
     * @return 头像路径
     */
    @Override
    public String getUserHeadImgByUid(int uid) {
        ResultSet rs = null;
        String avatarAddress = null;
        String sql = "select avatar_address from userInfo where uid=?";
        try {
            rs = this.executeQuery(sql, uid);
            if (rs.next()) {
                avatarAddress = rs.getString("avatar_address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return avatarAddress;
    }

    /**
     * 查询用户发帖的总记录数
     *
     * @param uid 用户ID
     * @return 返回受影响的行数
     */
    @Override
    public int getTopicCountByUid(int uid) {
        int count = 0;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM topic t,userInfo u WHERE t.topic_uid = u.uid AND u.uid = ?";
            rs = this.executeQuery(sql, uid);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        return count;
    }

    /**
     * 根据用户id修改用户个人信息
     *
     * @param userInfo 用户实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateUserInfoByUid(UserInfo userInfo) {
        int count = 0;
        String sql = "update userInfo set account=?,eamil=?,introduce=?,url=? where uid=?";
        try {
            count = this.executeUpdate(sql, userInfo.getAccount(), userInfo.getEamil(), userInfo.getIntroduce(), userInfo.getUrl(), userInfo.getUid());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        }
        return count;
    }

    /**
     * 根据用户id修改用户密码
     *
     * @param userInfo 用户实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateUserPasswordByUid(UserInfo userInfo) {
        int count = 0;
        String sql = "update userInfo set password=? where uid=?";
        try {
            count = this.executeUpdate(sql, userInfo.getPassword(), userInfo.getUid());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        }
        return count;
    }

    /**
     * 根据用户id修改手机号
     *
     * @param userInfo 用户实体类对象
     */
    @Override
    public int updateUserTelephoneByUid(UserInfo userInfo) {
        int count = 0;
        String sql = "update userInfo set telephone_number=? where uid=?";
        try {
            count = this.executeUpdate(sql, userInfo.getTelephoneNumber(), userInfo.getUid());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        }
        return count;
    }

    /**
     * 根据用户id修改头像
     *
     * @param userInfo 用户实体类对象
     */
    @Override
    public int updateUserHeadImgByUid(UserInfo userInfo) {
        int count = 0;
        String sql = "update userInfo set avatar_address=? where uid=?";
        try {
            count = this.executeUpdate(sql, userInfo.getAvatarAddress(), userInfo.getUid());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行过程中有误!" + e.getMessage());
        }
        return count;
    }
}
