package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.entity.BlogInfo;
import com.site.blog.pojo.vo.SimpleBlogListVO;

import java.util.List;


public interface BlogInfoService extends IService<BlogInfo> {

    /**
     * 返回最新的五条文章列表
     * 主页右侧显示
     */
    List<SimpleBlogListVO> getNewBlog();

    /**
     * 返回点击量最多的五条文章
     * 主页右侧显示
     */
    List<SimpleBlogListVO> getHotBlog();

    /**
     * 清除文章
     */
    boolean clearBlogInfo(Long blogId);

}
