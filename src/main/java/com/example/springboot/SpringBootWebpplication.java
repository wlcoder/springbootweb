package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//启动时不加载DataSourceAutoConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootWebpplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebpplication.class, args);
    }

}
