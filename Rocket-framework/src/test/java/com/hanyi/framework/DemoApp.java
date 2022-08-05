package com.hanyi.framework;

import com.hanyi.rocket.api.GrayTopicProducer;
import org.junit.Test;

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
    public void apiTest(){
        Stream.of(GrayTopicProducer.class.getMethods()).forEach(System.out::println);
    }


}
