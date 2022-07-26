package com.hanyi.rocket.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息处理组件
 * </p>
 *
 * @author wenchangwei
 * @since 9:20 下午 2020/12/8
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "shop-order", topic = "test-topic-1")
public class MessageComponent implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("收到信息的时间：{} ,消息内容为：{}", LocalDateTime.now(), s);
    }

}
