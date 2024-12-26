package org.ligson.jtreesize.filetree.event;

import lombok.Getter;
import org.ligson.jtreesize.core.event.Event;
import org.ligson.jtreesize.filetree.FileTreeNode;

@Getter
public class TreeWillExpandEvent extends Event {
    private final FileTreeNode fileTreeNode;

    public TreeWillExpandEvent(Object source, FileTreeNode fileTreeNode) {
        super(source);
        this.fileTreeNode = fileTreeNode;
    }

}
