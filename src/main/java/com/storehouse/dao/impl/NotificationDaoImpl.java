package com.storehouse.dao.impl;

import com.storehouse.dao.BaseDao;
import com.storehouse.dao.NotificationDao;
import com.storehouse.entity.Notification;
import com.storehouse.utils.BaseUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通知数据访问层的实现类
 *
 * @author nicole
 */
public class NotificationDaoImpl extends BaseDao implements NotificationDao {
    private static final Logger logger = Logger.getLogger(BaseUtils.class);

    /**
     * 通过构造传conn值过来
     */
    public NotificationDaoImpl(Connection conn) {
        super(conn);
    }

    /**
     * 添加通知
     *
     * @param notification 通知实体类
     * @return 返回受影响的行数
     */
    @Override
    public int addNotification(Notification notification) {
        //编写SQL语句
        String sql = "insert into notification(action,subject_id,user_Id,from_uid,read_at) values(?,?,?,?,?)";
        return this.executeUpdate(sql, notification.getAction(), notification.getSubjectId(), notification.getUserId(),
                notification.getFromUid(), notification.getReadAt());
    }

    /**
     * 根据通知的编号删除信息
     *
     * @param nid 通知编号
     * @return 返回受影响的行数
     */
    @Override
    public int delNotificationByNid(int nid) {
        //编写SQL语句
        String sql = "delete from notification where nid=?";
        return this.executeUpdate(sql, nid);
    }

    /**
     * 修改通知信息列表
     *
     * @param notification 实体类对象
     * @return 返回受影响的行数
     */
    @Override
    public int updateNotification(Notification notification) {
        //编写SQL语句
        String sql = "update notification set action=?,subject_id=?,user_Id=?,from_uid=?,read_at=? where nid=?";
        return this.executeUpdate(sql, notification.getAction(), notification.getSubjectId(), notification.getUserId(),
                notification.getFromUid(), notification.getReadAt(), notification.getNid());
    }

    /**
     * 查询所有通知信息
     *
     * @return list集合列表
     */
    @Override
    public List<Notification> getNotificationAll() {
        List<Notification> list = new ArrayList<>();
        ResultSet rs = null;
        //编写查询语句
        String sql = "select * from notification";
        try {
            //执行
            rs = this.executeQuery(sql);
            //遍历循环数据
            while (rs.next()) {
                //创建实体类对象
                Notification notification = new Notification();
                notification.setNid(rs.getInt("nid"));
                notification.setAction(rs.getString("action"));
                notification.setSubjectId(rs.getInt("subject_id"));
                notification.setUserId(rs.getInt("user_Id"));
                notification.setFromUid(rs.getInt("from_uid"));
                notification.setReadAt(rs.getDate("read_at"));
                //将内容添加到集合中
                list.add(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行过程中有误!" + e.getMessage());
        } finally {
            //关闭数据库连接
            BaseUtils.closeAll(rs, null, null);
        }
        //最后返回list集合
        return list;
    }

    /**
     * 据nid编号查询详情信息
     *
     * @param nid 标签ID
     * @return Notification实体类对象
     */
    @Override
    public Notification getNotificationByNid(int nid) {
        ResultSet rs = null;
        String sql = "select * from notification where nid=?";
        Notification notification = null;
        try {
            rs = this.executeQuery(sql, nid);
            if (rs.next()) {
                notification = new Notification();
                notification.setNid(rs.getInt("nid"));
                notification.setAction(rs.getString("action"));
                notification.setSubjectId(rs.getInt("subject_id"));
                notification.setUserId(rs.getInt("user_Id"));
                notification.setFromUid(rs.getInt("from_uid"));
                notification.setReadAt(rs.getDate("read_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(rs, null, null);
        }
        return notification;
    }
}
