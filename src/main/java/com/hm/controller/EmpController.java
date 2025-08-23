package com.hm.controller;


import com.hm.annotation.InsertLog;
import com.hm.pojo.Clazz;
import com.hm.pojo.Emp;
import com.hm.pojo.PageResult;
import com.hm.pojo.Result;
import com.hm.pojo.dto.PageQueryEmpDTO;
import com.hm.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description EmpMapper
 * @Author Lisheng Li
 * @Date 2025-08-14
 */
@RestController
@Slf4j
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    //read
    // findByPage和findByPage2用这条都行
/*    @GetMapping
    public Result findByPage(@RequestParam(defaultValue = "1")Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        //@RequestParam(defaultValue = "1")给接收的数据设置默认值，如果浏览器没传过来就用默认的
        log.info("开始分页，页数{},大小{}",page,pageSize);
         //PageResult<Emp> pageResult= empService.findByPage(page,pageSize);
         PageResult<Emp> pageResult= empService.findByPage2(page,pageSize);
         return Result.success(pageResult);
    }*/
/*    @GetMapping
    public Result findByPage3(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              String name, Integer gender,
                              @DateTimeFormat(pattern = "yyyy-MM-DD")LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-DD")LocalDate end){
        //@RequestParam(defaultValue = "1")给接收的数据设置默认值，如果浏览器没传过来就用默认的
        log.info("分页查询员工数据，参数：page={},pageSize={},name={},gender={},begin={},end={}",
                page,pageSize,name,gender,begin,end);

        PageResult<Emp> pageResult= empService.findByPage3(page,pageSize,name,gender,begin,end);

        return Result.success(pageResult);
    }*/
    @GetMapping
    public Result findByPage3(PageQueryEmpDTO nonono){
        //@RequestParam(defaultValue = "1")给接收的数据设置默认值，如果浏览器没传过来就用默认的
        log.info("分页查询员工数据，参数：{}",nonono);

        PageResult<Emp> pageResult= empService.findByPage3(
                nonono.getPage(),nonono.getPageSize(),
                nonono.getName(),nonono.getGender(),
                nonono.getBegin(),nonono.getEnd()
                );

        return Result.success(pageResult);
    }

    /**
     * 新增员工
     * @param emp
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Emp emp){

        log.info("新增员工，数据：{}",emp);

        //调用业务新增员工
        empService.save(emp);

        //返回数据
        return Result.success();
    }

    /**
     * 查询全部员工
     * @return
     */
    @GetMapping("/list")
    public Result getAll() {
        List<Emp> list = empService.getAll();
        log.info("查询所有员工：{}", list);
        return Result.success(list);
    }

    @DeleteMapping
    public Result deleteBatch(@RequestParam List<Integer> ids){
        log.info("批量删除：{}",ids);
        empService.deleteBatch(ids);

        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("开始查询员工详情,id为：{}",id);
        Emp emp= empService.findById2(id);
        return  Result.success(emp);
    }
    //员工更新
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工详情,emp为：{}",emp);
        empService.update(emp);
        return  Result.success();
    }
}
