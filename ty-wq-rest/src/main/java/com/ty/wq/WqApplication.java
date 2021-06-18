package com.ty.wq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@SpringBootApplication
public class WqApplication {
    public static void main(String[] args) {
        SpringApplication.run(WqApplication.class, args);
    }
}
