package xeadModeler.model.json;

/**
 * IOプールスタイルのJSONモデルクラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOSpoolStyle {
    
    private String backgroundColor;
    private String borderColor;
    private String fontSize;
    private String fontName;
    private String value;
    
    public String getBackgroundColor() {
        return backgroundColor;
    }
    
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public String getBorderColor() {
        return borderColor;
    }
    
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
    
    public String getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
    
    public String getFontName() {
        return fontName;
    }
    
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
