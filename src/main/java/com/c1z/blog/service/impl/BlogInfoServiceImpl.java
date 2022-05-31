package com.c1z.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.c1z.blog.constants.BlogStatusEnum;
import com.c1z.blog.constants.DeleteStatusEnum;
import com.c1z.blog.mapper.BlogCommentMapper;
import com.c1z.blog.mapper.BlogInfoMapper;
import com.c1z.blog.mapper.BlogTagRelationMapper;
import com.c1z.blog.pojo.vo.SimpleBlogListVO;
import com.c1z.blog.entity.BlogComment;
import com.c1z.blog.entity.BlogInfo;
import com.c1z.blog.entity.BlogTagRelation;
import com.c1z.blog.service.BlogInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Autowired
    private BlogCommentMapper blogCommentMapper;


    @Override
    public List<SimpleBlogListVO> getNewBlog() {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        Page<BlogInfo> page = new Page<>(1,5);
        blogInfoMapper.selectPage(page,new QueryWrapper<BlogInfo>()
                .lambda()
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .orderByDesc(BlogInfo::getCreateTime));
        for (BlogInfo blogInfo : page.getRecords()){
            SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
            BeanUtils.copyProperties(blogInfo, simpleBlogListVO);
            simpleBlogListVOS.add(simpleBlogListVO);
        }
        return simpleBlogListVOS;
    }

    @Override
    public List<SimpleBlogListVO> getHotBlog() {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        Page<BlogInfo> page = new Page<>(1,5);
        blogInfoMapper.selectPage(page,new QueryWrapper<BlogInfo>()
                .lambda()
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .orderByDesc(BlogInfo::getBlogViews));
        for (BlogInfo blogInfo : page.getRecords()){
            SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
            BeanUtils.copyProperties(blogInfo, simpleBlogListVO);
            simpleBlogListVOS.add(simpleBlogListVO);
        }
        return simpleBlogListVOS;
    }

    //
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearBlogInfo(Long blogId) {
        if (SqlHelper.retBool(blogInfoMapper.deleteById(blogId))){
            QueryWrapper<BlogTagRelation> tagRelationWrapper = new QueryWrapper<>();
            tagRelationWrapper.lambda().eq(BlogTagRelation::getBlogId,blogId);
            blogTagRelationMapper.delete(tagRelationWrapper);
            QueryWrapper<BlogComment> commentWrapper = new QueryWrapper<>();
            commentWrapper.lambda().eq(BlogComment::getBlogId,blogId);
            blogCommentMapper.delete(commentWrapper);
            return true;
        }
        return false;
    }
}
