package com.hanyi.rocket.api;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 9:40
 */
public interface RocketBaseProducer {

    SendResult send(Object message);

    void sendOneway(final Message msg);

}
