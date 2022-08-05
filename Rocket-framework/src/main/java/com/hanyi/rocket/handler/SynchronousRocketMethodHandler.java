package com.hanyi.rocket.handler;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 18:37
 */
public class SynchronousRocketMethodHandler implements RocketMethodHandler {

    private DefaultMQProducer defaultMqProducer;

    private final RocketMQProperties rocketMqProperties;

    public SynchronousRocketMethodHandler(RocketMQProperties rocketMqProperties) {
        this.rocketMqProperties = rocketMqProperties;
    }

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        if(Objects.isNull(defaultMqProducer)){
            defaultMqProducer = this.defaultMqProducer(rocketMqProperties);
        }

        Message message = new Message();
        message.setBody("哈哈哈".getBytes());
        return defaultMqProducer.send(message);
    }

    public DefaultMQProducer defaultMqProducer(RocketMQProperties rocketMqProperties) {
        RocketMQProperties.Producer producerConfig = rocketMqProperties.getProducer();
        String nameServer = rocketMqProperties.getNameServer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.producer.group] must not be null");

        DefaultMQProducer producer;
        String ak = rocketMqProperties.getProducer().getAccessKey();
        String sk = rocketMqProperties.getProducer().getSecretKey();
        if (!StringUtils.isEmpty(ak) && !StringUtils.isEmpty(sk)) {
            producer = new DefaultMQProducer(groupName, new AclClientRPCHook(new SessionCredentials(ak, sk)),
                    rocketMqProperties.getProducer().isEnableMsgTrace(),
                    rocketMqProperties.getProducer().getCustomizedTraceTopic());
            producer.setVipChannelEnabled(false);
        } else {
            producer = new DefaultMQProducer(groupName, rocketMqProperties.getProducer().isEnableMsgTrace(),
                    rocketMqProperties.getProducer().getCustomizedTraceTopic());
        }

        producer.setNamesrvAddr(nameServer);
        producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryNextServer());

        return producer;
    }

}
