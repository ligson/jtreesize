package org.ligson.jtreesize.core;

import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.annotation.Service;
import org.ligson.jtreesize.core.event.Event;
import org.ligson.jtreesize.core.event.EventListener;
import org.ligson.jtreesize.core.event.EventRegister;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanScanner {
    private final ApplicationContext context;

    public BeanScanner(ApplicationContext context) {
        this.context = context;
    }

    public Map<Class<?>, BeanDefinition> scanComponents(String[] basePackages) throws Exception {
        Map<Class<?>, BeanDefinition> beans = new HashMap<>();
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
            Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
            Set<Class<?>> beanClasses = new HashSet<>();
            beanClasses.addAll(components);
            beanClasses.addAll(services);
            for (Class<?> clazz : beanClasses) {
                BeanDefinition beanDefinition = getBeanDefinition(clazz);
                beans.put(clazz, beanDefinition);
            }
        }
        return beans;
    }

    private static BeanDefinition getBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setLazy(clazz.isAnnotationPresent(Component.class) ? clazz.getAnnotation(Component.class).lazy() : clazz.getAnnotation(Service.class).lazy());

        // Set the interface class if the class implements an interface
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            beanDefinition.setInterfaceClass(interfaces[0]);
        }
        return beanDefinition;
    }

    public void scanEventListeners(String[] basePackages, EventRegister eventRegister) throws Exception {
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<? extends EventListener>> listenerSet = reflections.getSubTypesOf(EventListener.class);
            for (Class<?> clazz : listenerSet) {
                Class type = (Class) (((ParameterizedType) (clazz.getGenericInterfaces()[0])).getActualTypeArguments()[0]);
                EventListener<? extends Event> listener = (EventListener<? extends Event>) context.getBean(clazz);
                eventRegister.register(type, listener);
            }
        }
    }
}
