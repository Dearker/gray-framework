package com.hanyi.framework.api;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/7 9:54 PM
 */
public class MethodApi {

    public void send(String topic,Long time){
        System.out.printf("topic is %s the time is %s%n", topic, time);
    }

    public void send(String topic,Integer age){
        System.out.printf("topic is %s the age is %s%n", topic, age);
    }

}
