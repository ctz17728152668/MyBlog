package com.c1z.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.c1z.blog.constants.DeleteStatusEnum;
import com.c1z.blog.constants.HttpStatusEnum;
import com.c1z.blog.pojo.dto.AjaxPutPage;
import com.c1z.blog.pojo.dto.AjaxResultPage;
import com.c1z.blog.pojo.dto.Result;
import com.c1z.blog.entity.BlogCategory;
import com.c1z.blog.entity.BlogInfo;
import com.c1z.blog.service.BlogCategoryService;
import com.c1z.blog.service.BlogInfoService;
import com.c1z.blog.util.DateUtils;
import com.c1z.blog.util.ResultGenerator;
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
 * 文章分类相关
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 查询所有的文章分类
     * @return
     */
    @ResponseBody
    @GetMapping("/category/list")
    public Result<List<BlogCategory>> categoryList() {
        LambdaQueryWrapper<BlogCategory> queryWrapper = new LambdaQueryWrapper<BlogCategory>()
                .eq(BlogCategory::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        List<BlogCategory> list = blogCategoryService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, list);
    }

    /**
     * 跳转进入文章分类页面
     * @return
     */
    @GetMapping("/category")
    public String gotoBlogCategory() {
        return "adminLayui/category-list";
    }

    /**
     * 查询文章分类分页信息
     * @param ajaxPutPage
     * @param condition
     * @return
     */
    @ResponseBody
    @GetMapping("/category/paging")
    public AjaxResultPage<BlogCategory> getCategoryList(AjaxPutPage<BlogCategory> ajaxPutPage, BlogCategory condition) {
        LambdaQueryWrapper<BlogCategory> queryWrapper = new LambdaQueryWrapper<>(condition)
                .eq(BlogCategory::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        Page<BlogCategory> page = ajaxPutPage.putPageToPage();
        blogCategoryService.page(page, queryWrapper);
        AjaxResultPage<BlogCategory> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改文章分类信息   （文章分类名，排序值）
     * @param blogCategory
     * @return
     */
    @ResponseBody
    @PostMapping("/category/update")
    public Result<String> updateCategory(BlogCategory blogCategory) {
        BlogCategory sqlCategory = blogCategoryService.getById(blogCategory.getCategoryId());
        boolean flag = sqlCategory.getCategoryName().equals(blogCategory.getCategoryName());
        if (!flag) {
            BlogInfo blogInfo = new BlogInfo()
                    .setBlogCategoryId(blogCategory.getCategoryId())
                    .setBlogCategoryName(blogCategory.getCategoryName());
            LambdaQueryWrapper<BlogInfo> updateWrapper = new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getBlogCategoryId, blogCategory.getCategoryId());
            blogInfoService.update(blogInfo, updateWrapper);
            blogCategoryService.updateById(blogCategory);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 修改文章分类状态 逻辑删除
     * @param blogCategory
     * @return
     */
    @ResponseBody
    @PostMapping("/category/isDel")
    public Result<String> updateCategoryStatus(BlogCategory blogCategory) {
        boolean flag = blogCategoryService.updateById(blogCategory);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 从数据库删除文章分类信息
     * @param blogCategory
     * @return
     */
    @ResponseBody
    @PostMapping("/category/clear")
    public Result<String> clearCategory(BlogCategory blogCategory) {
        if (blogCategoryService.clearCategory(blogCategory)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 跳转进入新增文章分类页面
     * @return
     */
    @GetMapping("/category/add")
    public String addBlogConfig() {
        return "adminLayui/category-add";
    }

    /**
     * 新增文章分类信息
     * @param blogCategory
     * @return
     */
    @ResponseBody
    @PostMapping("/category/add")
    public Result<String> addCategory(BlogCategory blogCategory) {
        blogCategory.setCreateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogCategoryService.save(blogCategory);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


}
