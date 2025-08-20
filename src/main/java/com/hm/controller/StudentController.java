package com.hm.controller;

import com.hm.pojo.Clazz;
import com.hm.pojo.PageResult;
import com.hm.pojo.Result;
import com.hm.pojo.Student;
import com.hm.pojo.dto.StudentDTO;
import com.hm.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description StudentController
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("备用")
    public Result getAll() {

        return Result.success(studentService.getAll());

    }

    @GetMapping
    public Result getAllByPage(StudentDTO studentDTO) {
        log.info("分页查询员工数据，参数：{}", studentDTO);

        // 返回类型为什么用 PageResult<Student>？
        //rows：需要返回 学生实体列表（List<Student>）
        //total：需要返回 匹配的总数
        //StudentDTO 无法满足的原因
        //数据结构不匹配：
        
        //StudentDTO包含的是查询条件（page/pageSize/name...）
        //但需要返回的是查询结果（学生列表 + 总数
        //最主要是参数不匹配，PageResult的row由student提供，total由pagehelper提供，下面跟StudentDTO一样的参数是要提交的参数，不是接收的参数
        PageResult<Student> pageResult = studentService.getAllByPage(
                studentDTO.getPage(), studentDTO.getPageSize(),
                studentDTO.getName(), studentDTO.getDegree(),
                studentDTO.getClazzId());

        return Result.success(pageResult);

    }
    @GetMapping("{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据id查询学生信息，id：{}",id);

        Student student= studentService.findById(id);
        return Result.success(student);
    }
    @PutMapping("/violation/{id}/{score}")
    public Result addViolatScore(@PathVariable Integer id,@PathVariable Integer score){
        log.info("执行违纪扣分，扣分：{}分",score);

        studentService.addViolatScore(id,score);
        return Result.success();
    }

    @PostMapping
    public Result addStu(@RequestBody Student student){

        log.info("添加学生，姓名：{}分",student.getName());
        studentService.addStu(student);
        return Result.success();
    }
}
