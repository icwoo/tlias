package com.hm.service;

import com.hm.pojo.EmpLog;

/**
 * @Description EmpLogService
 * @Author songyu
 * @Date 2025-08-15
 */
public interface EmpLogService {

    /**
     * 插入员工操作日志
     * @param empLog
     */
    void insert(EmpLog empLog);
}