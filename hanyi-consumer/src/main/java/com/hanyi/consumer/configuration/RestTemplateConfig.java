package com.hanyi.consumer.configuration;

import com.hanyi.consumer.feign.lb.api.GrayReactorLoadBalancer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/23 3:43 PM
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> grayReactorLoadBalancer(Environment environment,
                                                                        ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GrayReactorLoadBalancer(name, serviceInstanceListSupplierProvider);
    }

}
