package com.c1z.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 标签使用次数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogTagCount {
    private Integer tagId;

    private String tagName;

    private long tagCount;



}
