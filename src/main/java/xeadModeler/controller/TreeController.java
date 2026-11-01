package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;
import xeadModeler.service.TreeService;
import xeadModeler.view.TreeView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * ツリーコントローラー
 * ツリー操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class TreeController implements TreeView.TreeListener {
    
    @Autowired
    private TreeService treeService;
    
    private Modeler modeler;
    private TreeView treeView;
    private JFrame parentFrame;
    
    public TreeController(Modeler modeler, TreeView treeView, JFrame parentFrame) {
        this.modeler = modeler;
        this.treeView = treeView;
        this.parentFrame = parentFrame;
    }
    
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }
    
    // === TreeView.TreeListener インターフェースの実装 ===
    
    @Override
    public void onTreeNodeSelected(XeadTreeNode node) {
        // ノード選択時の処理
        if (node != null) {
            modeler.currentMainTreeNode = node;
            // TODO: コンテンツペインを更新
        }
    }
    
    @Override
    public void onTreeNodeRightClick(XeadTreeNode node, int x, int y) {
        // 右クリック時のポップアップメニュー表示
        if (node != null && treeView != null) {
            // TODO: ポップアップメニューを表示
            JPopupMenu popup = treeView.getJScrollPaneTreeView().getComponentPopupMenu();
            if (popup != null) {
                popup.show(treeView.getJTreeMain(), x, y);
            }
        }
    }
    
    @Override
    public void onTreeNodeDoubleClick(XeadTreeNode node) {
        // ダブルクリック時の処理
        if (node != null) {
            // TODO: ノードタイプに応じた処理を実行
        }
    }
    
    @Override
    public void onTreeNodeKeyPressed(KeyEvent e) {
        // キー入力時の処理
        // TODO: キーボードショートカットの処理
    }
    
    @Override
    public void onTreeNodeAdd() {
        // ノード追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: ノードを追加
        }
    }
    
    @Override
    public void onTreeNodeAddList() {
        // リスト追加
        // TODO: リストを追加
    }
    
    @Override
    public void onTreeNodePaste() {
        // ペースト
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: ノードをペースト
        }
    }
    
    @Override
    public void onTreeNodeCopy() {
        // コピー
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: ノードをコピー
        }
    }
    
    @Override
    public void onTreeNodeCut() {
        // 切り取り
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: ノードを切り取り
        }
    }
    
    @Override
    public void onTreeNodeDelete() {
        // 削除
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: ノードを削除
        }
    }
    
    @Override
    public void onTreeNodeAddIOPanel() {
        // IOPanel追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: IOPanelを追加
        }
    }
    
    @Override
    public void onTreeNodeAddIOSpool() {
        // IOSpool追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: IOSpoolを追加
        }
    }
    
    @Override
    public void onTreeNodeAddIOWebPage() {
        // IOWebPage追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: IOWebPageを追加
        }
    }
    
    @Override
    public void onTreeNodeAddFK() {
        // FK追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: FKを追加
        }
    }
    
    @Override
    public void onTreeNodeAddSK() {
        // SK追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: SKを追加
        }
    }
    
    @Override
    public void onTreeNodeAddIndex() {
        // Index追加
        XeadTreeNode currentNode = treeView.getSelectedTreeNode();
        if (currentNode != null) {
            // TODO: Indexを追加
        }
    }
}
