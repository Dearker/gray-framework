package com.hanyi.dubbo.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 远程客户端注解
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/31 9:27 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TelnetClient {


}
