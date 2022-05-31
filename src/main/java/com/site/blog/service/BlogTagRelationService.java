package com.site.blog.service;

import com.site.blog.entity.BlogInfo;
import com.site.blog.entity.BlogTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface BlogTagRelationService extends IService<BlogTagRelation> {

    void removeAndsaveBatch(List<Integer> blogTagIds, BlogInfo blogInfo);

}
