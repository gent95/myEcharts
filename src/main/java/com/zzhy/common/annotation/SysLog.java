package com.zzhy.common.annotation;

import java.lang.annotation.*;

/**
 * Created by majt on 2018-07-03.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
