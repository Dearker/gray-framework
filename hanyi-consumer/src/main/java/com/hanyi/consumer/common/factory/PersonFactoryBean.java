package com.hanyi.consumer.common.factory;

import com.hanyi.consumer.feign.pojo.Person;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/30 10:23 PM
 */
@Component
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

}
