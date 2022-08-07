package com.storehouse.entity;

/**
 * 创建板块实体对象
 */
public class Category {
    private int categoryId;
    private String categoryTitle;
    private int topicCount;
    private int commentCount;
    private String description;
    private String CategoryLogo;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryLogo() {
        return CategoryLogo;
    }

    public void setCategoryLogo(String categoryLogo) {
        CategoryLogo = categoryLogo;
    }
}
