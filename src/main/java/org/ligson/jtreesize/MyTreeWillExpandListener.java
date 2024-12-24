package org.ligson.jtreesize;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

public class MyTreeWillExpandListener implements TreeWillExpandListener {
    private final JTree jTree;
    private final FileInfoData fileInfoData;

    public MyTreeWillExpandListener(JTree jTree, FileInfoData fileInfoData) {
        this.jTree = jTree;
        this.fileInfoData = fileInfoData;
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        MyTreeNode myTreeNode = (MyTreeNode) event.getPath().getLastPathComponent();
        System.out.println(event.getPath() + "--treeWillExpand");
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) jTree.getModel();
        myTreeNode.loadChild(defaultTreeModel,fileInfoData);
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
}
