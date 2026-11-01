package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 主題領域のJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubjectArea {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String boundaryPosition;
    private String boundarySize;
    
    private List<DataflowNode> dataflowNodes = new ArrayList<>();
    private List<DataflowLine> dataflowLines = new ArrayList<>();
    
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
    
    public String getBoundaryPosition() {
        return boundaryPosition;
    }
    
    public void setBoundaryPosition(String boundaryPosition) {
        this.boundaryPosition = boundaryPosition;
    }
    
    public String getBoundarySize() {
        return boundarySize;
    }
    
    public void setBoundarySize(String boundarySize) {
        this.boundarySize = boundarySize;
    }
    
    public List<DataflowNode> getDataflowNodes() {
        return dataflowNodes;
    }
    
    public void setDataflowNodes(List<DataflowNode> dataflowNodes) {
        this.dataflowNodes = dataflowNodes;
    }
    
    public List<DataflowLine> getDataflowLines() {
        return dataflowLines;
    }
    
    public void setDataflowLines(List<DataflowLine> dataflowLines) {
        this.dataflowLines = dataflowLines;
    }
}
