package com.hanyi.rocket.annotation;

import com.hanyi.rocket.registrar.RocketClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 10:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RocketClientsRegistrar.class)
public @interface EnableRocketClients {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
