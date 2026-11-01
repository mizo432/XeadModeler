package xeadModeler.model.json;

/**
 * サブシステムリレーションシップのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubsystemRelationship {
    
    private String relationshipId;
    private String subsystemId;
    private String showOnModel;
    private String terminalIndex1;
    private String terminalIndex2;
    
    public String getRelationshipId() {
        return relationshipId;
    }
    
    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }
    
    public String getSubsystemId() {
        return subsystemId;
    }
    
    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }
    
    public String getShowOnModel() {
        return showOnModel;
    }
    
    public void setShowOnModel(String showOnModel) {
        this.showOnModel = showOnModel;
    }
    
    public String getTerminalIndex1() {
        return terminalIndex1;
    }
    
    public void setTerminalIndex1(String terminalIndex1) {
        this.terminalIndex1 = terminalIndex1;
    }
    
    public String getTerminalIndex2() {
        return terminalIndex2;
    }
    
    public void setTerminalIndex2(String terminalIndex2) {
        this.terminalIndex2 = terminalIndex2;
    }
}
