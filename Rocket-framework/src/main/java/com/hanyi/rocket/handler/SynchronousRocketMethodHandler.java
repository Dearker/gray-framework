package com.hanyi.rocket.handler;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 18:37
 */
@RequiredArgsConstructor
public class SynchronousRocketMethodHandler implements RocketMethodHandler {

    private final Method method;

    private final String topic;

    private final DefaultMQProducer defaultMqProducer;

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(argv[0].toString().getBytes());
        return defaultMqProducer.send(message);
    }

}
