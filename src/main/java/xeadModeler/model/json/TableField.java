package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブルフィールドのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableField {
    
    private String alias;
    private String attributeType;
    private String dataTypeId;
    private String default_;
    private String descriptions;
    private String id;
    private String name;
    private String noUpdate;
    private String notNull;
    private String showOnModel;
    private String sortKey;
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getAttributeType() {
        return attributeType;
    }
    
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
    
    public String getDataTypeId() {
        return dataTypeId;
    }
    
    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }
    
    public String getDefault_() {
        return default_;
    }
    
    public void setDefault_(String default_) {
        this.default_ = default_;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNoUpdate() {
        return noUpdate;
    }
    
    public void setNoUpdate(String noUpdate) {
        this.noUpdate = noUpdate;
    }
    
    public String getNotNull() {
        return notNull;
    }
    
    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }
    
    public String getShowOnModel() {
        return showOnModel;
    }
    
    public void setShowOnModel(String showOnModel) {
        this.showOnModel = showOnModel;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
