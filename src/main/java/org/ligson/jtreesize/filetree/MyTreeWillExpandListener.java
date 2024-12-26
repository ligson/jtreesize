package org.ligson.jtreesize.filetree;

import org.ligson.jtreesize.core.ApplicationContext;
import org.ligson.jtreesize.core.annotation.Component;
import org.ligson.jtreesize.filetree.event.TreeWillExpandEvent;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

@Component
public class MyTreeWillExpandListener implements TreeWillExpandListener {


    private final ApplicationContext applicationContext;

    public MyTreeWillExpandListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        MyTreeNode myTreeNode = (MyTreeNode) event.getPath().getLastPathComponent();
        TreeWillExpandEvent treeWillExpandEvent = new TreeWillExpandEvent(this, myTreeNode);
        applicationContext.publishEvent(treeWillExpandEvent);
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
}
