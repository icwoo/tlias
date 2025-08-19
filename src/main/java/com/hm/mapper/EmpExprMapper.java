package com.hm.mapper;

import com.hm.pojo.Emp;
import com.hm.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description EmpExprMapper
 * @Author songyu
 * @Date 2025-08-14  17:51
 */
@Mapper
public interface EmpExprMapper {

    /**
     * 批量添加员工工作经验
     * @param exprList
     */
    void insertBatch(List<EmpExpr> exprList);

    void deleteByIds(List<Integer> ids);

    @Select("select * from emp_expr where emp_id=#{empId}")
    List<EmpExpr> findById3(Integer id);

    /**
     * 根据员工id删除员工工作经历数据
     * @param empId
     */
    @Delete("delete from emp_expr where emp_id = #{empId}")
    void deleteByEmpId(Integer empId);

}