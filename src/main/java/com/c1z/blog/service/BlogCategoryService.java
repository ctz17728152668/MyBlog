package com.c1z.blog.service;

import com.c1z.blog.entity.BlogCategory;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BlogCategoryService extends IService<BlogCategory> {

    public boolean clearCategory(BlogCategory blogCategory);

}
