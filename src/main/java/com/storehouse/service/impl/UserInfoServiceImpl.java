package com.storehouse.service.impl;

import com.storehouse.dao.UserInfoDao;
import com.storehouse.dao.impl.UserInfoDaoImpl;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * 用户业务逻辑层的实现类
 *
 * @author nicole
 */
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 添加用户
     *
     * @param userInfo 用户实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addUsertInfo(UserInfo userInfo) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int result = new UserInfoDaoImpl(conn).addUsertInfo(userInfo);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 根据用户uid删除信息
     *
     * @param uid 用户编号
     * @return 返回受影响的行数
     */
    @Override
    public int delUsertInfoByUid(int uid) {
        int result;
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //创建数据访问层对象
        UserInfoDao userInfoDao = new UserInfoDaoImpl(conn);
        result = userInfoDao.delUsertInfoByUid(uid);
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 修改用户信息列表
     *
     * @param userInfo 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateUsertInfo(UserInfo userInfo) {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的修改方法
        int result = new UserInfoDaoImpl(conn).updateUsertInfo(userInfo);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return result;
    }

    /**
     * 查询所有用户信息
     *
     * @return list集合列表
     */
    @Override
    public List<UserInfo> getUsertInfoAll() {
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的查询所有信息的方法
        List<UserInfo> list = new UserInfoDaoImpl(conn).getUsertInfoAll();
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        UserInfo userInfo = new UserInfoDaoImpl(conn).getUsertInfoByUid(uid);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        UserInfo userInfo = new UserInfoDaoImpl(conn).getUsertInfoByAccount(account);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        UserInfo userInfo = new UserInfoDaoImpl(conn).getUsertInfoByTelephoneNumber(telephoneNumber);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        UserInfo userInfo = new UserInfoDaoImpl(conn).getUsertInfoByEmail(email);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        UserInfo userInfo = new UserInfoDaoImpl(conn).findUidByNanoId(nanoId);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
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
        //获取连接对象
        Connection conn = BaseUtils.getConnection();
        //调用数据访问层的按UID查询的方法
        String headImg = new UserInfoDaoImpl(conn).getUserHeadImgByUid(uid);
        //关闭数据库连接
        BaseUtils.closeAll(null, null, conn);
        return headImg;
    }

    /**
     * 查询用户发帖的总记录数
     *
     * @param uid 用户ID
     * @return 返回受影响的行数
     */
    @Override
    public int getTopicCountByUid(int uid) {
        Connection conn = BaseUtils.getConnection();
        int count = new UserInfoDaoImpl(conn).getTopicCountByUid(uid);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }

    /**
     * 根据用户id修改用户信息
     *
     * @param userInfo 用户实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateUserInfoByUid(UserInfo userInfo) {
        Connection conn = BaseUtils.getConnection();
        int count = new UserInfoDaoImpl(conn).updateUserInfoByUid(userInfo);
        BaseUtils.closeAll(null, null, conn);
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
        Connection conn = BaseUtils.getConnection();
        int count = new UserInfoDaoImpl(conn).updateUserPasswordByUid(userInfo);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }

    /**
     * 根据用户id修改手机号
     *
     * @param userInfo 用户实体类对象
     */
    @Override
    public int updateUserTelephoneByUid(UserInfo userInfo) {
        Connection conn = BaseUtils.getConnection();
        int count = new UserInfoDaoImpl(conn).updateUserTelephoneByUid(userInfo);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }

    /**
     * 根据用户id修改头像
     *
     * @param userInfo 用户实体类对象
     */
    @Override
    public int updateUserHeadImgByUid(UserInfo userInfo) {
        Connection conn = BaseUtils.getConnection();
        int count = new UserInfoDaoImpl(conn).updateUserHeadImgByUid(userInfo);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }
}
