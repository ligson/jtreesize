package org.ligson.jtreesize.filetree;

import lombok.Getter;
import org.ligson.jtreesize.core.event.EventPublisher;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class FileTree extends JTree {

    @Getter
    private DefaultTreeModel treeModel;

    public FileTree(FileInfoData fileInfoData, EventPublisher eventPublisher) {
        treeModel = new DefaultTreeModel(null);
        MyTreeNodeListener myTreeNodeListener = new MyTreeNodeListener();
        treeModel.addTreeModelListener(myTreeNodeListener);

        setModel(treeModel);
        setCellRenderer(new MyTreeCellRenderer(fileInfoData));
        addTreeWillExpandListener(new MyTreeWillExpandListener(this, fileInfoData));
        addMouseListener(new JTreeMouseListener(eventPublisher, this));
    }


    public void removeNode(File file) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode nodeToRemove = findNode(root, file);
        if (nodeToRemove != null) {
            treeModel.removeNodeFromParent(nodeToRemove);
        }
    }

    private DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, File file) {
        if (root.getUserObject().equals(file)) {
            return root;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            DefaultMutableTreeNode result = findNode(child, file);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public void updateNodeText(File file) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode nodeToUpdate = findNode(root, file);
        if (nodeToUpdate != null) {
            nodeToUpdate.setUserObject(file);
            treeModel.nodeChanged(nodeToUpdate);
        }
    }

}
