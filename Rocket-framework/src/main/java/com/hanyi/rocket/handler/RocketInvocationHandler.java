package com.hanyi.rocket.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 17:24
 */
public class RocketInvocationHandler implements InvocationHandler {

    private final Map<Method, RocketMethodHandler> dispatch;

    public RocketInvocationHandler(Map<Method, RocketMethodHandler> dispatch) {
        this.dispatch = dispatch;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "equals":
                try {
                    Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                    return equals(otherHandler);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            case "hashCode":
                return hashCode();
            case "toString":
                return toString();
            default:
                break;
        }

        return dispatch.get(method).invoke(args);
    }
}
