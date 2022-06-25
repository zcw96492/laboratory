package com.newcore.laboratory.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 实验室-网关服务-主启动类
 * @author zhouchaowei
 */
@SpringBootApplication
@EnableZuulProxy
public class LaboratoryGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryGateWayApplication.class, args);
    }
}
