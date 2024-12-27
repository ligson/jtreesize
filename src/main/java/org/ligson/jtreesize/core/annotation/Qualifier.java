// src/main/java/org/ligson/jtreesize/core/annotation/Qualifier.java
package org.ligson.jtreesize.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {
    String value();
}
