package com.xwc.open.example;

import com.xwc.open.easybatis.EnableEasyBatis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEasyBatis
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
