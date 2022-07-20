package com.hanyi.consumer.feign.lb.api;

import cn.hutool.core.util.StrUtil;
import com.hanyi.consumer.exception.BizException;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:08
 */
public abstract class AbstractLoadbalancerRuler<T> implements LoadbalancerRule<T> {

    private final Class<T> type;

    private final String applicationName;

    private final String path;

    public AbstractLoadbalancerRuler(Class<T> type) {
        this.type = type;
        FeignClient annotation = type.getAnnotation(FeignClient.class);
        if (annotation == null) {
            throw new BizException(1001, "can not find FeignClient annotation");
        }
        if (StrUtil.isBlank(annotation.value()) && StrUtil.isBlank(annotation.name())) {
            throw new BizException(1002, "value/name in FeignClient annotation can not be empty");
        }
        if (StrUtil.isBlank(annotation.value())) {
            applicationName = annotation.name();
        } else {
            applicationName = annotation.value();
        }
        if (StrUtil.isBlank(annotation.url())) {
            throw new BizException(1003, "url in FeignClient annotation can not be empty");
        }
        path = annotation.path();
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    protected String getPath() {
        return path;
    }

}
