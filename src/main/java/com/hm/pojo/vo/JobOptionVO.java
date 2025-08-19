package com.hm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description JobOptionVO
 * @Author Lisheng Li
 * @Date 2025-08-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOptionVO {
    private List jobList;//职位名称列表
    private List dataList;//职位统计数量列表
}
