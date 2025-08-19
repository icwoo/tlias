package com.hm.controller;

import com.hm.pojo.Emp;
import com.hm.pojo.Result;
import com.hm.pojo.vo.LoginVO;
import com.hm.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description LoginController
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("开始处理登录请求...用户：{} 进行登录",emp.getUsername());

        LoginVO loginVO =empService.login(emp);
        if(loginVO!=null){
            log.info("{} 用户登录成功！",emp.getUsername());
            return Result.success(loginVO);
        }else {
            log.info("{} 用户登录失败！",emp.getUsername());
            return Result.error("账号或密码错误~~");
        }
    }
}
