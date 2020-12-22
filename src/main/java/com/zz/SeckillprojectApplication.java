package com.zz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zz.dao")
public class SeckillprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillprojectApplication.class, args);
    }

}
