package com.storehouse.entity;

/**
 * 后台管理实体类
 */
public class Config {
    private int cid;
    private String smtpEamil;
    private String smtpPwd;
    private String baduAppKey;
    private String baiduSecretKey;
    private String geetestId;
    private String geetestKey;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSmtpEamil() {
        return smtpEamil;
    }

    public void setSmtpEamil(String smtpEamil) {
        this.smtpEamil = smtpEamil;
    }

    public String getSmtpPwd() {
        return smtpPwd;
    }

    public void setSmtpPwd(String smtpPwd) {
        this.smtpPwd = smtpPwd;
    }

    public String getBaduAppKey() {
        return baduAppKey;
    }

    public void setBaduAppKey(String baduAppKey) {
        this.baduAppKey = baduAppKey;
    }

    public String getBaiduSecretKey() {
        return baiduSecretKey;
    }

    public void setBaiduSecretKey(String baiduSecretKey) {
        this.baiduSecretKey = baiduSecretKey;
    }

    public String getGeetestId() {
        return geetestId;
    }

    public void setGeetestId(String geetestId) {
        this.geetestId = geetestId;
    }

    public String getGeetestKey() {
        return geetestKey;
    }

    public void setGeetestKey(String geetestKey) {
        this.geetestKey = geetestKey;
    }
}
