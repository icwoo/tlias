package com.hm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hm.mapper.StudentMapper;
import com.hm.pojo.PageResult;
import com.hm.pojo.Student;
import com.hm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description StudentServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Student> getAll() {
        List<Student> all = studentMapper.getAll();
        return all;
    }

    @Override
    public PageResult<Student> getAllByPage(Integer page, Integer pageSize, String name, Integer degree, Integer clazzId) {
        PageHelper.startPage(page,pageSize);
        List<Student> allByPage = studentMapper.getAllByPage(name, degree, clazzId);

        //再封装一手,mapper给的是list，用page封装一遍再返回
        Page<Student> pagelist= (Page<Student>) allByPage;

        return new PageResult<>(pagelist.getResult(),pagelist.getTotal());
    }

    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public void addViolatScore(Integer id, Integer score) {
        LocalDateTime now = LocalDateTime.now();
        //updatTime在sql语句里改
        studentMapper.addViolatScore(id,score,now);

    }

    @Override
    public void addStu(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.addStu(student);
    }
}
