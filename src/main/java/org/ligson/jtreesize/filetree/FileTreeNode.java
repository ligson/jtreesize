package org.ligson.jtreesize.filetree;

import lombok.Getter;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.*;

public class FileTreeNode implements TreeNode {

    @Getter
    private final File file;
    private boolean childLoaded = false;
    @Getter
    private final long fileSize;
    private final List<FileTreeNode> childNodes = new ArrayList<>();
    private final FileInfoData fileInfoData;

    public FileTreeNode(File file, FileInfoData fileInfoData) {
        this.file = file;
        this.fileInfoData = fileInfoData;
        this.fileSize = fileInfoData.getFileSize(file);
        loadChild();
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return childNodes.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childNodes.size();
    }

    @Override
    public TreeNode getParent() {
        if (file.getParent() == null) {
            return null;
        } else {
            return new FileTreeNode(file.getParentFile(), fileInfoData);
        }
    }

    @Override
    public int getIndex(TreeNode node) {
        return childNodes.indexOf((FileTreeNode) node);
    }

    @Override
    public boolean getAllowsChildren() {
        return file.isDirectory();
    }

    @Override
    public boolean isLeaf() {
        return file.isFile();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(childNodes);
    }

    private void loadChild() {
        if (!childLoaded) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.sort(files, (o1, o2) -> Long.compare(fileInfoData.getFileSize(o2), fileInfoData.getFileSize(o1)));
                for (File value : files) {
                    FileTreeNode fileTreeNode = new FileTreeNode(value, fileInfoData);
                    childNodes.add(fileTreeNode);
                }
            }
            childLoaded = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FileTreeNode that = (FileTreeNode) o;
        return file.equals(that.file);
    }
}
