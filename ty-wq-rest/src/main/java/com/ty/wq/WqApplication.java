package com.ty.wq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Administrator
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
@Slf4j
public class WqApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(WqApplication.class, args);
        ConfigurableEnvironment environment = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application Manager is running! Access URLs:\n\t"
                        + "Local: \t\thttp://localhost:"
                        + port
                        + path
                        + "/\n\t"
                        + "External: \thttp://"
                        + ip
                        + ":"
                        + port
                        + path
                        + "/\n\t"
                        + "swagger-ui: http://"
                        + ip
                        + ":"
                        + port
                        + path
                        + "/swagger-ui.html\n\t"
                        + "Doc: \t\thttp://"
                        + ip
                        + ":"
                        + port
                        + path
                        + "/v2/api-docs\n"
                        + "----------------------------------------------------------");
    }
}
