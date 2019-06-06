package com.hxd.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huxudong
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"com.baidu","com.hxd"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

