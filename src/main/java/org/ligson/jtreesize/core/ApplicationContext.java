package org.ligson.jtreesize.core;

import org.ligson.jtreesize.core.annotation.SpringBootApplication;
import org.ligson.jtreesize.core.event.Event;
import org.ligson.jtreesize.core.event.EventPublisher;
import org.ligson.jtreesize.core.event.EventRegister;
import org.ligson.jtreesize.core.exception.CircularDependencyException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private final Map<Class<?>, BeanDefinition> beans;
    private final BeanFactory beanFactory;
    private final EventPublisher eventPublisher;
    private final ThreadLocal<Set<Class<?>>> currentlyCreatingBeans = ThreadLocal.withInitial(HashSet::new);

    public ApplicationContext(Class<?> mainClass) throws Exception {
        if (!mainClass.isAnnotationPresent(SpringBootApplication.class)) {
            throw new RuntimeException("Main class must be annotated with @SpringBootApplication");
        }
        SpringBootApplication annotation = mainClass.getAnnotation(SpringBootApplication.class);
        String[] basePackages = annotation.scanBasePackages();
        if (basePackages.length == 0) {
            basePackages = new String[]{mainClass.getPackage().getName()};
        }
        beanFactory = new BeanFactory(this);
        BeanScanner beanScanner = new BeanScanner(this);
        beans = beanScanner.scanComponents(basePackages);
        beans.put(ApplicationContext.class, createApplicationContextBeanDefinition());
        BeanLifecycleManager lifecycleManager = new BeanLifecycleManager(this);
        lifecycleManager.initializeBeans(beans);

        // Initialize EventPublisher
        EventRegister eventRegister = new EventRegister();
        eventPublisher = new EventPublisher(eventRegister);
        beanScanner.scanEventListeners(basePackages, eventRegister);

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    private BeanDefinition createApplicationContextBeanDefinition() {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(ApplicationContext.class);
        beanDefinition.setInstance(this);
        return beanDefinition;
    }

    public <T> T getBean(Class<T> clazz) throws Exception {
        BeanDefinition beanDefinition = beans.get(clazz);
        if (beanDefinition == null) {
            throw new RuntimeException("No bean found for class: " + clazz);
        }
        if (beanDefinition.getInstance() == null) {
            Set<Class<?>> creatingBeans = currentlyCreatingBeans.get();
            if (creatingBeans.contains(clazz)) {
                throw new CircularDependencyException("Circular dependency detected: " + creatingBeans);
            }
            try {
                creatingBeans.add(clazz);
                Object bean = beanFactory.createBean(clazz);
                beanDefinition.setInstance(bean);
            } catch (Exception e) {
                throw new RuntimeException("Error creating bean for class: " + clazz, e);
            } finally {
                creatingBeans.remove(clazz);
            }
        }
        return clazz.cast(beanDefinition.getInstance());
    }

    public Object getBean(String name) {
        for (BeanDefinition beanDefinition : beans.values()) {
            if (beanDefinition.getName().equals(name)) {
                return beanDefinition.getInstance();
            }
        }
        return null;
    }

    public void publishEvent(Event event) {
        eventPublisher.publishEvent(event);
    }

    public void close() throws Exception {
        for (BeanDefinition beanDefinition : beans.values()) {
            if (beanDefinition.getInstance() != null) {
                beanFactory.destroyBean(beanDefinition.getInstance());
            }
        }
    }
}
