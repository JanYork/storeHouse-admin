package com.storehouse.entity;

/**
 * 标签分类的实体类
 *
 * @author nicole
 */
public class Tag {
    //标签 id
    private int tagId;
    //标签名称
    private String tagName;
    //标签话题数
    private int topicCount;
    //标签所属板块 id
    private int tagCategoryId;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public int getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(int tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }
}
