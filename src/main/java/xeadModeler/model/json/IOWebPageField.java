package xeadModeler.model.json;

/**
 * IOWebPageフィールドのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOWebPageField {
    
    private String id;
    private String sortKey;
    private String fieldId;
    private String fieldType;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    
    public String getFieldId() {
        return fieldId;
    }
    
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
    
    public String getFieldType() {
        return fieldType;
    }
    
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
