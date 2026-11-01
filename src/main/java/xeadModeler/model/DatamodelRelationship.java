package xeadModeler.model;

import org.w3c.dom.Element;

/**
 * データモデル間の関係を表現するモデルクラス
 * UI依存を除去した純粋な関係データを提供します。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DatamodelRelationship {
    private final Element subsystemRelationshipElement;
    private final Element relationshipElement;
    private String table1Id;
    private String table2Id;
    private String tableKey1Id;
    private String tableKey2Id;
    private boolean terminal1Optional;
    private boolean terminal2Optional;
    private boolean showOnModel;
    
    /**
     * データモデル関係を作成します
     * @param subsystemRelationshipElement サブシステム関係要素
     * @param relationshipElement 関係要素
     */
    public DatamodelRelationship(Element subsystemRelationshipElement, Element relationshipElement) {
        this.subsystemRelationshipElement = subsystemRelationshipElement;
        this.relationshipElement = relationshipElement;
        this.showOnModel = true;
        
        // 関係情報を抽出
        if (relationshipElement != null) {
            this.table1Id = relationshipElement.getAttribute("Table1ID");
            this.table2Id = relationshipElement.getAttribute("Table2ID");
            this.tableKey1Id = relationshipElement.getAttribute("TableKey1ID");
            this.tableKey2Id = relationshipElement.getAttribute("TableKey2ID");
            this.terminal1Optional = !relationshipElement.getAttribute("ExistWhen1").equals("");
            this.terminal2Optional = !relationshipElement.getAttribute("ExistWhen2").equals("");
        }
    }
    
    /**
     * サブシステム関係要素を返します
     * @return サブシステム関係要素
     */
    public Element getSubsystemRelationshipElement() {
        return subsystemRelationshipElement;
    }
    
    /**
     * 関係要素を返します
     * @return 関係要素
     */
    public Element getRelationshipElement() {
        return relationshipElement;
    }
    
    /**
     * 最初のテーブルIDを返します
     * @return テーブルID
     */
    public String getTable1Id() {
        return table1Id;
    }
    
    /**
     * 2番目のテーブルIDを返します
     * @return テーブルID
     */
    public String getTable2Id() {
        return table2Id;
    }
    
    /**
     * 最初のキーIDを返します
     * @return キーID
     */
    public String getTableKey1Id() {
        return tableKey1Id;
    }
    
    /**
     * 2番目のキーIDを返します
     * @return キーID
     */
    public String getTableKey2Id() {
        return tableKey2Id;
    }
    
    /**
     * 最初のターミナルがオプションかどうかを返します
     * @return オプションの場合true
     */
    public boolean isTerminal1Optional() {
        return terminal1Optional;
    }
    
    /**
     * 2番目のターミナルがオプションかどうかを返します
     * @return オプションの場合true
     */
    public boolean isTerminal2Optional() {
        return terminal2Optional;
    }
    
    /**
     * モデル上で表示するかどうかを返します
     * @return 表示する場合true
     */
    public boolean isShowOnModel() {
        return showOnModel;
    }
    
    /**
     * モデル上での表示設定を設定します
     * @param showOnModel 表示する場合true
     */
    public void setShowOnModel(boolean showOnModel) {
        this.showOnModel = showOnModel;
    }
}
