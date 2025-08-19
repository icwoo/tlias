package com.hm.interceptors;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hm.pojo.Result;
import com.hm.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Description LoginInterceptor
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@Slf4j
@Component//声明Bean加入容器中,这个注解是最大的注解，是通用注解，推荐非三层架构的类使用
// 1.定义拦截器类，实现HandlerInterceptor接口，并重写其所有方法
//2．注册拦截器,不在这创建，去配置类注册
public class LoginInterceptor implements HandlerInterceptor {
    //请求前拦截方法： 返回值为true代表放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始登录校验。。。");
        //3.不是登录资源，就要进行登录校验，获取请求头token
        String token = request.getHeader("token");

        //4.判断token是否不为空
        // if(token!=null && token!="")
        if (!StrUtil.isEmpty(token)) {
            try {
                //4.1 不为空，进行解析token，获取载荷
                Claims claims = JwtUtils.parseJWT(token);
                //4.2 获取载荷里面的用户唯一身份id并打印
                Integer empId = (Integer) claims.get("empId");
                //4.3 解析合法，放行
                log.info("员工id为：{} 登录校验成功，放行允许访问资源路径：{}", empId, request.getRequestURI());
                return true;//doFilter是Filter的放行方法
            } catch (Exception e) {
                e.printStackTrace();//异常打印给开发人员看
                log.error("解析token失败，失败原因：{}", e.getMessage());
            }
        }

        //5.token为空 或 解析报错 返回校验失败
        //5.1 设置响应状态码为401(这个401是与前端开发协商好，前端自动跳转到登录页面重新登录)
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(401);//401就代表未认证，登录校验不通过
        //5.2 设置通用返回结果{"code":0,msg:"登录失效，请重新登录",data:null}
        // return Result.error("登录失效，请重新登录"); 这样不行的，因为规范当前接口没有返回值
        //创建通用结果对象封装错误友好消息
        Result result = Result.error("登录失效，请重新登录");
        //将对象转换为json字符串
        String json = JSONUtil.toJsonStr(result);
        //将json字符串输出给前端
        response.getWriter().write(json);
        log.error("登录校验失败。。。");
        return false;
    }
}


