package com.fall.robok;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan(basePackages = "com.fall.robok.mapper")
public class RobokWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobokWebApplication.class, args);
    }

}
