package com.hm.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description Interceptor
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@Slf4j
@Component//声明Bean加入容器中,这个注解是最大的注解，是通用注解，推荐非三层架构的类使用
// 1.定义拦截器类，实现HandlerInterceptor接口，并重写其所有方法
//2．注册拦截器,不在这创建，去配置类注册
//read ctrl+i去重写方法，不是手敲的
public class DemoInterceptor implements HandlerInterceptor {
    //请求前拦截方法： 拦截请求先执行这个方法, 返回值为true代表放行给下一个资源去执行，返回值为false代表拦截，不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("DemoInterceptor的preHandle方法执行了...");
        return true;
    }

    //下面的很少用到
    //资源执行后拦截方法： 资源执行完并且不报错之后才运行这个方法
/*    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info("DemoInterceptor的postHandle方法执行了...");
    }

    //资源执行后拦截方法： 资源执行完，无论资源执行是否报错之后都会运行这个方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        log.info("DemoInterceptor的afterCompletion方法执行了...");
    }*/
}
