package org.ligson.jtreesize.event;

import org.ligson.jtreesize.core.event.EventListener;

import javax.swing.*;

public class StatusBarChangeEventListener implements EventListener<StatusBarChangeEvent> {
    private final JLabel statusBar;

    public StatusBarChangeEventListener(JLabel statusBar) {
        this.statusBar = statusBar;
    }

    @Override
    public void onEvent(StatusBarChangeEvent event) {
        statusBar.setText(event.getMessage());
    }
}
