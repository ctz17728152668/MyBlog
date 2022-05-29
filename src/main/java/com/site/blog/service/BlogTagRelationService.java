package com.site.blog.service;

import com.site.blog.entity.BlogInfo;
import com.site.blog.entity.BlogTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface BlogTagRelationService extends IService<BlogTagRelation> {

    /**
     * 移除博客原来的标签 并保存新的博客标签
     * @param blogTagIds
     * @param blogInfo
     */
    void removeAndsaveBatch(List<Integer> blogTagIds, BlogInfo blogInfo);

}
