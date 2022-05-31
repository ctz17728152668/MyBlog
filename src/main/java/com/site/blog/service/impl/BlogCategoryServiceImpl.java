package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.dao.BlogCategoryMapper;
import com.site.blog.dao.BlogInfoMapper;
import com.site.blog.entity.BlogCategory;
import com.site.blog.entity.BlogInfo;
import com.site.blog.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    /**
     * 删除博客分类
     * @param blogCategory
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearCategory(BlogCategory blogCategory) {
        //把所有含有当前 分类的博客 的分类设置为默认分类
        BlogInfo blogInfo = new BlogInfo()
                .setBlogCategoryId(Integer.valueOf(SysConfigConstants.DEFAULT_CATEGORY.getConfigField()))
                .setBlogCategoryName(SysConfigConstants.DEFAULT_CATEGORY.getConfigName());
        LambdaUpdateWrapper<BlogInfo> updateWrapper = Wrappers.<BlogInfo>lambdaUpdate()
                .eq(BlogInfo::getBlogCategoryId, blogCategory.getCategoryId());
        blogInfoMapper.update(blogInfo, updateWrapper);
        return retBool(baseMapper.deleteById(blogCategory.getCategoryId()));
    }
}
