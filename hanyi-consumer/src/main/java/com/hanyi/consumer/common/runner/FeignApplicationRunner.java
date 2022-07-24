package com.hanyi.consumer.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/23 10:29 PM
 */
@Slf4j
@Component
public class FeignApplicationRunner implements ApplicationRunner {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        List<String> stringList = Stream.of(beanDefinitionNames).filter(s -> {
            Object bean = applicationContext.getBean(s);
            FeignClient annotation = AnnotationUtils.findAnnotation(bean.getClass(), FeignClient.class);
            return Objects.nonNull(annotation);
        }).collect(Collectors.toList());
        log.info(stringList.toString());
    }

}
