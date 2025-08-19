package com.hm.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryClazzDTO {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate end;
}