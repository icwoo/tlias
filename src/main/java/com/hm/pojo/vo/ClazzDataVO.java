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
public class ClazzDataVO {
    private List<String> clazzList;//职位名称列表
    private List<Integer> dataList;//职位统计数量列表
}
