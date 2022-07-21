package com.hanyi.consumer.feign.lb.api;

import cn.hutool.core.text.CharSequenceUtil;
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

    protected AbstractLoadbalancerRuler(Class<T> type) {
        this.type = type;
        FeignClient annotation = type.getAnnotation(FeignClient.class);
        if (annotation == null) {
            throw new BizException(1001, "can not find FeignClient annotation");
        }
        if (CharSequenceUtil.isBlank(annotation.value()) && CharSequenceUtil.isBlank(annotation.name())) {
            throw new BizException(1002, "value/name in FeignClient annotation can not be empty");
        }
        String value = annotation.value();
        applicationName = CharSequenceUtil.isBlank(value) ? annotation.name() : value;
        if (CharSequenceUtil.isBlank(annotation.url())) {
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
