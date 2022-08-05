package com.hanyi.rocket.handler;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 18:12
 */
public interface RocketMethodHandler {

    /**
     * 调用
     *
     * @param argv argv 参数
     * @return {@link Object}
     * @throws Throwable throwable
     */
    Object invoke(Object[] argv) throws Throwable;

}
