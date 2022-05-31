package com.c1z.blog.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 响应前台分页信息
 * @param <T>
 */
@Data
public class AjaxResultPage<T> implements Serializable {

    //状态码
    private int code;

    //提示消息
    private String msg;

    //总条数
    private long count;

    //表格数据
    private transient List<T> data;

}
