package com.c1z.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 博客跟标签的关系表
 * @TableName tb_blog_tag_relation
 */
@TableName(value ="tb_blog_tag_relation")
@Data
@Accessors(chain = true)
public class BlogTagRelation implements Serializable {
    /**
     * 关系表id
     */
    @TableId(type = IdType.AUTO)
    private Long relationId;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}