package com.hanyi.rocket.handler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 *  直接调用本地方法
 * {@see DefaultMethodHandler}
 * </p>
 *
 * @author wcwei@iflytek.com
 * @since 2022-08-05 18:08
 */
public class DefaultRocketMethodHandler implements RocketMethodHandler {

    private final MethodHandle unboundHandle;

    private MethodHandle handle;

    public DefaultRocketMethodHandler(Method defaultMethod) {
        Class<?> declaringClass = defaultMethod.getDeclaringClass();

        try {
            MethodHandles.Lookup lookup = readLookup(declaringClass);
            this.unboundHandle = lookup.unreflectSpecial(defaultMethod, declaringClass);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private MethodHandles.Lookup readLookup(Class<?> declaringClass)
            throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        try {
            return safeReadLookup(declaringClass);
        } catch (NoSuchMethodException e) {
            try {
                return androidLookup(declaringClass);
            } catch (InstantiationException | NoSuchMethodException instantiationException) {
                return legacyReadLookup();
            }
        }
    }

    public MethodHandles.Lookup androidLookup(Class<?> declaringClass) throws InstantiationException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup;
        try {
            // Android 9+ double reflection
            Class<?> classReference = Class.class;
            Class<?>[] classType = new Class[] {Class.class};
            Method reflectedGetDeclaredConstructor = classReference.getDeclaredMethod(
                    "getDeclaredConstructor",
                    Class[].class);
            reflectedGetDeclaredConstructor.setAccessible(true);
            Constructor<?> someHiddenMethod =
                    (Constructor<?>) reflectedGetDeclaredConstructor.invoke(MethodHandles.Lookup.class, (Object) classType);
            lookup = (MethodHandles.Lookup) someHiddenMethod.newInstance(declaringClass);
        } catch (IllegalAccessException ex0) {
            // Android < 9 reflection
            Constructor<MethodHandles.Lookup> lookupConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
            lookupConstructor.setAccessible(true);
            lookup = lookupConstructor.newInstance(declaringClass);
        }
        return (lookup);
    }

    private MethodHandles.Lookup safeReadLookup(Class<?> declaringClass)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Object privateLookupIn =
                MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class)
                        .invoke(null, declaringClass, lookup);
        return (MethodHandles.Lookup) privateLookupIn;
    }

    private MethodHandles.Lookup legacyReadLookup() throws NoSuchFieldException, IllegalAccessException {
        Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
        field.setAccessible(true);
        return (MethodHandles.Lookup) field.get(null);
    }

    public void bindTo(Object proxy) {
        if (handle != null) {
            throw new IllegalStateException("Attempted to rebind a default method handler that was already bound");
        }
        handle = unboundHandle.bindTo(proxy);
    }

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        if (handle == null) {
            throw new IllegalStateException("Default method handler invoked before proxy has been bound.");
        }
        return handle.invokeWithArguments(argv);
    }

}
