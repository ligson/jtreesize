package org.ligson.jtreesize.event;

import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.event.EventListener;
import org.ligson.jtreesize.filetree.FileTree;

import java.io.File;


@Component
public class FileSizeRefreshEventListener implements EventListener<FileSizeRefreshEvent> {
    private final FileTree fileTree;

    public FileSizeRefreshEventListener(FileTree fileTree) {
        this.fileTree = fileTree;
    }

    @Override
    public void onEvent(FileSizeRefreshEvent event) {
        for (File file : event.getFiles()) {
            fileTree.updateNodeText(file);
        }

    }
}
