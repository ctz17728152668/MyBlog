package com.site.blog.config;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.pojo.dto.Result;
import com.site.blog.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class ApplicationControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerExceptionHandler.class);


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result<String> bindException(BindException ex) {
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> message.append(error.getDefaultMessage()).append(";"));
        return ResultGenerator.getResultByMsg(HttpStatusEnum.BAD_REQUEST,
                message.substring(0, message.length() - 1));
    }



    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> message.append(error.getDefaultMessage()).append(";"));
        return ResultGenerator.getResultByMsg(HttpStatusEnum.BAD_REQUEST,
                message.substring(0, message.length() - 1));
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> handlerError(HttpServletRequest req, Exception e) {
        // 输出异常信息到控制台
        logger.error(e.getMessage());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "出现异常错误,请及时查看后台日志！");
    }


}
