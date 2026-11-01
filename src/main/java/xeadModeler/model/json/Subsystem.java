package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * サブシステムのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Subsystem {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    
    private List<SubsystemTable> subsystemTables = new ArrayList<>();
    private List<SubsystemRelationship> subsystemRelationships = new ArrayList<>();
    
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
    
    public List<SubsystemTable> getSubsystemTables() {
        return subsystemTables;
    }
    
    public void setSubsystemTables(List<SubsystemTable> subsystemTables) {
        this.subsystemTables = subsystemTables;
    }
    
    public List<SubsystemRelationship> getSubsystemRelationships() {
        return subsystemRelationships;
    }
    
    public void setSubsystemRelationships(List<SubsystemRelationship> subsystemRelationships) {
        this.subsystemRelationships = subsystemRelationships;
    }
}
