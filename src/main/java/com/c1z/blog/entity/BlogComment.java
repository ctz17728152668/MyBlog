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
     * 评论人的邮箱
     */
    private String email;

    /**
     * 网址
     */
    private String websiteUrl;

    /**
     * 评论内容
     */
    private String commentBody;

    /**
     * 评论提交时间
     */
    private Date commentCreateTime;

    /**
     * 评论时的ip地址
     */
    private String commentatorIp;

    /**
     * 回复内容
     */
    private String replyBody;

    /**
     * 回复时间
     */
    private Date replyCreateTime;

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
        BlogComment other = (BlogComment) that;
        return (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
            && (this.getBlogId() == null ? other.getBlogId() == null : this.getBlogId().equals(other.getBlogId()))
            && (this.getCommentator() == null ? other.getCommentator() == null : this.getCommentator().equals(other.getCommentator()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getWebsiteUrl() == null ? other.getWebsiteUrl() == null : this.getWebsiteUrl().equals(other.getWebsiteUrl()))
            && (this.getCommentBody() == null ? other.getCommentBody() == null : this.getCommentBody().equals(other.getCommentBody()))
            && (this.getCommentCreateTime() == null ? other.getCommentCreateTime() == null : this.getCommentCreateTime().equals(other.getCommentCreateTime()))
            && (this.getCommentatorIp() == null ? other.getCommentatorIp() == null : this.getCommentatorIp().equals(other.getCommentatorIp()))
            && (this.getReplyBody() == null ? other.getReplyBody() == null : this.getReplyBody().equals(other.getReplyBody()))
            && (this.getReplyCreateTime() == null ? other.getReplyCreateTime() == null : this.getReplyCreateTime().equals(other.getReplyCreateTime()))
            && (this.getCommentStatus() == null ? other.getCommentStatus() == null : this.getCommentStatus().equals(other.getCommentStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getBlogId() == null) ? 0 : getBlogId().hashCode());
        result = prime * result + ((getCommentator() == null) ? 0 : getCommentator().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getWebsiteUrl() == null) ? 0 : getWebsiteUrl().hashCode());
        result = prime * result + ((getCommentBody() == null) ? 0 : getCommentBody().hashCode());
        result = prime * result + ((getCommentCreateTime() == null) ? 0 : getCommentCreateTime().hashCode());
        result = prime * result + ((getCommentatorIp() == null) ? 0 : getCommentatorIp().hashCode());
        result = prime * result + ((getReplyBody() == null) ? 0 : getReplyBody().hashCode());
        result = prime * result + ((getReplyCreateTime() == null) ? 0 : getReplyCreateTime().hashCode());
        result = prime * result + ((getCommentStatus() == null) ? 0 : getCommentStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", blogId=").append(blogId);
        sb.append(", commentator=").append(commentator);
        sb.append(", email=").append(email);
        sb.append(", websiteUrl=").append(websiteUrl);
        sb.append(", commentBody=").append(commentBody);
        sb.append(", commentCreateTime=").append(commentCreateTime);
        sb.append(", commentatorIp=").append(commentatorIp);
        sb.append(", replyBody=").append(replyBody);
        sb.append(", replyCreateTime=").append(replyCreateTime);
        sb.append(", commentStatus=").append(commentStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}