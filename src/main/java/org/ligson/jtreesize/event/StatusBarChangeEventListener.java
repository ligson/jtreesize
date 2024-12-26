package org.ligson.jtreesize.event;

import org.ligson.jtreesize.JWin;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.event.EventListener;

@Component
public class StatusBarChangeEventListener implements EventListener<StatusBarChangeEvent> {
    private final JWin jWin;

    public StatusBarChangeEventListener(JWin jWin) {
        this.jWin = jWin;
    }

    @Override
    public void onEvent(StatusBarChangeEvent event) {
        jWin.updateStatusBar(event.getMessage());
    }
}
