package com.site.blog.service;

import com.site.blog.entity.BlogCategory;
import com.baomidou.mybatisplus.extension.service.IService;


public interface BlogCategoryService extends IService<BlogCategory> {

    public boolean clearCategory(BlogCategory blogCategory);

}
