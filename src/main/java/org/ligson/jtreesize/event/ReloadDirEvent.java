package org.ligson.jtreesize.event;

import lombok.Getter;
import org.ligson.jtreesize.core.event.Event;


public class ReloadDirEvent extends Event {
    @Getter
    private final String path;

    public ReloadDirEvent(Object source, String path) {
        super(source);
        this.path = path;
    }

}
