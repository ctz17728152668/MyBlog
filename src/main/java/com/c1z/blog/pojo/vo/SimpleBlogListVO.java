package com.c1z.blog.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleBlogListVO implements Serializable {

    private Long blogId;

    private String blogTitle;
}
