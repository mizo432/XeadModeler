package xeadModeler.model.json;

/**
 * タスク機能IOのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TaskFunctionIO {
    
    private String id;
    private String sortKey;
    private String functionId;
    private String ioId;
    private String ioType;
    private String ioIdSeq;
    private String operations;
    
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
    
    public String getFunctionId() {
        return functionId;
    }
    
    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
    
    public String getIoId() {
        return ioId;
    }
    
    public void setIoId(String ioId) {
        this.ioId = ioId;
    }
    
    public String getIoType() {
        return ioType;
    }
    
    public void setIoType(String ioType) {
        this.ioType = ioType;
    }
    
    public String getIoIdSeq() {
        return ioIdSeq;
    }
    
    public void setIoIdSeq(String ioIdSeq) {
        this.ioIdSeq = ioIdSeq;
    }
    
    public String getOperations() {
        return operations;
    }
    
    public void setOperations(String operations) {
        this.operations = operations;
    }
}
