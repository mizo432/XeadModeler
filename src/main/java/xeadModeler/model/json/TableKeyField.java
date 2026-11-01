package xeadModeler.model.json;

/**
 * テーブルキーフィールドのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableKeyField {
    
    private String fieldId;
    private String sortKey;
    
    public String getFieldId() {
        return fieldId;
    }
    
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
