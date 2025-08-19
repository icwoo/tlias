package com.hm.service;

import com.hm.pojo.Dept;
import com.hm.pojo.Result;

import java.util.List;

/**
 * @Description DeptService
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);

    void insert(Dept dept);

    Dept getById(Integer deptId);

    void update(Dept dept);
}
