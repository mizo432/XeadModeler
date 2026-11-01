package xeadModeler.model.json;

/**
 * インスタンスのJSONモデルクラス
 * データモデルのテーブルに表示するインスタンスデータを表現します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class Instance {
    
    private String text;
    private String sortKey;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getSortKey() {
        return sortKey;
    }
    
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
