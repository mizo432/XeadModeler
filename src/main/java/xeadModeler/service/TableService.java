package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

/**
 * テーブル操作サービスのインターフェース
 * テーブルデータの編集、更新などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface TableService {
    
    /**
     * テーブルのセル値を更新します。
     * @param object 更新するオブジェクト
     * @param propertyName プロパティ名
     * @param value 新しい値
     */
    void updateCell(Object object, String propertyName, String value);
    
    /**
     * テーブルに行を追加します。
     * @param parentTypeId 親テーブルのID
     * @param rowType 行のタイプ (TableField, TableKey)
     * @return 追加されたオブジェクト
     */
    Object addRow(String parentTypeId, String parentType, String rowType);
    
    /**
     * テーブルから行を削除します。
     * @param type 行のタイプ
     * @param id 行のID
     */
    void removeRow(String type, String id);
    
    /**
     * テーブルのデータを再読み込みします。
     * @param documentModel ドキュメントモデル
     */
    void refreshTable(XeadDocumentModel documentModel);
    
    /**
     * 指定された属性値を持つ要素を検索します。
     * @param elementType 要素タイプ
     * @param propertyName プロパティ名
     * @param propertyValue プロパティ値
     * @return 見つかったオブジェクト、見つからない場合はnull
     */
    Object findElementByAttribute(String elementType, String propertyName, String propertyValue);
}
