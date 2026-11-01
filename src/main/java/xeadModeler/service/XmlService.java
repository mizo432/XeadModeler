package xeadModeler.service;

import xeadModeler.model.json.XeadSystem;

import java.util.List;

/**
 * データ管理サービスのインターフェース
 * XeadSystemの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface XmlService {
    
    /**
     * XEADフォーマット仕様に従って要素の順序を整列します
     */
    void arrangeOrderOfDomElement();
    
    /**
     * 削除されたTableに関連するSubsystemTable要素を削除します
     */
    void purgeInvalidSubsystemTable();
    
    /**
     * 削除されたRelationshipに関連するSubsystemRelationship要素を削除します
     */
    void purgeInvalidSubsystemRelationship();
    
    /**
     * 指定されたタイプとIDの要素を取得します
     * @param type 要素のタイプ (Table, Relationship, Subsystem, Function, Task, Role, SubjectArea, MaintenanceLog)
     * @param id 要素のID
     * @return 見つかった要素、見つからなかった場合はnull
     */
    Object getSpecificElement(String type, String id);
    
    /**
     * 指定されたタイプのすべての要素を取得します
     * @param type 要素のタイプ
     * @return 要素のリスト
     */
    List<?> getElementsByType(String type);
    
    /**
     * システム要素を取得します
     * @return XeadSystem
     */
    XeadSystem getSystemElement();
}
