package com.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 */
@SuppressWarnings("all")
public class PageUtils implements Serializable {
    private static final long serialVersionUID = -8741766802354222579L;

    //每页数量
    private int pageSize;

    //当前是多少页
    private int currentPage;

    //总数
    private int totalRecord;

    //总页数
    private int totalPage;

    //集合
    private List<?> dataList;

    public PageUtils(int pageNum, int pageSize, List<?> dataList){
        if (dataList.isEmpty()) {
            return;
        }
        if (pageNum < 1) {
            pageNum = 1;
        }

        //获取总数
        this.totalRecord = dataList.size();
        //获取页显示多少数据
        this.pageSize = pageSize;
        //获取总页数
        this.totalPage = this.totalRecord / this.pageSize;
        if (this.totalRecord%this.pageSize!=0) {
            this.totalPage+=1;
        }

        //当前第几页数据
        this.currentPage = this.totalPage < pageNum ? this.totalPage : pageNum;

        //起始索引
        int fromIndex = this.pageSize * (this.currentPage - 1);
        //结束索引
        int toIndex =this.pageSize * this.currentPage > this.totalRecord ?  this.totalRecord :  this.pageSize * this.currentPage;
        //根据起始索引和结束索引获取集合的数据
        this.dataList = dataList.subList(fromIndex, toIndex);

    }


    public PageUtils() {
        super();
    }

    public PageUtils(int pageSize, int currentPage, int totalRecord, int totalPage, List<?> dataList) {
        super();
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.dataList = dataList;
    }



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

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getDevList() {
        return dataList;
    }

    public void setDevList(List<?> devList) {
        this.dataList = devList;
    }
}
