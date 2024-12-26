package org.ligson.jtreesize.filetree.event;

import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.event.EventListener;
import org.ligson.jtreesize.filetree.FileInfoData;
import org.ligson.jtreesize.filetree.FileTree;
import org.ligson.jtreesize.filetree.FileTreeNode;

import javax.swing.tree.DefaultTreeModel;

@Component
public class TreeWillExpandEventListener implements EventListener<TreeWillExpandEvent> {
    private final FileTree fileTree;
    private final FileInfoData fileInfoData;

    public TreeWillExpandEventListener(FileTree fileTree, FileInfoData fileInfoData) {
        this.fileTree = fileTree;
        this.fileInfoData = fileInfoData;
    }

    @Override
    public void onEvent(TreeWillExpandEvent event) {
        FileTreeNode fileTreeNode = event.getFileTreeNode();
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) fileTree.getModel();
        fileTreeNode.loadChild(defaultTreeModel, fileInfoData);
    }
}
