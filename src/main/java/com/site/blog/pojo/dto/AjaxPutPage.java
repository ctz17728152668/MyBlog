package com.site.blog.pojo.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 前台传入layui格式分页数据
 * page和limit
 * 转换成mp的Page分页对象
 * @param <T>
 */
public class AjaxPutPage<T> {
    /**
     * 当前页码
     */
    Integer page;

    /**
     * 每页显示
     */
    Integer limit;

    /**
     * 从多少开始
     */
    Integer start;

    /**
     * 条件类
     */
    T condition;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        this.start = (this.page - 1) * this.limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }


    public Page<T> putPageToPage() {
        return new Page<>(this.page, this.limit);
    }

    @Override
    public String toString() {
        return "AjaxPutPage{" +
                "page=" + page +
                ", limit=" + limit +
                ", start=" + start +
                ", condition=" + condition +
                '}';
    }
}
