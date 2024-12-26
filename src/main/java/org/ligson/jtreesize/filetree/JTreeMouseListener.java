package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.core.annotation.Component;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class JTreeMouseListener extends MouseAdapter {

    private final TreeNodeContext treeNodeContext;


    public JTreeMouseListener(
            TreeNodeContext treeNodeContext) {
        this.treeNodeContext = treeNodeContext;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JTree jTree = (JTree) e.getComponent();
            TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
            if (path == null)
                return;

            FileTreeNode fileTreeNode = (FileTreeNode) path
                    .getLastPathComponent();

            System.out.println("选中：" + fileTreeNode.getFile().getAbsolutePath());


            treeNodeContext.showMenu(e.getX(), e.getY(), fileTreeNode.getFile());
        }
    }
}
