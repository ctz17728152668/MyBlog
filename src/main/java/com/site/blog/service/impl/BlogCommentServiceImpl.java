package com.site.blog.service.impl;

import com.site.blog.entity.BlogComment;
import com.site.blog.dao.BlogCommentMapper;
import com.site.blog.service.BlogCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

}
