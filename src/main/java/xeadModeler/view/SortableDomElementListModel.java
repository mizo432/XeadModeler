package xeadModeler.view;

import xeadModeler.model.XeadElement;

import javax.swing.*;
import java.util.*;

/**
 * ソート可能なDOM要素のListModel
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SortableDomElementListModel extends DefaultListModel<XeadElement> {

    private static final long serialVersionUID = 1L;

    public void addElementFromDom(org.w3c.dom.Element element) {
        XeadElement xeadElement = new XeadElement(element);
        super.addElement(xeadElement);
    }

    public void sortElements() {
        ArrayList<XeadElement> list = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            list.add((XeadElement) super.getElementAt(i));
        }
        this.removeAllElements();
        Collections.sort(list);
        Iterator<XeadElement> it = list.iterator();
        while (it.hasNext()) {
            super.addElement(it.next());
        }
    }

    public XeadElement getElementAt(int index) {
        return super.getElementAt(index);
    }
}
