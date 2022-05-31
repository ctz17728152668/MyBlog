package com.c1z.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 博客信息表
 * @TableName tb_blog_info
 */
@TableName(value ="tb_blog_info")
@Data
@Accessors(chain = true)
public class BlogInfo implements Serializable {
    /**
     * 博客表主键id
     */
    @TableId(type = IdType.AUTO)
    private Long blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客自定义路径url
     */
    private String blogSubUrl;

    /**
     * 博客前言
     */
    private String blogPreface;

    /**
     * 博客内容
     */
    private String blogContent;

    /**
     * 博客分类id
     */
    private Integer blogCategoryId;

    /**
     * 博客分类(冗余字段)
     */
    private String blogCategoryName;

    /**
     * 博客标签(冗余字段)
     */
    private String blogTags;

    /**
     * 0-草稿 1-发布
     */
    private Integer blogStatus;

    /**
     * 阅读量
     */
    private Long blogViews;

    /**
     * 0-允许评论 1-不允许评论
     */
    private Integer enableComment;

    /**
     * 是否删除 0=否 1=是
     */
    private Integer isDeleted;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BlogInfo other = (BlogInfo) that;
        return (this.getBlogId() == null ? other.getBlogId() == null : this.getBlogId().equals(other.getBlogId()))
            && (this.getBlogTitle() == null ? other.getBlogTitle() == null : this.getBlogTitle().equals(other.getBlogTitle()))
            && (this.getBlogSubUrl() == null ? other.getBlogSubUrl() == null : this.getBlogSubUrl().equals(other.getBlogSubUrl()))
            && (this.getBlogPreface() == null ? other.getBlogPreface() == null : this.getBlogPreface().equals(other.getBlogPreface()))
            && (this.getBlogContent() == null ? other.getBlogContent() == null : this.getBlogContent().equals(other.getBlogContent()))
            && (this.getBlogCategoryId() == null ? other.getBlogCategoryId() == null : this.getBlogCategoryId().equals(other.getBlogCategoryId()))
            && (this.getBlogCategoryName() == null ? other.getBlogCategoryName() == null : this.getBlogCategoryName().equals(other.getBlogCategoryName()))
            && (this.getBlogTags() == null ? other.getBlogTags() == null : this.getBlogTags().equals(other.getBlogTags()))
            && (this.getBlogStatus() == null ? other.getBlogStatus() == null : this.getBlogStatus().equals(other.getBlogStatus()))
            && (this.getBlogViews() == null ? other.getBlogViews() == null : this.getBlogViews().equals(other.getBlogViews()))
            && (this.getEnableComment() == null ? other.getEnableComment() == null : this.getEnableComment().equals(other.getEnableComment()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBlogId() == null) ? 0 : getBlogId().hashCode());
        result = prime * result + ((getBlogTitle() == null) ? 0 : getBlogTitle().hashCode());
        result = prime * result + ((getBlogSubUrl() == null) ? 0 : getBlogSubUrl().hashCode());
        result = prime * result + ((getBlogPreface() == null) ? 0 : getBlogPreface().hashCode());
        result = prime * result + ((getBlogContent() == null) ? 0 : getBlogContent().hashCode());
        result = prime * result + ((getBlogCategoryId() == null) ? 0 : getBlogCategoryId().hashCode());
        result = prime * result + ((getBlogCategoryName() == null) ? 0 : getBlogCategoryName().hashCode());
        result = prime * result + ((getBlogTags() == null) ? 0 : getBlogTags().hashCode());
        result = prime * result + ((getBlogStatus() == null) ? 0 : getBlogStatus().hashCode());
        result = prime * result + ((getBlogViews() == null) ? 0 : getBlogViews().hashCode());
        result = prime * result + ((getEnableComment() == null) ? 0 : getEnableComment().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", blogId=").append(blogId);
        sb.append(", blogTitle=").append(blogTitle);
        sb.append(", blogSubUrl=").append(blogSubUrl);
        sb.append(", blogPreface=").append(blogPreface);
        sb.append(", blogContent=").append(blogContent);
        sb.append(", blogCategoryId=").append(blogCategoryId);
        sb.append(", blogCategoryName=").append(blogCategoryName);
        sb.append(", blogTags=").append(blogTags);
        sb.append(", blogStatus=").append(blogStatus);
        sb.append(", blogViews=").append(blogViews);
        sb.append(", enableComment=").append(enableComment);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}