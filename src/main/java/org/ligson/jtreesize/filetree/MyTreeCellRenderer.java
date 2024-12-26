package org.ligson.jtreesize.filetree;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

@org.ligson.jtreesize.core.annotation.Component
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
    private final FileInfoData fileInfoData;

    public MyTreeCellRenderer(FileInfoData fileInfoData) {
        this.fileInfoData = fileInfoData;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        MyTreeNode myTreeNode = (MyTreeNode) value;
        File file = myTreeNode.getFile();
        String size = fileInfoData.getFileSizeHuman(file);
        setText(file.getName() + "(" + size + ")");
        if (file.isFile()) {
            Icon icon = getSmallIcon(file);
            if (icon != null) {
                setIcon(icon);
            }
        }
        return this;
    }

    /**
     * 获取小图标
     *
     * @param f
     * @return
     */

    private static Icon getSmallIcon(File f) {
        if (f != null && f.exists()) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            return (fsv.getSystemIcon(f));
        }
        return (null);
    }
}
