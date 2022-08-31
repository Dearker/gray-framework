package com.hanyi.dubbo.builder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.env.PropertyResolver;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.alibaba.spring.util.AnnotationUtils.getAttributes;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/8/31 10:03 PM
 */
public class AnnotationPropertyValuesAdapter implements PropertyValues {

    private final PropertyValues delegate;

    /**
     * @param attributes
     * @param propertyResolver
     * @param ignoreAttributeNames
     * @since 2.7.3
     */
    public AnnotationPropertyValuesAdapter(Map<String, Object> attributes, PropertyResolver propertyResolver,
                                           String... ignoreAttributeNames) {
        this.delegate = new MutablePropertyValues(getAttributes(attributes, propertyResolver, ignoreAttributeNames));
    }

    public AnnotationPropertyValuesAdapter(Annotation annotation, PropertyResolver propertyResolver,
                                           boolean ignoreDefaultValue, String... ignoreAttributeNames) {
        this.delegate = new MutablePropertyValues(getAttributes(annotation, propertyResolver, ignoreDefaultValue, ignoreAttributeNames));
    }

    public AnnotationPropertyValuesAdapter(Annotation annotation, PropertyResolver propertyResolver, String... ignoreAttributeNames) {
        this(annotation, propertyResolver, true, ignoreAttributeNames);
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return delegate.getPropertyValues();
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        return delegate.getPropertyValue(propertyName);
    }

    @Override
    public PropertyValues changesSince(PropertyValues old) {
        return delegate.changesSince(old);
    }

    @Override
    public boolean contains(String propertyName) {
        return delegate.contains(propertyName);
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

}
