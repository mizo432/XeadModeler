package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;

/**
 * エクスポートコントローラー
 * データのエクスポート操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class ExportController {
    
    @Autowired
    private FileService fileService;
    
    private Modeler modeler;
    private JFrame parentFrame;
    
    public ExportController(Modeler modeler, JFrame parentFrame) {
        this.modeler = modeler;
        this.parentFrame = parentFrame;
    }
    
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    
    /**
     * JSON形式でエクスポート
     */
    public void exportToJson() {
        // TODO: JSONエクスポート処理を実装
    }
    
    /**
     * XML形式でエクスポート
     */
    public void exportToXml() {
        // TODO: XMLエクスポート処理を実装
    }
    
    /**
     * CSV形式でエクスポート
     */
    public void exportToCsv() {
        // TODO: CSVエクスポート処理を実装
    }
    
    /**
     * Excel形式でエクスポート
     */
    public void exportToExcel() {
        // TODO: Excelエクスポート処理を実装
    }
    
    /**
     * PDF形式でエクスポート
     */
    public void exportToPdf() {
        // TODO: PDFエクスポート処理を実装
    }
    
    /**
     * HTML形式でエクスポート
     */
    public void exportToHtml() {
        // TODO: HTMLエクスポート処理を実装
    }
    
    /**
     * 画像形式でエクスポート
     */
    public void exportToImage() {
        // TODO: 画像エクスポート処理を実装
    }
    
    /**
     * データベースにエクスポート
     */
    public void exportToDatabase() {
        // TODO: データベースエクスポート処理を実装
    }
}
