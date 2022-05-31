package com.c1z.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1z.blog.constants.DeleteStatusEnum;
import com.c1z.blog.constants.SysConfigConstants;
import com.c1z.blog.dao.BlogTagMapper;
import com.c1z.blog.entity.BlogInfo;
import com.c1z.blog.entity.BlogTag;
import com.c1z.blog.entity.BlogTagCount;
import com.c1z.blog.entity.BlogTagRelation;
import com.c1z.blog.service.BlogInfoService;
import com.c1z.blog.service.BlogTagRelationService;
import com.c1z.blog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

    @Autowired
    private BlogTagRelationService blogTagRelationService;


    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 获取所有被博客使用的标签以及使用次数
     * @return
     */
    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(BlogTag::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        List<BlogTag> list = baseMapper.selectList(queryWrapper);
        return list.stream()
                .map(blogTag -> new BlogTagCount()
                        .setTagId(blogTag.getTagId())
                        .setTagName(blogTag.getTagName())
                        .setTagCount(
                                blogTagRelationService.count(new QueryWrapper<BlogTagRelation>()
                                        .lambda()
                                        .eq(BlogTagRelation::getTagId,blogTag.getTagId()))
                        ))
                .collect(Collectors.toList());
    }

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearTag(Integer tagId) {
        LambdaQueryWrapper<BlogTagRelation> queryWrapper = Wrappers.<BlogTagRelation>lambdaQuery()
                .eq(BlogTagRelation::getTagId,tagId);
        List<BlogTagRelation> tagRelationList = blogTagRelationService.list(queryWrapper);

        // 获取所有使用该标签的博客
        List<BlogInfo> infoList = tagRelationList.stream()
                .map(tagRelation -> new BlogInfo()
                        .setBlogId(tagRelation.getBlogId())
                        .setBlogTags(SysConfigConstants.DEFAULT_TAG.getConfigName())).collect(Collectors.toList());
        List<Long> blogIds = infoList.stream().map(BlogInfo::getBlogId).collect(Collectors.toList());


        // 改为默认标签
        List<BlogTagRelation> tagRelations = tagRelationList.stream()
                .map(tagRelation -> new BlogTagRelation()
                        .setBlogId(tagRelation.getBlogId())
                        .setTagId(Integer.valueOf(SysConfigConstants.DEFAULT_TAG.getConfigField())))
                .collect(Collectors.toList());


        blogInfoService.updateBatchById(infoList);


        // 删除需要删除的标签
        blogTagRelationService.remove(new QueryWrapper<BlogTagRelation>()
                .lambda()
                .in(BlogTagRelation::getBlogId,blogIds));
        blogTagRelationService.saveBatch(tagRelations);
        return retBool(baseMapper.deleteById(tagId));
    }
}
