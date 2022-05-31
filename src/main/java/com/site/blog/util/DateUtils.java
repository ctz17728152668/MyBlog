package com.site.blog.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 获取当前事件工具类
 */
public class DateUtils {

    public static Timestamp getLocalCurrentDate(){
        return new Timestamp(new Date().getTime());
    }
}
