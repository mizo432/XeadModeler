package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.w3c.dom.Element;

/**
 * ツリー操作サービスのインターフェース
 * ツリーモデルの初期化、ノードの操作などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface TreeService {
    
    /**
     * ツリーモデルを初期化します。
     * @param documentModel ドキュメントモデル
     * @throws Exception エラーが発生した場合
     */
    void initializeTreeModel(XeadDocumentModel documentModel) throws Exception;
    
    /**
     * 指定されたノードの子ノードを追加します。
     * @param parentElement 親要素
     * @param nodeType ノードタイプ
     * @param name ノード名
     * @param alias ノードエイリアス
     * @return 追加された要素
     */
    Element addChildNode(Element parentElement, String nodeType, String name, String alias);
    
    /**
     * 指定されたノードを削除します。
     * @param element 削除する要素
     */
    void removeNode(Element element);
    
    /**
     * 指定されたノードを更新します。
     * @param element 更新する要素
     * @param name 新しい名前
     * @param alias 新しいエイリアス
     */
    void updateNode(Element element, String name, String alias);
    
    /**
     * 指定されたタイプのノードを検索します。
     * @param type ノードタイプ
     * @param id ノードID
     * @return 見つかった要素、見つからない場合はnull
     */
    Element findNode(String type, String id);
}
