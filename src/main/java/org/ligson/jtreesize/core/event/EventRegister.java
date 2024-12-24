package org.ligson.jtreesize.core.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class EventRegister {
    private final Map<Class<?>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    public <E extends Event> void register(Class<E> event, EventListener<E> listener) {
        List<EventListener<?>> eventListeners = listeners.computeIfAbsent(event, k -> new ArrayList<>());
        eventListeners.add(listener);
    }

    public <E extends Event> Optional<List<EventListener<?>>> getListeners(Class<E> event) {
        return Optional.ofNullable(listeners.get(event));
    }
}
