package com.hanyi.producer.service.impl;

import com.hanyi.producer.pojo.Person;
import com.hanyi.producer.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 8:10 PM
 */
@Service
public class PersonServiceImpl implements PersonService {

    private static final Map<Long, Person> PERSON_MAP = new HashMap<>();

    static {
        LongStream.rangeClosed(0, 10).forEach(s -> PERSON_MAP.put(s, new Person(s, "柯基：" + s + "号")));
    }

    @Override
    public Person getPersonById(Long id) {
        return PERSON_MAP.getOrDefault(id, new Person());
    }
}
