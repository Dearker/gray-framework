package com.hanyi.rocket.api;

import com.hanyi.rocket.annotation.RocketClient;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 10:23
 */
@RocketClient(topic = "test-topic-1")
public interface GrayTopicProducer extends RocketBaseProducer {


}
