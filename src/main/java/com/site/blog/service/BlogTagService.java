package com.site.blog.service;

import com.site.blog.entity.BlogTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.entity.BlogTagCount;

import java.util.List;

public interface BlogTagService extends IService<BlogTag> {

    List<BlogTagCount> getBlogTagCountForIndex();

    boolean clearTag(Integer tagId);

}
