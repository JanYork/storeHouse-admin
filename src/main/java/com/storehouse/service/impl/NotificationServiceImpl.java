package com.storehouse.service.impl;

import com.storehouse.dao.NotificationDao;
import com.storehouse.dao.impl.NotificationDaoImpl;
import com.storehouse.entity.Notification;
import com.storehouse.service.NotificationService;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * 通知实体类业务逻辑层的实现类
 * @author nicole
 */
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);
    /**
     * 添加通知
     * @param notification 通知实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addNotification(Notification notification) {
        //获取连接对象
        Connection conn= BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int  result=new NotificationDaoImpl(conn).addNotification(notification);
        //关闭数据库连接
        BaseUtils.closeAll(null,null,conn);
        return  result;
    }

    /**
     * 根据通知的编号删除信息
     *
     * @param nid 通知编号
     * @return 返回受影响的行数
     */
    @Override
    public int delNotificationByNid(int nid) {
        int  result;
        //获取连接对象
        Connection conn= BaseUtils.getConnection();
        //创建数据访问层对象
        NotificationDao notificationDao = new NotificationDaoImpl(conn);
        result=notificationDao.delNotificationByNid(nid);
        BaseUtils.closeAll(null,null,conn);
        return  result;
    }

    /**
     * 修改通知信息列表
     *
     * @param notification 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateNotification(Notification notification) {
        //获取连接对象
        Connection conn= BaseUtils.getConnection();
        //调用数据访问层的添加方法
        int  result=new NotificationDaoImpl(conn).updateNotification(notification);
        //关闭数据库连接
        BaseUtils.closeAll(null,null,conn);
        return  result;
    }

    /**
     * 查询所有通知信息
     *
     * @return list集合列表
     */
    @Override
    public List<Notification> getNotificationAll() {
        //获取连接对象
        Connection conn= BaseUtils.getConnection();
        //调用数据访问层的添加方法
        List<Notification> list=new NotificationDaoImpl(conn).getNotificationAll();
        //关闭数据库连接
        BaseUtils.closeAll(null,null,conn);
        return  list;
    }

    /**
     * 据nid编号查询详情信息
     *
     * @param nid 标签ID
     * @return Notification实体类对象
     */
    @Override
    public Notification getNotificationByNid(int nid) {
        //获取连接对象
        Connection conn= BaseUtils.getConnection();
        //调用数据访问层的按tagId查询的方法
        Notification notification = new NotificationDaoImpl(conn).getNotificationByNid(nid);
        //关闭数据库连接
        BaseUtils.closeAll(null,null,conn);
        return notification;
    }
}
