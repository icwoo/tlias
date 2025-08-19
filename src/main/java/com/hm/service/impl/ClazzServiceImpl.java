package com.hm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hm.mapper.ClazzMapper;
import com.hm.pojo.Clazz;
import com.hm.pojo.PageResult;
import com.hm.pojo.Result;
import com.hm.pojo.dto.PageQueryClazzDTO;
import com.hm.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description ClazzServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-13
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public void addClazz(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.addClazz(clazz);
    }

    @Override
    public List<Clazz> getAll() {
        List<Clazz> list=clazzMapper.getAll();
        return list;
    }

    @Override
    public PageResult<Clazz> pageQueryClazz(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {

        PageHelper.startPage(page,pageSize);
        List<Clazz> queryClazz = clazzMapper.pageQueryClazz(name, begin, end);

        for (Clazz clazz : queryClazz) {
            if (clazz.getBeginDate().isAfter(LocalDate.now())){
                clazz.setStatus("未开课");
            }else if (clazz.getEndDate().isBefore(LocalDate.now())){
                clazz.setStatus("已结课");
            }else {
                clazz.setStatus("在读");
            }
        }

        Page<Clazz> page1= (Page<Clazz>) queryClazz;

        return new PageResult<>(page1.getResult(),page1.getTotal());
    }

    @Override
    public Clazz findById(Integer id) {

        return clazzMapper.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    @Override
    public void updateById(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

}
