package com.hanyi.dubbo.controller;

import com.hanyi.dubbo.api.PersonService;
import com.hanyi.dubbo.pojo.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-26 9:20
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/id")
    public Person getPersonById(Integer id){
        return personService.getPersonById(id);
    }

}
