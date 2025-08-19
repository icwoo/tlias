package com.hm.filters;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hm.pojo.Result;
import com.hm.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description LoginFilter
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@Slf4j
//不用了注释即可@WebFilter("/*")
public class LoginFilter implements Filter {
    //初始化和销毁的方法不重写也行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进行登录校验拦截。。。");
        //1.获取请求路径
        // servletRequest.getRequestURI(); ServletRequest是父接口功能少，子接口HttpServletRequest功能多
        // 将ServletRequest转换为HttpServletRequest
        // 将ServletResponse转换为HttpServletResponse

        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        //再次获取请求路径
        String requestURI = request.getRequestURI();


        //2.判断请求路径是否为登录路径//登录资源路径 “/login”
        if (requestURI.contains("/login")){
            //2.1 是，放行
            filterChain.doFilter(request,response);
            log.info("放行-----------------------------------");
            return;
        }


        //3.不是登录资源，就要进行登录校验，获取请求头token
        String token = request.getHeader("token");

        //4.判断token是否不为空
        if (!StrUtil.isEmpty(token)){//为什么要这么写，为什么不能!token.isEmpty()
            try {
                Claims claims = JwtUtils.parseJWT(token);//这里仅能判断token有无被篡改
                //4.1 不为空，进行解析token，获取载荷
                //4.2 获取载荷里面的用户唯一身份id并打印
                String id = (String) claims.get("empId");
                //4.3 解析合法，放行(向下传递
                filterChain.doFilter(request,response);
                log.info("校验成功，放行用户:{}",id);
                return;
                // read 如果成功，这里就return了，第五步的内容不会执行

            } catch (Exception e) {
                e.printStackTrace();//异常打印给开发人员看
                log.error("解析token失败，失败原因：{}",e.getMessage());
            }
        }

        //5.token为空 或 解析报错 返回校验失败
        response.setContentType("application/json;charset=utf-8");
        //5.1 设置响应状态码为401(这个401是与前端开发协商好，前端自动跳转到登录页面重新登录)
        response.setStatus(401);//401代表未认证，登录校验不通过
        //5.2 设置通用返回结果{"code":0,msg:"登录失效，请重新登录",data:null}
        // return Result.error("登录失效，请重新登录"); 因为方法是void没有返回值的，所以这样写是错的
        //创建通用结果对象封装错误友好消息
        Result result = Result.error("登录失效，请重新登录");
        String jsonStr = JSONUtil.toJsonStr(result);
        //响应给前端
        response.getWriter().write(jsonStr);
    }
}
