package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.FileService;
import xeadModeler.service.JsonService;

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
    
    @Autowired
    private JsonService jsonService;
    
    private Modeler modeler;
    private JFrame parentFrame;
    
    public ExportController(Modeler modeler, JFrame parentFrame) {
        this.modeler = modeler;
        this.parentFrame = parentFrame;
    }
    
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
    
    /**
     * JSON形式でエクスポート
     */
    public void exportToJson() {
        if (modeler == null) {
            JOptionPane.showMessageDialog(parentFrame, "Modelerが初期化されていません。", "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("JSONとして保存");
        fileChooser.setSelectedFile(new File("system.json"));
        
        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                xeadModeler.model.json.XeadSystem system = modeler.getSystemElement();
                if (system == null) {
                    JOptionPane.showMessageDialog(parentFrame, "エクスポートするデータがありません。", "エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                jsonService.saveSystem(system, selectedFile.getAbsolutePath());
                JOptionPane.showMessageDialog(parentFrame, "JSONエクスポートが完了しました。", "完了", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "エクスポートに失敗しました:\n" + e.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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
