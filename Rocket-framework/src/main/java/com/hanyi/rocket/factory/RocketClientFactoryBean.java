package com.hanyi.rocket.factory;

import com.hanyi.rocket.handler.RocketInvocationHandler;
import com.hanyi.rocket.handler.RocketMethodHandler;
import com.hanyi.rocket.handler.SynchronousRocketMethodHandler;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
public class RocketClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware, BeanFactoryAware, EnvironmentAware {

    private Class<?> type;

    private String name;

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    private Environment environment;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() {
        Method[] methods = type.getMethods();
        Map<Method, RocketMethodHandler> methodHandlerMap = new HashMap<>(methods.length);

        //获取mq的配置属性
        RocketMQProperties rocketMqProperties = Binder.get(environment)
                .bind("rocketmq", RocketMQProperties.class).orElse(null);

        for (Method method : methods) {
            SynchronousRocketMethodHandler handler = new SynchronousRocketMethodHandler(rocketMqProperties);
            methodHandlerMap.put(method, handler);
        }

        RocketInvocationHandler rocketInvocationHandler = new RocketInvocationHandler(methodHandlerMap);
        return Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), rocketInvocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static boolean isDefault(Method method) {
        // Default methods are public non-abstract, non-synthetic, and non-static instance methods
        // declared in an interface.
        // method.isDefault() is not sufficient for our usage as it does not check
        // for synthetic methods. As a result, it picks up overridden methods as well as actual default
        // methods.
        final int SYNTHETIC = 0x00001000;
        return ((method.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC | SYNTHETIC)) == Modifier.PUBLIC)
                && method.getDeclaringClass().isInterface();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
