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
}
