package xeadModeler.model.json;

/**
 * 機能使用関係のJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class FunctionUsedByThis {
    
    private String functionId;
    private String sortKey;
    private String launchEvent;
    
    public String getFunctionId() {
        return functionId;
    }
    
    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
    
    public String getLaunchEvent() {
        return launchEvent;
    }
    
    public void setLaunchEvent(String launchEvent) {
        this.launchEvent = launchEvent;
    }
}
