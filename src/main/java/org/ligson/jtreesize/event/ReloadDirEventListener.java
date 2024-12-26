package org.ligson.jtreesize.event;

import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.event.EventListener;
import org.ligson.jtreesize.filetree.FileInfoData;

import java.io.File;

@Component
public class ReloadDirEventListener implements EventListener<ReloadDirEvent> {
    private final FileInfoData fileInfoData;

    public ReloadDirEventListener(FileInfoData fileInfoData) {
        this.fileInfoData = fileInfoData;
    }

    @Override
    public void onEvent(ReloadDirEvent event) {
        fileInfoData.refresh(new File(event.getPath()));
    }
}
