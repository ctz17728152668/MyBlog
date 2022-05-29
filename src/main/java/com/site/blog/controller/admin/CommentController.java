package com.site.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.pojo.dto.AjaxPutPage;
import com.site.blog.pojo.dto.AjaxResultPage;
import com.site.blog.pojo.dto.Result;
import com.site.blog.entity.BlogComment;
import com.site.blog.service.BlogCommentService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理接口
 * 评论相关
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Resource
    private BlogCommentService blogCommentService;

    /**
     * 跳转进入评论页面
     * @return
     */
    @GetMapping("/v1/comment")
    public String gotoComment(){
        return "adminLayui/comment-list";
    }

    /**
     * 返回分页评论列表
     * @param ajaxPutPage
     * @param condition
     * @return
     */
    @ResponseBody
    @GetMapping("/v1/comment/paging")
    public AjaxResultPage<BlogComment> getCommentList(AjaxPutPage<BlogComment> ajaxPutPage, BlogComment condition){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>(condition);
        Page<BlogComment> page = ajaxPutPage.putPageToPage();
        blogCommentService.page(page,queryWrapper);
        AjaxResultPage<BlogComment> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改评论状态 逻辑删除 是否开放
     * @param blogComment
     * @return
     */
    @ResponseBody
    @PostMapping(value = {"/v1/comment/isDel","/v1/comment/commentStatus"})
    public Result<String> updateCommentStatus(BlogComment blogComment){
        boolean flag = blogCommentService.updateById(blogComment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/comment/delete")
    public Result<String> deleteComment(@RequestParam Long commentId){
        boolean flag = blogCommentService.removeById(commentId);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 编辑评论
     * @param blogComment
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/comment/edit")
    public Result<String> editComment(BlogComment blogComment){
        blogComment.setReplyCreateTime(DateUtils.getLocalCurrentDate());
        blogComment.setCommentBody(blogComment.getCommentBody());
        boolean flag = blogCommentService.updateById(blogComment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
