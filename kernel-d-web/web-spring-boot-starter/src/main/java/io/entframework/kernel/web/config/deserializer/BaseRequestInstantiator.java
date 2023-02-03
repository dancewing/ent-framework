/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.web.config.deserializer;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BaseRequestInstantiator extends ValueInstantiator {

    private final ValueInstantiator proxy;
    private final ApplicationContext context;

    public BaseRequestInstantiator(ApplicationContext context, ValueInstantiator proxy) {
        this.context = context;
        this.proxy = proxy;
    }

    @Override
    public ValueInstantiator createContextual(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
        return this;
    }

    @Override
    public Class<?> getValueClass() {
        return proxy.getValueClass();
    }

    @Override
    public String getValueTypeDesc() {
        return proxy.getValueTypeDesc();
    }

    @Override
    public boolean canInstantiate() {
        return proxy.canInstantiate();
    }

    @Override
    public boolean canCreateFromString() {
        return proxy.canCreateFromString();
    }

    @Override
    public boolean canCreateFromInt() {
        return proxy.canCreateFromInt();
    }

    @Override
    public boolean canCreateFromLong() {
        return proxy.canCreateFromLong();
    }

    @Override
    public boolean canCreateFromBigInteger() {
        return proxy.canCreateFromBigInteger();
    }

    @Override
    public boolean canCreateFromDouble() {
        return proxy.canCreateFromDouble();
    }

    @Override
    public boolean canCreateFromBigDecimal() {
        return proxy.canCreateFromBigDecimal();
    }

    @Override
    public boolean canCreateFromBoolean() {
        return proxy.canCreateFromBoolean();
    }

    @Override
    public boolean canCreateUsingDefault() {
        return proxy.canCreateUsingDefault();
    }

    @Override
    public boolean canCreateUsingDelegate() {
        return proxy.canCreateUsingDelegate();
    }

    @Override
    public boolean canCreateUsingArrayDelegate() {
        return proxy.canCreateUsingArrayDelegate();
    }

    @Override
    public boolean canCreateFromObjectWith() {
        return proxy.canCreateFromObjectWith();
    }

    @Override
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
        return proxy.getFromObjectArguments(config);
    }

    @Override
    public JavaType getDelegateType(DeserializationConfig config) {
        return proxy.getDelegateType(config);
    }

    @Override
    public JavaType getArrayDelegateType(DeserializationConfig config) {
        return proxy.getArrayDelegateType(config);
    }

    @Override
    public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
        Class<?> typeClass = this.proxy.getValueClass();
        try {
            return this.context.getBean(typeClass);
        } catch(Exception ex){
            return this.proxy.createUsingDefault(ctxt);
        }
    }

    @Override
    public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
        throw new UnsupportedOperationException("标记一下,不知道什么时候会调用");
    }

    @Override
    public Object createFromObjectWith(DeserializationContext ctxt, SettableBeanProperty[] props, PropertyValueBuffer buffer) throws IOException {
        throw new UnsupportedOperationException("标记一下,不知道什么时候会调用");
    }

    @Override
    public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
        return proxy.createUsingDelegate(ctxt, delegate);
    }

    @Override
    public Object createUsingArrayDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
        return proxy.createUsingArrayDelegate(ctxt, delegate);
    }

    @Override
    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
        return proxy.createFromString(ctxt, value);
    }

    @Override
    public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
        return proxy.createFromInt(ctxt, value);
    }

    @Override
    public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
        return proxy.createFromLong(ctxt, value);
    }

    @Override
    public Object createFromBigInteger(DeserializationContext ctxt, BigInteger value) throws IOException {
        return proxy.createFromBigInteger(ctxt, value);
    }

    @Override
    public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
        return proxy.createFromDouble(ctxt, value);
    }

    @Override
    public Object createFromBigDecimal(DeserializationContext ctxt, BigDecimal value) throws IOException {
        return proxy.createFromBigDecimal(ctxt, value);
    }

    @Override
    public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
        return proxy.createFromBoolean(ctxt, value);
    }

    @Override
    public AnnotatedWithParams getDefaultCreator() {
        return proxy.getDefaultCreator();
    }

    @Override
    public AnnotatedWithParams getDelegateCreator() {
        return proxy.getDelegateCreator();
    }

    @Override
    public AnnotatedWithParams getArrayDelegateCreator() {
        return proxy.getArrayDelegateCreator();
    }

    @Override
    public AnnotatedWithParams getWithArgsCreator() {
        return proxy.getWithArgsCreator();
    }
}
