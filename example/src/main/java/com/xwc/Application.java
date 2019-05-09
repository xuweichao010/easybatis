package com.xwc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 创建人：徐卫超
 * 时间: 2018/5/12
 * 功能：
 * 描述：
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.xwc.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
