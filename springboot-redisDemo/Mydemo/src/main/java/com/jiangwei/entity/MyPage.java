package com.jiangwei.entity;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/16
 */
public class MyPage implements Serializable {
    private int pageNum ;
    private int pageSize ;
    private long totalNum ;
    private int size ;
    private List pageData ;

    public MyPage() {
    }

    public MyPage(PageInfo pageInfo) {
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalNum = pageInfo.getTotal();
        this.size = pageInfo.getSize();
        this.pageData = pageInfo.getList();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalNum=" + totalNum +
                ", size=" + size +
                ", pageData=" + pageData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPage myPage = (MyPage) o;
        return pageNum == myPage.pageNum &&
                pageSize == myPage.pageSize &&
                totalNum == myPage.totalNum &&
                size == myPage.size &&
                Objects.equals(pageData, myPage.pageData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pageNum, pageSize, totalNum, size,  pageData);
    }
}
