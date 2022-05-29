package com.site.blog.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 上传图片工具类
 */
public class UploadFileUtils {

    /**
     * 获取图片后缀
     * @param file
     * @return
     */
    public static String getSuffixName(MultipartFile file){
        //获取文件上传时的文件名
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)){
            throw new RuntimeException("获取图片后缀失败");
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名 防止上传的图片重名
     * @param suffixName
     * @return
     */
    public static String getNewFileName(String suffixName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        int random = new Random().nextInt(100);
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(random).append(suffixName);
        return tempName.toString();
    }
}
