package com.site.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.UploadConstants;
import com.site.blog.pojo.dto.AjaxPutPage;
import com.site.blog.pojo.dto.AjaxResultPage;
import com.site.blog.pojo.dto.Result;
import com.site.blog.entity.BlogInfo;
import com.site.blog.entity.BlogTagRelation;
import com.site.blog.service.BlogInfoService;
import com.site.blog.service.BlogTagRelationService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import com.site.blog.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 后台管理接口
 * 博客相关
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private BlogTagRelationService blogTagRelationService;

    /**
     * 跳转进入博客编辑界面
     * @param blogId 可以不传 若传入则为修改  没有传入则为新建博客
     * @param model
     * @return
     */
    @GetMapping("/v1/blog/edit")
    public String gotoBlogEdit(@RequestParam(required = false) Long blogId, Model model) {
//        如果是编辑博客 则需要先把博客相关信息搜索出来
        if (blogId != null) {
            BlogInfo blogInfo = blogInfoService.getById(blogId);
            List<BlogTagRelation> list = blogTagRelationService.list(
                    new QueryWrapper<BlogTagRelation>()
                            .lambda()
                            .eq(BlogTagRelation::getBlogId, blogId)
            );
            List<Integer> tags = null;
            if (!CollectionUtils.isEmpty(list)) {
                tags = list.stream().map(
                        BlogTagRelation::getTagId)
                        .collect(Collectors.toList());
            }
            model.addAttribute("blogTags", tags);
            model.addAttribute("blogInfo", blogInfo);
        }
        return "adminLayui/blog-edit";
    }

    /**
     * 跳转进入文章列表页面
     * @return
     */
    @GetMapping("/v1/blog")
    public String gotoBlogList() {
        return "adminLayui/blog-list";
    }

    /**
     * 编辑文章时上传图片
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/blog/uploadFile")
    public Map<String, Object> uploadFileByEditormd(
                                                    @RequestParam(name = "editormd-image-file") MultipartFile file) {
        String suffixName = UploadFileUtils.getSuffixName(file);
        //生成文件名称通用方法
        String newFileName = UploadFileUtils.getNewFileName(suffixName);
        File fileDirectory = new File(UploadConstants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(UploadConstants.FILE_UPLOAD_DIC + newFileName);

        //editer.md需要返回的格式
        Map<String, Object> result = new HashMap<>();
        try {
            if (!fileDirectory.exists() && !fileDirectory.mkdirs()) {
                throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
            }
            file.transferTo(destFile);
            String fileUrl = UploadConstants.FILE_SQL_DIC + newFileName;
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", fileUrl);
        } catch (IOException e) {
            result.put("success", 0);
        }
        return result;
    }

    /**
     * 保存博客内容
     * @param blogTagIds
     * @param blogInfo
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/blog/edit")
    public Result<String> saveBlog(@RequestParam("blogTagIds[]") List<Integer> blogTagIds, BlogInfo blogInfo) {
        if (CollectionUtils.isEmpty(blogTagIds) || ObjectUtils.isEmpty(blogInfo)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        blogInfo.setCreateTime(DateUtils.getLocalCurrentDate());
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());
        //如果新增或修改博客成功
        if (blogInfoService.saveOrUpdate(blogInfo)) {
            //则删除之前的博客和标签关系 并新增博客与标签的关系
            blogTagRelationService.removeAndsaveBatch(blogTagIds, blogInfo);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        //若操作博客失败则返回错误信息
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 文章分页列表
     *
     * @param ajaxPutPage 分页参数
     * @param condition   筛选条件
     */
    @ResponseBody
    @GetMapping("/v1/blog/list")
        public AjaxResultPage<BlogInfo> getContractList(AjaxPutPage<BlogInfo> ajaxPutPage, BlogInfo condition) {
        LambdaQueryWrapper<BlogInfo> queryWrapper = new LambdaQueryWrapper<BlogInfo>(condition).orderByDesc(BlogInfo::getUpdateTime);
        Page<BlogInfo> page = ajaxPutPage.putPageToPage();
        blogInfoService.page(page, queryWrapper);
        AjaxResultPage<BlogInfo> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 更新微博状态 文章是否发布 是否可以评论
     * @param blogInfo
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/blog/blogStatus")
    public Result<String> updateBlogStatus(BlogInfo blogInfo) {
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 逻辑删除博客
     * @param blogId
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/blog/delete")
    public Result<String> deleteBlog(@RequestParam Long blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 清除文章
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 14:02
     */
    @ResponseBody
    @PostMapping("/v1/blog/clear")
    public Result<String> clearBlog(@RequestParam Long blogId) {
        if (blogInfoService.clearBlogInfo(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 逻辑恢复文章
     * @param blogId
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/blog/restore")
    public Result<String> restoreBlog(@RequestParam Long blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.NO_DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询所有博客
     * @return
     */
    @ResponseBody
    @GetMapping("v1/blog/select")
    public List<BlogInfo> getBlogInfoSelect() {
        return blogInfoService.list();
    }

}
