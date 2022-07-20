package com.hanyi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 * 网关启动类，查看文档地址：https://www.jianshu.com/p/6db15bc0be8f
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 14:49
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GrayGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrayGatewayApplication.class);
    }

}
