package com.hm.service.impl;

import com.hm.mapper.EmpMapper;
import com.hm.pojo.vo.JobOptionVO;
import com.hm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description ReportServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public JobOptionVO countByEmpJobData() {
        //1.调用mapper查询执行数据统计返回List<Map<String,Object>>
        List<Map<String, Object>> jobDataList = empMapper.findJobData();

        //开始拆分map集合，拆成两条list    //2.将List<Map<String,Object>>抽取里面所有map的key为“job”的数据转换为新建的List<String>
        List<String> joblist = jobDataList.stream().map(map -> map.get("job").toString()).toList();
        //3.将List<Map<String,Object>>抽取里面所有map的key为“count”的数据转换为新建的List<Integer>

        List<Integer> countList = jobDataList.stream().map(map -> Integer.valueOf(map.get("count").toString())).toList();

        //4.封装成JobOptionVO返回
        return new JobOptionVO(joblist,countList);
    }

    @Override
    public List<Map<String,Object>> countEmpGender() {
        return empMapper.countEmpGender();
    }

    @Override
    public List<Map<String, Object>> countStudentDegree() {

        return empMapper.countStudentDegree();
    }
}
