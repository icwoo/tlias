package com.hm.service;

import com.hm.pojo.Clazz;
import com.hm.pojo.PageResult;
import com.hm.pojo.dto.PageQueryClazzDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description ClazzService
 * @Author Lisheng Li
 * @Date 2025-08-13
 */
public interface ClazzService {


     void addClazz(Clazz clazz);

    List<Clazz> getAll();

    PageResult<Clazz> pageQueryClazz(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    Clazz findById(Integer id);

    void deleteById(Integer id);

    void updateById(Clazz clazz);
}
