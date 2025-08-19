package com.hm.exception;

import com.hm.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description GlobalException
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
/**
 * @Description GlobalExceptionHandler
 * @RestControllerAdvice 适合前后端分离场景使用，处理异步请求，@RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * @ControllerAdvice  适合前后端不分离场景使用，处理同步请求
 */
@RestControllerAdvice//和@ControllerAdvice比就是多了个@ResponseBody注解
//标识当前类为全局异常处理类，作用是捕获所有controller抛出的异常
@Slf4j
public class GlobalExceptionHandler {
    //兜底的异常
    @ExceptionHandler(Exception.class)
    public Result finalException(Exception e){
        log.error("未知异常：{}",e);
        return Result.error("服务器忙，请稍后再试~");
    }

    //针对特定子类类型异常捕获返回特定友好信息
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleSQLIntegrityConstraintViolationException(DuplicateKeyException e) {
        log.error("发生异常，异常原因：{}",e.getMessage());//记录日志文件
        e.printStackTrace();//给开发人员立刻定位错误，注意1500以后就会清除
        String[] split = e.getMessage().split(" ");
        return Result.error("操作失败，【"+split[9]+"】值重复！");
    }
}
