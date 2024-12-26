package org.ligson.jtreesize.filetree;

import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultTreeModel;

@Getter
@Setter
public class FileTreeModel extends DefaultTreeModel {
    private MyTreeNode myTreeNode;

    public FileTreeModel(MyTreeNode root) {
        super(root);
        this.myTreeNode = root;
    }
}
