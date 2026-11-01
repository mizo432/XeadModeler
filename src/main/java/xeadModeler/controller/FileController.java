package xeadModeler.controller;

import xeadModeler.model.XeadDocumentModel;
import xeadModeler.service.FileService;
import xeadModeler.service.ChangeStateService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * ファイル操作コントローラー
 * ファイルの新規作成・開く・保存などのユーザー操作を処理します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class FileController {
    
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    
    private final FileService fileService;
    private final ChangeStateService changeStateService;
    private final JFrame parentFrame;
    private final String formatVersion;
    
    /**
     * FileControllerを作成します
     * @param fileService ファイルサービス
     * @param changeStateService 変更状態サービス
     * @param parentFrame 親フレーム
     * @param formatVersion フォーマットバージョン
     */
    public FileController(FileService fileService, ChangeStateService changeStateService, 
                          JFrame parentFrame, String formatVersion) {
        this.fileService = fileService;
        this.changeStateService = changeStateService;
        this.parentFrame = parentFrame;
        this.formatVersion = formatVersion;
    }
    
    /**
     * 新規ファイル作成アクション
     */
    public void newFile() {
        int rtn = 0;
        
        // 変更がある場合は保存確認
        if (changeStateService.isChanged()) {
            Object[] bts = {res.getString("S1103"), res.getString("S1104"), res.getString("S1100")};
            rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1106"),
                    res.getString("S1107"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, bts, bts[0]);
            if (rtn == 0) {
                saveFile(true);
            }
        }
        
        // キャンセルされた場合は処理終了
        if (rtn == 2) {
            return;
        }
        
        // ファイル名入力ダイアログ
        String name = showNewFileDialog();
        if (!name.equals("")) {
            // カーソル変更
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                // 新規ファイル作成
                boolean success = fileService.createNewFile(name, formatVersion);
                
                if (success) {
                    // タイトル更新
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + name + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, res.getString("S1149"));
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    /**
     * ファイルオープンアクション
     */
    public void openFile() {
        int rtn = 0;
        
        // 変更がある場合は保存確認
        if (changeStateService.isChanged()) {
            Object[] bts = {res.getString("S1114"), res.getString("S1115"), res.getString("S1100")};
            rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1117"),
                    res.getString("S1118"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, bts, bts[0]);
            if (rtn == 0) {
                saveFile(true);
            }
        }
        
        // キャンセルされた場合は処理終了
        if (rtn == 2) {
            return;
        }
        
        // ファイル選択ダイアログ
        String name = showOpenFileDialog();
        if (!name.equals("")) {
            // カーソル変更
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                // ファイルオープン
                boolean success = fileService.openFile(name);
                
                if (success) {
                    // タイトル更新
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + name + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, res.getString("S1149"));
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    /**
     * ファイル保存アクション
     * @param checkLastModifyTime 最終更新時刻をチェックするかどうか
     */
    public void saveFile(boolean checkLastModifyTime) {
        XeadDocumentModel docModel = fileService.getDocumentModel();
        String fileName = docModel.getCurrentFileName();
        
        if (fileName == null || fileName.equals("")) {
            // ファイル名がない場合は「名前を付けて保存」
            saveAsFile();
            return;
        }
        
        // カーソル変更
        parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        try {
            // 最終更新時刻チェック
            if (checkLastModifyTime) {
                java.io.File file = new java.io.File(fileName);
                if (docModel.getLastModifyTime() != file.lastModified()) {
                    Object[] bts = {res.getString("S1111"), res.getString("S1112"), res.getString("S1113")};
                    int rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1150"),
                            res.getString("S1102"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                            null, bts, bts[0]);
                    
                    // 「名前を付けて保存」を選択
                    if (rtn == 1) {
                        saveAsFile();
                        return;
                    }
                    // キャンセル
                    if (rtn == 2) {
                        return;
                    }
                }
            }
            
            // ファイル保存
            boolean success = fileService.saveFile(false);
            
            if (!success) {
                JOptionPane.showMessageDialog(parentFrame, res.getString("S1149"));
            }
        } finally {
            parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    /**
     * 名前を付けて保存アクション
     */
    public void saveAsFile() {
        String name = showSaveFileDialog();
        if (!name.equals("")) {
            // カーソル変更
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                // 名前を付けて保存
                boolean success = fileService.saveAsFile(name);
                
                if (success) {
                    // タイトル更新
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + name + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, res.getString("S1149"));
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    /**
     * 新規ファイルダイアログを表示します
     * @return ファイル名
     */
    private String showNewFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(res.getString("S788"));
        fileChooser.setApproveButtonText(res.getString("S1109"));
        fileChooser.setSelectedFile(new java.io.File("project1.xead"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        
        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            String name = fileChooser.getSelectedFile().getAbsolutePath();
            if (!name.toLowerCase().endsWith(".xead")) {
                name += ".xead";
            }
            return name;
        }
        return "";
    }
    
    /**
     * ファイルオープンダイアログを表示します
     * @return ファイル名
     */
    private String showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(res.getString("S1119"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
    
    /**
     * 保存ダイアログを表示します
     * @return ファイル名
     */
    private String showSaveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(res.getString("S788"));
        fileChooser.setApproveButtonText(res.getString("S1125"));
        
        XeadDocumentModel docModel = fileService.getDocumentModel();
        String currentFileName = docModel.getCurrentFileName();
        if (currentFileName != null && !currentFileName.equals("")) {
            fileChooser.setSelectedFile(new java.io.File(currentFileName));
        }
        
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XEAD File (*.xead)", "xead"));
        
        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            String name = fileChooser.getSelectedFile().getAbsolutePath();
            if (!name.toLowerCase().endsWith(".xead")) {
                name += ".xead";
            }
            return name;
        }
        return "";
    }
}
