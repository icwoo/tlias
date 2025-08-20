package com.hm.mapper;

import com.hm.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description StudentMapper
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Mapper
public interface StudentMapper {

    List<Student> getAll();

    List<Student> getAllByPage(String name, Integer degree, Integer clazzId);

    @Select("select * from student where id=#{id}")
    Student findById(Integer id);

    void addViolatScore(Integer id, Integer score, LocalDateTime updateTime);

    void addStu(Student student);
}
