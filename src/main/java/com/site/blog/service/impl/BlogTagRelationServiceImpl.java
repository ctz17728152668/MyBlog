package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.entity.BlogInfo;
import com.site.blog.entity.BlogTagRelation;
import com.site.blog.dao.BlogTagRelationMapper;
import com.site.blog.service.BlogTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BlogTagRelationServiceImpl extends ServiceImpl<BlogTagRelationMapper, BlogTagRelation> implements BlogTagRelationService {

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAndsaveBatch(List<Integer> blogTagIds, BlogInfo blogInfo) {
        Long blogId = blogInfo.getBlogId();
        List<BlogTagRelation> list = blogTagIds.stream().map(blogTagId -> new BlogTagRelation()
                .setTagId(blogTagId)
                .setBlogId(blogId)).collect(Collectors.toList());
        blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>()
                .lambda()
                .eq(BlogTagRelation::getBlogId, blogInfo.getBlogId()));
        for (BlogTagRelation item : list) {
            blogTagRelationMapper.insert(item);
        }
    }
}
