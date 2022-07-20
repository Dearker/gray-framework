package com.hanyi.producer.web;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.hanyi.producer.pojo.Person;
import com.hanyi.producer.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 8:04 PM
 */
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping("/byId")
    public ResponseEntity<Person> getPerson(Long id) {
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        Person person = personService.getPersonById(id);
        String serviceType = metadata.get("service.type");
        //设置灰度属性
        if (CharSequenceUtil.isNotBlank(serviceType)) {
            String name = person.getName();
            person = new Person(person.getId(), serviceType + " -- " + name);
        }

        return ResponseEntity.ok(person);
    }

}
