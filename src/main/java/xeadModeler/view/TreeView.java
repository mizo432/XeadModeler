package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * ツリービュー
 * メインツリーのUIを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TreeView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    
    // ツリーコンポーネント
    private JScrollPane jScrollPaneTreeView = new JScrollPane();
    private JTree jTreeMain = new JTree();
    private DefaultTreeModel treeModel;
    
    // ポップアップメニュー
    private JPopupMenu jPopupMenuXeadTreeNode = new JPopupMenu();
    private JMenuItem jMenuItemXeadTreeNodeAdd = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddList = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeCopy = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeCut = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodePaste = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeDelete = new JMenuItem();
    private JMenu jMenuXeadTreeNodeAdd = new JMenu();
    private JMenuItem jMenuItemXeadTreeNodeAddIOPanel = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddIOSpool = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddIOWebPage = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddFK = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddSK = new JMenuItem();
    private JMenuItem jMenuItemXeadTreeNodeAddIndex = new JMenuItem();
    
    /**
     * ツリー操作リスナーインターフェース
     */
    public interface TreeListener {
        void onTreeNodeSelected(XeadTreeNode node);
        void onTreeNodeRightClick(XeadTreeNode node, int x, int y);
        void onTreeNodeDoubleClick(XeadTreeNode node);
        void onTreeNodeAdd();
        void onTreeNodeAddList();
        void onTreeNodePaste();
        void onTreeNodeCopy();
        void onTreeNodeCut();
        void onTreeNodeDelete();
        void onTreeNodeAddIOPanel();
        void onTreeNodeAddIOSpool();
        void onTreeNodeAddIOWebPage();
        void onTreeNodeAddFK();
        void onTreeNodeAddSK();
        void onTreeNodeAddIndex();
        void onTreeNodeKeyPressed(KeyEvent e);
    }
    
    private TreeListener treeListener;
    private Modeler modeler;
    
    public TreeView(Modeler modeler, TreeListener listener) {
        this.modeler = modeler;
        this.treeListener = listener;
        initComponents();
    }
    
    private void initComponents() {
        // ツリー設定
        jTreeMain.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTreeMain.setRowHeight(Modeler.TREE_ROW_HEIGHT);
        jTreeMain.setCellRenderer(new CustomTreeRenderer(modeler));
        jTreeMain.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        // イベントリスナー
        jTreeMain.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (treeListener != null) {
                    treeListener.onTreeNodeKeyPressed(e);
                }
            }
        });
        
        jTreeMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // ダブルクリック
                    TreePath path = jTreeMain.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        XeadTreeNode node = (XeadTreeNode) path.getLastPathComponent();
                        if (treeListener != null) {
                            treeListener.onTreeNodeDoubleClick(node);
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // 右クリック
                    TreePath path = jTreeMain.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        XeadTreeNode node = (XeadTreeNode) path.getLastPathComponent();
                        jTreeMain.setSelectionPath(path);
                        if (treeListener != null) {
                            treeListener.onTreeNodeRightClick(node, e.getX(), e.getY());
                        }
                    }
                }
            }
        });
        
        // ポップアップメニュー設定
        jMenuItemXeadTreeNodeAdd.setText(res.getString("S100"));
        jMenuItemXeadTreeNodeAdd.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAdd();
        });
        
        jMenuItemXeadTreeNodeAddList.setText(res.getString("S101"));
        jMenuItemXeadTreeNodeAddList.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddList();
        });
        
        jMenuItemXeadTreeNodePaste.setText(res.getString("S102"));
        jMenuItemXeadTreeNodePaste.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodePaste();
        });
        
        jMenuItemXeadTreeNodeCopy.setText(res.getString("S103"));
        jMenuItemXeadTreeNodeCopy.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeCopy();
        });
        
        jMenuItemXeadTreeNodeCut.setText(res.getString("S104"));
        jMenuItemXeadTreeNodeCut.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeCut();
        });
        
        jMenuItemXeadTreeNodeDelete.setText(res.getString("S105"));
        jMenuItemXeadTreeNodeDelete.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeDelete();
        });
        
        jMenuXeadTreeNodeAdd.setText(res.getString("S106"));
        jMenuItemXeadTreeNodeAddIOPanel.setText(res.getString("S107"));
        jMenuItemXeadTreeNodeAddIOPanel.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddIOPanel();
        });
        
        jMenuItemXeadTreeNodeAddIOSpool.setText(res.getString("S108"));
        jMenuItemXeadTreeNodeAddIOSpool.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddIOSpool();
        });
        
        jMenuItemXeadTreeNodeAddIOWebPage.setText(res.getString("S109"));
        jMenuItemXeadTreeNodeAddIOWebPage.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddIOWebPage();
        });
        
        jMenuItemXeadTreeNodeAddFK.setText(res.getString("S110"));
        jMenuItemXeadTreeNodeAddFK.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddFK();
        });
        
        jMenuItemXeadTreeNodeAddSK.setText(res.getString("S111"));
        jMenuItemXeadTreeNodeAddSK.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddSK();
        });
        
        jMenuItemXeadTreeNodeAddIndex.setText(res.getString("S112"));
        jMenuItemXeadTreeNodeAddIndex.addActionListener(e -> {
            if (treeListener != null) treeListener.onTreeNodeAddIndex();
        });
        
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddIOPanel);
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddIOSpool);
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddIOWebPage);
        jMenuXeadTreeNodeAdd.addSeparator();
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddFK);
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddSK);
        jMenuXeadTreeNodeAdd.add(jMenuItemXeadTreeNodeAddIndex);
        
        jMenuItemXeadTreeNodeAdd.addActionListener(e -> {
            // アドオンメニューを表示
        });
        
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodeAdd);
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodeAddList);
        jPopupMenuXeadTreeNode.addSeparator();
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodeCopy);
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodeCut);
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodePaste);
        jPopupMenuXeadTreeNode.addSeparator();
        jPopupMenuXeadTreeNode.add(jMenuItemXeadTreeNodeDelete);
        
        // スクロールペインにツリーを追加
        jScrollPaneTreeView.getViewport().add(jTreeMain, null);
        jScrollPaneTreeView.setMinimumSize(new Dimension(0, 0));
    }
    
    public JTree getJTreeMain() {
        return jTreeMain;
    }
    
    public JScrollPane getJScrollPaneTreeView() {
        return jScrollPaneTreeView;
    }
    
    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }
    
    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
        jTreeMain.setModel(treeModel);
    }
    
    public XeadTreeNode getSelectedTreeNode() {
        TreePath path = jTreeMain.getSelectionPath();
        if (path != null) {
            return (XeadTreeNode) path.getLastPathComponent();
        }
        return null;
    }
    
    public void expandAll() {
        for (int i = 0; i < jTreeMain.getRowCount(); i++) {
            jTreeMain.expandRow(i);
        }
    }
    
    public void collapseAll() {
        for (int i = jTreeMain.getRowCount() - 1; i >= 0; i--) {
            jTreeMain.collapseRow(i);
        }
    }
    
    /**
     * カスタムツリーレンダラー
     */
    static class CustomTreeRenderer extends DefaultTreeCellRenderer {
        private static final long serialVersionUID = 1L;
        private Modeler modeler;
        
        public CustomTreeRenderer(Modeler modeler) {
            this.modeler = modeler;
        }
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object treeNode, boolean selected,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component renderer = super.getTreeCellRendererComponent(tree, treeNode, selected, expanded,
                    leaf, row, hasFocus);
            
            if (hasFocus) {
                this.setBackgroundSelectionColor(Modeler.ACTIVE_COLOR);
            } else {
                this.setBackgroundSelectionColor(Modeler.INACTIVE_COLOR);
                this.setForeground(Color.black);
            }
            
            // テキスト表示
            this.setText(((XeadTreeNode) treeNode).toString());
            
            // アイコン設定
            String nodeType = ((XeadTreeNode) treeNode).getType();
            boolean isError = ((XeadTreeNode) treeNode).isError();
            boolean isExtRefered = ((XeadTreeNode) treeNode).isExtRefered();
            
            if ("System".equals(nodeType)) {
                this.setIcon(modeler.imageIconSystem);
            } else if ("SubjectAreaList".equals(nodeType)) {
                this.setIcon(modeler.imageIconSubjectAreaList);
            } else if ("SubjectArea".equals(nodeType)) {
                this.setIcon(modeler.imageIconSubjectArea);
            } else if ("RoleList".equals(nodeType)) {
                this.setIcon(modeler.imageIconRoleList);
            } else if ("Role".equals(nodeType)) {
                this.setIcon(modeler.imageIconRole);
            } else if ("Task".equals(nodeType)) {
                this.setIcon(modeler.imageIconTask);
            } else if ("SubsystemList".equals(nodeType)) {
                this.setIcon(isError ? modeler.imageIconSubsystemListErr : modeler.imageIconSubsystemList);
            } else if ("Subsystem".equals(nodeType)) {
                this.setIcon(isError ? modeler.imageIconSubsystemErr : modeler.imageIconSubsystem);
            } else if ("TableList".equals(nodeType)) {
                this.setIcon(isError ? modeler.imageIconTableListErr : modeler.imageIconTableList);
            } else if ("Table".equals(nodeType)) {
                if (isError) {
                    this.setIcon(modeler.imageIconTableErr);
                } else if (isExtRefered) {
                    this.setIcon(modeler.imageIconTableSyn);
                } else {
                    this.setIcon(modeler.imageIconTable);
                }
            } else if ("TableFieldList".equals(nodeType)) {
                this.setIcon(modeler.imageIconFieldList);
            } else if ("TableField".equals(nodeType)) {
                this.setIcon(modeler.imageIconField);
            } else if ("TableKeyList".equals(nodeType)) {
                this.setIcon(modeler.imageIconKeyList);
            } else if ("TableKey".equals(nodeType)) {
                this.setIcon(modeler.imageIconKey);
            } else if ("FunctionList".equals(nodeType)) {
                this.setIcon(modeler.imageIconFunctionList);
            } else if ("Function".equals(nodeType)) {
                this.setIcon(modeler.imageIconFunction);
            } else if ("ReccursiveFunction".equals(nodeType)) {
                this.setIcon(modeler.imageIconFunction);
            } else if ("IOPanel".equals(nodeType)) {
                this.setIcon(modeler.imageIconIOPanel);
            } else if ("IOTable".equals(nodeType)) {
                this.setIcon(modeler.imageIconIOTable);
            } else if ("IOSpool".equals(nodeType)) {
                this.setIcon(modeler.imageIconIOSpool);
            } else if ("IOWebPage".equals(nodeType)) {
                this.setIcon(modeler.imageIconIOWebPage);
            } else if ("TaskActionFirst".equals(nodeType)) {
                this.setIcon(modeler.imageIconActionFirst);
            } else if ("TaskAction".equals(nodeType)) {
                this.setIcon(modeler.imageIconAction);
            } else if ("TaskActionLast".equals(nodeType)) {
                this.setIcon(modeler.imageIconAction);
            } else if ("ForeignFunction".equals(nodeType)) {
                this.setIcon(modeler.imageIconForeignFunction);
            } else if ("ForeignTable".equals(nodeType)) {
                this.setIcon(modeler.imageIconForeignTable);
            }
            
            return renderer;
        }
    }
}
