package com.hm.mapper;

import com.hm.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description LogMapper
 * @Author Lisheng Li
 * @Date 2025-08-21
 */
@Mapper
public interface LogMapper {
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
     void insertIntoLog(OperateLog log);
}
