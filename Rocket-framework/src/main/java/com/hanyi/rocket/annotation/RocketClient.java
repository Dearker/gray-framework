package com.hanyi.rocket.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 10:49
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RocketClient {

    /**
     * @return topic name
     */
    @AliasFor("name")
    String value() default "";

    /**
     * @return topic name
     */
    @AliasFor("value")
    String name() default "";

    /**
     * @return whether to mark the rocket proxy as a primary bean. Defaults to true.
     */
    boolean primary() default true;
}
