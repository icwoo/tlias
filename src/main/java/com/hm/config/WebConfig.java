package com.hm.config;

import com.hm.interceptors.DemoInterceptor;

import com.hm.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description WebConfig
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@Configuration//标识为配置类，注解里还有@Component注解
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private DemoInterceptor demoInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    //注册拦截器 ctrl+i去重写addInterceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册demoInterceptor拦截器并绑定拦截路径“/**”
        //   注意在spring框架中“**”代表任意字符匹配
        registry.addInterceptor(demoInterceptor).addPathPatterns("/**");

        //注册LoginInterceptor，并设置拦截路径，并设置放行路径
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**"). excludePathPatterns("/login");
    }
}
