package com.c1z.blog.service.impl;

import com.c1z.blog.entity.BlogComment;
import com.c1z.blog.mapper.BlogCommentMapper;
import com.c1z.blog.service.BlogCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

}
