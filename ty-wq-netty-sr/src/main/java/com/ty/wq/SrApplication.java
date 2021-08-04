package com.ty.wq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 轮询回调客户端
 * @author Administrator
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class SrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrApplication.class, args);
    }

}
