package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * IOWebPageのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOWebPage {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    
    private List<IOWebPageField> iowebpageFields = new ArrayList<>();
    
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
    
    public List<IOWebPageField> getIowebpageFields() {
        return iowebpageFields;
    }
    
    public void setIowebpageFields(List<IOWebPageField> iowebpageFields) {
        this.iowebpageFields = iowebpageFields;
    }
}
