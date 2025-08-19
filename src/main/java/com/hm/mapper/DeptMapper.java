package com.hm.mapper;

import com.hm.pojo.Dept;
import com.hm.pojo.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description DeptMapper
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
@Mapper
public interface DeptMapper {

    /**
     *
     * @return
     */
    List<Dept> findAll();

    void deleteById(Integer id);

    void insert(Dept dept);

    Dept findById(Integer deptId);

    void update(Dept dept);
}
