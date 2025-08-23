package com.hm.service;

import com.hm.annotation.InsertLog;
import com.hm.pojo.EmpLog;
import com.hm.pojo.OperateLog;
import com.hm.pojo.PageResult;
import com.hm.pojo.vo.ClazzDataVO;
import com.hm.pojo.vo.JobOptionVO;

import java.util.List;
import java.util.Map;

/**
 * @Description ReportService
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
public interface ReportService {
     /* @return
             */
    JobOptionVO countByEmpJobData();
    List<Map<String,Object>> countEmpGender();
    List<Map<String, Object>> countStudentDegree();
    ClazzDataVO countStudent();

    PageResult<OperateLog> getAllByPage(Integer page, Integer pageSize);
}
