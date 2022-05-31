package com.site.blog.util;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 接受从前台的分页参数
 */
@Data
public class PageQueryUtils extends LinkedHashMap<String, Object> {
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public PageQueryUtils(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("start", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

}
