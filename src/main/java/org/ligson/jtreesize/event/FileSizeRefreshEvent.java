package org.ligson.jtreesize.event;

import lombok.Getter;
import org.ligson.jtreesize.core.event.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FileSizeRefreshEvent extends Event {
    private List<File> files = new ArrayList<>();

    public FileSizeRefreshEvent(Object source, List<File> files) {
        super(source);
        this.files = files;
    }
}
