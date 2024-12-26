package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.core.annotation.Component;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

@Component
public class MyTreeNodeListener implements TreeModelListener {
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        TreePath treePath = e.getTreePath();
        if (treePath != null) {
            FileTreeNode fileTreeNode = (FileTreeNode) treePath.getLastPathComponent();


        }
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {

    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {

    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {

    }
}
