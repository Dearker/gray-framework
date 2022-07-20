package com.hanyi.consumer.feign.lb.interceptor;

import com.hanyi.consumer.feign.lb.api.LoadbalancerRule;
import com.hanyi.consumer.feign.service.PersonFeign;
import feign.RequestInterceptor;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:01
 */
@Slf4j
public class LoadbalancerFeignInterceptor {

    //@Bean
    //@ConditionalOnBean(LoadbalancerRule.class)
    public RequestInterceptor getRequestInterceptor(DiscoveryClient discoveryClient, List<LoadbalancerRule<PersonFeign>> lbRules) {
        Map<Class<PersonFeign>, LoadbalancerRule<PersonFeign>> collect = lbRules.stream()
                .collect(Collectors.toMap(LoadbalancerRule::getType, Function.identity()));

        return requestTemplate -> {
            Target<?> target = requestTemplate.feignTarget();
            LoadbalancerRule<PersonFeign> lbRule = collect.get(target.type());
            if (lbRule != null) {
                List<ServiceInstance> instances = discoveryClient.getInstances(lbRule.getApplicationName());
                String newTarget = lbRule.rule(instances, requestTemplate);
                log.info("IRule ==> {}", newTarget);
                requestTemplate.target(newTarget);
            }
        };
    }

}
