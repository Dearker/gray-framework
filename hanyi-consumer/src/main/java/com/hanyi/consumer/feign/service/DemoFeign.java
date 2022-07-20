package com.hanyi.consumer.feign.service;

import com.hanyi.consumer.feign.lb.api.HashLoadbalancerRule;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:28
 */
@FeignClient(value= "xxx", url = HashLoadbalancerRule.NAME)
public interface DemoFeign {

    

}
