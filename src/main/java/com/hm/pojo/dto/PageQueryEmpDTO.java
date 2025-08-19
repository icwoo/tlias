package com.hm.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * @Description PageQueryEmpDTO
 * @Author Lisheng Li
 * @Date 2025-08-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryEmpDTO {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private Integer gender;
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate end;
}


