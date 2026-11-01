package xeadModeler.model;

import org.w3c.dom.Element;
import tools.jackson.databind.JsonNode;

/**
 * XEAD要素のラッパークラス
 * SortKey属性でソート可能な比較可能オブジェクトを提供します。
 * XML DOM要素とJSONオブジェクトの両方をサポートします。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadElement implements Comparable<XeadElement> {
    private final Element domNode;
    private final JsonNode jsonNode;
    private final Object pojo; // JSON POJO (Table, Function, etc.)
    
    /**
     * XeadElementを作成します (XML DOM用)
     * @param node ラップするDOM要素
     */
    public XeadElement(Element node) {
        this.domNode = node;
        this.jsonNode = null;
        this.pojo = null;
    }
    
    /**
     * XeadElementを作成します (JSON用)
     * @param node ラップするJSONノード
     */
    public XeadElement(JsonNode node) {
        this.domNode = null;
        this.jsonNode = node;
        this.pojo = null;
    }
    
    /**
     * XeadElementを作成します (JSON POJO用)
     * @param pojo ラップするJSON POJO
     */
    public XeadElement(Object pojo) {
        this.domNode = null;
        this.jsonNode = null;
        this.pojo = pojo;
    }
    
    /**
     * ラップされているDOM要素を返します
     * @return DOM要素 (JSONの場合はnull)
     */
    public Element getElement() {
        return domNode;
    }
    
    /**
     * ラップされているJSONノードを返します
     * @return JSONノード (XMLの場合はnull)
     */
    public JsonNode getJsonNode() {
        return jsonNode;
    }
    
    /**
     * ラップされているJSON POJOを返します
     * @return JSON POJO (XMLまたはJsonNodeの場合はnull)
     */
    public Object getPojo() {
        return pojo;
    }
    
    /**
     * SortKey属性の値を取得します
     * @return SortKeyの値
     */
    private String getSortKey() {
        if (domNode != null) {
            return domNode.getAttribute("SortKey");
        } else if (jsonNode != null && jsonNode.has("sortKey")) {
            return jsonNode.get("sortKey").asText();
        } else if (pojo != null) {
            try {
                java.lang.reflect.Method method = pojo.getClass().getMethod("getSortKey");
                Object result = method.invoke(pojo);
                return result != null ? result.toString() : "";
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
    
    /**
     * SortKey属性に基づいて他のXeadElementと比較します
     * @param other 比較対象のXeadElement
     * @return 比較結果
     */
    @Override
    public int compareTo(XeadElement other) {
        return getSortKey().compareTo(other.getSortKey());
    }
}
