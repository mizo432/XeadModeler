package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

/**
 * ファイル操作サービスのインターフェース
 * ファイルの新規作成・開く・保存などの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface FileService {
    
    /**
     * 新規XEADファイルを作成します
     * @param fileName ファイル名
     * @param formatVersion フォーマットバージョン
     * @return 成功した場合true
     */
    boolean createNewFile(String fileName, String formatVersion);
    
    /**
     * 既存のXEADファイルを開きます
     * @param fileName ファイル名
     * @return 成功した場合true
     */
    boolean openFile(String fileName);
    
    /**
     * 現在のファイルを保存します
     * @param checkLastModifyTime 最終更新時刻をチェックするかどうか
     * @return 成功した場合true
     */
    boolean saveFile(boolean checkLastModifyTime);
    
    /**
     * 名前を付けて保存します
     * @param fileName 新しいファイル名
     * @return 成功した場合true
     */
    boolean saveAsFile(String fileName);
    
    /**
     * バックアップファイルを保存します
     * @param backupFolder バックアップフォルダ
     * @return 成功した場合true
     */
    boolean saveBackup(String backupFolder);
    
    /**
     * ドキュメントモデルを取得します
     * @return XeadDocumentModel
     */
    XeadDocumentModel getDocumentModel();
}
