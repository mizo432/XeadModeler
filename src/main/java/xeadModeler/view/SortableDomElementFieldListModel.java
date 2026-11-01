package xeadModeler.view;

import xeadModeler.model.XeadFieldElement;

import javax.swing.*;
import java.util.*;

/**
 * Aliasでソート可能なフィールド要素のListModel
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SortableDomElementFieldListModel extends DefaultListModel<XeadFieldElement> {

    private static final long serialVersionUID = 1L;

    public void addElementFromDom(org.w3c.dom.Element element) {
        XeadFieldElement xeadFieldElement = new XeadFieldElement(element);
        super.addElement(xeadFieldElement);
    }

    public void sortElements() {
        ArrayList<XeadFieldElement> list = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            list.add((XeadFieldElement) super.getElementAt(i));
        }
        this.removeAllElements();
        Collections.sort(list);
        Iterator<XeadFieldElement> it = list.iterator();
        while (it.hasNext()) {
            super.addElement(it.next());
        }
    }

    public XeadFieldElement getElementAt(int index) {
        return super.getElementAt(index);
    }
}
