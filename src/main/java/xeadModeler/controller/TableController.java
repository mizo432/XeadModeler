package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;
import xeadModeler.service.TreeService;
import xeadModeler.view.ContentView;
import xeadModeler.view.TableView;
import xeadModeler.view.TableFieldView;
import xeadModeler.view.TableKeyView;
import xeadModeler.view.TableListView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * テーブルコントローラー
 * テーブル関連の操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class TableController {
    
    @Autowired
    private TreeService treeService;
    
    private Modeler modeler;
    private ContentView contentView;
    
    public TableController(Modeler modeler, ContentView contentView) {
        this.modeler = modeler;
        this.contentView = contentView;
    }
    
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }
    
    /**
     * テーブルの追加
     */
    public void addTable() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルを追加
        }
    }
    
    /**
     * テーブルの削除
     */
    public void deleteTable() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルを削除
        }
    }
    
    /**
     * テーブルフィールドの追加
     */
    public void addTableField() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルフィールドを追加
        }
    }
    
    /**
     * テーブルフィールドの削除
     */
    public void deleteTableField() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルフィールドを削除
        }
    }
    
    /**
     * テーブルキーの追加
     */
    public void addTableKey() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルキーを追加
        }
    }
    
    /**
     * テーブルキーの削除
     */
    public void deleteTableKey() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: テーブルキーを削除
        }
    }
    
    /**
     * 外部キーの追加
     */
    public void addForeignKey() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: 外部キーを追加
        }
    }
    
    /**
     * 検索キーの追加
     */
    public void addSearchKey() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: 検索キーを追加
        }
    }
    
    /**
     * インデックスの追加
     */
    public void addIndex() {
        XeadTreeNode currentNode = modeler.currentMainTreeNode;
        if (currentNode != null) {
            // TODO: インデックスを追加
        }
    }
    
    /**
     * テーブル情報の更新
     */
    public void updateTableInfo() {
        TableView tableView = contentView.getTableView();
        if (tableView != null) {
            // TODO: テーブル情報を更新
        }
    }
    
    /**
     * テーブルフィールド情報の更新
     */
    public void updateTableFieldInfo() {
        TableFieldView tableFieldView = contentView.getTableFieldView();
        if (tableFieldView != null) {
            // TODO: テーブルフィールド情報を更新
        }
    }
    
    /**
     * テーブルキー情報の更新
     */
    public void updateTableKeyInfo() {
        TableKeyView tableKeyView = contentView.getTableKeyView();
        if (tableKeyView != null) {
            // TODO: テーブルキー情報を更新
        }
    }
}
