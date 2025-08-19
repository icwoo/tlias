package com.hm.service.impl;

import com.hm.mapper.EmpLogMapper;
import com.hm.pojo.EmpLog;
import com.hm.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description EmpLogServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-15
 */
@Service
public class EmpLogServiceImpl implements EmpLogService {
    @Autowired
    private EmpLogMapper empLogMapper;

    /**
     * 插入员工操作日志
     *
     * @param empLog
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insert(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
