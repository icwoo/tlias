package com.hm.service;

import com.hm.pojo.PageResult;
import com.hm.pojo.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description StudentService
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
public interface StudentService {
    List<Student> getAll();
    PageResult<Student> getAllByPage(Integer page, Integer pageSize, String name, Integer degree, Integer clazzId);

    Student findById(Integer id);

    void addViolatScore(Integer id, Integer score);

    void addStu(Student student);

    void updateStu(Student student);
}
