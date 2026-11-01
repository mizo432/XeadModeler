package xeadModeler.model;

import org.w3c.dom.Element;

/**
 * XEAD要素のラッパークラス
 * SortKey属性でソート可能な比較可能オブジェクトを提供します。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadElement implements Comparable<XeadElement> {
    private final Element domNode;
    
    /**
     * XeadElementを作成します
     * @param node ラップするDOM要素
     */
    public XeadElement(Element node) {
        this.domNode = node;
    }
    
    /**
     * ラップされているDOM要素を返します
     * @return DOM要素
     */
    public Element getElement() {
        return domNode;
    }
    
    /**
     * SortKey属性に基づいて他のXeadElementと比較します
     * @param other 比較対象のXeadElement
     * @return 比較結果
     */
    @Override
    public int compareTo(XeadElement other) {
        return domNode.getAttribute("SortKey").compareTo(other.getElement().getAttribute("SortKey"));
    }
}
