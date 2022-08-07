package com.storehouse.dao;

import com.storehouse.entity.Notification;

import java.util.List;

/**
 * 标签数据访问层的接口
 *
 * @author nicole
 */
public interface NotificationDao {
    /**
     * 添加通知
     *
     * @param notification 通知实体类
     * @return 返回受影响的行数
     */
    int addNotification(Notification notification);

    /**
     * 根据通知的编号删除信息
     *
     * @param nid 通知编号
     * @return 返回受影响的行数
     */
    int delNotificationByNid(int nid);

    /**
     * 修改通知信息列表
     *
     * @param notification 实体类对象
     * @return 返回受影响的行数
     */
    int updateNotification(Notification notification);

    /**
     * 查询所有通知信息
     *
     * @return list集合列表
     */
    List<Notification> getNotificationAll();

    /**
     * 据nid编号查询详情信息
     *
     * @param nid 标签ID
     * @return Notification实体类对象
     */
    Notification getNotificationByNid(int nid);
}
