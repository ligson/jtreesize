package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.core.event.EventPublisher;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTreeMouseListener extends MouseAdapter {

    private final TreeNodeContext treeNodeContext;

    public JTreeMouseListener(EventPublisher eventPublisher, FileTree fileTree) {
        treeNodeContext = new TreeNodeContext(eventPublisher, fileTree);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JTree jTree = (JTree) e.getComponent();
            TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
            if (path == null)
                return;

            MyTreeNode myTreeNode = (MyTreeNode) path
                    .getLastPathComponent();

            System.out.println("选中：" + myTreeNode.getFile().getAbsolutePath());


            treeNodeContext.showMenu(e.getX(), e.getY(), myTreeNode.getFile());
        }
    }
}
