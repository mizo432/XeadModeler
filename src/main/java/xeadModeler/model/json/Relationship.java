package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * リレーションシップのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Relationship {
    
    private String descriptions;
    private String existWhen1;
    private String existWhen2;
    private String id;
    private String name;
    private String sortKey;
    private String table1Id;
    private String table2Id;
    private String tableKey1Id;
    private String tableKey2Id;
    
    private List<SubsystemRelationship> subsystemRelationships = new ArrayList<>();
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public String getExistWhen1() {
        return existWhen1;
    }
    
    public void setExistWhen1(String existWhen1) {
        this.existWhen1 = existWhen1;
    }
    
    public String getExistWhen2() {
        return existWhen2;
    }
    
    public void setExistWhen2(String existWhen2) {
        this.existWhen2 = existWhen2;
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
    
    public String getTable1Id() {
        return table1Id;
    }
    
    public void setTable1Id(String table1Id) {
        this.table1Id = table1Id;
    }
    
    public String getTable2Id() {
        return table2Id;
    }
    
    public void setTable2Id(String table2Id) {
        this.table2Id = table2Id;
    }
    
    public String getTableKey1Id() {
        return tableKey1Id;
    }
    
    public void setTableKey1Id(String tableKey1Id) {
        this.tableKey1Id = tableKey1Id;
    }
    
    public String getTableKey2Id() {
        return tableKey2Id;
    }
    
    public void setTableKey2Id(String tableKey2Id) {
        this.tableKey2Id = tableKey2Id;
    }
    
    public List<SubsystemRelationship> getSubsystemRelationships() {
        return subsystemRelationships;
    }
    
    public void setSubsystemRelationships(List<SubsystemRelationship> subsystemRelationships) {
        this.subsystemRelationships = subsystemRelationships;
    }
}
