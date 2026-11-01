package xeadModeler.view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * メニューバービュー
 * メインメニューのUIを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class MenuBarView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    
    // メニュー
    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu jMenuFile = new JMenu();
    private JMenu jMenuEdit = new JMenu();
    private JMenu jMenuScan = new JMenu();
    private JMenu jMenuTool = new JMenu();
    private JMenu jMenuHelp = new JMenu();
    
    // Fileメニュー
    private JMenuItem jMenuItemFileNew = new JMenuItem();
    private JMenuItem jMenuItemFileOpen = new JMenuItem();
    private JMenuItem jMenuItemFileExit = new JMenuItem();
    private JMenuItem jMenuItemFileSave = new JMenuItem();
    private JMenuItem jMenuItemFileSaveAs = new JMenuItem();
    private JMenuItem jMenuItemFilePrintFormat = new JMenuItem();
    private JMenuItem jMenuItemFileDiff = new JMenuItem();
    private JMenu jMenuImport = new JMenu();
    private JMenuItem jMenuItemImportXEAD = new JMenuItem();
    private JMenuItem jMenuItemImportXEAF = new JMenuItem();
    private JMenuItem jMenuItemImportSQL = new JMenuItem();
    
    // Editメニュー
    private JMenuItem jMenuItemEditUndo = new JMenuItem();
    private JMenuItem jMenuItemEditRedo = new JMenuItem();
    
    // Scanメニュー
    private JMenuItem jMenuItemScanString = new JMenuItem();
    private JMenu jMenuScanSetBookmark = new JMenu();
    private JMenuItem jMenuItemScanSetBookmark1 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark2 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark3 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark4 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark5 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark6 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark7 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark8 = new JMenuItem();
    private JMenuItem jMenuItemScanSetBookmark9 = new JMenuItem();
    private JMenu jMenuScanJumpBookmark = new JMenu();
    private JMenuItem jMenuItemScanJumpBookmark1 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark2 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark3 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark4 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark5 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark6 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark7 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark8 = new JMenuItem();
    private JMenuItem jMenuItemScanJumpBookmark9 = new JMenuItem();
    
    // Toolメニュー
    private JMenuItem jMenuItemToolTaskList = new JMenuItem();
    private JMenuItem jMenuItemToolTableList = new JMenuItem();
    private JMenuItem jMenuItemToolDDLList = new JMenuItem();
    private JMenuItem jMenuItemToolFieldList = new JMenuItem();
    private JMenuItem jMenuItemToolFunctionList = new JMenuItem();
    private JMenuItem jMenuItemToolNotedItemList = new JMenuItem();
    private JMenuItem jMenuItemToolMatrixList = new JMenuItem();
    private JMenuItem jMenuItemToolCreateTableStatement = new JMenuItem();
    private JMenuItem jMenuItemToolDocuments = new JMenuItem();
    private JMenuItem jMenuItemToolReorganizeDataTypes = new JMenuItem();
    private JMenuItem jMenuItemToolJumpToURLPage = new JMenuItem();
    
    // Helpメニュー
    private JMenuItem jMenuItemHelpHelp = new JMenuItem();
    private JMenuItem jMenuItemHelpAbout = new JMenuItem();
    
    /**
     * メニュー操作リスナーインターフェース
     */
    public interface MenuBarListener {
        void onNewFile();
        void onOpenFile();
        void onExit();
        void onSave();
        void onSaveAs();
        void onPrintFormat();
        void onDiff();
        void onImportXEAD();
        void onImportXEAF();
        void onImportSQL();
        void onUndo();
        void onRedo();
        void onScanString();
        void onSetBookmark(int number);
        void onJumpBookmark(int number);
        void onTaskList();
        void onTableList();
        void onDDLList();
        void onFieldList();
        void onFunctionList();
        void onNotedItemList();
        void onMatrixList();
        void onCreateTableStatement();
        void onDocuments();
        void onReorganizeDataTypes();
        void onJumpToURLPage();
        void onHelp();
        void onAbout();
        void onMenuFileOpened();
        void onMenuEditOpened();
        void onMenuToolOpened();
        void onMenuScanJumpBookmarkOpened();
    }
    
    private MenuBarListener menuBarListener;
    
    public MenuBarView(MenuBarListener listener) {
        this.menuBarListener = listener;
        initComponents();
    }
    
    private void initComponents() {
        // Fileメニュー
        jMenuFile.setText(res.getString("S50"));
        jMenuFile.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (menuBarListener != null) menuBarListener.onMenuFileOpened();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        jMenuItemFileNew.setText(res.getString("S51"));
        jMenuItemFileNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.ActionEvent.CTRL_MASK));
        jMenuItemFileNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onNewFile();
            }
        });
        
        jMenuItemFileOpen.setText(res.getString("S52"));
        jMenuItemFileOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.ActionEvent.CTRL_MASK));
        jMenuItemFileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onOpenFile();
            }
        });
        
        jMenuItemFileExit.setText(res.getString("S53"));
        jMenuItemFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onExit();
            }
        });
        
        jMenuItemFilePrintFormat.setText(res.getString("S54"));
        jMenuItemFilePrintFormat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onPrintFormat();
            }
        });
        
        jMenuItemFileSave.setText(res.getString("S56"));
        jMenuItemFileSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.ActionEvent.CTRL_MASK));
        jMenuItemFileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onSave();
            }
        });
        
        jMenuItemFileSaveAs.setText(res.getString("S57"));
        jMenuItemFileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onSaveAs();
            }
        });
        
        jMenuItemFileDiff.setText(res.getString("S120"));
        jMenuItemFileDiff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (menuBarListener != null) menuBarListener.onDiff();
            }
        });
        
        // Importサブメニュー
        jMenuImport.setText(res.getString("S64"));
        jMenuItemImportXEAD.setText(res.getString("S65"));
        jMenuItemImportXEAD.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onImportXEAD();
        });
        jMenuItemImportXEAF.setText(res.getString("S7"));
        jMenuItemImportXEAF.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onImportXEAF();
        });
        jMenuItemImportSQL.setText(res.getString("S66"));
        jMenuItemImportSQL.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onImportSQL();
        });
        
        jMenuImport.add(jMenuItemImportXEAD);
        jMenuImport.add(jMenuItemImportXEAF);
        jMenuImport.add(jMenuItemImportSQL);
        
        // Fileメニューに追加
        jMenuFile.add(jMenuItemFileNew);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuItemFileOpen);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuImport);
        jMenuFile.add(jMenuItemFileDiff);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuItemFileSave);
        jMenuFile.add(jMenuItemFileSaveAs);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuItemFilePrintFormat);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuItemFileExit);
        
        // Editメニュー
        jMenuEdit.setText(res.getString("S58"));
        jMenuEdit.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (menuBarListener != null) menuBarListener.onMenuEditOpened();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        jMenuItemEditUndo.setText(res.getString("S59"));
        jMenuItemEditUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        jMenuItemEditUndo.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onUndo();
        });
        jMenuItemEditUndo.setEnabled(false);
        
        jMenuItemEditRedo.setText(res.getString("S60"));
        jMenuItemEditRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        jMenuItemEditRedo.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onRedo();
        });
        jMenuItemEditRedo.setEnabled(false);
        
        jMenuEdit.add(jMenuItemEditUndo);
        jMenuEdit.add(jMenuItemEditRedo);
        
        // Scanメニュー
        jMenuScan.setText(res.getString("S61"));
        jMenuItemScanString.setText(res.getString("S62"));
        jMenuItemScanString.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        jMenuItemScanString.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onScanString();
        });
        
        jMenuScanSetBookmark.setText(res.getString("S63"));
        setupBookmarkMenu();
        
        jMenuScanJumpBookmark.setText(res.getString("S73"));
        jMenuScanJumpBookmark.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (menuBarListener != null) menuBarListener.onMenuScanJumpBookmarkOpened();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        setupJumpBookmarkMenu();
        
        jMenuScan.add(jMenuItemScanString);
        jMenuScan.addSeparator();
        jMenuScan.add(jMenuScanSetBookmark);
        jMenuScan.add(jMenuScanJumpBookmark);
        
        // Toolメニュー
        jMenuTool.setText(res.getString("S83"));
        jMenuTool.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (menuBarListener != null) menuBarListener.onMenuToolOpened();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}
            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        jMenuItemToolTaskList.setText(res.getString("S84"));
        jMenuItemToolTaskList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onTaskList();
        });
        
        jMenuItemToolTableList.setText(res.getString("S85"));
        jMenuItemToolTableList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onTableList();
        });
        
        jMenuItemToolDDLList.setText(res.getString("S93"));
        jMenuItemToolDDLList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onDDLList();
        });
        
        jMenuItemToolFieldList.setText(res.getString("S99"));
        jMenuItemToolFieldList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onFieldList();
        });
        
        jMenuItemToolFunctionList.setText(res.getString("S86"));
        jMenuItemToolFunctionList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onFunctionList();
        });
        
        jMenuItemToolNotedItemList.setText(res.getString("S122"));
        jMenuItemToolNotedItemList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onNotedItemList();
        });
        
        jMenuItemToolMatrixList.setText(res.getString("S74"));
        jMenuItemToolMatrixList.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onMatrixList();
        });
        
        jMenuItemToolCreateTableStatement.setText(res.getString("S87"));
        jMenuItemToolCreateTableStatement.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onCreateTableStatement();
        });
        
        jMenuItemToolDocuments.setText(res.getString("S82"));
        jMenuItemToolDocuments.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onDocuments();
        });
        
        jMenuItemToolReorganizeDataTypes.setText(res.getString("S88"));
        jMenuItemToolReorganizeDataTypes.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onReorganizeDataTypes();
        });
        
        jMenuItemToolJumpToURLPage.setText(res.getString("S89"));
        jMenuItemToolJumpToURLPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        jMenuItemToolJumpToURLPage.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpToURLPage();
        });
        
        jMenuTool.add(jMenuItemToolTaskList);
        jMenuTool.add(jMenuItemToolTableList);
        jMenuTool.add(jMenuItemToolDDLList);
        jMenuTool.add(jMenuItemToolFieldList);
        jMenuTool.add(jMenuItemToolFunctionList);
        jMenuTool.addSeparator();
        jMenuTool.add(jMenuItemToolNotedItemList);
        jMenuTool.add(jMenuItemToolMatrixList);
        jMenuTool.add(jMenuItemToolDocuments);
        jMenuTool.addSeparator();
        jMenuTool.add(jMenuItemToolCreateTableStatement);
        jMenuTool.add(jMenuItemToolReorganizeDataTypes);
        jMenuTool.addSeparator();
        jMenuTool.add(jMenuItemToolJumpToURLPage);
        
        // Helpメニュー
        jMenuHelp.setText(res.getString("S90"));
        jMenuItemHelpHelp.setText(res.getString("S91"));
        jMenuItemHelpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        jMenuItemHelpHelp.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onHelp();
        });
        jMenuItemHelpAbout.setText(res.getString("S92"));
        jMenuItemHelpAbout.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onAbout();
        });
        
        jMenuHelp.add(jMenuItemHelpAbout);
        jMenuHelp.add(jMenuItemHelpHelp);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuItemHelpAbout);
        
        // メニューバーに追加
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuEdit);
        jMenuBar.add(jMenuScan);
        jMenuBar.add(jMenuTool);
        jMenuBar.add(jMenuHelp);
    }
    
    private void setupBookmarkMenu() {
        jMenuItemScanSetBookmark1.setText("[1]");
        jMenuItemScanSetBookmark1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark1.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(1);
        });
        
        jMenuItemScanSetBookmark2.setText("[2]");
        jMenuItemScanSetBookmark2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark2.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(2);
        });
        
        jMenuItemScanSetBookmark3.setText("[3]");
        jMenuItemScanSetBookmark3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark3.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(3);
        });
        
        jMenuItemScanSetBookmark4.setText("[4]");
        jMenuItemScanSetBookmark4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark4.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(4);
        });
        
        jMenuItemScanSetBookmark5.setText("[5]");
        jMenuItemScanSetBookmark5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark5.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(5);
        });
        
        jMenuItemScanSetBookmark6.setText("[6]");
        jMenuItemScanSetBookmark6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark6.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(6);
        });
        
        jMenuItemScanSetBookmark7.setText("[7]");
        jMenuItemScanSetBookmark7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark7.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(7);
        });
        
        jMenuItemScanSetBookmark8.setText("[8]");
        jMenuItemScanSetBookmark8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark8.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(8);
        });
        
        jMenuItemScanSetBookmark9.setText("[9]");
        jMenuItemScanSetBookmark9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        jMenuItemScanSetBookmark9.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onSetBookmark(9);
        });
        
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark1);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark2);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark3);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark4);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark5);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark6);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark7);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark8);
        jMenuScanSetBookmark.add(jMenuItemScanSetBookmark9);
    }
    
    private void setupJumpBookmarkMenu() {
        jMenuItemScanJumpBookmark1.setText("[1]");
        jMenuItemScanJumpBookmark1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark1.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(1);
        });
        
        jMenuItemScanJumpBookmark2.setText("[2]");
        jMenuItemScanJumpBookmark2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark2.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(2);
        });
        
        jMenuItemScanJumpBookmark3.setText("[3]");
        jMenuItemScanJumpBookmark3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark3.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(3);
        });
        
        jMenuItemScanJumpBookmark4.setText("[4]");
        jMenuItemScanJumpBookmark4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark4.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(4);
        });
        
        jMenuItemScanJumpBookmark5.setText("[5]");
        jMenuItemScanJumpBookmark5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark5.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(5);
        });
        
        jMenuItemScanJumpBookmark6.setText("[6]");
        jMenuItemScanJumpBookmark6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark6.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(6);
        });
        
        jMenuItemScanJumpBookmark7.setText("[7]");
        jMenuItemScanJumpBookmark7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark7.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(7);
        });
        
        jMenuItemScanJumpBookmark8.setText("[8]");
        jMenuItemScanJumpBookmark8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark8.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(8);
        });
        
        jMenuItemScanJumpBookmark9.setText("[9]");
        jMenuItemScanJumpBookmark9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.CTRL_MASK));
        jMenuItemScanJumpBookmark9.addActionListener(e -> {
            if (menuBarListener != null) menuBarListener.onJumpBookmark(9);
        });
        
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark1);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark2);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark3);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark4);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark5);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark6);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark7);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark8);
        jMenuScanJumpBookmark.add(jMenuItemScanJumpBookmark9);
    }
    
    public JMenuBar getMenuBar() {
        return jMenuBar;
    }
    
    // アクセサー（メニュー項目の有効/無効制御用）
    public JMenuItem getJMenuItemFileNew() { return jMenuItemFileNew; }
    public JMenuItem getJMenuItemFileOpen() { return jMenuItemFileOpen; }
    public JMenuItem getJMenuItemFileSave() { return jMenuItemFileSave; }
    public JMenuItem getJMenuItemFileSaveAs() { return jMenuItemFileSaveAs; }
    public JMenuItem getJMenuItemEditUndo() { return jMenuItemEditUndo; }
    public JMenuItem getJMenuItemEditRedo() { return jMenuItemEditRedo; }
    public JMenuItem getJMenuItemFileDiff() { return jMenuItemFileDiff; }
}
