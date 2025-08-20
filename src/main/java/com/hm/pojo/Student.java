package com.hm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description Student
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;                // 学生ID（主键）
    private String name;              // 姓名
    private String no;                // 学号
    private Integer gender;           // 性别（0:女, 1:男）
    private String phone;            // 电话
    private Integer degree;           // 学历（如：1:本科, 2:硕士, 3:博士）
    private String idCard;           // 身份证号
    private Integer isCollege;        // 是否在校（0:否, 1:是）
    private String address;          // 地址
    private LocalDate graduationDate;  // 毕业日期
    private Integer violationCount;   // 违纪次数
    private Integer violationScore;   // 违纪扣分
    private Integer clazzId;          // 班级ID
    private String clazzName;         // 班级名称
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
