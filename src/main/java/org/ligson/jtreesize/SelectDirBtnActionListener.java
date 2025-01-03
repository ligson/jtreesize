package org.ligson.jtreesize;

import lombok.Getter;
import org.ligson.jtreesize.core.ApplicationContext;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.event.StatusBarChangeEvent;
import org.ligson.jtreesize.filetree.FileInfoData;
import org.ligson.jtreesize.filetree.FileTree;
import org.ligson.jtreesize.filetree.FileTreeModel;
import org.ligson.jtreesize.filetree.FileTreeNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.CompletableFuture;

@Component
public class SelectDirBtnActionListener implements ActionListener {
    private final FileTree fileTree;
    private final JFileChooser jFileChooser;
    @Getter
    private final FileInfoData fileInfoData;
    private File rootDir;

    private ApplicationContext applicationContext;

    public SelectDirBtnActionListener(FileTree fileTree, FileInfoData fileInfoData, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.fileTree = fileTree;
        this.fileInfoData = fileInfoData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jFileChooser.showOpenDialog(null);
        rootDir = jFileChooser.getSelectedFile();
        if (rootDir != null) {
            CompletableFuture.runAsync(this::reload);
        }
    }

    public void reload() {
        StatusBarChangeEvent statusBarChangeEvent = new StatusBarChangeEvent(this, "正在计算目录大小....");
        applicationContext.publishEvent(statusBarChangeEvent);
        fileInfoData.init(rootDir);
        StatusBarChangeEvent statusBarChangeEvent1 = new StatusBarChangeEvent(this, "计算完成");
        applicationContext.publishEvent(statusBarChangeEvent1);

        FileTreeModel defaultTreeModel = (FileTreeModel) fileTree.getModel();
        FileTreeNode rootTreeNode = new FileTreeNode(rootDir, fileInfoData);
        defaultTreeModel.setRoot(rootTreeNode);
        defaultTreeModel.reload();
    }


}
