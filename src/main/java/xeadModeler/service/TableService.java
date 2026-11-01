package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.w3c.dom.Element;

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
     * @param element 更新する要素
     * @param attributeName 属性名
     * @param value 新しい値
     */
    void updateCell(Element element, String attributeName, String value);
    
    /**
     * テーブルに行を追加します。
     * @param parentElement 親要素
     * @param rowType 行のタイプ
     * @return 追加された要素
     */
    Element addRow(Element parentElement, String rowType);
    
    /**
     * テーブルから行を削除します。
     * @param element 削除する要素
     */
    void removeRow(Element element);
    
    /**
     * テーブルのデータを再読み込みします。
     * @param documentModel ドキュメントモデル
     */
    void refreshTable(XeadDocumentModel documentModel);
    
    /**
     * 指定された属性値を持つ要素を検索します。
     * @param elementType 要素タイプ
     * @param attributeName 属性名
     * @param attributeValue 属性値
     * @return 見つかった要素、見つからない場合はnull
     */
    Element findElementByAttribute(String elementType, String attributeName, String attributeValue);
}
