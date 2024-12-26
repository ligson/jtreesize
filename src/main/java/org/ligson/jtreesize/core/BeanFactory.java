package org.ligson.jtreesize.core;

import org.ligson.jtreesize.core.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private final ApplicationContext context;
    private final Map<String, Object> namedBeans = new HashMap<>();

    public BeanFactory(ApplicationContext context) {
        this.context = context;
    }

    public Object createBean(Class<?> clazz) throws Exception {
        // Initialize dependencies first
        Constructor<?> constructor = clazz.getConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = context.getBean(parameterTypes[i]);
        }

        Object bean = constructor.newInstance(parameters);
        injectFields(bean);
        invokeAnnotatedMethods(bean, PreConstructor.class);
        String beanName = getBeanName(clazz);
        namedBeans.put(beanName, bean);
        return bean;
    }

    private void injectFields(Object bean) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = context.getBean(field.getType());
                field.set(bean, dependency);
            }
        }
    }

    public void destroyBean(Object bean) throws Exception {
        invokeAnnotatedMethods(bean, PreDestroy.class);
    }

    private void invokeAnnotatedMethods(Object bean, Class<?> annotation) throws Exception {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent((Class<? extends Annotation>) annotation)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }

    private String getBeanName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            return component.name().isEmpty() ? getDefaultBeanName(clazz) : component.name();
        } else if (clazz.isAnnotationPresent(Service.class)) {
            Service service = clazz.getAnnotation(Service.class);
            return service.name().isEmpty() ? getDefaultBeanName(clazz) : service.name();
        }
        return getDefaultBeanName(clazz);
    }

    private String getDefaultBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
    }

    public Object getBeanByName(String name) {
        return namedBeans.get(name);
    }
}
