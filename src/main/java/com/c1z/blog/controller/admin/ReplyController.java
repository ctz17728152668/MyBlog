package com.c1z.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.c1z.blog.constants.HttpStatusEnum;
import com.c1z.blog.entity.BlogReply;
import com.c1z.blog.pojo.dto.AjaxPutPage;
import com.c1z.blog.pojo.dto.AjaxResultPage;
import com.c1z.blog.pojo.dto.Result;
import com.c1z.blog.service.BlogReplyService;
import com.c1z.blog.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 后台管理接口
 * 回复相关
 */
@Controller
@RequestMapping("/admin")
public class ReplyController {

    @Autowired
    private BlogReplyService blogReplyService;


    /**
     * 跳转进入回复页面
     * @return
     */
    @GetMapping("/reply")
    public String gotoReply(){
        return "adminLayui/reply-list";
    }

    /**
     * 返回分页回复列表
     * @param ajaxPutPage
     * @param condition
     * @return
     */
    @ResponseBody
    @GetMapping("/reply/paging")
    public AjaxResultPage<BlogReply> getReplyList(AjaxPutPage<BlogReply> ajaxPutPage, BlogReply condition){
        QueryWrapper<BlogReply> queryWrapper = new QueryWrapper<>(condition);
        Page<BlogReply> page = ajaxPutPage.putPageToPage();
        blogReplyService.page(page,queryWrapper);
        AjaxResultPage<BlogReply> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改回复状态 逻辑删除 是否开放
     * @param BlogReply
     * @return
     */
    @ResponseBody
    @PostMapping(value = {"/reply/isDel","/reply/replyStatus"})
    public Result<String> updateReplyStatus(BlogReply BlogReply){
        boolean flag = blogReplyService.updateById(BlogReply);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除评论
     * @param replyId
     * @return
     */
    @ResponseBody
    @PostMapping("/reply/delete")
    public Result<String> deleteReply(@RequestParam Long replyId){
        boolean flag = blogReplyService.removeById(replyId);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
