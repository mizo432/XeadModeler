package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * IOTableのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOTable {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String tableId;
    private String nameExtension;
    private String opC;
    private String opR;
    private String opU;
    private String opD;
    
    private List<IOTableField> iotableFields = new ArrayList<>();
    
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
    
    public String getTableId() {
        return tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    
    public List<IOTableField> getIotableFields() {
        return iotableFields;
    }
    
    public void setIotableFields(List<IOTableField> iotableFields) {
        this.iotableFields = iotableFields;
    }

    public String getNameExtension() {
        return nameExtension;
    }

    public void setNameExtension(String nameExtension) {
        this.nameExtension = nameExtension;
    }

    public String getOpC() {
        return opC;
    }

    public void setOpC(String opC) {
        this.opC = opC;
    }

    public String getOpR() {
        return opR;
    }

    public void setOpR(String opR) {
        this.opR = opR;
    }

    public String getOpU() {
        return opU;
    }

    public void setOpU(String opU) {
        this.opU = opU;
    }

    public String getOpD() {
        return opD;
    }

    public void setOpD(String opD) {
        this.opD = opD;
    }
}
