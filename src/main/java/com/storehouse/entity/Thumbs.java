package com.storehouse.entity;

/**
 * 创建点赞实体类
 */
public class Thumbs {
    private int thumbsId;
    private int thumbsUid;
    private int thumbsTopicld;

    private int thumbsCount;

    public int getThumbsId() {
        return thumbsId;
    }

    public void setThumbsId(int thumbsId) {
        this.thumbsId = thumbsId;
    }

    public int getThumbsUid() {
        return thumbsUid;
    }

    public void setThumbsUid(int thumbsUid) {
        this.thumbsUid = thumbsUid;
    }

    public int getThumbsTopicld() {
        return thumbsTopicld;
    }

    public void setThumbsTopicld(int thumbsTopicld) {
        this.thumbsTopicld = thumbsTopicld;
    }

    public int getThumbsCount() {
        return thumbsCount;
    }

    public void setThumbsCount(int thumbsCount) {
        this.thumbsCount = thumbsCount;
    }
}
