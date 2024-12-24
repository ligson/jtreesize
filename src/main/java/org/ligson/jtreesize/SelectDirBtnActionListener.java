package org.ligson.jtreesize;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SelectDirBtnActionListener implements ActionListener {
    private final JWin jWin;
    private final JTree jTree;
    private final JFileChooser jFileChooser;
    private final FileInfoData fileInfoData;
    private File rootDir;

    public SelectDirBtnActionListener(JWin jWin, JTree jTree) {
        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jTree = jTree;
        this.jWin = jWin;
        fileInfoData = new FileInfoData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jFileChooser.showOpenDialog(jWin);
        rootDir = jFileChooser.getSelectedFile();
        reload();
    }

    public void reload() {
        JOptionPane.showMessageDialog(null,"计算中");
        fileInfoData.init(rootDir);
        System.out.println("select:" + rootDir.getAbsolutePath());
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) jTree.getModel();
        defaultTreeModel.setRoot(new MyTreeNode(rootDir));
        defaultTreeModel.reload();
        JOptionPane.showMessageDialog(null,"计算完成");
    }

    public FileInfoData getFileInfoData() {
        return fileInfoData;
    }


}
