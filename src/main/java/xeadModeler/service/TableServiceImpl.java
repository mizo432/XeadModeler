package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * テーブル操作サービスの実装クラス
 * テーブルデータの編集、更新などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class TableServiceImpl implements TableService {
    
    private XeadDocumentModel documentModel;
    
    public TableServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void updateCell(Element element, String attributeName, String value) {
        // セル値の更新ロジック
        element.setAttribute(attributeName, value);
    }
    
    @Override
    public Element addRow(Element parentElement, String rowType) {
        // 行の追加ロジック
        Element newRow = documentModel.getDomDocument().createElement(rowType);
        newRow.setAttribute("ID", generateId(rowType));
        parentElement.appendChild(newRow);
        return newRow;
    }
    
    @Override
    public void removeRow(Element element) {
        // 行の削除ロジック
        Node parentNode = element.getParentNode();
        if (parentNode != null) {
            parentNode.removeChild(element);
        }
    }
    
    @Override
    public void refreshTable(XeadDocumentModel documentModel) {
        // テーブルの再読み込みロジック
        // 現在の実装では何もしません
    }
    
    @Override
    public Element findElementByAttribute(String elementType, String attributeName, String attributeValue) {
        // 要素の検索ロジック
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(elementType);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute(attributeName).equals(attributeValue)) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * 要素IDを生成します。
     * @param elementType 要素タイプ
     * @return 生成されたID
     */
    private String generateId(String elementType) {
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(elementType);
        int maxId = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getAttribute("ID");
            if (id != null && !id.isEmpty()) {
                try {
                    int numericId = Integer.parseInt(id.replace(elementType, ""));
                    if (numericId > maxId) {
                        maxId = numericId;
                    }
                } catch (NumberFormatException e) {
                    // 数値として解析できない場合はスキップ
                }
            }
        }
        return elementType + (maxId + 1);
    }
}
