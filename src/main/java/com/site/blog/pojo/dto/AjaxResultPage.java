package com.site.blog.pojo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 响应前台分页信息
 * @param <T>
 */
public class AjaxResultPage<T> implements Serializable {

    //状态码
    private int code;

    //提示消息
    private String msg;

    //总条数
    private long count;

    //表格数据
    private transient List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxResultPage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
