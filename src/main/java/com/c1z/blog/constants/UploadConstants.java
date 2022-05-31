package com.c1z.blog.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 图片上传路径变量
 */
@Component
public class UploadConstants {

    // 用户头像默认上传路径
    public static String UPLOAD_AUTHOR_IMG;
    // 文章图片默认上传路径
    public static String FILE_UPLOAD_DIC;
    // 用户头像数据库路径
    public static String SQL_AUTHOR_IMG;
    // 文章图片数据库路径
    public static String FILE_SQL_DIC;


    @Value("${upload.uploadAuthorImg}")
    public void setUploadAuthorImg(String uploadAuthorImg) {
        UploadConstants.UPLOAD_AUTHOR_IMG = uploadAuthorImg;
    }

    @Value("${upload.fileUploadDic}")
    public void setFileUploadDic(String fileUploadDic) {
        UploadConstants.FILE_UPLOAD_DIC = fileUploadDic;
    }

    @Value("${upload.sqlAuthorImg}")
    public void setSqlAuthorImg(String sqlAuthorImg) {
        UploadConstants.SQL_AUTHOR_IMG = sqlAuthorImg;
    }

    @Value("${upload.fileSqlDic}")
    public void setFileSqlDic(String fileSqlDic) {
        UploadConstants.FILE_SQL_DIC = fileSqlDic;
    }
}
