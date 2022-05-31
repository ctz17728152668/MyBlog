package com.c1z.blog.service;

import com.c1z.blog.entity.BlogTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.c1z.blog.entity.BlogTagCount;

import java.util.List;

public interface BlogTagService extends IService<BlogTag> {

    List<BlogTagCount> getBlogTagCountForIndex();

    boolean clearTag(Integer tagId);

}
