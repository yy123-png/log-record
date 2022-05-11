package com.logrecord.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author yeyu
 * @since 2022-05-10 14:15
 */
@Data
@AllArgsConstructor
public class LogRootObject {

    private final Method method;

    private final Class<?> targetClass;

    private final Object[] args;

}
