package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * データフローラインのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataflowLine {
    
    private String id;
    private String sortKey;
    private String fromNodeId;
    private String toNodeId;
    private String descriptions;
    private String name;
    private String nameExt;
    private int slideNumber;
    private String imageType;
    private String nodeID1;
    private String nodeID2;
    private String showArrow1;
    private String showArrow2;
    private int terminalPosIndex1;
    private int terminalPosIndex2;
    
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
    
    public String getFromNodeId() {
        return fromNodeId;
    }
    
    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }
    
    public String getToNodeId() {
        return toNodeId;
    }
    
    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
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
    
    public String getImageType() {
        return imageType;
    }
    
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    
    public String getNodeID1() {
        return nodeID1;
    }
    
    public void setNodeID1(String nodeID1) {
        this.nodeID1 = nodeID1;
    }
    
    public String getNodeID2() {
        return nodeID2;
    }
    
    public void setNodeID2(String nodeID2) {
        this.nodeID2 = nodeID2;
    }
    
    public String getShowArrow1() {
        return showArrow1;
    }
    
    public void setShowArrow1(String showArrow1) {
        this.showArrow1 = showArrow1;
    }
    
    public String getShowArrow2() {
        return showArrow2;
    }
    
    public void setShowArrow2(String showArrow2) {
        this.showArrow2 = showArrow2;
    }
    
    public int getTerminalPosIndex1() {
        return terminalPosIndex1;
    }
    
    public void setTerminalPosIndex1(int terminalPosIndex1) {
        this.terminalPosIndex1 = terminalPosIndex1;
    }
    
    public int getTerminalPosIndex2() {
        return terminalPosIndex2;
    }
    
    public void setTerminalPosIndex2(int terminalPosIndex2) {
        this.terminalPosIndex2 = terminalPosIndex2;
    }
}
