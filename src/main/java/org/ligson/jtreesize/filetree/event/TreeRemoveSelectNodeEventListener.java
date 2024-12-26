package org.ligson.jtreesize.filetree.event;

import org.apache.commons.io.FileUtils;
import org.ligson.jtreesize.core.ApplicationContext;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.event.EventListener;
import org.ligson.jtreesize.event.ReloadDirEvent;
import org.ligson.jtreesize.event.StatusBarChangeEvent;
import org.ligson.jtreesize.filetree.FileTree;
import org.ligson.jtreesize.filetree.FileTreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Component
public class TreeRemoveSelectNodeEventListener implements EventListener<TreeRemoveSelectNodeEvent> {
    private final FileTree fileTree;
    private final ApplicationContext applicationContext;

    public TreeRemoveSelectNodeEventListener(FileTree fileTree, ApplicationContext applicationContext) {
        this.fileTree = fileTree;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onEvent(TreeRemoveSelectNodeEvent event) {
        TreePath[] paths = fileTree.getSelectionModel().getSelectionPaths();

        int returnValue = JOptionPane.showConfirmDialog(null, "确认删除" + paths.length + "个文件吗?", "删除", JOptionPane.YES_NO_OPTION);
        if (returnValue == JOptionPane.YES_OPTION) {
            CompletableFuture.runAsync(() -> {
                int successCount = 0;
                java.util.List<File> deleteFailFiles = new ArrayList<>();
                for (TreePath path : paths) {
                    FileTreeNode node = (FileTreeNode) path.getLastPathComponent();
                    File deleteFile = node.getFile();
                    boolean re = deleteFile(deleteFile);
                    if (re) {
                        fileTree.removeNode(deleteFile);
                        ReloadDirEvent reloadDirEvent = new ReloadDirEvent(this, deleteFile.getParent());
                        applicationContext.publishEvent(reloadDirEvent);
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
            applicationContext.publishEvent(statusBarChangeEvent1);
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                FileUtils.delete(file);
            }
            StatusBarChangeEvent statusBarChangeEvent2 = new StatusBarChangeEvent(this, "删除" + file.getAbsolutePath() + "完成");
            applicationContext.publishEvent(statusBarChangeEvent2);
            return true;
        } catch (IOException e) {
            StatusBarChangeEvent statusBarChangeEvent2 = new StatusBarChangeEvent(this, "删除" + file.getAbsolutePath() + "失败");
            applicationContext.publishEvent(statusBarChangeEvent2);
            return false;
        }
    }
}
