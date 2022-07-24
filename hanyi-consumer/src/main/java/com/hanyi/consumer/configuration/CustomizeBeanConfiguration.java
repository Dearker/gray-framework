package com.hanyi.consumer.configuration;

import com.hanyi.consumer.feign.lb.api.HashLoadbalancerRule;
import com.hanyi.consumer.feign.lb.api.LoadbalancerRule;
import com.hanyi.consumer.feign.service.PersonFeign;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:27
 */
//@Configuration
public class CustomizeBeanConfiguration {

    //@Bean
    public LoadbalancerRule<PersonFeign> getLBRule(){
        return new HashLoadbalancerRule<>(PersonFeign.class);
    }

}
