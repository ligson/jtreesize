package org.ligson.jtreesize.core;

import org.ligson.jtreesize.core.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

public class BeanLifecycleManager {
    private final ApplicationContext context;

    public BeanLifecycleManager(ApplicationContext context) {
        this.context = context;
    }

    public void initializeBeans(Map<Class<?>, BeanDefinition> beans) throws Exception {
        for (BeanDefinition beanDefinition : beans.values()) {
            if (!beanDefinition.isLazy() && beanDefinition.getInstance() == null) {
                Object bean = context.getBean(beanDefinition.getBeanClass());
                beanDefinition.setInstance(bean);
            }
        }
        for (BeanDefinition beanDefinition : beans.values()) {
            if (beanDefinition.getInstance() != null) {
                injectFields(beanDefinition.getInstance());
            }
        }
    }

    private void injectFields(Object bean) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                field.set(bean, context.getBean(field.getType()));
            }
        }
    }
}
