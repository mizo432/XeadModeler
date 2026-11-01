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
}
