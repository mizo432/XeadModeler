package xeadModeler.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

/**
 * XEADドキュメントのモデルクラス
 * DOMドキュメントの管理とXMLファイルの読み込み・書き込みを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadDocumentModel {
    
    private Document domDocument;
    private Document domDocumentSaved;
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
    private final Map<String, Document> synchFileMap = new HashMap<>();
    private final List<String> referringFileNameList = new ArrayList<>();
    private final List<Document> referringFileDocList = new ArrayList<>();
    
    // システム情報
    private String systemName;
    
    public XeadDocumentModel() {
    }
    
    // Getters and Setters
    
    public Document getDomDocument() {
        return domDocument;
    }
    
    public void setDomDocument(Document domDocument) {
        this.domDocument = domDocument;
    }
    
    public Document getDomDocumentSaved() {
        return domDocumentSaved;
    }
    
    public void setDomDocumentSaved(Document domDocumentSaved) {
        this.domDocumentSaved = domDocumentSaved;
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
    
    public Map<String, Document> getSynchFileMap() {
        return synchFileMap;
    }
    
    public List<String> getReferringFileNameList() {
        return referringFileNameList;
    }
    
    public List<Document> getReferringFileDocList() {
        return referringFileDocList;
    }
    
    /**
     * XEADファイルをパースしてDOMドキュメントを作成します
     * @param fileName ファイルパス
     * @throws Exception パースエラー
     */
    public void parseDocument(String fileName) throws Exception {
        DOMParser domParser = new DOMParser();
        domParser.parse(new InputSource(new FileInputStream(fileName)));
        this.domDocument = domParser.getDocument();
        
        File file = new File(fileName);
        this.currentFileFolder = file.getParent();
        this.lastModifyTime = file.lastModified();
    }
    
    /**
     * バックアップ用のDOMドキュメントを作成します
     * @param fileName ファイルパス
     * @throws Exception パースエラー
     */
    public void parseDocumentForBackup(String fileName) throws Exception {
        DOMParser domParser = new DOMParser();
        domParser.parse(new InputSource(new FileInputStream(fileName)));
        this.domDocumentSaved = domParser.getDocument();
    }
    
    /**
     * DOMドキュメントをファイルに保存します
     * @throws IOException I/Oエラー
     */
    public void saveDocument() throws IOException {
        OutputFormat outputFormat = new OutputFormat(domDocument);
        outputFormat.setEncoding("UTF-8");
        FileOutputStream fileOutStream = new FileOutputStream(currentFileName);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutStream, "UTF-8");
        Writer writer = new BufferedWriter(outputStreamWriter);
        XMLSerializer xmlSerializer = new XMLSerializer(writer, outputFormat);
        xmlSerializer.serialize(domDocument.getDocumentElement());
        writer.close();
        
        File file = new File(currentFileName);
        this.lastModifyTime = file.lastModified();
    }
    
    /**
     * バックアップファイルを保存します
     * @param backupFileName バックアップファイル名
     * @throws IOException I/Oエラー
     */
    public void saveBackupDocument(String backupFileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(backupFileName);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        Writer writer = new BufferedWriter(outputStreamWriter);
        OutputFormat outputFormat = new OutputFormat(domDocumentSaved);
        outputFormat.setEncoding("UTF-8");
        XMLSerializer xmlSerializer = new XMLSerializer(writer, outputFormat);
        xmlSerializer.serialize(domDocumentSaved.getDocumentElement());
        writer.close();
    }
    
    /**
     * 新規XEADファイルを作成します
     * @param fileName ファイルパス
     * @param formatVersion フォーマットバージョン
     * @throws IOException I/Oエラー
     */
    public void createNewXeadFile(String fileName, String formatVersion) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ja")) {
            osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            osw.write("<System Descriptions=\"\" Name=\"新規システム\" Version=\"0.0.1\" FormatVersion=\"" + formatVersion + " \">\n");
            osw.write("<Department Descriptions=\"\" ID=\"1\" Name=\"未指定\" SortKey=\"000\" />\n");
            osw.write("<Department Descriptions=\"説明１\" ID=\"2\" Name=\"部署１\" SortKey=\"001\" />\n");
            osw.write("<TableType Descriptions=\"\" ID=\"1\" Name=\"未指定\" SortKey=\"000\" />\n");
            osw.write("<TableType Descriptions=\"説明１\" ID=\"2\" Name=\"テーブルタイプ１\" SortKey=\"001\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"1\" Length=\"0\" Name=\"未指定\" SortKey=\"000\" />\n");
            osw.write("<DataType BasicType=\"SignedNumber\" Decimal=\"0\" ID=\"2\" Length=\"9\" Name=\"符号付９桁整数\" SortKey=\"NR09.0-\" SQLExpression=\"NUMBER(9)\" />\n");
            osw.write("<DataType BasicType=\"UnsignedNumber\" Decimal=\"0\" ID=\"3\" Length=\"9\" Name=\"正の９桁整数\" SortKey=\"NR09.0\" SQLExpression=\"NUMBER(9)\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"4\" Length=\"50\" Name=\"５０桁文字\" SortKey=\"TX050\" SQLExpression=\"VARCHAR(50)\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"5\" Length=\"1\" Name=\"区分\" SortKey=\"KB1\" SQLExpression=\"CHAR(1)\" />\n");
            osw.write("<FunctionType Descriptions=\"\" ID=\"1\" Name=\"未指定\" SortKey=\"000\" />\n");
            osw.write("<FunctionType Descriptions=\"説明１\" ID=\"2\" Name=\"機能タイプ１\" SortKey=\"001\" />\n");
            osw.write("<MaintenanceLog Descriptions=\"新規追加\" Headder=\"0.0.1\" ID=\"0001\" SortKey=\"\" />\n");
            osw.write("</System>\n");
        } else {
            osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            osw.write("<System Descriptions=\"\" Name=\"New System\" Version=\"0.0.1\" FormatVersion=\"" + formatVersion + " \">\n");
            osw.write("<Department Descriptions=\"\" ID=\"1\" Name=\"Unknown\" SortKey=\"000\" />\n");
            osw.write("<Department Descriptions=\"\" ID=\"2\" Name=\"Section1\" SortKey=\"001\" />\n");
            osw.write("<TableType Descriptions=\"\" ID=\"1\" Name=\"Unknown\" SortKey=\"000\" />\n");
            osw.write("<TableType Descriptions=\"\" ID=\"2\" Name=\"TableType1\" SortKey=\"001\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"1\" Length=\"0\" Name=\"Unknown\" SortKey=\"000\" />\n");
            osw.write("<DataType BasicType=\"SignedNumber\" Decimal=\"0\" ID=\"2\" Length=\"9\" Name=\"Signed 9-digit Integer\" SortKey=\"NR09.0-\" SQLExpression=\"NUMBER(9)\" />\n");
            osw.write("<DataType BasicType=\"UnsignedNumber\" Decimal=\"0\" ID=\"3\" Length=\"9\" Name=\"Unsigned 9-digit Integer\" SortKey=\"NR09.0\" SQLExpression=\"NUMBER(9)\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"4\" Length=\"50\" Name=\"50-digit String\" SortKey=\"TX050\" SQLExpression=\"VARCHAR(50)\" />\n");
            osw.write("<DataType BasicType=\"String\" Decimal=\"0\" ID=\"5\" Length=\"1\" Name=\"1-digit Type\" SortKey=\"KB1\" SQLExpression=\"CHAR(1)\" />\n");
            osw.write("<FunctionType Descriptions=\"\" ID=\"1\" Name=\"Unknown\" SortKey=\"000\" />\n");
            osw.write("<FunctionType Descriptions=\"\" ID=\"2\" Name=\"FunctionType1\" SortKey=\"001\" />\n");
            osw.write("<MaintenanceLog Descriptions=\"Created\" Headder=\"0.0.1\" ID=\"0001\" SortKey=\"\" />\n");
            osw.write("</System>\n");
        }
        osw.flush();
        osw.close();
    }
    
    /**
     * システムノードの情報を取得します
     * @return System要素
     */
    public Element getSystemElement() {
        if (domDocument == null) {
            return null;
        }
        return (Element) domDocument.getElementsByTagName("System").item(0);
    }
    
    /**
     * DDL設定をDOMドキュメントから読み込みます
     */
    public void loadDdlConfig() {
        Element systemElement = getSystemElement();
        if (systemElement == null) {
            return;
        }
        
        if (!systemElement.getAttribute("DDLCommentMark").equals("")
            || !systemElement.getAttribute("DDLSectionMark").equals("")
            || !systemElement.getAttribute("DDLAdditionalParms").equals("")
            || !systemElement.getAttribute("DDLCommentWithAlias").equals("")
            || !systemElement.getAttribute("DDLIgnoreFK").equals("")
            || !systemElement.getAttribute("DDLConvertCamel").equals("")) {
            
            this.ddlCommentMark = systemElement.getAttribute("DDLCommentMark");
            this.ddlSectionMark = systemElement.getAttribute("DDLSectionMark");
            this.ddlAdditionalParms = systemElement.getAttribute("DDLAdditionalParms");
            
            String commentWithAlias = systemElement.getAttribute("DDLCommentWithAlias");
            if (commentWithAlias.equals("true")) {
                this.ddlFieldCommentType = "1";
            } else if (commentWithAlias.equals("false")) {
                this.ddlFieldCommentType = "0";
            } else {
                this.ddlFieldCommentType = commentWithAlias;
            }
            
            this.ignoreForeignKeyConstraints = systemElement.getAttribute("DDLIgnoreFK").equals("true");
            this.isToConvertCamel = !systemElement.getAttribute("DDLConvertCamel").equals("false");
            this.useTableNameAsTableNameInCreateTableStatement = !systemElement.getAttribute("UseTableNameToCreateTable").equals("false");
        } else {
            this.ddlCommentMark = "--";
            this.ddlSectionMark = ";";
            this.ddlAdditionalParms = "";
            this.ddlFieldCommentType = "1";
            this.ignoreForeignKeyConstraints = false;
            this.isToConvertCamel = true;
            this.useTableNameAsTableNameInCreateTableStatement = true;
        }
        
        // システム名の取得
        this.systemName = systemElement.getAttribute("Name");
    }
    
    /**
     * ドキュメントをリセットします
     */
    public void reset() {
        this.domDocument = null;
        this.domDocumentSaved = null;
        this.currentFileName = null;
        this.currentFileFolder = null;
        this.lastModifyTime = 0;
        this.systemName = null;
        this.synchFileMap.clear();
        this.referringFileNameList.clear();
        this.referringFileDocList.clear();
    }
}
