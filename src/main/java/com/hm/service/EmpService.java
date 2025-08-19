package com.hm.service;

import com.hm.pojo.Emp;
import com.hm.pojo.EmpExpr;
import com.hm.pojo.PageResult;
import com.hm.pojo.vo.LoginVO;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description EmpService
 * @Author Lisheng Li
 * @Date 2025-08-14
 */
public interface EmpService {
    PageResult<Emp> findByPage(Integer page, Integer pageSize);

    PageResult<Emp> findByPage2(Integer page, Integer pageSize);

    PageResult<Emp> findByPage3(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    /**
     * 保存员工
     * @param emp
     */
    void save(Emp emp);

    List<Emp> getAll();

    void deleteBatch(List<Integer> ids);

    Emp findById(Integer id);
    Emp findById2(Integer id);

    void update(Emp emp);

    LoginVO login(Emp emp);
}
