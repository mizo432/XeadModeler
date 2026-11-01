package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * IOプールのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOSpool {
    
    private String descriptions;
    private String id;
    private String name;
    private String sortKey;
    private String background;
    private String imageText;
    private String size;
    
    private List<IOSpoolField> iospoolFields = new ArrayList<>();
    private IOSpoolStyle iospoolStyle;
    
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
    
    public List<IOSpoolField> getIospoolFields() {
        return iospoolFields;
    }
    
    public void setIospoolFields(List<IOSpoolField> iospoolFields) {
        this.iospoolFields = iospoolFields;
    }
    
    public IOSpoolStyle getIospoolStyle() {
        return iospoolStyle;
    }
    
    public void setIospoolStyle(IOSpoolStyle iospoolStyle) {
        this.iospoolStyle = iospoolStyle;
    }
    
    public String getBackground() {
        return background;
    }
    
    public void setBackground(String background) {
        this.background = background;
    }
    
    public String getImageText() {
        return imageText;
    }
    
    public void setImageText(String imageText) {
        this.imageText = imageText;
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
}
