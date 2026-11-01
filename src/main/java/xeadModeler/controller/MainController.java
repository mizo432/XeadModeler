package xeadModeler.controller;

import xeadModeler.Application;
import xeadModeler.model.XeadDocumentModel;
import xeadModeler.service.*;
import xeadModeler.view.DialogAbout;
import xeadModeler.view.MenuBarView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * メインコントローラー
 * メニュー操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class MainController implements MenuBarView.MenuBarListener {
    
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ChangeStateService changeStateService;
    
    @Autowired
    private XeadUndoManager xeadUndoManager;
    
    @Autowired
    private DrawingService drawingService;
    
    @Autowired
    private TreeService treeService;
    
    @Autowired
    private TableService tableService;
    
    @Autowired
    private DialogService dialogService;
    
    private JFrame parentFrame;
    private Application application;
    private MenuBarView menuBarView;
    private String formatVersion;
    
    public MainController(JFrame parentFrame, Application application, MenuBarView menuBarView) {
        this.parentFrame = parentFrame;
        this.application = application;
        this.menuBarView = menuBarView;
        this.formatVersion = DialogAbout.FORMAT_VERSION;
    }
    
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    
    public void setChangeStateService(ChangeStateService changeStateService) {
        this.changeStateService = changeStateService;
    }
    
    public void setXeadUndoManager(XeadUndoManager xeadUndoManager) {
        this.xeadUndoManager = xeadUndoManager;
    }
    
    // === Fileメニュー ===
    
    @Override
    public void onNewFile() {
        int rtn = 0;
        
        // 変更がある場合は保存確認
        if (changeStateService.isChanged()) {
            Object[] bts = {res.getString("S1103"), res.getString("S1104"), res.getString("S1100")};
            rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1106"),
                    res.getString("S1107"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, bts, bts[0]);
            if (rtn == 0) {
                onSave();
            }
        }
        
        if (rtn == 2) {
            return;
        }
        
        // ファイル名入力ダイアログ
        String name = JOptionPane.showInputDialog(parentFrame, res.getString("S1101"), res.getString("S1102"), JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.equals("")) {
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                boolean success = fileService.createNewFile(name, formatVersion);
                
                if (success) {
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + name + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                    changeStateService.reset();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, res.getString("S1149"));
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    @Override
    public void onOpenFile() {
        int rtn = 0;
        
        // 変更がある場合は保存確認
        if (changeStateService.isChanged()) {
            Object[] bts = {res.getString("S1114"), res.getString("S1115"), res.getString("S1100")};
            rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1117"),
                    res.getString("S1118"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, bts, bts[0]);
            if (rtn == 0) {
                onSave();
            }
        }
        
        if (rtn == 2) {
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XEAD File", "xead"));
        
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                boolean success = fileService.openFile(fileName);
                
                if (success) {
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + docModel.getCurrentFileName() + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                    changeStateService.reset();
                    // TODO: UIを更新（ツリー、コンテンツペインなど）
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    @Override
    public void onExit() {
        int rtn = 0;
        
        if (changeStateService.isChanged()) {
            Object[] bts = {res.getString("S1103"), res.getString("S1104"), res.getString("S1100")};
            rtn = JOptionPane.showOptionDialog(parentFrame, res.getString("S1106"),
                    res.getString("S1107"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, bts, bts[0]);
            if (rtn == 0) {
                onSave();
            }
        }
        
        if (rtn != 2) {
            System.exit(0);
        }
    }
    
    @Override
    public void onSave() {
        parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        try {
            boolean success = fileService.saveFile(true);
            if (success) {
                changeStateService.reset();
            }
        } finally {
            parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    @Override
    public void onSaveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XEAD File", "xead"));
        
        int result = fileChooser.showSaveDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            if (!fileName.toLowerCase().endsWith(".xead")) {
                fileName += ".xead";
            }
            
            parentFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
            try {
                boolean success = fileService.saveAsFile(fileName);
                if (success) {
                    XeadDocumentModel docModel = fileService.getDocumentModel();
                    String title = "XeadModeler - [" + docModel.getCurrentFileName() + "] - " + docModel.getSystemName();
                    parentFrame.setTitle(title);
                    changeStateService.reset();
                }
            } finally {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    @Override
    public void onPrintFormat() {
        // TODO: 印刷フォーマット設定
    }
    
    @Override
    public void onDiff() {
        // TODO: ファイル比較
    }
    
    // === Importメニュー ===
    
    @Override
    public void onImportXEAD() {
        // TODO: XEADインポート
    }
    
    @Override
    public void onImportXEAF() {
        // TODO: XEAFインポート
    }
    
    @Override
    public void onImportSQL() {
        // TODO: SQLインポート
    }
    
    // === Editメニュー ===
    
    @Override
    public void onUndo() {
        xeadUndoManager.undo();
        updateUndoRedoState();
        // TODO: UIを更新
        changeStateService.setChanged(true);
    }
    
    @Override
    public void onRedo() {
        xeadUndoManager.redo();
        updateUndoRedoState();
        // TODO: UIを更新
        changeStateService.setChanged(true);
    }
    
    private void updateUndoRedoState() {
        if (menuBarView != null) {
            menuBarView.getJMenuItemEditUndo().setEnabled(xeadUndoManager.canUndo());
            menuBarView.getJMenuItemEditRedo().setEnabled(xeadUndoManager.canRedo());
        }
    }
    
    // === Scanメニュー ===
    
    @Override
    public void onScanString() {
        // TODO: スキャン
    }
    
    @Override
    public void onSetBookmark(int number) {
        // TODO: ブックマーク設定
    }
    
    @Override
    public void onJumpBookmark(int number) {
        // TODO: ブックマークジャンプ
    }
    
    // === Toolメニュー ===
    
    @Override
    public void onTaskList() {
        // TODO: タスクリスト
    }
    
    @Override
    public void onTableList() {
        // TODO: テーブルリスト
    }
    
    @Override
    public void onDDLList() {
        // TODO: DDLリスト
    }
    
    @Override
    public void onFieldList() {
        // TODO: フィールドリスト
    }
    
    @Override
    public void onFunctionList() {
        // TODO: 機能リスト
    }
    
    @Override
    public void onNotedItemList() {
        // TODO: 備考リスト
    }
    
    @Override
    public void onMatrixList() {
        dialogService.showMatrixListDialog(parentFrame);
    }
    
    @Override
    public void onCreateTableStatement() {
        dialogService.showCreateTableStatementDialog(parentFrame);
    }
    
    @Override
    public void onDocuments() {
        dialogService.showDocumentsDialog(parentFrame);
    }
    
    @Override
    public void onReorganizeDataTypes() {
        dialogService.showReorganizeDataTypesDialog(parentFrame);
    }
    
    @Override
    public void onJumpToURLPage() {
        // TODO: URLジャンプ
    }
    
    // === Helpメニュー ===
    
    @Override
    public void onHelp() {
        // TODO: ヘルプ
    }
    
    @Override
    public void onAbout() {
        // TODO: DialogAboutのコンストラクタをJFrameに変更するまでコメントアウト
        // DialogAbout dialogAbout = new DialogAbout(parentFrame);
        // dialogAbout.setLocationRelativeTo(parentFrame);
        // dialogAbout.setVisible(true);
    }
    
    // === Menu Listeners ===
    
    @Override
    public void onMenuFileOpened() {
        // TODO: Fileメニュー開いた時の処理
    }
    
    @Override
    public void onMenuEditOpened() {
        updateUndoRedoState();
    }
    
    @Override
    public void onMenuToolOpened() {
        // TODO: Toolメニュー開いた時の処理
    }
    
    @Override
    public void onMenuScanJumpBookmarkOpened() {
        // TODO: ブックマークジャンプメニュー開いた時の処理
    }
}
