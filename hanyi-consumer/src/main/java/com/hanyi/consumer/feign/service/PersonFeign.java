package com.hanyi.consumer.feign.service;

import com.hanyi.consumer.feign.pojo.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-07-20 18:28
 */
@FeignClient(name= "hanyi-producer", path = "/producer/person"/*, url = HashLoadbalancerRule.NAME*/)
public interface PersonFeign {

    @GetMapping("/byId")
    ResponseEntity<Person> getPerson(@RequestParam("id") Long id);

}
