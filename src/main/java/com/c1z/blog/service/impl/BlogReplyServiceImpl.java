package com.c1z.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1z.blog.entity.BlogReply;
import com.c1z.blog.service.BlogReplyService;
import com.c1z.blog.mapper.BlogReplyMapper;
import org.springframework.stereotype.Service;

/**
* @author 1
* @description 针对表【tb_blog_reply】的数据库操作Service实现
* @createDate 2022-05-31 20:12:03
*/
@Service
public class BlogReplyServiceImpl extends ServiceImpl<BlogReplyMapper, BlogReply>
    implements BlogReplyService{

}




