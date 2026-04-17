package com.leetcode.leetcode_ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.leetcode.leetcode_ai.**.mapper")
@ConfigurationPropertiesScan
@EnableScheduling
public class LeetcodeAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeetcodeAiApplication.class, args);
    }

}
