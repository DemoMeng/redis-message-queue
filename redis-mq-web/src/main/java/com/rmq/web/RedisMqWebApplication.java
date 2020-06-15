package com.rmq.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author mqz
 */
@SpringBootApplication
@EnableSwagger2
public class RedisMqWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisMqWebApplication.class, args);
    }

}
