package com.site.blog.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应前台json数据
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCode;
    private String message;
    private T data;

}
