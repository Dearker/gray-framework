package com.hanyi.producer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 8:06 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private Long id;

    private String name;

}
