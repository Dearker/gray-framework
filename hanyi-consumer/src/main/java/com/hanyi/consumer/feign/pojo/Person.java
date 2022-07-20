package com.hanyi.consumer.feign.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 8:22 PM
 */
@Data
public class Person implements Serializable {

    private Long id;

    private String name;

}
