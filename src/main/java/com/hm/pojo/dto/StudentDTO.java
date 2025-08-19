package com.hm.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description StudentDTO
 * @Author Lisheng Li
 * @Date 2025-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private Integer degree;
    private Integer clazzId;
}
