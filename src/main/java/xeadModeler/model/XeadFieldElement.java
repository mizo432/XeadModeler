package xeadModeler.model;

import tools.jackson.databind.JsonNode;

/**
 * XEADフィールド要素のラッパークラス
 * Alias属性でソート可能な比較可能オブジェクトを提供します。
 * JSONノードとJSON POJOをサポートします。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadFieldElement implements Comparable<XeadFieldElement> {
    private final JsonNode jsonNode;
    private final Object pojo; // JSON POJO (TableField, etc.)
    
    /**
     * XeadFieldElementを作成します (JSON用)
     * @param node ラップするJSONノード
     */
    public XeadFieldElement(JsonNode node) {
        this.jsonNode = node;
        this.pojo = null;
    }
    
    /**
     * XeadFieldElementを作成します (JSON POJO用)
     * @param pojo ラップするJSON POJO
     */
    public XeadFieldElement(Object pojo) {
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
     * Alias属性の値を取得します
     * @return Aliasの値
     */
    private String getAlias() {
        if (jsonNode != null && jsonNode.has("alias")) {
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
