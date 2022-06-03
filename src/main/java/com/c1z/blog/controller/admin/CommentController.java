package com.c1z.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.c1z.blog.constants.HttpStatusEnum;
import com.c1z.blog.mapper.BlogReplyMapper;
import com.c1z.blog.pojo.dto.AjaxPutPage;
import com.c1z.blog.pojo.dto.AjaxResultPage;
import com.c1z.blog.pojo.dto.Result;
import com.c1z.blog.entity.BlogComment;
import com.c1z.blog.pojo.vo.BlogCommentVo;
import com.c1z.blog.service.BlogCommentService;
import com.c1z.blog.service.BlogReplyService;
import com.c1z.blog.util.DateUtils;
import com.c1z.blog.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理接口
 * 评论相关
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private BlogCommentService blogCommentService;


    @Autowired
    private BlogReplyService blogReplyService;

    /**
     * 跳转进入评论页面
     * @return
     */
    @GetMapping("/comment")
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
    @GetMapping("/comment/paging")
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
    @PostMapping(value = {"/comment/isDel","/comment/commentStatus"})
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
    @PostMapping("/comment/delete")
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
    @PostMapping("/comment/edit")
    public Result<String> editComment(BlogComment blogComment){
        blogComment.setCommentBody(blogComment.getCommentBody());
        boolean flag = blogCommentService.updateById(blogComment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
