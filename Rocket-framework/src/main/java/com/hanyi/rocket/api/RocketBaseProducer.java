package com.hanyi.rocket.api;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 9:40
 */
public interface RocketBaseProducer {

    SendResult send(final Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

    void sendOneway(final Message msg) throws MQClientException, RemotingException,
            InterruptedException;

}
