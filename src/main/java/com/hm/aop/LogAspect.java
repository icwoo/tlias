package com.hm.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.hm.mapper.LogMapper;
import com.hm.pojo.OperateLog;
import com.hm.pojo.OperateLog;
import com.hm.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @Description LogAspect
 * @Author Lisheng Li
 * @Date 2025-08-21
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private LogMapper logMapper;

    @Pointcut("@annotation(com.hm.annotation.InsertLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {

        LocalDateTime start = LocalDateTime.now();

        //这一步只是获取方法的返回值，其实跟下面获取类名、方法信息、参数没什么区别
        Object result = pjp.proceed();

        OperateLog operateLog = new OperateLog();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader("token");
        if (!StrUtil.isEmpty(token)) {

            try { //4.1 不为空，进行解析token，获取载荷
                Claims claims = JwtUtils.parseJWT(token);
                //4.2 获取载荷里面的用户唯一身份id并打印
                Integer empId = (Integer) claims.get("empId");
                System.out.println("-----------");
                System.out.println("empId is:" + empId);
                System.out.println("-----------");
                operateLog.setOperateEmpId(empId); //操作人ID

            } catch (Exception e) {
                e.printStackTrace();//异常打印给开发人员看
                log.error("解析token失败，失败原因：{}", e.getMessage());
            }

            operateLog.setOperateTime(LocalDateTime.now());//操作时间
            operateLog.setClassName(pjp.getTarget().getClass().getName());//操作类名
            operateLog.setMethodName(pjp.getSignature().getName());//操作方法名


            // 获取方法参数
            Object[] args = pjp.getArgs();
            // 将参数数组转换为字符串表示
            String methodParams = "[]";
            if (args != null && args.length > 0) {
                StringBuilder paramsBuilder = new StringBuilder("[");
                for (int i = 0; i < args.length; i++) {
                    if (i > 0) {
                        paramsBuilder.append(", ");
                    }
                    // 处理参数为null的情况
                    if (args[i] == null) {
                        paramsBuilder.append("null");
                    } else {
                        // 对于字符串参数，加上引号以便识别
                        if (args[i] instanceof String) {
                            paramsBuilder.append("\"").append(args[i]).append("\"");
                        } else {
                            paramsBuilder.append(args[i]);
                        }
                    }
                }
                paramsBuilder.append("]");
                methodParams = paramsBuilder.toString();
            }
            operateLog.setMethodParams(methodParams);//操作方法参数




            operateLog.setReturnValue(result != null ? result.toString() : "");//操作方法返回值

            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);
            operateLog.setCostTime(duration.toMillis());//操作耗时(毫秒)

            //operateLog.setOperateEmpName();//操作人姓名

            logMapper.insertIntoLog(operateLog);

            System.out.println("-------------------------");
        }

        return result;
    }
}
