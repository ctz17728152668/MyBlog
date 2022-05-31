package com.site.blog.service;

import com.site.blog.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.entity.BlogConfig;


public interface AdminUserService extends IService<AdminUser> {
    
    boolean validatePassword(Integer userId,String oldPwd);

    boolean updateUserInfo(AdminUser adminUser);

}
