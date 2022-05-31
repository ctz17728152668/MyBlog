package com.c1z.blog.service;

import com.c1z.blog.entity.BlogConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface BlogConfigService extends IService<BlogConfig> {

    Map<String, String> getAllConfigs();

}
