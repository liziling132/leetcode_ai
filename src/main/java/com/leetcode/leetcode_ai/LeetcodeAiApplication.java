package com.leetcode.leetcode_ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@MapperScan("com.leetcode.leetcode_ai.**.mapper")
@ConfigurationPropertiesScan
public class LeetcodeAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeetcodeAiApplication.class, args);
    }

}
