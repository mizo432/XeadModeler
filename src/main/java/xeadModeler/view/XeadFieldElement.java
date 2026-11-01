package xeadModeler.view;

import org.w3c.dom.Element;

/**
 * Aliasでソート可能なComparable Field Elementクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadFieldElement implements Comparable<Object> {

    private Element domNode_;

    public XeadFieldElement(Element node) {
        domNode_ = node;
    }

    public Element getElement() {
        return domNode_;
    }

    public int compareTo(Object other) {
        XeadFieldElement otherNode = (XeadFieldElement) other;
        return domNode_.getAttribute("Alias").compareTo(otherNode.getElement().getAttribute("Alias"));
    }
}
