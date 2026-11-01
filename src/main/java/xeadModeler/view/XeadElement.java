package xeadModeler.view;

import org.w3c.dom.Element;

/**
 * SortKeyでソート可能なComparable Elementクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadElement implements Comparable<Object> {

    private Element domNode_;

    public XeadElement(Element node) {
        domNode_ = node;
    }

    public Element getElement() {
        return domNode_;
    }

    public int compareTo(Object other) {
        XeadElement otherNode = (XeadElement) other;
        return domNode_.getAttribute("SortKey")
            .compareTo(otherNode.getElement().getAttribute("SortKey"));
    }
}
