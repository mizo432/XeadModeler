package xeadModeler.model;

import org.w3c.dom.Element;

/**
 * XEADフィールド要素のラッパークラス
 * Alias属性でソート可能な比較可能オブジェクトを提供します。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadFieldElement implements Comparable<XeadFieldElement> {
    private final Element domNode;
    
    /**
     * XeadFieldElementを作成します
     * @param node ラップするDOM要素
     */
    public XeadFieldElement(Element node) {
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
     * Alias属性に基づいて他のXeadFieldElementと比較します
     * @param other 比較対象のXeadFieldElement
     * @return 比較結果
     */
    @Override
    public int compareTo(XeadFieldElement other) {
        return domNode.getAttribute("Alias").compareTo(other.getElement().getAttribute("Alias"));
    }
}
