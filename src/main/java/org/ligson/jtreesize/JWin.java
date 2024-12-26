package org.ligson.jtreesize;

import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.core.annotation.PreConstructor;
import org.ligson.jtreesize.filetree.FileTree;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Component(lazy = false)
public class JWin extends JFrame {

    private JLabel statusBar;


    public JWin(SelectDirBtnActionListener selectDirBtnActionListener, FileTree fileTree) {

        statusBar = new JLabel("状态栏");


        setTitle("文件删除");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JButton selectDirBtn = new JButton("选择目录");


        selectDirBtn.addActionListener(selectDirBtnActionListener);
        JScrollPane jScrollPane = new JScrollPane(fileTree);
        add(selectDirBtn, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);

        add(statusBar, BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/treesize.png")));
        setIconImage(icon.getImage());

        TrayIcon trayIcon = new TrayIcon(icon.getImage(), "文件删除");
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatusBar(String text) {
        statusBar.setText(text);
        statusBar.repaint();
    }

    @PreConstructor
    public void init() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }
}
