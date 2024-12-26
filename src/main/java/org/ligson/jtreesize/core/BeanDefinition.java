package org.ligson.jtreesize.core;


import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class BeanDefinition {
    private String name;
    private Class<?> beanClass;
    private Class<?> interfaceClass;
    private Method initMethod;
    private Method destroyMethod;
    private Object instance;
    private boolean lazy;

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
