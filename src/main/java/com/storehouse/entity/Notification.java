package com.storehouse.entity;

import java.util.Date;

/**
 * 通知的实体类
 *
 * @author nicole
 */
public class Notification {
    //通知自增 id
    private int nid;
    //通知操作
    private String action;
    //附带的主题 id
    private int subjectId;
    //用户 id
    private int userId;
    //发送通知者 id
    private int fromUid;
    //通知阅读时间
    private Date readAt;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
}
