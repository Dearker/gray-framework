package com.hanyi.gateway.filter;

import cn.hutool.core.util.NumberUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 10:29 PM
 */
@Component
public class GrayHeaderFilter implements GlobalFilter, Ordered {

    private static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10149;

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取所有参数
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String id = queryParams.getFirst("id");
        if (NumberUtil.isNumber(id)) {
            long parseLong = Long.parseLong(id);
            if (parseLong % 2 == 0) {
                //构建请求头
                ServerHttpRequest httpRequest = request.mutate().header("version", "gray").build();
                return chain.filter(exchange.mutate().request(httpRequest).build());
            }
        }

        //放行请求
        return chain.filter(exchange);
    }
}
