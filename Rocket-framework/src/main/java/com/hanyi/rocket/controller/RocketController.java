package com.hanyi.rocket.controller;

import com.hanyi.rocket.api.GrayTopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/5 9:25 PM
 */
@RestController
@RequestMapping
public class RocketController {

    @Autowired
    private GrayTopicProducer grayTopicProducer;

    @GetMapping("/send")
    public void send(String message){
        grayTopicProducer.send(message);
    }

}
