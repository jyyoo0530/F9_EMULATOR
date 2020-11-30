package com.emulator.f9;

import com.emulator.f9.service.UnlocodeMDM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.emulator"})
public class F9Application {

    public static void main(String[] args) {
        SpringApplication.run(F9Application.class, args);
    }

}
