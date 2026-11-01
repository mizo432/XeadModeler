package xeadModeler.service;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * XMLデータ管理のサービスインターフェース
 * DOMドキュメントの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface XmlService {
    
    /**
     * XEADフォーマット仕様に従ってDOM要素の順序を整列します
     */
    void arrangeOrderOfDomElement();
    
    /**
     * 指定されたタイプで要素をソートします
     * @param type ソートする要素のタイプ
     * @param topElement ソートされた要素が挿入される前の要素
     * @return ソートされた要素のトップ要素
     */
    Element sortElementsByType(String type, Element topElement);
    
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
     * @param type 要素のタイプ
     * @param id 要素のID
     * @return 見つかった要素、見つからなかった場合はnull
     */
    Element getSpecificElement(String type, String id);
    
    /**
     * 指定されたタイプのすべての要素を取得します
     * @param type 要素のタイプ
     * @return 要素のリスト
     */
    NodeList getElementsByType(String type);
    
    /**
     * システム要素を取得します
     * @return システム要素
     */
    Element getSystemElement();
}
