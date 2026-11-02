package xeadModeler.model;

/**
 * XEAD要素のラッパークラス
 * SortKey属性でソート可能な比較可能オブジェクトを提供します。
 * JSON POJOのみをサポートします。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadElement implements Comparable<XeadElement> {
    private final Object pojo; // JSON POJO (Table, Function, etc.)
    
    /**
     * XeadElementを作成します (JSON POJO用)
     * @param pojo ラップするJSON POJO
     */
    public XeadElement(Object pojo) {
        this.pojo = pojo;
    }
    
    /**
     * ラップされているJSON POJOを返します
     * @return JSON POJO
     */
    public Object getPojo() {
        return pojo;
    }
    
    /**
     * SortKey属性の値を取得します
     * @return SortKeyの値
     */
    private String getSortKey() {
        if (pojo != null) {
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
