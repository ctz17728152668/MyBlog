package com.c1z.blog.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDetailVO {
    private Long blogId;

    private String blogTitle;

    private Integer blogCategoryId;

    private Integer commentCount;

    private String blogCategoryIcon;

    private String blogCategoryName;

    private String blogCoverImage;

    private Long blogViews;

    private List<String> blogTags;

    private String blogContent;

    private Integer enableComment;

    private Date createTime;


}
