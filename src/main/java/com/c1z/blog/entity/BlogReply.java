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


@TableName(value ="tb_blog_reply")
@Data
@Accessors(chain = true)
public class BlogReply implements Serializable {

    @TableId(value = "reply_id",type = IdType.AUTO)
    private Integer replyId;


    private Integer blogId;


    private Integer commentId;


    private String fromName;


    private String toName;


    private String replyBody;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyCreateTime;


    private Integer replyStatus;


    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}