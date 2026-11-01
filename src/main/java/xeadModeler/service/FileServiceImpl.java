package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * ファイル操作サービスの実装
 * ファイルの新規作成・開く・保存などの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class FileServiceImpl implements FileService {
    
    private final XeadDocumentModel documentModel;
    private final XeadUndoManager undoManager;
    private final ChangeStateService changeStateService;
    
    // バックアップ設定
    private boolean isNeedToBackup = false;
    private String backupFolder = "";
    
    // リスナー（UI更新用）
    private FileOperationListener fileOperationListener;
    
    /**
     * ファイル操作リスナーインターフェース
     */
    public interface FileOperationListener {
        void onFileOpened(String fileName);
        void onFileSaved(String fileName);
        void onFileCreated(String fileName);
        void onError(String message);
    }
    
    public FileServiceImpl(XeadDocumentModel documentModel, XeadUndoManager undoManager, ChangeStateService changeStateService) {
        this.documentModel = documentModel;
        this.undoManager = undoManager;
        this.changeStateService = changeStateService;
    }
    
    @Override
    public boolean createNewFile(String fileName, String formatVersion) {
        try {
            // 新規ファイルを作成
            documentModel.createNewXeadFile(fileName, formatVersion);
            
            // ドキュメントをパース
            documentModel.setCurrentFileName(fileName);
            documentModel.parseDocument(fileName);
            documentModel.loadDdlConfig();
            
            // Undo/Redoリセット
            undoManager.clear();
            changeStateService.reset();
            
            if (fileOperationListener != null) {
                fileOperationListener.onFileCreated(fileName);
            }
            
            return true;
        } catch (IOException e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to create file: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to parse file: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean openFile(String fileName) {
        try {
            // ファイルの存在確認
            File file = new File(fileName);
            if (!file.exists()) {
                if (fileOperationListener != null) {
                    fileOperationListener.onError("File not found: " + fileName);
                }
                return false;
            }
            
            // ドキュメントをパース
            documentModel.setCurrentFileName(fileName);
            documentModel.parseDocument(fileName);
            
            // バックアップ用ドキュメントも作成（必要に応じて）
            if (!backupFolder.equals("")) {
                documentModel.parseDocumentForBackup(fileName);
            }
            
            // DDL設定を読み込み
            documentModel.loadDdlConfig();
            
            // フォーマットバージョンチェック
            String formatVersion = documentModel.getSystemElement().getFormatVersion();
            // TODO: フォーマットバージョンのチェックロジックを追加
            
            // Undo/Redoリセット
            undoManager.clear();
            changeStateService.reset();
            
            if (fileOperationListener != null) {
                fileOperationListener.onFileOpened(fileName);
            }
            
            return true;
        } catch (Exception e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to open file: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean saveFile(boolean checkLastModifyTime) {
        try {
            String fileName = documentModel.getCurrentFileName();
            if (fileName == null || fileName.equals("")) {
                return false;
            }
            
            // 最終更新時刻チェック
            if (checkLastModifyTime) {
                File file = new File(fileName);
                if (documentModel.getLastModifyTime() != file.lastModified()) {
                    // ファイルが外部から変更されている場合
                    // TODO: ユーザーに確認ダイアログを表示するロジックはController側で処理
                }
            }
            
            // バックアップ保存（必要に応じて）
            if (isNeedToBackup) {
                isNeedToBackup = false;
                saveBackup(backupFolder);
            }
            
            // ドキュメントを保存
            documentModel.saveDocument();
            
            if (fileOperationListener != null) {
                fileOperationListener.onFileSaved(fileName);
            }
            
            return true;
        } catch (Exception e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to save file: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean saveAsFile(String fileName) {
        try {
            // 新しいファイル名を設定
            documentModel.setCurrentFileName(fileName);
            
            // ドキュメントを保存
            documentModel.saveDocument();
            
            if (fileOperationListener != null) {
                fileOperationListener.onFileSaved(fileName);
            }
            
            return true;
        } catch (Exception e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to save file: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean saveBackup(String backupFolder) {
        try {
            String currentFileName = documentModel.getCurrentFileName();
            String currentFileFolder = documentModel.getCurrentFileFolder();
            
            if (currentFileName == null || currentFileFolder == null) {
                return false;
            }
            
            // バックアップフォルダの作成
            String newFolder = backupFolder.replace("<CURRENT>", currentFileFolder);
            File newFolderDir = new File(newFolder);
            if (!newFolderDir.exists()) {
                newFolderDir.mkdir();
            }
            
            // バックアップファイル名の生成
            String backupFileName = currentFileName.replace(".xead", getDateTimeString() + ".xead");
            backupFileName = backupFileName.replace(currentFileFolder, newFolder);
            
            // バックアップ保存
            documentModel.saveBackupDocument(backupFileName);
            
            return true;
        } catch (Exception e) {
            if (fileOperationListener != null) {
                fileOperationListener.onError("Failed to save backup: " + e.getMessage());
            }
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public XeadDocumentModel getDocumentModel() {
        return documentModel;
    }
    
    /**
     * バックアップ設定を設定します
     * @param needToBackup バックアップが必要かどうか
     * @param backupFolder バックアップフォルダ
     */
    public void setBackupConfig(boolean needToBackup, String backupFolder) {
        this.isNeedToBackup = needToBackup;
        this.backupFolder = backupFolder;
    }
    
    /**
     * ファイル操作リスナーを設定します
     * @param listener リスナー
     */
    public void setFileOperationListener(FileOperationListener listener) {
        this.fileOperationListener = listener;
    }
    
    /**
     * 日付文字列を生成します
     * @return 日付文字列
     */
    private String getDateTimeString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new java.util.Date());
    }
}
