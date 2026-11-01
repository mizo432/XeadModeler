package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import java.awt.*;

/**
 * コンテンツペインビュー
 * ツリーノードに応じて切り替わるコンテンツ表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class ContentView {
    
    // コンテンツペイン
    private JPanel jPanelContentsPane = new JPanel();
    private CardLayout cardLayoutContentsPane = new CardLayout();
    
    // 各ノードタイプのパネル
    public JPanel jPanelSystem;
    public JPanel jPanelSubjectAreaList;
    public JPanel jPanelSubjectArea;
    public JPanel jPanelRoleList;
    public JPanel jPanelRole;
    public JPanel jPanelTask;
    public JPanel jPanelSubsystemList;
    public JPanel jPanelSubsystem;
    public JPanel jPanelTableList;
    public JPanel jPanelTable;
    public JPanel jPanelTableFieldList;
    public JPanel jPanelTableField;
    public JPanel jPanelTableKeyList;
    public JPanel jPanelTableKey;
    public JPanel jPanelFunctionList;
    public JPanel jPanelFunction;
    public JPanel jPanelIOPanel;
    public JPanel jPanelIOSpool;
    public JPanel jPanelIOTable;
    public JPanel jPanelIOWebPage;
    
    // リストタイプのViewクラス
    private SubjectAreaListView subjectAreaListView;
    private RoleListView roleListView;
    private SubsystemListView subsystemListView;
    private FunctionListView functionListView;
    private TableFieldListView tableFieldListView;
    private TableKeyListView tableKeyListView;
    
    // 非リストタイプのViewクラス
    private SystemView systemView;
    private SubjectAreaView subjectAreaView;
    private RoleView roleView;
    private TaskView taskView;
    private SubsystemView subsystemView;
    private TableListView tableListView;
    private TableView tableView;
    private TableFieldView tableFieldView;
    private TableKeyView tableKeyView;
    private FunctionView functionView;
    private IOPanelView ioPanelView;
    private IOSpoolView ioSpoolView;
    private IOTableView ioTableView;
    private IOWebPageView ioWebPageView;
    
    private Modeler modeler;
    
    /**
     * コンテンツ操作リスナーインターフェース
     */
    public interface ContentListener {
        void onContentPanelChanged(String panelName);
    }
    
    private ContentListener contentListener;
    
    public ContentView(Modeler modeler, ContentListener listener) {
        this.modeler = modeler;
        this.contentListener = listener;
        initComponents();
    }
    
    private void initComponents() {
        jPanelContentsPane.setLayout(cardLayoutContentsPane);
        
        // 各パネルの初期化
        jPanelSystem = new JPanel();
        jPanelSubjectArea = new JPanel();
        jPanelRole = new JPanel();
        jPanelTask = new JPanel();
        jPanelSubsystem = new JPanel();
        jPanelTableList = new JPanel();
        jPanelTable = new JPanel();
        jPanelTableField = new JPanel();
        jPanelTableKey = new JPanel();
        jPanelFunction = new JPanel();
        jPanelIOPanel = new JPanel();
        jPanelIOSpool = new JPanel();
        jPanelIOTable = new JPanel();
        jPanelIOWebPage = new JPanel();
        
        // リストタイプのViewクラスを使用
        subjectAreaListView = new SubjectAreaListView();
        jPanelSubjectAreaList = subjectAreaListView.getPanel();
        
        roleListView = new RoleListView();
        jPanelRoleList = roleListView.getPanel();
        
        subsystemListView = new SubsystemListView();
        jPanelSubsystemList = subsystemListView.getPanel();
        
        functionListView = new FunctionListView();
        jPanelFunctionList = functionListView.getPanel();
        
        tableFieldListView = new TableFieldListView();
        jPanelTableFieldList = tableFieldListView.getPanel();
        
        tableKeyListView = new TableKeyListView();
        jPanelTableKeyList = tableKeyListView.getPanel();
        
        // 非リストタイプのViewクラスを使用
        systemView = new SystemView(modeler, null);
        jPanelSystem = systemView.getPanel();
        
        subjectAreaView = new SubjectAreaView();
        jPanelSubjectArea = subjectAreaView.getPanel();
        
        roleView = new RoleView();
        jPanelRole = roleView.getPanel();
        
        taskView = new TaskView();
        jPanelTask = taskView.getPanel();
        
        subsystemView = new SubsystemView();
        jPanelSubsystem = subsystemView.getPanel();
        
        tableListView = new TableListView(modeler, null);
        jPanelTableList = tableListView.getPanel();
        
        tableView = new TableView(modeler, null);
        jPanelTable = tableView.getPanel();
        
        tableFieldView = new TableFieldView(modeler, null);
        jPanelTableField = tableFieldView.getPanel();
        
        tableKeyView = new TableKeyView(modeler, null);
        jPanelTableKey = tableKeyView.getPanel();
        
        functionView = new FunctionView(modeler, null);
        jPanelFunction = functionView.getPanel();
        
        ioPanelView = new IOPanelView(modeler, null);
        jPanelIOPanel = ioPanelView.getPanel();
        
        ioSpoolView = new IOSpoolView(modeler, null);
        jPanelIOSpool = ioSpoolView.getPanel();
        
        ioTableView = new IOTableView(modeler, null);
        jPanelIOTable = ioTableView.getPanel();
        
        ioWebPageView = new IOWebPageView(modeler, null);
        jPanelIOWebPage = ioWebPageView.getPanel();
        
        // パネルをコンテンツペインに追加
        jPanelContentsPane.add(jPanelSystem, "jPanelSystem");
        jPanelContentsPane.add(jPanelSubjectAreaList, "jPanelSubjectAreaList");
        jPanelContentsPane.add(jPanelSubjectArea, "jPanelSubjectArea");
        jPanelContentsPane.add(jPanelRoleList, "jPanelRoleList");
        jPanelContentsPane.add(jPanelRole, "jPanelRole");
        jPanelContentsPane.add(jPanelTask, "jPanelTask");
        jPanelContentsPane.add(jPanelSubsystemList, "jPanelSubsystemList");
        jPanelContentsPane.add(jPanelSubsystem, "jPanelSubsystem");
        jPanelContentsPane.add(jPanelTableList, "jPanelTableList");
        jPanelContentsPane.add(jPanelTable, "jPanelTable");
        jPanelContentsPane.add(jPanelTableFieldList, "jPanelTableFieldList");
        jPanelContentsPane.add(jPanelTableField, "jPanelTableField");
        jPanelContentsPane.add(jPanelTableKeyList, "jPanelTableKeyList");
        jPanelContentsPane.add(jPanelTableKey, "jPanelTableKey");
        jPanelContentsPane.add(jPanelFunctionList, "jPanelFunctionList");
        jPanelContentsPane.add(jPanelFunction, "jPanelFunction");
        jPanelContentsPane.add(jPanelIOPanel, "jPanelIOPanel");
        jPanelContentsPane.add(jPanelIOSpool, "jPanelIOSpool");
        jPanelContentsPane.add(jPanelIOTable, "jPanelIOTable");
        jPanelContentsPane.add(jPanelIOWebPage, "jPanelIOWebPage");
    }
    
    /**
     * 主題領域リストのViewを取得します。
     */
    public SubjectAreaListView getSubjectAreaListView() {
        return subjectAreaListView;
    }
    
    /**
     * ロールリストのViewを取得します。
     */
    public RoleListView getRoleListView() {
        return roleListView;
    }
    
    /**
     * サブシステムリストのViewを取得します。
     */
    public SubsystemListView getSubsystemListView() {
        return subsystemListView;
    }
    
    /**
     * 機能リストのViewを取得します。
     */
    public FunctionListView getFunctionListView() {
        return functionListView;
    }
    
    /**
     * テーブルフィールドリストのViewを取得します。
     */
    public TableFieldListView getTableFieldListView() {
        return tableFieldListView;
    }
    
    /**
     * テーブルキーリストのViewを取得します。
     */
    public TableKeyListView getTableKeyListView() {
        return tableKeyListView;
    }
    
    /**
     * システムのViewを取得します。
     */
    public SystemView getSystemView() {
        return systemView;
    }
    
    /**
     * 主題領域のViewを取得します。
     */
    public SubjectAreaView getSubjectAreaView() {
        return subjectAreaView;
    }
    
    /**
     * ロールのViewを取得します。
     */
    public RoleView getRoleView() {
        return roleView;
    }
    
    /**
     * タスクのViewを取得します。
     */
    public TaskView getTaskView() {
        return taskView;
    }
    
    /**
     * サブシステムのViewを取得します。
     */
    public SubsystemView getSubsystemView() {
        return subsystemView;
    }
    
    /**
     * テーブルリストのViewを取得します。
     */
    public TableListView getTableListView() {
        return tableListView;
    }
    
    /**
     * テーブルのViewを取得します。
     */
    public TableView getTableView() {
        return tableView;
    }
    
    /**
     * テーブルフィールドのViewを取得します。
     */
    public TableFieldView getTableFieldView() {
        return tableFieldView;
    }
    
    /**
     * テーブルキーのViewを取得します。
     */
    public TableKeyView getTableKeyView() {
        return tableKeyView;
    }
    
    /**
     * 機能のViewを取得します。
     */
    public FunctionView getFunctionView() {
        return functionView;
    }
    
    /**
     * IOパネルのViewを取得します。
     */
    public IOPanelView getIOPanelView() {
        return ioPanelView;
    }
    
    /**
     * IOプールのViewを取得します。
     */
    public IOSpoolView getIOSpoolView() {
        return ioSpoolView;
    }
    
    /**
     * IOテーブルのViewを取得します。
     */
    public IOTableView getIOTableView() {
        return ioTableView;
    }
    
    /**
     * IOウェブページのViewを取得します。
     */
    public IOWebPageView getIOWebPageView() {
        return ioWebPageView;
    }
    
    public JPanel getJPanelContentsPane() {
        return jPanelContentsPane;
    }
    
    /**
     * 指定されたパネル名を表示します。
     * @param panelName 表示するパネル名
     */
    public void showPanel(String panelName) {
        cardLayoutContentsPane.show(jPanelContentsPane, panelName);
        if (contentListener != null) {
            contentListener.onContentPanelChanged(panelName);
        }
    }
    
    /**
     * ノードタイプに応じて適切なパネルを表示します。
     * @param node 選択されたノード
     */
    public void showPanelForNode(XeadTreeNode node) {
        if (node == null) return;
        
        String type = node.getType();
        String panelName = getPanelNameForNodeType(type);
        if (panelName != null) {
            showPanel(panelName);
        }
    }
    
    private String getPanelNameForNodeType(String nodeType) {
        switch (nodeType) {
            case "System":
                return "jPanelSystem";
            case "SubjectAreaList":
                return "jPanelSubjectAreaList";
            case "SubjectArea":
                return "jPanelSubjectArea";
            case "RoleList":
                return "jPanelRoleList";
            case "Role":
                return "jPanelRole";
            case "Task":
                return "jPanelTask";
            case "SubsystemList":
                return "jPanelSubsystemList";
            case "Subsystem":
                return "jPanelSubsystem";
            case "TableList":
                return "jPanelTableList";
            case "Table":
                return "jPanelTable";
            case "TableFieldList":
                return "jPanelTableFieldList";
            case "TableField":
                return "jPanelTableField";
            case "TableKeyList":
                return "jPanelTableKeyList";
            case "TableKey":
                return "jPanelTableKey";
            case "FunctionList":
                return "jPanelFunctionList";
            case "Function":
                return "jPanelFunction";
            case "IOPanel":
                return "jPanelIOPanel";
            case "IOSpool":
                return "jPanelIOSpool";
            case "IOTable":
                return "jPanelIOTable";
            case "IOWebPage":
                return "jPanelIOWebPage";
            default:
                return null;
        }
    }
}
