package com.c1z.blog.service;

import com.c1z.blog.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AdminUserService extends IService<AdminUser> {
    
    boolean validatePassword(Integer userId,String oldPwd);

    boolean updateUserInfo(AdminUser adminUser);

}
