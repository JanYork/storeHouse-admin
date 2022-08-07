package com.storehouse.entity;

import java.util.List;

public class Pages {
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数 计算总页数
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        if (this.totalCount > 0) {
            // 计算总页数 总页数 = 总记录数 / 每页记录数 余数为0时 取整数 否则取整数+1
            this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Topic> getListTopic() {
        return listTopic;
    }

    public void setListTopic(List<Topic> listTopic) {
        this.listTopic = listTopic;
    }

    //每页显示的记录数
    private int pageSize;
    //当前页
    private int currentPage;
    //总记录数
    private int totalCount;
    //总页数
    private int totalPage;
    /**
     * 分页显示主题列表
     */
    private List<Topic> listTopic;
}
