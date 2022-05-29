package com.site.blog.util;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.pojo.dto.Result;

/**
 * 生成响应信息工具类
 */
public class ResultGenerator {

    private ResultGenerator() {
    }


    /**
     * 根据http类型 返回数据  生成响应信息
     * @param constants
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, T data) {
        Result<T> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(constants.getContent());
        result.setData(data);
        return result;
    }

    /**
     * 根据http类型 错误信息 生成相应信息
     * @param constants
     * @param msg
     * @return
     */
    public static Result<String> getResultByMsg(HttpStatusEnum constants, String msg) {
        Result<String> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(msg);
        return result;
    }


    public static Result<String> getResultByHttp(HttpStatusEnum constants) {
        Result<String> result = new Result<>();
        result.setResultCode(constants.getStatus());
        result.setMessage(constants.getContent());
        return result;
    }
}
