package com.c1z.blog.service;

import com.c1z.blog.entity.BlogInfo;
import com.c1z.blog.entity.BlogTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface BlogTagRelationService extends IService<BlogTagRelation> {

    void removeAndsaveBatch(List<Integer> blogTagIds, BlogInfo blogInfo);

}
