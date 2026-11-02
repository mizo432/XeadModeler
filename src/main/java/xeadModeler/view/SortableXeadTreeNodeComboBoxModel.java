package xeadModeler.view;

import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import java.util.*;

/**
 * ソート可能なXeadTreeNodeのComboBoxモデル
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SortableXeadTreeNodeComboBoxModel extends DefaultComboBoxModel<XeadTreeNode> {

    private static final long serialVersionUID = 1L;

    public void sortElements() {
        ArrayList<XeadTreeNode> list = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            list.add((XeadTreeNode) this.getElementAt(i));
        }
        this.removeAllElements();
        Collections.sort(list);
        Iterator<XeadTreeNode> it = list.iterator();
        while (it.hasNext()) {
            this.addElement(it.next());
        }
    }

    @Override
    public int getIndexOf(Object anObject) {
        for (int i = 0; i < getSize(); i++) {
            if (getElementAt(i).equals(anObject)) {
                return i;
            }
        }
        return -1;
    }
}
