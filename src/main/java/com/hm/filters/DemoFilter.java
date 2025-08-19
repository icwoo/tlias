package com.hm.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description DemoFilter
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
//第三步，去启动类添加注解 @ServletComponentScan 开启Servlet组件支持。
@Slf4j
//不用了注释即可@WebFilter("/*")//第二步，设置拦截的路径，这里是拦截所有
public class DemoFilter implements Filter {//第一步，实现filter接口的方法
    @Override
    //服务器启动的时候会触发，用于初始化资源使用，只会运行一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("---------DemoFilter初始化init方法--------------");
    }

    @Override
    //过滤方法，每次请求都会触发，多次运行
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("---------DemoFilter过滤方法doFilter方法【请求拦截】--------------");

        //放行，让这次请求跳过当前拦截，使其继续执行（也有可能会被下一个拦截器拦截，如果还有拦截器的话）
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("---------DemoFilter过滤方法doFilter方法【响应拦截】--------------");

    }

    //销毁方法，服务器关闭前会触发运行一次
    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("---------DemoFilter销毁destory方法--------------");

    }
}
