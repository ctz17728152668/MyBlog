package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.entity.BlogInfo;
import com.site.blog.pojo.vo.SimpleBlogListVO;

import java.util.List;


public interface BlogInfoService extends IService<BlogInfo> {

    /**
     * 返回最新的五条文章列表
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:04
     */
    List<SimpleBlogListVO> getNewBlog();

    /**
     * 返回点击量最多的五条文章
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:15
     */
    List<SimpleBlogListVO> getHotBlog();

    /**
     * 清除文章
     *
     * @param blogId
     * @return boolean
     * @date 2020/1/29 21:54
     */
    boolean clearBlogInfo(Long blogId);

}
