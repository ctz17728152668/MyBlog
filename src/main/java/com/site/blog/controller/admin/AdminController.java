package com.site.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.*;
import com.site.blog.pojo.dto.Result;
import com.site.blog.entity.*;
import com.site.blog.service.*;
import com.site.blog.util.MD5Utils;
import com.site.blog.util.ResultGenerator;
import com.site.blog.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 后台管理接口
 * 登录及个人信息相关
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private BlogCategoryService blogCategoryService;
    @Autowired
    private BlogCommentService blogCommentService;
    @Autowired
    private BlogConfigService blogConfigService;



    /**
     * 跳转进入登录页面
     * @return
     */
    @GetMapping(value = "/v1/login")
    public String login() {
        return "adminLayui/login";
    }

    /**
     * 显示个人信息页面
     * @return
     */
    @GetMapping("/v1/welcome")
    public String welcome(){
        return "adminLayui/welcome";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("/v1/logout")
    public String logout(HttpSession session) {
        //销毁session 则去除登录记录
        session.invalidate();
        //随后返回登录页面
        return "adminLayui/login";
    }

    /**
     * 弹出个人管理修改个人信息页面
     * @return
     */
    @GetMapping("/v1/userInfo")
    public String gotoUserInfo(){
        return "adminLayui/userInfo-edit";
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/v1/login")
    public Result<String> login(String username, String password,
                        HttpSession session) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }

        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<AdminUser>().
                eq(AdminUser::getLoginUserName, username).
                eq(AdminUser::getLoginPassword, MD5Utils.MD5Encode(password, "UTF-8"));

        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            session.setAttribute(SessionConstants.LOGIN_USER, adminUser.getNickName());
            session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.getAdminUserId());
            session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.getLoginUserName());
            session.setAttribute(SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                    SysConfigConstants.SYS_AUTHOR_IMG.getConfigField()));
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,"/admin/v1/index");
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
    }

    /**
     * 个人信息编辑修改密码时 验证旧密码
     * @param oldPwd
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/v1/password")
    public Result<String> validatePassword(String oldPwd,HttpSession session){
        Integer userId = (Integer) session.getAttribute(SessionConstants.LOGIN_USER_ID);
        boolean flag = adminUserService.validatePassword(userId,oldPwd);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
    }

    /**
     * 跳转进入后台管理首页 并返回相关信息
     * @param session
     * @return
     */
    @GetMapping("/v1/index")
    public String index(HttpSession session) {
        session.setAttribute("categoryCount", blogCategoryService.count(
                new QueryWrapper<BlogCategory>().lambda().eq(BlogCategory::getIsDeleted,
                        DeleteStatusEnum.NO_DELETED.getStatus())
        ));
        session.setAttribute("blogCount", blogInfoService.count(
                new QueryWrapper<BlogInfo>().lambda().eq(BlogInfo::getIsDeleted,
                        DeleteStatusEnum.NO_DELETED.getStatus())
        ));
        session.setAttribute("tagCount", blogTagService.count(
                new QueryWrapper<BlogTag>().lambda().eq(BlogTag::getIsDeleted,
                        DeleteStatusEnum.NO_DELETED.getStatus())
        ));
        session.setAttribute("commentCount", blogCommentService.count(
                new QueryWrapper<BlogComment>().lambda().eq(BlogComment::getIsDeleted,
                        DeleteStatusEnum.NO_DELETED.getStatus())
        ));
        session.setAttribute("sysList",blogConfigService.list());
        return "adminLayui/index";
    }

    /**
     * 修改管理员个人信息 修改完成跳转回登录页面重新登录
     * @param session
     * @param userName
     * @param newPwd
     * @param nickName
     * @return
     */
    @ResponseBody
    @PostMapping("/v1/userInfo")
    public Result<String> userInfoUpdate(HttpSession session,String userName, String newPwd,
                                 String nickName) {
        if (StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(nickName)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        Integer loginUserId = (int) session.getAttribute(SessionConstants.LOGIN_USER_ID);
        AdminUser adminUser = new AdminUser()
                .setAdminUserId(loginUserId)
                .setLoginUserName(userName)
                .setNickName(nickName)
                .setLoginPassword(MD5Utils.MD5Encode(newPwd, "UTF-8"));
        if (adminUserService.updateUserInfo(adminUser)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,"/admin/v1/logout");
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @GetMapping("/v1/reload")
    public boolean reload(HttpSession session){
        Integer userId = (Integer) session.getAttribute(SessionConstants.LOGIN_USER_ID);
        return userId != null && userId != 0;
    }

    /**
     * 管理员上传头像
     * @param request
     * @param file
     * @return
     * @throws URISyntaxException
     */
    @PostMapping({"/upload/authorImg"})
    @ResponseBody
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String suffixName = UploadFileUtils.getSuffixName(file);
        //生成文件名称通用方法
        String newFileName = UploadFileUtils.getNewFileName(suffixName);
        File fileDirectory = new File(UploadConstants.UPLOAD_AUTHOR_IMG);
        //创建文件
        File destFile = new File(UploadConstants.UPLOAD_AUTHOR_IMG + newFileName);
        try {
            if (!fileDirectory.exists() && !fileDirectory.mkdirs()) {
                throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
            }
            file.transferTo(destFile);
            String sysAuthorImg = UploadConstants.SQL_AUTHOR_IMG + newFileName;
            BlogConfig blogConfig = new BlogConfig()
                    .setConfigField(SysConfigConstants.SYS_AUTHOR_IMG.getConfigField())
                    .setConfigValue(sysAuthorImg);
            blogConfigService.updateById(blogConfig);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
