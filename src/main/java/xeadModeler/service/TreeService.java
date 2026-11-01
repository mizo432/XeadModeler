package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

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
     * 指定されたタイプのノードを追加します。
     * @param nodeType ノードタイプ (Table, Relationship, Subsystem, Function, Task, Role, SubjectArea, MaintenanceLog)
     * @param name ノード名
     * @param alias ノードエイリアス
     * @return 追加されたオブジェクト
     */
    Object addChildNode(String nodeType, String name, String alias);
    
    /**
     * 指定されたノードを削除します。
     * @param type ノードタイプ
     * @param id ノードID
     */
    void removeNode(String type, String id);
    
    /**
     * 指定されたノードを更新します。
     * @param type ノードタイプ
     * @param id ノードID
     * @param name 新しい名前
     * @param alias 新しいエイリアス
     */
    void updateNode(String type, String id, String name, String alias);
    
    /**
     * 指定されたタイプのノードを検索します。
     * @param type ノードタイプ
     * @param id ノードID
     * @return 見つかったオブジェクト、見つからない場合はnull
     */
    Object findNode(String type, String id);
}
