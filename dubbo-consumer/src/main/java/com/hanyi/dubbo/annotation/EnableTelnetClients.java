package com.hanyi.dubbo.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/26 10:16 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableTelnetClients {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
