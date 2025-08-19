package com.hm.controller;

import com.hm.pojo.Dept;
import com.hm.pojo.Result;
import com.hm.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description DeptController
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result findAll() {
        //1.查询
        List<Dept> list = deptService.findAll();

        //2.封装通用结果返回
        // Result result = new Result();
        // result.setCode(1);
        // result.setMsg("success");
        // result.setData(list);
        // return result;
        return Result.success(list);
    }

    @DeleteMapping
    public Result deleteById(Integer id) {

        deptService.deleteById(id);

        return Result.success();
    }

    @PostMapping
    public Result insert(@RequestBody Dept dept) {
        try {
            deptService.insert(dept);
            return Result.success();
        } catch (Exception e) {
            return  Result.error("添加失败");
        }
    }

    @GetMapping("/{deptId}")
    public Result findById(@PathVariable Integer deptId){

        //调用service获取部门详情数据
        Dept dept = deptService.getById(deptId);

        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept){
        try {
            //调用service进行修改部门数据
            deptService.update(dept);
            //返回数据
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();//打印，方便开发，开发过程中，可以打印异常信息，方便定位问题
            if(e.getMessage().contains("Duplicate")){
                //名字重复
                return Result.error("修改部门失败，部门名称重复！");
            }else if(e.getMessage().contains("Data too long")){
                return Result.error("修改部门失败，部门名称过长！");
            }else{
                return Result.error("服务器忙，请稍后再试");
            }
        }
    }

}
