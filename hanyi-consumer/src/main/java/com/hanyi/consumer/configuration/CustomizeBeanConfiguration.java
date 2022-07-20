package com.hanyi.consumer.configuration;

import com.hanyi.consumer.feign.lb.api.HashLoadbalancerRule;
import com.hanyi.consumer.feign.lb.api.LoadbalancerRule;
import com.hanyi.consumer.feign.service.DemoFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:27
 */
@Configuration
public class CustomizeBeanConfiguration {

    @Bean
    public LoadbalancerRule<DemoFeign> getLBRule(){
        return new HashLoadbalancerRule<>(DemoFeign.class,"key");
    }

    @Bean
    public LoadbalancerRule<DemoFeign> getLBRule2(){
        return new HashLoadbalancerRule<>(DemoFeign.class,"key");
    }

}
