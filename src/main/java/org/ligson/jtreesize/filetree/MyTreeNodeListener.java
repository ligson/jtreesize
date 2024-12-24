package org.ligson.jtreesize.filetree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class MyTreeNodeListener implements TreeModelListener {
    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        TreePath treePath = e.getTreePath();
        if (treePath != null) {
            MyTreeNode myTreeNode = (MyTreeNode) treePath.getLastPathComponent();


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
