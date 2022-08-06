package com.hanyi.rocket.controller;

import com.hanyi.rocket.api.GrayTopicProducer;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RocketController {

    private final GrayTopicProducer grayTopicProducer;

    @GetMapping("/send")
    public void send(String message){
        grayTopicProducer.send(message);
    }

}
