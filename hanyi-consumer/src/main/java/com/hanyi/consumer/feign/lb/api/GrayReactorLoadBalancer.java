package com.hanyi.consumer.feign.lb.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 灰度负载均衡策略，结合@LoadBalanced使用
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/23 2:53 PM
 */
@Slf4j
@RequiredArgsConstructor
public class GrayReactorLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    @Resource
    private DiscoveryClient discoveryClient;

    // 服务名
    private final String serviceId;

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(serviceInstances ->
                this.processInstanceResponse(request, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(Request<RequestDataContext> request, List<ServiceInstance> serviceInstances) {
        RequestDataContext requestContext = request.getContext();

        //根据请求头中的版本号信息，选取注册中心中的相应服务实例
        RequestData clientRequest = requestContext.getClientRequest();
        String version = clientRequest.getHeaders().getFirst("version");

        if(serviceInstances.isEmpty()) {
            serviceInstances = discoveryClient.getInstances(clientRequest.getUrl().getHost());
        }

        List<ServiceInstance> instanceList = serviceInstances.stream()
                .filter(instance -> Objects.equals(version, instance.getMetadata().get("service.type"))).collect(Collectors.toList());

        if (instanceList.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("没有可用的服务，服务ID： " + this.serviceId);
            }

            return new EmptyResponse();
        }

        return instanceList.stream().findAny().map(DefaultResponse::new).get();
    }

}
