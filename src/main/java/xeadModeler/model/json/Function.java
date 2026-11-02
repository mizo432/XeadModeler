package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 機能のJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Function {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String functionTypeId;
    private String subsystemId;
    private String parameters;
    private String return_;
    private String summary;
    private String docFile;
    
    private List<FunctionUsedByThis> functionUsedByThisList = new ArrayList<>();
    private List<IOPanel> ioPanels = new ArrayList<>();
    private List<IOSpool> ioSpools = new ArrayList<>();
    private List<IOTable> ioTables = new ArrayList<>();
    private List<IOWebPage> ioWebPages = new ArrayList<>();
    
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
    
    public String getFunctionTypeId() {
        return functionTypeId;
    }
    
    public void setFunctionTypeId(String functionTypeId) {
        this.functionTypeId = functionTypeId;
    }
    
    public String getSubsystemId() {
        return subsystemId;
    }
    
    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }
    
    /**
     * Alias for getSubsystemId() for compatibility
     */
    public String getSubsystemID() {
        return subsystemId;
    }
    
    public List<IOPanel> getIOPanels() {
        return ioPanels;
    }
    
    public void setIOPanels(List<IOPanel> ioPanels) {
        this.ioPanels = ioPanels;
    }
    
    public List<IOPanel> getIopanels() {
        return ioPanels;
    }
    
    public List<IOSpool> getIOSpools() {
        return ioSpools;
    }
    
    public void setIOSpools(List<IOSpool> ioSpools) {
        this.ioSpools = ioSpools;
    }
    
    public List<IOSpool> getIospools() {
        return ioSpools;
    }
    
    public List<IOTable> getIOTables() {
        return ioTables;
    }
    
    public void setIOTables(List<IOTable> ioTables) {
        this.ioTables = ioTables;
    }
    
    public List<IOWebPage> getIOWebPages() {
        return ioWebPages;
    }
    
    public void setIOWebPages(List<IOWebPage> ioWebPages) {
        this.ioWebPages = ioWebPages;
    }
    
    public List<IOWebPage> getIowebpages() {
        return ioWebPages;
    }
    
    public List<FunctionUsedByThis> getFunctionUsedByThisList() {
        return functionUsedByThisList;
    }
    
    public void setFunctionUsedByThisList(List<FunctionUsedByThis> functionUsedByThisList) {
        this.functionUsedByThisList = functionUsedByThisList;
    }
    
    public String getParameters() {
        return parameters;
    }
    
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
    
    public String getReturn() {
        return return_;
    }
    
    public void setReturn(String return_) {
        this.return_ = return_;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getDocFile() {
        return docFile;
    }
    
    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }
}
