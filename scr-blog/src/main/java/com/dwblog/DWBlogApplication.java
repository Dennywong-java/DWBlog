package com.dwblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dwblog.mapper")
public class DWBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DWBlogApplication.class,args);
    }
}
