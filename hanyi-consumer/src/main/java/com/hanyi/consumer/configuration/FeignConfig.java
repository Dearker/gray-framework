package com.hanyi.consumer.configuration;

import cn.hutool.core.collection.CollUtil;
import com.hanyi.consumer.feign.lb.api.HashLoadbalancerRule;
import com.hanyi.consumer.feign.lb.api.LoadbalancerRule;
import com.hanyi.consumer.feign.service.PersonFeign;
import feign.RequestInterceptor;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/21 8:02 PM
 */
@Slf4j
@Configuration
public class FeignConfig {

    @Resource
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = configurableListableBeanFactory.getBeansWithAnnotation(FeignClient.class);
        if (CollUtil.isNotEmpty(beansWithAnnotation)) {
            beansWithAnnotation.forEach((k, v) -> {
                try {
                    Class<?> clazz = Class.forName(k);
                    configurableListableBeanFactory.registerSingleton(k + "rule", new HashLoadbalancerRule<>(clazz));
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage());
                }
            });
        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnBean(LoadbalancerRule.class)
    public RequestInterceptor getRequestInterceptor(List<LoadbalancerRule<PersonFeign>> lbRules) {
        Map<Class<PersonFeign>, LoadbalancerRule<PersonFeign>> collect = lbRules.stream()
                .collect(Collectors.toMap(LoadbalancerRule::getType, Function.identity()));

        return requestTemplate -> {
            Target<?> target = requestTemplate.feignTarget();
            LoadbalancerRule<PersonFeign> lbRule = collect.get(target.type());
            if (lbRule != null) {
                String newTarget = lbRule.rule(requestTemplate);
                log.info("IRule ==> {}", newTarget);
                requestTemplate.target(newTarget);
            }
        };
    }

    /**
     * 将请求头信息写入这个新建的请求对象中
     *
     * @return 返回拦截器对象
     */
    @Bean
    public RequestInterceptor requestHeaderInterceptor() {
        return requestTemplate -> {
            //获取请求头
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestTemplate.header(headerName, request.getHeader(headerName));
            }
        };
    }

}
