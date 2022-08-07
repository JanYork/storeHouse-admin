package com.storehouse.entity;

import java.util.Date;

/**
 * 用户表的实体类
 *
 * @author nicole
 */
public class UserInfo {
    //用户id
    private int uid;
    //昵称
    private String account;
    //密码
    private String password;
    //电话号码
    private String telephoneNumber;
    //头像地址
    private String avatarAddress;
    //邮箱
    private String eamil;
    //介绍
    private String introduce;
    //注册时间
    private Date createTime;
    //话题数目
    private int topicCount;
    //评论数目
    private int commentCount;
    //唯一身份标识
    private String nanoId;
    //网址
    private String url;

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    //用户状态
    private int userState;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getNanoId() {
        return nanoId;
    }

    public void setNanoId(String nanoId) {
        this.nanoId = nanoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
