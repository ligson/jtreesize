package org.ligson.jtreesize;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTreeMouseListener extends MouseAdapter {
    private final FileInfoData fileInfoData;
    private final JTree jTree;
    private TreeNodeContext treeNodeContext;
    private SelectDirBtnActionListener selectDirBtnActionListener;

    public JTreeMouseListener(FileInfoData fileInfoData, JTree jTree, SelectDirBtnActionListener selectDirBtnActionListener) {
        this.fileInfoData = fileInfoData;
        this.jTree = jTree;
        this.selectDirBtnActionListener = selectDirBtnActionListener;
        treeNodeContext = new TreeNodeContext(selectDirBtnActionListener);
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


            treeNodeContext.showMenu(e.getComponent(), e.getX(), e.getY(), myTreeNode.getFile());
        }
    }
}
