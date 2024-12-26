package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.core.ApplicationContext;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.event.ReloadDirEvent;
import org.ligson.jtreesize.filetree.event.TreeRemoveSelectNodeEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;


@Component
public class TreeNodeContext extends JPopupMenu {
    private File file;

    private final ApplicationContext applicationContext;


    public void showMenu(int x, int y, File file) {
        FileTree fileTree = null;
        try {
            fileTree = applicationContext.getBean(FileTree.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public TreeNodeContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        JMenuItem delMenuItem = new JMenuItem("删除");
        delMenuItem.addActionListener(e -> deleteItemHandler());
        JMenuItem openMenuItem = new JMenuItem("打开所在位置");
        openMenuItem.addActionListener(e -> openDirectory());

        JMenuItem refreshMenuItem = new JMenuItem("刷新");
        refreshMenuItem.addActionListener(e -> {
            ReloadDirEvent reloadDirEvent = new ReloadDirEvent(this, file.getAbsolutePath());
            applicationContext.publishEvent(reloadDirEvent);
        });


        add(delMenuItem);
        add(openMenuItem);
        add(refreshMenuItem);
    }


    private void deleteItemHandler() {
        TreeRemoveSelectNodeEvent treeRemoveSelectNodeEvent = new TreeRemoveSelectNodeEvent(this);
        applicationContext.publishEvent(treeRemoveSelectNodeEvent);

    }


}
