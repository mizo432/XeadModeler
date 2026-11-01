package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブルキーのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableKey {
    
    private String id;
    private String sortKey;
    private String type;
    
    private List<TableKeyField> tableKeyFields = new ArrayList<>();
    
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public List<TableKeyField> getTableKeyFields() {
        return tableKeyFields;
    }
    
    public void setTableKeyFields(List<TableKeyField> tableKeyFields) {
        this.tableKeyFields = tableKeyFields;
    }
}
