package com.c1z.blog.pojo.vo;

import com.c1z.blog.entity.BlogComment;
import com.c1z.blog.entity.BlogReply;
import lombok.Data;

import java.util.List;

@Data
public class BlogCommentVo extends BlogComment {

    private List<BlogReply> replyList;
}
