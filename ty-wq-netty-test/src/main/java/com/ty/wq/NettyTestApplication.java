package com.ty.wq;

import com.ty.wq.scanner.WsScanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class NettyTestApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(new WsScanner());
        thread.start();
    }
}
