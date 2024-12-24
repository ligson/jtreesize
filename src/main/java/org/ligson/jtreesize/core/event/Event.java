package org.ligson.jtreesize.core.event;

import lombok.Data;

@Data
public abstract class Event {
    private Object source;

    public Event(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
