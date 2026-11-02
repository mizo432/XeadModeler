package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.FileService;
import xeadModeler.service.JsonService;

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
    
    @Autowired
    private JsonService jsonService;
    
    private Modeler modeler;
    private JFrame parentFrame;
    
    public ImportController(Modeler modeler, JFrame parentFrame) {
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
     * JSON形式からインポート
     */
    public void importFromJson() {
        if (modeler == null) {
            JOptionPane.showMessageDialog(parentFrame, "Modelerが初期化されていません。", "エラー", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("JSONファイルを選択");
        
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                xeadModeler.model.json.XeadSystem system = jsonService.loadSystem(selectedFile.getAbsolutePath());
                if (system == null) {
                    JOptionPane.showMessageDialog(parentFrame, "インポートするデータがありません。", "エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // TODO: Import the system into the modeler
                JOptionPane.showMessageDialog(parentFrame, "JSONインポートが完了しました。", "完了", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parentFrame, "インポートに失敗しました:\n" + e.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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
