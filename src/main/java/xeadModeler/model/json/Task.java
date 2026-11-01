package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * タスクのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Task {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String taskTypeId;
    private String roleId;
    
    private List<TaskAction> taskActions = new ArrayList<>();
    private List<TaskFunctionIO> taskFunctionIOs = new ArrayList<>();
    
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
    
    public String getTaskTypeId() {
        return taskTypeId;
    }
    
    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }
    
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    public List<TaskAction> getTaskActions() {
        return taskActions;
    }
    
    public void setTaskActions(List<TaskAction> taskActions) {
        this.taskActions = taskActions;
    }
    
    public List<TaskFunctionIO> getTaskFunctionIOs() {
        return taskFunctionIOs;
    }
    
    public void setTaskFunctionIOs(List<TaskFunctionIO> taskFunctionIOs) {
        this.taskFunctionIOs = taskFunctionIOs;
    }
}
