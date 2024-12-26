package org.ligson.jtreesize.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    boolean lazy() default true;

    String name() default "";
}