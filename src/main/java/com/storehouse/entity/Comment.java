package com.storehouse.entity;

import java.sql.Date;

/**
 * 用户评论实体类
 */
public class Comment {
    private int commentId;
    private String content;
    private int commentUid;
    private int commentTopicId;
    private Date commentTime;
    private String commentIp;
    private String commentEquipment;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(int commentUid) {
        this.commentUid = commentUid;
    }

    public int getCommentTopicId() {
        return commentTopicId;
    }

    public void setCommentTopicId(int commentTopicId) {
        this.commentTopicId = commentTopicId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentIp() {
        return commentIp;
    }

    public void setCommentIp(String commentIp) {
        this.commentIp = commentIp;
    }

    public String getCommentEquipment() {
        return commentEquipment;
    }

    public void setCommentEquipment(String commentEquipment) {
        this.commentEquipment = commentEquipment;
    }
}
