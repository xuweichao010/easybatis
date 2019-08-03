package com.xwc.open.example;


import com.xwc.open.esbatis.EnableEsBatis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEsBatis
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
