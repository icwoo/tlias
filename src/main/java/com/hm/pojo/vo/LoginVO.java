package com.hm.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 用于登录返回的信息
 * @Author songyu
 * @Date 2025-08-18  9:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private Integer id;
    private String username;
    private String name;
    private String token;
}