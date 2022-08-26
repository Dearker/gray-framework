package com.hanyi.dubbo.api;

import com.hanyi.dubbo.pojo.Person;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-26 9:09
 */
public interface PersonService {

    /**
     * 通过id获取书
     *
     * @param id id
     * @return {@link Person}
     */
    Person getPersonById(Integer id);

}
