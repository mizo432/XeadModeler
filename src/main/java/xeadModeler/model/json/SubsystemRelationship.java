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
}
