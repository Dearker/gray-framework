package com.hanyi.dubbo.web;

import com.hanyi.dubbo.api.PersonService;
import com.hanyi.dubbo.pojo.Person;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-26 14:40
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @DubboReference
    private PersonService personService;

    @GetMapping("/id")
    public Person getPersonById(Integer id){
        return personService.getPersonById(id);
    }

}
