package xeadModeler.model;

import tools.jackson.databind.JsonNode;

/**
 * XEAD要素のラッパークラス
 * SortKey属性でソート可能な比較可能オブジェクトを提供します。
 * JSONノードとJSON POJOをサポートします。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadElement implements Comparable<XeadElement> {
    private final JsonNode jsonNode;
    private final Object pojo; // JSON POJO (Table, Function, etc.)
    
    /**
     * XeadElementを作成します (JSON用)
     * @param node ラップするJSONノード
     */
    public XeadElement(JsonNode node) {
        this.jsonNode = node;
        this.pojo = null;
    }
    
    /**
     * XeadElementを作成します (JSON POJO用)
     * @param pojo ラップするJSON POJO
     */
    public XeadElement(Object pojo) {
        this.jsonNode = null;
        this.pojo = pojo;
    }
    
    /**
     * ラップされているJSONノードを返します
     * @return JSONノード (POJOの場合はnull)
     */
    public JsonNode getJsonNode() {
        return jsonNode;
    }
    
    /**
     * ラップされているJSON POJOを返します
     * @return JSON POJO (JsonNodeの場合はnull)
     */
    public Object getPojo() {
        return pojo;
    }
    
    /**
     * SortKey属性の値を取得します
     * @return SortKeyの値
     */
    private String getSortKey() {
        if (jsonNode != null && jsonNode.has("sortKey")) {
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
