package com.c1z.blog.controller.blog;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.c1z.blog.constants.*;
import com.c1z.blog.entity.*;
import com.c1z.blog.pojo.dto.AjaxPutPage;
import com.c1z.blog.pojo.dto.AjaxResultPage;
import com.c1z.blog.pojo.dto.BlogPageCondition;
import com.c1z.blog.pojo.dto.Result;
import com.c1z.blog.pojo.vo.BlogDetailVO;
import com.c1z.blog.service.*;
import com.c1z.blog.util.PageResult;
import com.c1z.blog.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 博客前台页面
 */
@Controller
public class MyBlogController {


    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private BlogConfigService blogConfigService;

    @Autowired
    private BlogTagRelationService blogTagRelationService;

    @Autowired
    private BlogCommentService blogCommentService;



    /**
     * 进入首页
     * @param request
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, new BlogPageCondition()
                .setPageNum(1)
                .setPageName("首页")
        );
    }

    /**
     * 点击分类 搜索当前分类所有博客
     * @param request
     * @param categoryName
     * @return
     */
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return this.page(request, new BlogPageCondition()
                .setPageNum(1)
                .setPageName("分类")
                .setCategoryName(categoryName));
    }

    /**
     * 根据关键字搜索
     * @param request
     * @param keyword
     * @return
     */
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return this.page(request, new BlogPageCondition()
                .setPageNum(1)
                .setPageName("首页")
                .setKeyword(keyword)
        );
    }

    /**
     * 点击标签 查询所有该标签的博客
     * @param request
     * @param tagId
     * @return
     */
    @GetMapping({"/tag/{tagId}"})
    public String tag(HttpServletRequest request, @PathVariable("tagId") String tagId) {
        return this.page(request, new BlogPageCondition()
                .setPageNum(1)
                .setPageName("标签")
                .setTagId(tagId));
    }

    /**
     * 博客分页查询
     *
     * @param request
     * @param condition
     */
    @GetMapping({"/page"})
    public String page(HttpServletRequest request, BlogPageCondition condition) {
        //如果是第一次进入首页 则显示第一页的内容 每页5条数据
        if (Objects.isNull(condition.getPageNum())) {
            condition.setPageNum(1);
        }
        if (Objects.isNull(condition.getPageSize())) {
            condition.setPageSize(5);
        }
        Page<BlogInfo> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        //根据关键词模糊查询 博客种类 博客状态查询
        LambdaQueryWrapper<BlogInfo> sqlWrapper = Wrappers.<BlogInfo>lambdaQuery()
                .like(Objects.nonNull(condition.getKeyword()), BlogInfo::getBlogTitle, condition.getKeyword())
                .eq(Objects.nonNull(condition.getCategoryName()), BlogInfo::getBlogCategoryName, condition.getCategoryName())
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        //根据标签查询
        if (Objects.nonNull(condition.getTagId())) {
            List<BlogTagRelation> list = blogTagRelationService.list(new QueryWrapper<BlogTagRelation>()
                    .lambda().eq(BlogTagRelation::getTagId, condition.getTagId()));
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.in(BlogInfo::getBlogId, list.stream().map(BlogTagRelation::getBlogId).toArray());
            }
        }
        //根据发布博客时间降序
        sqlWrapper.orderByDesc(BlogInfo::getCreateTime);
        blogInfoService.page(page, sqlWrapper);
        PageResult blogPageResult = new PageResult(page.getRecords(), page.getTotal(), condition.getPageSize(), condition.getPageNum());


        //设置 搜索关键词 标签id 文章种类名称的回显
        if (Objects.nonNull(condition.getKeyword())) {
            request.setAttribute("keyword", condition.getKeyword());
        }
        if (Objects.nonNull(condition.getTagId())) {
            request.setAttribute("tagId", condition.getTagId());
        }
        if (Objects.nonNull(condition.getCategoryName())) {
            request.setAttribute("categoryName", condition.getCategoryName());
        }
        //搜索博客的主体内容
        request.setAttribute("blogPageResult", blogPageResult);


        //页面的名称 显示在title
        request.setAttribute("pageName", condition.getPageName());


        //热门标签 最新发布 点击最多
        request.setAttribute("newBlogs", blogInfoService.getNewBlog());
        request.setAttribute("hotBlogs", blogInfoService.getHotBlog());
        request.setAttribute("hotTags", blogTagService.getBlogTagCountForIndex());


        //系统变量
        request.setAttribute("configurations", blogConfigService.getAllConfigs());
        return "blog/index";
    }

    /**
     * 进入博客详情页面
     * @param request
     * @param blogId
     * @return
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        // 获得文章info
        BlogInfo blogInfo = blogInfoService.getById(blogId);
        //获取博客所有关联的标签id
        List<BlogTagRelation> blogTagRelations = blogTagRelationService.list(new QueryWrapper<BlogTagRelation>()
                .lambda()
                .eq(BlogTagRelation::getBlogId, blogId));

        //博客访问数+1
        blogInfoService.updateById(new BlogInfo()
                .setBlogId(blogInfo.getBlogId())
                .setBlogViews(blogInfo.getBlogViews() + 1));

        // 获得关联的标签列表
        List<Integer> tagIds;
        List<BlogTag> tagList = new ArrayList<>();
        if (!blogTagRelations.isEmpty()) {
            tagIds = blogTagRelations.stream()
                    .map(BlogTagRelation::getTagId).collect(Collectors.toList());
            tagList = blogTagService.list(new QueryWrapper<BlogTag>().lambda().in(BlogTag::getTagId, tagIds));
        }

        // 关联评论的Count
        Integer blogCommentCount = blogCommentService.count(new QueryWrapper<BlogComment>()
                .lambda()
                .eq(BlogComment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(BlogComment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .eq(BlogComment::getBlogId, blogId));

        BlogDetailVO blogDetailVO = new BlogDetailVO();
        BeanUtils.copyProperties(blogInfo, blogDetailVO);
        blogDetailVO.setCommentCount(blogCommentCount);
        request.setAttribute("blogDetailVO", blogDetailVO);
        request.setAttribute("tagList", tagList);
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", blogConfigService.getAllConfigs());
        return "blog/detail";
    }

    /**
     * 分页查询评论列表
     * @param ajaxPutPage
     * @param blogId
     * @return
     */
    @GetMapping("/blog/listComment")
    @ResponseBody
    public AjaxResultPage<BlogComment> listComment(AjaxPutPage<BlogComment> ajaxPutPage, Integer blogId) {
        Page<BlogComment> page = ajaxPutPage.putPageToPage();
        blogCommentService.page(page, new QueryWrapper<BlogComment>()
                .lambda()
                .eq(BlogComment::getBlogId, blogId)
                .eq(BlogComment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(BlogComment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .orderByDesc(BlogComment::getCommentCreateTime));
        AjaxResultPage<BlogComment> ajaxResultPage = new AjaxResultPage<>();
        ajaxResultPage.setCount(page.getTotal());
        ajaxResultPage.setData(page.getRecords());
        return ajaxResultPage;
    }


    /**
     * 评论博客
     * @param request
     * @param blogComment
     * @return
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result<String> comment(HttpServletRequest request,
                                  @Validated BlogComment blogComment) {
        blogComment.setCommentBody(blogComment.getCommentBody());
        boolean flag = blogCommentService.save(blogComment);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
