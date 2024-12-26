package org.ligson.jtreesize.filetree;

import lombok.Getter;
import org.ligson.jtreesize.core.annotation.Component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

@Component
public class FileTree extends JTree {

    @Getter
    private FileTreeModel fileTreeModel;

    public FileTree(
            MyTreeNodeListener myTreeNodeListener,
            MyTreeCellRenderer myTreeCellRenderer,
            MyTreeWillExpandListener myTreeWillExpandListener,
            JTreeMouseListener jTreeMouseListener) {
        fileTreeModel = new FileTreeModel(null);
        fileTreeModel.addTreeModelListener(myTreeNodeListener);

        setModel(fileTreeModel);
        setCellRenderer(myTreeCellRenderer);
        addTreeWillExpandListener(myTreeWillExpandListener);
        addMouseListener(jTreeMouseListener);
    }


    public void removeNode(File file) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) fileTreeModel.getRoot();
        DefaultMutableTreeNode nodeToRemove = findNode(root, file);
        if (nodeToRemove != null) {
            fileTreeModel.removeNodeFromParent(nodeToRemove);
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
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) fileTreeModel.getRoot();
        DefaultMutableTreeNode nodeToUpdate = findNode(root, file);
        if (nodeToUpdate != null) {
            nodeToUpdate.setUserObject(file);
            fileTreeModel.nodeChanged(nodeToUpdate);
        }
    }

}
