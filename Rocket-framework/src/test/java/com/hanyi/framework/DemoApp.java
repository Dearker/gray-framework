package com.hanyi.framework;

import com.hanyi.framework.api.MethodApi;
import com.hanyi.rocket.api.GrayTopicProducer;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 10:24
 */
public class DemoApp {

    @Test
    public void apiTest() {
        Stream.of(GrayTopicProducer.class.getMethods()).forEach(System.out::println);
    }

    @Test
    public void methodTest() throws Exception {
        MethodApi methodApi = new MethodApi();
        Object[] objects = new Object[2];
        objects[0] = "哈哈哈";
        objects[1] = 100L;
        Method send = MethodApi.class.getMethod("send", objects[0].getClass(), objects[1].getClass());
        send.invoke(methodApi, objects);
    }

}
