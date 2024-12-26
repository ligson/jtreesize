package org.ligson.jtreesize.filetree;

import lombok.Getter;
import org.ligson.jtreesize.core.annotation.Component;

import javax.swing.*;
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
        FileTreeNode root = (FileTreeNode) fileTreeModel.getRoot();
        FileTreeNode nodeToRemove = findNode(root, file);
        if (nodeToRemove != null) {
            fileTreeModel.removeNodeFromParent(nodeToRemove);
            if (nodeToRemove.getParent() != null) {
                fileTreeModel.reload(nodeToRemove.getParent());
            } else {
                fileTreeModel.reload();
            }
        }
    }

    private FileTreeNode findNode(FileTreeNode root, File file) {
        if (root.getFile().equals(file)) {
            return root;
        }

        if (root.getFileInfoData().exist(file)) {
            return new FileTreeNode(file, root.getFileInfoData());
        } else {
            return null;
        }
    }

    public void updateNodeText(File file) {
        FileTreeNode root = (FileTreeNode) fileTreeModel.getRoot();
        FileTreeNode nodeToUpdate = findNode(root, file);
        if (nodeToUpdate != null) {
            nodeToUpdate.setFile(file);
            fileTreeModel.nodeChanged(nodeToUpdate);
            fileTreeModel.reload();
            repaint();
        }
    }

}
