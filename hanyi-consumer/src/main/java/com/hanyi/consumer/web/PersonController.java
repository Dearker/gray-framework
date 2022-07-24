package com.hanyi.consumer.web;

import com.hanyi.consumer.feign.pojo.Person;
import com.hanyi.consumer.feign.service.PersonFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    private final RestTemplate restTemplate;

    @GetMapping("/byId")
    public ResponseEntity<Person> getPerson(Long id) {
        return personFeign.getPerson(id);
    }

    @GetMapping("/lbById")
    public ResponseEntity<Person> getPersonByLb(Long id) {
        return restTemplate.getForEntity("http://hanyi-producer/producer/person/byId?id=" + id, Person.class);
    }

}
