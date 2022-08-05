package com.hanyi.rocket;

import com.hanyi.rocket.annotation.EnableRocketClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 9:37
 */
@EnableRocketClients
@SpringBootApplication
public class RocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketApplication.class, args);
    }

}
