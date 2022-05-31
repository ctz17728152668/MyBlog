package com.c1z.blog.service.impl;

import com.c1z.blog.entity.BlogConfig;
import com.c1z.blog.dao.BlogConfigMapper;
import com.c1z.blog.service.BlogConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig> implements BlogConfigService {

    @Autowired
    private BlogConfigMapper blogConfigMapper;


    /**
     * 获取所有系统变量
     * @return
     */
    @Override
    public Map<String, String> getAllConfigs() {
        List<BlogConfig> list = blogConfigMapper.selectList(null);
        return list.stream().collect(Collectors.toMap(
                BlogConfig::getConfigField,BlogConfig::getConfigValue
        ));
    }
}
