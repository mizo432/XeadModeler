package xeadModeler.model;

import xeadModeler.model.json.XeadSystem;
import xeadModeler.service.JsonService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * XEADドキュメントのモデルクラス
 * JSONデータの管理とファイルの読み込み・書き込みを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadDocumentModel {
    
    private XeadSystem xeadSystem;
    private XeadSystem xeadSystemSaved;
    private String currentFileName;
    private String currentFileFolder;
    private long lastModifyTime;
    
    // DDL設定
    private String ddlCommentMark = "--";
    private String ddlSectionMark = ";";
    private String ddlAdditionalParms = "";
    private String ddlFieldCommentType = "1";
    private boolean ignoreForeignKeyConstraints = false;
    private boolean isToConvertCamel = true;
    private boolean useTableNameAsTableNameInCreateTableStatement = true;
    
    // リファレンスファイル
    private final Map<String, XeadSystem> synchFileMap = new HashMap<>();
    private final List<String> referringFileNameList = new ArrayList<>();
    private final List<XeadSystem> referringFileDocList = new ArrayList<>();
    
    // システム情報
    private String systemName;
    
    private JsonService jsonService;
    
    public XeadDocumentModel() {
    }
    
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
    
    public JsonService getJsonService() {
        return jsonService;
    }
    
    // Getters and Setters
    
    public XeadSystem getXeadSystem() {
        return xeadSystem;
    }
    
    public void setXeadSystem(XeadSystem xeadSystem) {
        this.xeadSystem = xeadSystem;
    }
    
    public XeadSystem getXeadSystemSaved() {
        return xeadSystemSaved;
    }
    
    public void setXeadSystemSaved(XeadSystem xeadSystemSaved) {
        this.xeadSystemSaved = xeadSystemSaved;
    }
    
    public String getCurrentFileName() {
        return currentFileName;
    }
    
    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
        if (currentFileName != null) {
            this.currentFileFolder = new File(currentFileName).getParent();
        }
    }
    
    public String getCurrentFileFolder() {
        return currentFileFolder;
    }
    
    public long getLastModifyTime() {
        return lastModifyTime;
    }
    
    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    
    public String getSystemName() {
        return systemName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    // DDL設定のGetter/Setter
    
    public String getDdlCommentMark() {
        return ddlCommentMark;
    }
    
    public void setDdlCommentMark(String ddlCommentMark) {
        this.ddlCommentMark = ddlCommentMark;
    }
    
    public String getDdlSectionMark() {
        return ddlSectionMark;
    }
    
    public void setDdlSectionMark(String ddlSectionMark) {
        this.ddlSectionMark = ddlSectionMark;
    }
    
    public String getDdlAdditionalParms() {
        return ddlAdditionalParms;
    }
    
    public void setDdlAdditionalParms(String ddlAdditionalParms) {
        this.ddlAdditionalParms = ddlAdditionalParms;
    }
    
    public String getDdlFieldCommentType() {
        return ddlFieldCommentType;
    }
    
    public void setDdlFieldCommentType(String ddlFieldCommentType) {
        this.ddlFieldCommentType = ddlFieldCommentType;
    }
    
    public boolean isIgnoreForeignKeyConstraints() {
        return ignoreForeignKeyConstraints;
    }
    
    public void setIgnoreForeignKeyConstraints(boolean ignoreForeignKeyConstraints) {
        this.ignoreForeignKeyConstraints = ignoreForeignKeyConstraints;
    }
    
    public boolean isIsToConvertCamel() {
        return isToConvertCamel;
    }
    
    public void setIsToConvertCamel(boolean isToConvertCamel) {
        this.isToConvertCamel = isToConvertCamel;
    }
    
    public boolean isUseTableNameAsTableNameInCreateTableStatement() {
        return useTableNameAsTableNameInCreateTableStatement;
    }
    
    public void setUseTableNameAsTableNameInCreateTableStatement(boolean useTableNameAsTableNameInCreateTableStatement) {
        this.useTableNameAsTableNameInCreateTableStatement = useTableNameAsTableNameInCreateTableStatement;
    }
    
    public Map<String, XeadSystem> getSynchFileMap() {
        return synchFileMap;
    }
    
    public List<String> getReferringFileNameList() {
        return referringFileNameList;
    }
    
    public List<XeadSystem> getReferringFileDocList() {
        return referringFileDocList;
    }
    
    
    /**
     * XEADファイルをパースしてXeadSystemオブジェクトを作成します
     * @param fileName ファイルパス
     * @throws Exception パースエラー
     */
    public void parseDocument(String fileName) throws Exception {
        this.xeadSystem = jsonService.loadSystem(fileName);
        
        File file = new File(fileName);
        this.currentFileFolder = file.getParent();
        this.lastModifyTime = file.lastModified();
    }
    
    /**
     * バックアップ用のXeadSystemオブジェクトを作成します
     * @param fileName ファイルパス
     * @throws Exception パースエラー
     */
    public void parseDocumentForBackup(String fileName) throws Exception {
        this.xeadSystemSaved = jsonService.loadSystem(fileName);
    }
    
    /**
     * XeadSystemオブジェクトをファイルに保存します
     * @throws IOException I/Oエラー
     */
    public void saveDocument() throws Exception {
        jsonService.saveSystem(xeadSystem, currentFileName);
        
        File file = new File(currentFileName);
        this.lastModifyTime = file.lastModified();
    }
    
    /**
     * バックアップファイルを保存します
     * @param backupFileName バックアップファイル名
     * @throws IOException I/Oエラー
     */
    public void saveBackupDocument(String backupFileName) throws Exception {
        jsonService.saveSystem(xeadSystemSaved, backupFileName);
    }
    
    /**
     * 新規XEADファイル（JSON）を作成します
     * @param fileName ファイルパス
     * @param formatVersion フォーマットバージョン
     * @throws IOException I/Oエラー
     */
    public void createNewXeadFile(String fileName, String formatVersion) throws Exception {
        this.xeadSystem = jsonService.createNewSystem(fileName, formatVersion);
    }
    
    /**
     * システムオブジェクトを取得します
     * @return XeadSystem
     */
    public XeadSystem getSystemElement() {
        return xeadSystem;
    }
    
    /**
     * 後方互換性のために残したメソッド（非推奨）
     * @return XeadSystem
     * @deprecated Use getXeadSystem() instead
     */
    @Deprecated
    public XeadSystem getDomDocument() {
        return xeadSystem;
    }
    
    /**
     * DDL設定をXeadSystemから読み込みます
     */
    public void loadDdlConfig() {
        if (xeadSystem == null) {
            return;
        }
        
        this.ddlCommentMark = xeadSystem.getDdlCommentMark();
        this.ddlSectionMark = xeadSystem.getDdlSectionMark();
        this.ddlAdditionalParms = xeadSystem.getDdlAdditionalParms();
        this.ddlFieldCommentType = xeadSystem.getDdlFieldCommentType();
        this.ignoreForeignKeyConstraints = xeadSystem.isIgnoreForeignKeyConstraints();
        this.isToConvertCamel = xeadSystem.isIsToConvertCamel();
        this.useTableNameAsTableNameInCreateTableStatement = xeadSystem.isUseTableNameAsTableNameInCreateTableStatement();
        
        // システム名の取得
        this.systemName = xeadSystem.getName();
    }
    
    /**
     * ドキュメントをリセットします
     */
    public void reset() {
        this.xeadSystem = null;
        this.xeadSystemSaved = null;
        this.currentFileName = null;
        this.currentFileFolder = null;
        this.lastModifyTime = 0;
        this.systemName = null;
        this.synchFileMap.clear();
        this.referringFileNameList.clear();
        this.referringFileDocList.clear();
    }
}
