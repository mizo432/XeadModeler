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
    private String label;
    private String name;
    private String ioType;
    private String descriptions;
    
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
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIoType() {
        return ioType;
    }
    
    public void setIoType(String ioType) {
        this.ioType = ioType;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
