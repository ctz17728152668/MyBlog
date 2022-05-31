package com.site.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.pojo.dto.AjaxPutPage;
import com.site.blog.pojo.dto.AjaxResultPage;
import com.site.blog.pojo.dto.Result;
import com.site.blog.entity.BlogTag;
import com.site.blog.service.BlogTagService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台管理接口
 * 标签相关
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private BlogTagService blogTagService;


    /**
     * 跳转进入标签管理页面
     * @return
     */
    @GetMapping("/v1/tags")
    public String gotoTag(){
        return "adminLayui/tag-list";
    }

    /**
     * 查询所有标签
     * @return
     */
    @ResponseBody
    @GetMapping("/v1/tags/list")
    public Result<List<BlogTag>> tagsList(){
        LambdaQueryWrapper<BlogTag> queryWrapper = new LambdaQueryWrapper<BlogTag>()
                .eq(BlogTag::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        List<BlogTag> list = blogTagService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,list);
    }

    /**
     * 分页查询标签
     * @param ajaxPutPage
     * @param condition
     * @return
     */
    @ResponseBody
    @GetMapping("/v1/tags/paging")
    public AjaxResultPage<BlogTag> getCategoryList(AjaxPutPage<BlogTag> ajaxPutPage, BlogTag condition){
        LambdaQueryWrapper<BlogTag> queryWrapper = new LambdaQueryWrapper<>(condition)
                .eq(BlogTag::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());

        Page<BlogTag> page = ajaxPutPage.putPageToPage();
        blogTagService.page(page,queryWrapper);
        AjaxResultPage<BlogTag> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改标签状态
     * @param blogTag
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/tags/isDel")
    public Result<String> updateCategoryStatus(BlogTag blogTag){
        boolean flag = blogTagService.updateById(blogTag);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 添加新的标签
     * @param blogTag
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/tags/add")
    public Result<String> addTag(BlogTag blogTag){
        blogTag.setCreateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogTagService.save(blogTag);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 从数据库删除标签
     * @param tagId
     * @return
     * @throws RuntimeException
     */
    @ResponseBody
    @PostMapping("/v1/tags/clear")
    public Result<String> clearTag(Integer tagId) throws RuntimeException{
        if (blogTagService.clearTag(tagId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 修改标签名字
     * @param blogTag
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/tags/update")
    public Result<String> updateCategory(BlogTag blogTag) {
        BlogTag sqlBlogTag = blogTagService.getById(blogTag.getTagId());
        boolean flag = sqlBlogTag.getTagName().equals(blogTag.getTagName());
        if (!flag) {
            blogTagService.updateById(blogTag);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
