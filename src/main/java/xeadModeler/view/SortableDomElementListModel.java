package xeadModeler.view;

import xeadModeler.model.XeadElement;
import tools.jackson.databind.JsonNode;

import javax.swing.*;
import java.util.*;

/**
 * ソート可能な要素のListModel
 * JSONノードとJSON POJOをサポートします。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SortableDomElementListModel extends DefaultListModel<XeadElement> {

    private static final long serialVersionUID = 1L;

    public void addElementFromJson(JsonNode node) {
        XeadElement xeadElement = new XeadElement(node);
        super.addElement(xeadElement);
    }

    public void addElementFromPojo(Object pojo) {
        XeadElement xeadElement = new XeadElement(pojo);
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
