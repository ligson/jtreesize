package org.ligson.jtreesize;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TreeNodeContext extends JPopupMenu {
    private File file;
    private SelectDirBtnActionListener selectDirBtnActionListener;

    public void showMenu(Component jTree, int x, int y, File file) {
        show(jTree, x, y);
        System.out.println("====" + x + "===" + y);
        this.file = file;
    }

    private void openDirectory() {
        //Check if the feature supported on your platform
        if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE_FILE_DIR)) {
            //Open directory with browse_file_dir option
            Desktop.getDesktop().browseFileDirectory(file);
        }
    }

    public TreeNodeContext(SelectDirBtnActionListener selectDirBtnActionListener) {
        this.selectDirBtnActionListener = selectDirBtnActionListener;
        JMenuItem delMenuItem = new JMenuItem("删除");
        delMenuItem.addActionListener(e -> {
            boolean re = deleteFile(file);
            if (re) {
                selectDirBtnActionListener.reload();
                JOptionPane.showMessageDialog(null, "删除成功");
            } else {
                JOptionPane.showMessageDialog(null, "删除失败");
            }
        });
        JMenuItem openMenuItem = new JMenuItem("打开所在位置");
        openMenuItem.addActionListener(e -> openDirectory());
        add(delMenuItem);
        add(openMenuItem);
    }

    private boolean deleteFile(File file) {
        try {
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                FileUtils.delete(file);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
