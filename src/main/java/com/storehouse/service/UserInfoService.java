package com.storehouse.service;

import com.storehouse.entity.UserInfo;

import java.util.List;

/**
 * 用户实体类业务逻辑层
 *
 * @author nicole
 */
public interface UserInfoService {
    /**
     * 添加用户
     *
     * @param userInfo 用户实体类
     * @return 返回受影响的行数
     */
    int addUsertInfo(UserInfo userInfo);

    /**
     * 根据用户uid删除信息
     *
     * @param uid 用户编号
     * @return 返回受影响的行数
     */
    int delUsertInfoByUid(int uid);

    /**
     * 修改用户信息列表
     *
     * @param userInfo 实体类对象
     * @return 返回受影响的行数
     */
    int updateUsertInfo(UserInfo userInfo);

    /**
     * 查询所有用户信息
     *
     * @return list集合列表
     */
    List<UserInfo> getUsertInfoAll();

    /**
     * 据id编号查询详情信息
     *
     * @param uid 标签ID
     * @return TUsertInfo实体类对象
     */
    UserInfo getUsertInfoByUid(int uid);

    /**
     * 根据用户名查询用户信息
     *
     * @param account 用户名
     * @return 用户实体类对象
     */
    UserInfo getUsertInfoByAccount(String account);

    /**
     * 根据手机号查询用户信息
     *
     * @param telephoneNumber 手机号
     * @return 用户实体类对象
     */
    UserInfo getUsertInfoByTelephoneNumber(String telephoneNumber);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户实体类对象
     */
    UserInfo getUsertInfoByEmail(String email);

    /**
     * 根据nanoId查询用户信息
     *
     * @param nanoId nanoId
     * @return 用户实体类对象
     */
    UserInfo findUidByNanoId(String nanoId);

    /**
     * 根据用户ID查询用户头像
     *
     * @param uid 用户ID
     * @return 头像路径
     */
    String getUserHeadImgByUid(int uid);

    /**
     * 查询用户发帖的总记录数
     *
     * @param uid 用户ID
     * @return 返回受影响的行数
     */
    int getTopicCountByUid(int uid);

    /**
     * 根据用户id修改用户信息
     * @param userInfo 用户实体类对象
     * @return 返回受影响的行数
     */
    int updateUserInfoByUid(UserInfo userInfo);

    /**
     * 根据用户id修改用户密码
     * @param userInfo 用户实体类对象
     * @return 返回受影响的行数
     */
    int updateUserPasswordByUid(UserInfo userInfo);

    /**
     * 根据用户id修改手机号
     */
    int updateUserTelephoneByUid(UserInfo userInfo);

    /**
     * 根据用户id修改头像
     */
    int updateUserHeadImgByUid(UserInfo userInfo);
}
