package com.hanyi.rocket.factory;

import com.hanyi.rocket.handler.RocketInvocationHandler;
import com.hanyi.rocket.handler.RocketMethodHandler;
import com.hanyi.rocket.handler.SynchronousRocketMethodHandler;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 10:56
 */
public class RocketClientFactoryBean implements FactoryBean<Object>, BeanFactoryAware {

    private Class<?> type;

    private String topic;

    private BeanFactory beanFactory;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public Object getObject() {
        Method[] methods = type.getMethods();
        Map<Method, RocketMethodHandler> methodHandlerMap = new HashMap<>(methods.length);

        //获取mq的配置属性
        DefaultMQProducer defaultMqProducer = this.getBeanFactory().getBean(DefaultMQProducer.class);

        for (Method method : methods) {
            SynchronousRocketMethodHandler handler = new SynchronousRocketMethodHandler(method, this.getTopic(), defaultMqProducer);
            methodHandlerMap.put(method, handler);
        }

        RocketInvocationHandler rocketInvocationHandler = new RocketInvocationHandler(methodHandlerMap);
        return Proxy.newProxyInstance(this.getType().getClassLoader(), new Class[]{this.getType()}, rocketInvocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
