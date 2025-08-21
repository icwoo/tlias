package com.hm.controller;

import com.hm.annotation.InsertLog;
import com.hm.pojo.Clazz;
import com.hm.pojo.PageResult;
import com.hm.pojo.Result;
import com.hm.pojo.dto.PageQueryClazzDTO;
import com.hm.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description ClazzController
 * @Author Lisheng Li
 * @Date 2025-08-13
 */
@RestController
@RequestMapping("/clazzs")
@Slf4j
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/list")
    public Result getAll() {
        List<Clazz> list = clazzService.getAll();
        log.info("查询所有班级：{}", list);
        return Result.success(list);
    }

    //添加班级
    @PostMapping
    @InsertLog
    public Result addClazz(@RequestBody Clazz clazz) {
        log.info("执行班级添加：{}", clazz);
         clazzService.addClazz(clazz);
        return Result.success();
    }

    /**
     * 班级分页条件查询
     * @param dto
     * @return
     */
    @GetMapping
    public Result pageQueryClazz(PageQueryClazzDTO dto){
        log.info("班级分页条件查询参数：{}",dto);

        PageResult<Clazz> pageResult = clazzService.pageQueryClazz(dto.getPage(),
                dto.getPageSize(),
                dto.getName(),
                dto.getBegin(),
                dto.getEnd());

        return Result.success(pageResult);
    }

    @GetMapping("{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据id查询，id：{}",id);

        Clazz clazz= clazzService.findById(id);
        return Result.success(clazz);
    }
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("根据id删除，id：{}",id);

        clazzService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result updateById(@RequestBody Clazz clazz){
        log.info("根据id，修改员工，员工姓名：{}，id：{}",clazz.getName(),clazz.getId());

        clazzService.updateById(clazz);
        return Result.success();
    }

}

