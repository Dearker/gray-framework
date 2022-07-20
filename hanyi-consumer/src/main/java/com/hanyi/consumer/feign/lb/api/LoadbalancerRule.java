package com.hanyi.consumer.feign.lb.api;

import feign.RequestTemplate;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:06
 */
public interface LoadbalancerRule<T> {

    /**
     * 得到规则名称
     *
     * @return {@link String}
     */
    String getRuleName();

    /**
     * 获取应用程序名称
     *
     * @return {@link String}
     */
    String getApplicationName();

    /**
     *
     * @param instances 当前注册中心中的实例
     * @param template 当此请求template，可以获取到请求参数
     * @return 返回负载均衡算法的请求url
     */
    String rule(List<ServiceInstance> instances, RequestTemplate template);

    /**
     * 获取类型
     *
     * @return {@link Class}<{@link T}>
     */
    Class<T> getType();

}
