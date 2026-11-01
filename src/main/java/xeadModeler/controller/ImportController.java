package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;

/**
 * インポートコントローラー
 * データのインポート操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class ImportController {
    
    @Autowired
    private FileService fileService;
    
    private Modeler modeler;
    private JFrame parentFrame;
    
    public ImportController(Modeler modeler, JFrame parentFrame) {
        this.modeler = modeler;
        this.parentFrame = parentFrame;
    }
    
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    
    /**
     * JSON形式からインポート
     */
    public void importFromJson() {
        // TODO: JSONインポート処理を実装
    }
    
    /**
     * XML形式からインポート
     */
    public void importFromXml() {
        // TODO: XMLインポート処理を実装
    }
    
    /**
     * CSV形式からインポート
     */
    public void importFromCsv() {
        // TODO: CSVインポート処理を実装
    }
    
    /**
     * Excel形式からインポート
     */
    public void importFromExcel() {
        // TODO: Excelインポート処理を実装
    }
    
    /**
     * データベースからインポート
     */
    public void importFromDatabase() {
        // TODO: データベースインポート処理を実装
    }
    
    /**
     * 別のXeadModelerファイルからインポート
     */
    public void importFromXeadModeler() {
        // TODO: XeadModelerファイルインポート処理を実装
    }
}
