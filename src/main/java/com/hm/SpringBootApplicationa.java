package com.hm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description com.hm.SpringBootApplicationa
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
@ServletComponentScan
@SpringBootApplication
public class SpringBootApplicationa {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationa.class, args);
    }

}
