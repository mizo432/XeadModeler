package xeadModeler.model;

import org.w3c.dom.Element;
import tools.jackson.databind.JsonNode;

/**
 * XEADフィールド要素のラッパークラス
 * Alias属性でソート可能な比較可能オブジェクトを提供します。
 * XML DOM要素とJSONノードの両方をサポートします。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadFieldElement implements Comparable<XeadFieldElement> {
    private final Element domNode;
    private final JsonNode jsonNode;
    private final Object pojo; // JSON POJO (TableField, etc.)
    
    /**
     * XeadFieldElementを作成します (XML DOM用)
     * @param node ラップするDOM要素
     */
    public XeadFieldElement(Element node) {
        this.domNode = node;
        this.jsonNode = null;
        this.pojo = null;
    }
    
    /**
     * XeadFieldElementを作成します (JSON用)
     * @param node ラップするJSONノード
     */
    public XeadFieldElement(JsonNode node) {
        this.domNode = null;
        this.jsonNode = node;
        this.pojo = null;
    }
    
    /**
     * XeadFieldElementを作成します (JSON POJO用)
     * @param pojo ラップするJSON POJO
     */
    public XeadFieldElement(Object pojo) {
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
     * Alias属性の値を取得します
     * @return Aliasの値
     */
    private String getAlias() {
        if (domNode != null) {
            return domNode.getAttribute("Alias");
        } else if (jsonNode != null && jsonNode.has("alias")) {
            return jsonNode.get("alias").asText();
        } else if (pojo != null) {
            try {
                java.lang.reflect.Method method = pojo.getClass().getMethod("getAlias");
                Object result = method.invoke(pojo);
                return result != null ? result.toString() : "";
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
    
    /**
     * Alias属性に基づいて他のXeadFieldElementと比較します
     * @param other 比較対象のXeadFieldElement
     * @return 比較結果
     */
    @Override
    public int compareTo(XeadFieldElement other) {
        return getAlias().compareTo(other.getAlias());
    }
}
