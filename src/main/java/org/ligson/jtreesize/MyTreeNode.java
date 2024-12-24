package org.ligson.jtreesize;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.Arrays;

public class MyTreeNode extends DefaultMutableTreeNode {
    private File file;
    private boolean childLoaded = false;

    public MyTreeNode(File file) {
        super(file, file.isDirectory());
        this.file = file;

    }

    public File getFile() {
        return this.file;
    }

    @Override
    public boolean isLeaf() {
        return file.isFile();
    }

    public void loadChild(DefaultTreeModel defaultTreeModel, FileInfoData fileInfoData) {
        if (!childLoaded) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.sort(files, (o1, o2) -> (int) (fileInfoData.getFileSize(o2) - fileInfoData.getFileSize(o1)));
                for (int i = 0; i < files.length; i++) {
                    MyTreeNode myTreeNode = new MyTreeNode(files[i]);
                    defaultTreeModel.insertNodeInto(myTreeNode, this, getChildCount());
                    //insert(myTreeNode, i);
                }
                System.out.println(files.length);
                defaultTreeModel.reload();
            }
            childLoaded = true;
        }

    }
}
