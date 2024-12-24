package org.ligson.jtreesize;

import lombok.Getter;
import org.ligson.jtreesize.filetree.FileInfoData;
import org.ligson.jtreesize.filetree.MyTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectDirBtnActionListener implements ActionListener {
    private final JWin jWin;
    private final JTree jTree;
    private final JFileChooser jFileChooser;
    @Getter
    private final FileInfoData fileInfoData;
    private File rootDir;

    public SelectDirBtnActionListener(JWin jWin, JTree jTree, FileInfoData fileInfoData) {
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jTree = jTree;
        this.jWin = jWin;
        this.fileInfoData = fileInfoData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jFileChooser.showOpenDialog(jWin);
        rootDir = jFileChooser.getSelectedFile();
        if (rootDir != null) {
            reload();
        }
    }

    public void reload() {
        jWin.updateStatusBar("正在计算目录大小....");
        fileInfoData.init(rootDir);
        jWin.updateStatusBar("计算完成");
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) jTree.getModel();
        defaultTreeModel.setRoot(new MyTreeNode(rootDir));
        defaultTreeModel.reload();
    }


}
