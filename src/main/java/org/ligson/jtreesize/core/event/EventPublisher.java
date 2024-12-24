package org.ligson.jtreesize.core.event;

import java.util.concurrent.CompletableFuture;

public class EventPublisher {
    private final EventRegister eventRegister;

    public EventPublisher(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> void publishEvent(E event) {
        eventRegister.getListeners(event.getClass()).ifPresent(listeners -> {
            for (EventListener<?> listener : listeners) {
                CompletableFuture.runAsync(() ->
                        ((EventListener<E>) listener).onEvent(event));
            }
        });
    }
}
