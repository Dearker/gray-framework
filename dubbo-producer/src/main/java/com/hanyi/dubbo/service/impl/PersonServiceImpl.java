package com.hanyi.dubbo.service.impl;

import com.hanyi.dubbo.api.PersonService;
import com.hanyi.dubbo.pojo.Person;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-26 9:11
 */
@DubboService
public class PersonServiceImpl implements PersonService {

    private static final Map<Integer, Person> PERSON_MAP = Stream.of(new Person(1, "哈哈哈"),
            new Person(2, "快快快"), new Person(3, "看看看")).collect(Collectors.toMap(Person::getId, Function.identity()));

    /**
     * 通过id获取书
     *
     * @param id id
     * @return {@link Person}
     */
    @Override
    public Person getPersonById(Integer id) {
        return PERSON_MAP.get(id);
    }
}
