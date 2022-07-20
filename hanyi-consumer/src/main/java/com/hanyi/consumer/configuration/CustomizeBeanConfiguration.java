package com.hanyi.consumer.configuration;

import com.hanyi.consumer.feign.lb.api.HashLoadbalancerRule;
import com.hanyi.consumer.feign.lb.api.LoadbalancerRule;
import com.hanyi.consumer.feign.service.PersonFeign;
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

    //@Bean
    public LoadbalancerRule<PersonFeign> getLBRule(){
        return new HashLoadbalancerRule<>(PersonFeign.class,"key");
    }

    //@Bean
    public LoadbalancerRule<PersonFeign> getLBRule2(){
        return new HashLoadbalancerRule<>(PersonFeign.class,"key");
    }

}
