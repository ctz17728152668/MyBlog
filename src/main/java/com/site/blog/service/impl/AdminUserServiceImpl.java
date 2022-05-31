package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.site.blog.dao.BlogConfigMapper;
import com.site.blog.entity.AdminUser;
import com.site.blog.dao.AdminUserMapper;
import com.site.blog.entity.BlogConfig;
import com.site.blog.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 修改密码时 验证旧密码是否正确
     * @param userId
     * @param oldPwd
     * @return
     */
    @Override
    public boolean validatePassword(Integer userId, String oldPwd) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>(
                new AdminUser().setAdminUserId(userId)
                        .setLoginPassword(MD5Utils.MD5Encode(oldPwd, "UTF-8"))
        );
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        return !StringUtils.isEmpty(adminUser);
    }

    /**
     * 更新管理员信息 根据返回修改条数判断
     * @param adminUser
     * @return
     */
    @Transactional
    @Override
    public boolean updateUserInfo(AdminUser adminUser) {
        return SqlHelper.retBool(adminUserMapper.updateById(adminUser));
    }
}
