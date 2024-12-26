package org.ligson.jtreesize.filetree;

import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultTreeModel;

@Getter
@Setter
public class FileTreeModel extends DefaultTreeModel {
    private FileTreeNode fileTreeNode;

    public FileTreeModel(FileTreeNode root) {
        super(root);
        this.fileTreeNode = root;
    }
}
