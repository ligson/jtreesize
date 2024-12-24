package org.ligson.jtreesize.filetree;

import org.apache.commons.io.FileUtils;
import org.ligson.jtreesize.core.event.EventPublisher;
import org.ligson.jtreesize.event.ReloadDirEvent;
import org.ligson.jtreesize.event.StatusBarChangeEvent;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class TreeNodeContext extends JPopupMenu {
    private File file;

    private final EventPublisher eventPublisher;
    private final FileTree fileTree;


    public void showMenu(int x, int y, File file) {
        show(fileTree, x, y);
        this.file = file;
    }

    private void openDirectory() {
        //Check if the feature supported on your platform
        if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE_FILE_DIR)) {
            //Open directory with browse_file_dir option
            Desktop.getDesktop().browseFileDirectory(file);
        }
    }

    public TreeNodeContext(EventPublisher eventPublisher, FileTree fileTree) {
        this.eventPublisher = eventPublisher;
        this.fileTree = fileTree;

        JMenuItem delMenuItem = new JMenuItem("删除");
        delMenuItem.addActionListener(e -> deleteItemHandler());
        JMenuItem openMenuItem = new JMenuItem("打开所在位置");
        openMenuItem.addActionListener(e -> openDirectory());

        JMenuItem refreshMenuItem = new JMenuItem("刷新");
        refreshMenuItem.addActionListener(e -> {
            ReloadDirEvent reloadDirEvent = new ReloadDirEvent(this, file.getAbsolutePath());
            eventPublisher.publishEvent(reloadDirEvent);
        });


        add(delMenuItem);
        add(openMenuItem);
        add(refreshMenuItem);
    }


    private void deleteItemHandler() {

        TreePath[] paths = fileTree.getSelectionModel().getSelectionPaths();

        int returnValue = JOptionPane.showConfirmDialog(null, "确认删除" + paths.length + "个文件吗?", "删除", JOptionPane.YES_NO_OPTION);
        if (returnValue == JOptionPane.YES_OPTION) {
            CompletableFuture.runAsync(() -> {
                int successCount = 0;
                java.util.List<File> deleteFailFiles = new ArrayList<>();
                for (TreePath path : paths) {
                    MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
                    File deleteFile = node.getFile();
                    boolean re = deleteFile(deleteFile);
                    if (re) {
                        fileTree.removeNode(deleteFile);
                        ReloadDirEvent reloadDirEvent = new ReloadDirEvent(this, deleteFile.getParent());
                        eventPublisher.publishEvent(reloadDirEvent);
                        successCount++;
                    } else {
                        deleteFailFiles.add(deleteFile);
                    }
                }
                if (successCount == paths.length) {
                    JOptionPane.showMessageDialog(null, "删除成功");
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败:" + deleteFailFiles);
                }

            });
        }

    }

    private boolean deleteFile(File file) {
        try {
            StatusBarChangeEvent statusBarChangeEvent1 = new StatusBarChangeEvent(this, "正在删除:" + file.getAbsolutePath());
            eventPublisher.publishEvent(statusBarChangeEvent1);
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                FileUtils.delete(file);
            }
            StatusBarChangeEvent statusBarChangeEvent2 = new StatusBarChangeEvent(this, "删除" + file.getAbsolutePath() + "完成");
            eventPublisher.publishEvent(statusBarChangeEvent2);
            return true;
        } catch (IOException e) {
            StatusBarChangeEvent statusBarChangeEvent2 = new StatusBarChangeEvent(this, "删除" + file.getAbsolutePath() + "失败");
            eventPublisher.publishEvent(statusBarChangeEvent2);
            return false;
        }
    }
}
