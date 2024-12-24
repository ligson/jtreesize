package org.ligson.jtreesize.event;

import org.ligson.jtreesize.core.event.Event;

public class StatusBarChangeEvent extends Event {
    private final String message;

    public StatusBarChangeEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
