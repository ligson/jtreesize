package org.ligson.jtreesize;

import lombok.Getter;
import org.ligson.jtreesize.core.event.EventPublisher;
import org.ligson.jtreesize.core.event.EventRegister;
import org.ligson.jtreesize.event.*;
import org.ligson.jtreesize.filetree.FileInfoData;
import org.ligson.jtreesize.filetree.FileTree;

import javax.swing.*;
import java.awt.*;

public class JWin extends JFrame {
    @Getter
    private JLabel statusBar;

    @Getter
    private SelectDirBtnActionListener selectDirBtnActionListener;

    private final FileInfoData fileInfoData;

    private final EventRegister eventRegister;
    private final EventPublisher eventPublisher;

    public JWin() {

        eventRegister = new EventRegister();
        eventPublisher = new EventPublisher(eventRegister);

        fileInfoData = new FileInfoData(eventPublisher);

        ReloadDirEventListener reloadDirEventListener = new ReloadDirEventListener(fileInfoData);
        eventRegister.register(ReloadDirEvent.class, reloadDirEventListener);

        FileTree jTree = new FileTree(fileInfoData, eventPublisher);

        FileSizeRefreshEventListener fileSizeRefreshEventListener = new FileSizeRefreshEventListener(jTree);
        eventRegister.register(FileSizeRefreshEvent.class, fileSizeRefreshEventListener);
        statusBar = new JLabel("状态栏");
        StatusBarChangeEventListener statusBarChangeEventListener = new StatusBarChangeEventListener(statusBar);
        eventRegister.register(StatusBarChangeEvent.class, statusBarChangeEventListener);

        setTitle("文件删除");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JButton selectDirBtn = new JButton("选择目录");


        selectDirBtnActionListener = new SelectDirBtnActionListener(this, jTree, fileInfoData);

        selectDirBtn.addActionListener(selectDirBtnActionListener);
        JScrollPane jScrollPane = new JScrollPane(jTree);
        add(selectDirBtn, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);

        add(statusBar, BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/treesize.png"));
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
}
