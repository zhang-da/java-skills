package com.da.learn.cloud.prometheus.aspect;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodMetric {
    String name() default "";

    String description() default "";

    String[] tags() default {};
}
