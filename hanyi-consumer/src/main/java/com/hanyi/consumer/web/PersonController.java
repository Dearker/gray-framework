package com.hanyi.consumer.web;

import com.hanyi.consumer.feign.pojo.Person;
import com.hanyi.consumer.feign.service.PersonFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 8:24 PM
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonFeign personFeign;

    @GetMapping("/byId")
    public ResponseEntity<Person> getPerson(Long id) {
        return personFeign.getPerson(id);
    }

}
