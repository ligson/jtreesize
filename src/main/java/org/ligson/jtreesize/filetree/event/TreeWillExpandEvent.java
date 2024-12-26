package org.ligson.jtreesize.filetree.event;

import lombok.Getter;
import org.ligson.jtreesize.core.event.Event;
import org.ligson.jtreesize.filetree.MyTreeNode;

@Getter
public class TreeWillExpandEvent extends Event {
    private final MyTreeNode myTreeNode;

    public TreeWillExpandEvent(Object source, MyTreeNode myTreeNode) {
        super(source);
        this.myTreeNode = myTreeNode;
    }

}
