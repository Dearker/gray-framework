package com.hanyi.consumer.feign.lb.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Request;
import feign.RequestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:22
 */
public class HashLoadbalancerRule<T> extends AbstractLoadbalancerRuler<T> {

    public static final String NAME = "hash";

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * @param type feignclient 的类
     */
    public HashLoadbalancerRule(Class<T> type) {
        super(type);
    }

    @Override
    public String getRuleName() {
        return NAME;
    }

    @Override
    public String rule(RequestTemplate template) {
        List<ServiceInstance> instances = discoveryClient.getInstances(super.getApplicationName());

        instances.sort(Comparator.comparing(ServiceInstance::getHost));
        String value = this.getValue(template);
        int i = Math.abs(value.hashCode() % instances.size());
        ServiceInstance serviceInstance = instances.get(i);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String path = super.getPath();
        if (CharSequenceUtil.isBlank(path)) {
            return String.format("http://%s:%s", host, port);
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return String.format("http://%s:%s/%s", host, port, path);
    }

    public boolean isGetRequestTemplate(RequestTemplate requestTemplate) {
        return requestTemplate.method().equalsIgnoreCase(Request.HttpMethod.GET.name());
    }

    public String getValue(RequestTemplate requestTemplate) {
        if (this.isGetRequestTemplate(requestTemplate)) {
            //获取所有参数集合
            Map<String, Collection<String>> queriesMap = requestTemplate.queries();
            if (CollUtil.isNotEmpty(queriesMap)) {
                return queriesMap.values().stream().flatMap(Collection::stream).sorted()
                        .findFirst().orElse(CharSequenceUtil.EMPTY);
            }
        } else {
            byte[] body = requestTemplate.body();
            JSONObject jsonObject = JSON.parseObject(new String(body));
            return jsonObject.toJSONString();
        }
        return null;
    }

}
