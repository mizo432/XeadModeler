package xeadModeler.model.json;

/**
 * データフローノードのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataflowNode {
    
    private String id;
    private String sortKey;
    private String nodeId;
    private String nodeType;
    private String descriptions;
    private String type;
    private String position;
    private String name;
    private String nameExt;
    private int slideNumber;
    private String taskID;
    private String eventPos;
    private String roleID;
    
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
    
    public String getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getNodeType() {
        return nodeType;
    }
    
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNameExt() {
        return nameExt;
    }
    
    public void setNameExt(String nameExt) {
        this.nameExt = nameExt;
    }
    
    public int getSlideNumber() {
        return slideNumber;
    }
    
    public void setSlideNumber(int slideNumber) {
        this.slideNumber = slideNumber;
    }
    
    public String getTaskID() {
        return taskID;
    }
    
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    
    public String getEventPos() {
        return eventPos;
    }
    
    public void setEventPos(String eventPos) {
        this.eventPos = eventPos;
    }
    
    public String getRoleID() {
        return roleID;
    }
    
    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
