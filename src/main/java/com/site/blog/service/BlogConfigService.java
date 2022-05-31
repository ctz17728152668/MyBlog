package com.site.blog.service;

import com.site.blog.entity.BlogConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface BlogConfigService extends IService<BlogConfig> {

    Map<String, String> getAllConfigs();

}
