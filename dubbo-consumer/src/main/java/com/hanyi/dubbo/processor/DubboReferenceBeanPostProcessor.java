package com.hanyi.dubbo.processor;

import com.hanyi.dubbo.annotation.TelnetClient;
import com.hanyi.dubbo.builder.ReferenceBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/31 8:42 PM
 */
@Slf4j
@Component
public class DubboReferenceBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    private final ConcurrentMap<String, ReferenceBean<?>> referenceBeanCache = new ConcurrentHashMap<>(Byte.SIZE);

    private final Map<String, Object> beansWithAnnotationMap = new HashMap<>(Byte.SIZE);

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        //key为全类名，value为具体对象
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(TelnetClient.class);
        beansWithAnnotationMap.putAll(map);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Set<String> keySet = beansWithAnnotationMap.keySet();
        Stream.of(bean.getClass().getDeclaredFields()).filter(s -> keySet.contains(s.getClass().getName())).forEach(s -> {
            ReflectionUtils.makeAccessible(s);
            ReflectionUtils.setField(s, bean, beansWithAnnotationMap.get(s.getClass().getName()));
        });

        return pvs;
    }

    /**
     * 如果没有建立引用bean
     *
     * @param referenceBeanName 引用bean名称
     * @param attributes        属性
     * @param referencedType    引用类型
     * @return {@link ReferenceBean}<{@link ?}>
     * @throws Exception 异常
     */
    private ReferenceBean<?> buildReferenceBeanIfAbsent(String referenceBeanName, AnnotationAttributes attributes,
                                                        Class<?> referencedType) throws Exception {
        ReferenceBean<?> referenceBean = referenceBeanCache.get(referenceBeanName);

        if (referenceBean == null) {
            ReferenceBeanBuilder beanBuilder = ReferenceBeanBuilder
                    .create(attributes, applicationContext)
                    .interfaceClass(referencedType);
            referenceBean = beanBuilder.build();
            referenceBeanCache.put(referenceBeanName, referenceBean);
        } else if (!referencedType.isAssignableFrom(referenceBean.getInterfaceClass())) {
            throw new IllegalArgumentException("reference bean name " + referenceBeanName + " has been duplicated, but interfaceClass " +
                    referenceBean.getInterfaceClass().getName() + " cannot be assigned to " + referencedType.getName());
        }
        return referenceBean;
    }

}
