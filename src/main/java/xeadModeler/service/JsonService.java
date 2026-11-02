package xeadModeler.service;

import xeadModeler.model.json.XeadSystem;

/**
 * JSONデータ管理のサービスインターフェース
 * JSONファイルの読み込み・書き込みを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface JsonService {
    
    /**
     * JSONファイルからXeadSystemオブジェクトを読み込みます
     * @param fileName ファイルパス
     * @return XeadSystemオブジェクト
     * @throws Exception パースエラー
     */
    XeadSystem loadSystem(String fileName) throws Exception;
    
    /**
     * XeadSystemオブジェクトをJSONファイルに保存します
     * @param system XeadSystemオブジェクト
     * @param fileName ファイルパス
     * @throws Exception 書き込みエラー
     */
    void saveSystem(XeadSystem system, String fileName) throws Exception;
    
    /**
     * 新規XEADファイル（JSON）を作成します
     * @param fileName ファイルパス
     * @param formatVersion フォーマットバージョン
     * @return 作成されたXeadSystemオブジェクト
     * @throws Exception 作成エラー
     */
    XeadSystem createNewSystem(String fileName, String formatVersion) throws Exception;
    
    /**
     * JSON POJOオブジェクトをクローンします
     * @param obj クローンするオブジェクト
     * @return クローンされたオブジェクト
     */
    Object cloneObject(Object obj) throws Exception;
    
    /**
     * リフレクションを使用してJSON POJOから属性値を取得します
     * @param obj 読み取り元のオブジェクト
     * @param attributeName 属性名
     * @return 属性値、見つからない場合は空文字列
     */
    String getAttributeFromObject(Object obj, String attributeName);
    
    /**
     * リフレクションを使用してJSON POJOに属性値を設定します
     * @param pojo JSON POJOオブジェクト
     * @param attributeName 属性名（例: "FunctionID"）
     * @param value 設定する値
     */
    void setAttributeOnJsonPojo(Object pojo, String attributeName, String value);
    
    /**
     * 子JSON POJOを親JSON POJOのリストに追加します
     * @param parentPojo 親JSON POJO
     * @param childPojo 追加する子JSON POJO
     * @param childType 子のタイプ名（例: "FunctionUsedByThis"）
     */
    void appendChildToJsonPojo(Object parentPojo, Object childPojo, String childType);
    
    /**
     * 親JSON POJOのリストから子JSON POJOを削除します
     * @param parentPojo 親JSON POJO
     * @param childPojo 削除する子JSON POJO
     * @param childType 子のタイプ名
     */
    void removeChildFromJsonPojo(Object parentPojo, Object childPojo, String childType);
    
    /**
     * 親JSON POJOのリストに子JSON POJOを挿入します（兄弟の前に）
     * @param parentPojo 親JSON POJO
     * @param childPojo 挿入する子JSON POJO
     * @param siblingPojo 挿入先の兄弟
     */
    void insertBeforeInPojo(Object parentPojo, Object childPojo, Object siblingPojo);
    
    /**
     * 子JSON POJOを親JSON POJOのリストプロパティに追加します
     * @param parent 親オブジェクト
     * @param listPropertyName リストプロパティ名
     * @param child 追加する子オブジェクト
     */
    void addChildToPojo(Object parent, String listPropertyName, Object child);
    
    /**
     * 子のタイプ名から親のリストフィールド名を取得します
     * @param childType 子のタイプ名
     * @return リストフィールド名、見つからない場合はnull
     */
    String getListFieldNameForType(String childType);
}
