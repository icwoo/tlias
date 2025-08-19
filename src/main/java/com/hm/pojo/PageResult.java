package com.hm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description PageResult
 * @Author songyu
 * @Date 2025-08-14  11:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private List<T> rows; // 当前页列表数据
    private Long total; //总条数
}