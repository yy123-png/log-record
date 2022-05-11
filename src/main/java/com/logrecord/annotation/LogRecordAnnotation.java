package com.logrecord.annotation;

import java.lang.annotation.*;

/**
 * @author yeyu
 * @since 2022-05-11 14:28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecordAnnotation {

    String bizNo();

    String content();

    String serviceKey() default "";

    boolean needOld() default false;
}
