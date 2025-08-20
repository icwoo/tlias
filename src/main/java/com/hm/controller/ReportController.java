package com.hm.controller;

import com.hm.pojo.Result;
import com.hm.pojo.vo.ClazzDataVO;
import com.hm.pojo.vo.JobOptionVO;
import com.hm.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

/**
 * @Description ReportController
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    //工作统计
    @GetMapping("/empJobData")
    public Result empJobData(){

        JobOptionVO jobOptionVO = reportService.countByEmpJobData();

        return Result.success(jobOptionVO);
    }

    //员工性别统计
    @GetMapping("/empGenderData")
    public Result countEmpGender(){

        List<Map<String,Object>> list = reportService.countEmpGender();

        return Result.success(list);
    }
    //学员学历
    @GetMapping("/studentDegreeData")
    public Result countStudentDegree(){

        List<Map<String,Object>> list = reportService.countStudentDegree();

        return Result.success(list);
    }
    /**
     * 该接口用于统计每个班级的人数信息
     * @return
     */
    @GetMapping("/studentCountData")
    public Result countStudent(){

        ClazzDataVO  list = reportService.countStudent();

        return Result.success(list);
    }
}
