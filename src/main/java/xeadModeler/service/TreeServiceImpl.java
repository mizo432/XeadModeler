package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ツリー操作サービスの実装クラス
 * ツリーモデルの初期化、ノードの操作などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class TreeServiceImpl implements TreeService {
    
    private XeadDocumentModel documentModel;
    
    public TreeServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void initializeTreeModel(XeadDocumentModel documentModel) throws Exception {
        // ツリーモデルの初期化ロジック
        // Modeler.javaのsetupMainTreeModelWithCurrentFileNameメソッドから抽出
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Element addChildNode(Element parentElement, String nodeType, String name, String alias) {
        // 子ノードの追加ロジック
        // Modeler.javaのjMenuItemXeadTreeNodeAdd_actionPerformedメソッドから抽出
        Element newElement = documentModel.getDomDocument().createElement(nodeType);
        newElement.setAttribute("Name", name);
        newElement.setAttribute("Alias", alias);
        newElement.setAttribute("ID", generateId(nodeType));
        parentElement.appendChild(newElement);
        return newElement;
    }
    
    @Override
    public void removeNode(Element element) {
        // ノードの削除ロジック
        // Modeler.javaのjMenuItemXeadTreeNodeDelete_actionPerformedメソッドから抽出
        Node parentNode = element.getParentNode();
        if (parentNode != null) {
            parentNode.removeChild(element);
        }
    }
    
    @Override
    public void updateNode(Element element, String name, String alias) {
        // ノードの更新ロジック
        element.setAttribute("Name", name);
        element.setAttribute("Alias", alias);
    }
    
    @Override
    public Element findNode(String type, String id) {
        // ノードの検索ロジック
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(type);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("ID").equals(id)) {
                return element;
            }
        }
        return null;
    }
    
    /**
     * ノードIDを生成します。
     * @param nodeType ノードタイプ
     * @return 生成されたID
     */
    private String generateId(String nodeType) {
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(nodeType);
        int maxId = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getAttribute("ID");
            if (id != null && !id.isEmpty()) {
                try {
                    int numericId = Integer.parseInt(id.replace(nodeType, ""));
                    if (numericId > maxId) {
                        maxId = numericId;
                    }
                } catch (NumberFormatException e) {
                    // 数値として解析できない場合はスキップ
                }
            }
        }
        return nodeType + (maxId + 1);
    }
}
