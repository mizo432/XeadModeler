package xeadModeler.model.json;

/**
 * データタイプのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataType {
    
    private String basicType;
    private String decimal;
    private String id;
    private String length;
    private String name;
    private String sortKey;
    private String sqlExpression;
    
    public String getBasicType() {
        return basicType;
    }
    
    public void setBasicType(String basicType) {
        this.basicType = basicType;
    }
    
    public String getDecimal() {
        return decimal;
    }
    
    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getLength() {
        return length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    
    public String getSqlExpression() {
        return sqlExpression;
    }
    
    public void setSqlExpression(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }
}
