package com.ty.wq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableTransactionManagement
public class WqManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WqManagerApplication.class, args);
    }
}
