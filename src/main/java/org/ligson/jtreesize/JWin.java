package org.ligson.jtreesize;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class JWin extends JFrame {

    public JWin() {
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JButton selectDirBtn = new JButton("选择目录");
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(null);
        JTree jTree = new JTree();
        SelectDirBtnActionListener selectDirBtnActionListener = new SelectDirBtnActionListener(this, jTree);
        jTree.setModel(defaultTreeModel);
        jTree.setCellRenderer(new MyTreeCellRenderer(selectDirBtnActionListener.getFileInfoData()));
        jTree.addTreeWillExpandListener(new MyTreeWillExpandListener(jTree, selectDirBtnActionListener.getFileInfoData()));
        jTree.addMouseListener(new JTreeMouseListener(selectDirBtnActionListener.getFileInfoData(), jTree, selectDirBtnActionListener));
        selectDirBtn.addActionListener(selectDirBtnActionListener);
        JScrollPane jScrollPane = new JScrollPane(jTree);
        add(selectDirBtn, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);
    }
}
