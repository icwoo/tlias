package com.hm.service.impl;

import com.hm.mapper.DeptMapper;
import com.hm.pojo.Dept;
import com.hm.pojo.Result;
import com.hm.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description DeptServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> findAll() {
        List<Dept> list = deptMapper.findAll();
        return list;
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    //@Transactional // 添加此注解
    public void insert(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer deptId) {
        return deptMapper.findById(deptId);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
