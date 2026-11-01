package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブルのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Table {
    
    private String alias;
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String synchFile;
    private String subsystemId;
    private String tableTypeId;
    
    private List<TableField> tableFields = new ArrayList<>();
    private List<TableKey> tableKeys = new ArrayList<>();
    private SubsystemTable subsystemTable;
    
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
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    
    public String getSynchFile() {
        return synchFile;
    }
    
    public void setSynchFile(String synchFile) {
        this.synchFile = synchFile;
    }
    
    public String getSubsystemId() {
        return subsystemId;
    }
    
    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }
    
    /**
     * Alias for getSubsystemId() for compatibility
     */
    public String getSubsystemID() {
        return subsystemId;
    }
    
    public String getTableTypeId() {
        return tableTypeId;
    }
    
    public void setTableTypeId(String tableTypeId) {
        this.tableTypeId = tableTypeId;
    }
    
    public List<TableField> getTableFields() {
        return tableFields;
    }
    
    public void setTableFields(List<TableField> tableFields) {
        this.tableFields = tableFields;
    }
    
    public List<TableKey> getTableKeys() {
        return tableKeys;
    }
    
    public void setTableKeys(List<TableKey> tableKeys) {
        this.tableKeys = tableKeys;
    }
    
    public SubsystemTable getSubsystemTable() {
        return subsystemTable;
    }
    
    public void setSubsystemTable(SubsystemTable subsystemTable) {
        this.subsystemTable = subsystemTable;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
}
