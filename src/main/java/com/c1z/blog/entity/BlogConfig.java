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
 * 
 * @TableName tb_blog_config
 */
@TableName(value ="tb_blog_config")
@Data
@Accessors(chain = true)
public class BlogConfig implements Serializable {
    /**
     * 字段名
     */
    @TableId
    private String configField;

    /**
     * 配置名
     */
    private String configName;

    /**
     * 配置项的值
     */
    private String configValue;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}