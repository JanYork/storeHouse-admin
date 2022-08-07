package com.storehouse.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 发帖实体类
 *
 * @author nicole
 */
public class Topic {
    //话题 id
    private int topicId;
    //话题标题
    private String title;
    //话题内容
    private String topicContent;
    //评论总数
    private int commentCount;
    //发帖时间
    private Timestamp topicTime;
    //创建者 id
    private int topicUid;
    //所属板块 id
    private int topicCategoryId;
    //所属标签 id
    private int topicTagId;
    //浏览量
    private int browseCount;
    //点赞数目
    private int thumbsUp;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    //图片路径
    private String picPath;
    // 板块logo
    private String logo;
    // 标签名
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Timestamp getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(Timestamp topicTime) {
        this.topicTime = topicTime;
    }

    public int getTopicUid() {
        return topicUid;
    }

    public void setTopicUid(int topicUid) {
        this.topicUid = topicUid;
    }

    public int getTopicCategoryId() {
        return topicCategoryId;
    }

    public void setTopicCategoryId(int topicCategoryId) {
        this.topicCategoryId = topicCategoryId;
    }

    public int getTopicTagId() {
        return topicTagId;
    }

    public void setTopicTagId(int topicTagId) {
        this.topicTagId = topicTagId;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

}
