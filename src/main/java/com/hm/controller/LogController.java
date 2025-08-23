package com.hm.controller;

import com.hm.pojo.EmpLog;
import com.hm.pojo.OperateLog;
import com.hm.pojo.PageResult;
import com.hm.pojo.Result;
import com.hm.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description LogController
 * @Author Lisheng Li
 * @Date 2025-08-20
 */
@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/page")
    public Result getAllBypage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10")Integer pageSize){
        log.info("查询日志");

        PageResult<OperateLog> result= reportService.getAllByPage(page,pageSize);

        return Result.success(result);
    }
}
