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
 * 评论信息表
 * @TableName tb_blog_comment
 */
@TableName(value ="tb_blog_comment")
@Data
@Accessors(chain = true)
public class BlogComment implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long commentId;

    /**
     * 关联的blog主键
     */
    private Long blogId;

    /**
     * 评论者名称
     */
    private String commentator;


    /**
     * 评论内容
     */
    private String commentBody;

    /**
     * 评论提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentCreateTime;


    /**
     * 是否审核通过 0-未审核 1-审核通过
     */
    private Integer commentStatus;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}