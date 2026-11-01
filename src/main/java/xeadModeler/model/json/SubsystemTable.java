package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * サブシステムテーブルのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubsystemTable {
    
    private String boxPosition;
    private String extDivLoc;
    private String intDivLoc;
    private String showOnModel;
    private String showInstance;
    private String instance; // 後方互換性のために残す
    private List<Instance> instances = new ArrayList<>();
    private String tableId;
    
    public String getBoxPosition() {
        return boxPosition;
    }
    
    public void setBoxPosition(String boxPosition) {
        this.boxPosition = boxPosition;
    }
    
    public String getExtDivLoc() {
        return extDivLoc;
    }
    
    public void setExtDivLoc(String extDivLoc) {
        this.extDivLoc = extDivLoc;
    }
    
    public String getIntDivLoc() {
        return intDivLoc;
    }
    
    public void setIntDivLoc(String intDivLoc) {
        this.intDivLoc = intDivLoc;
    }
    
    public String getShowOnModel() {
        return showOnModel;
    }
    
    public void setShowOnModel(String showOnModel) {
        this.showOnModel = showOnModel;
    }
    
    public String getShowInstance() {
        return showInstance;
    }
    
    public void setShowInstance(String showInstance) {
        this.showInstance = showInstance;
    }
    
    public String getInstance() {
        return instance;
    }
    
    public void setInstance(String instance) {
        this.instance = instance;
    }
    
    public String getTableId() {
        return tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    
    public List<Instance> getInstances() {
        return instances;
    }
    
    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
}
