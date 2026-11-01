package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * タスクアクションのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TaskAction {
    
    private String id;
    private String sortKey;
    private String actionType;
    private String descriptions;
    private List<TaskFunctionIO> taskFunctionIOs = new ArrayList<>();
    
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
    
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public List<TaskFunctionIO> getTaskFunctionIOs() {
        return taskFunctionIOs;
    }
    
    public void setTaskFunctionIOs(List<TaskFunctionIO> taskFunctionIOs) {
        this.taskFunctionIOs = taskFunctionIOs;
    }
}
