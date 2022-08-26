package com.hanyi.dubbo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-26 9:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private Integer id;

    private String name;

}
