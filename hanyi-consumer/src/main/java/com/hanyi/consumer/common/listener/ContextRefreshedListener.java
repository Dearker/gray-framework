package com.hanyi.consumer.common.listener;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/24 9:09 AM
 */
@Slf4j
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beansWithAnnotation = event.getApplicationContext().getBeansWithAnnotation(FeignClient.class);
        if(CollUtil.isNotEmpty(beansWithAnnotation)){
            log.info(beansWithAnnotation.toString());
        }
    }
}
