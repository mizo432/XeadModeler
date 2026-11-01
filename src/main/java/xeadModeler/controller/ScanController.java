package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.TreeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * スキャンコントローラー
 * モデルのスキャン操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class ScanController {
    
    @Autowired
    private TreeService treeService;
    
    private Modeler modeler;
    
    public ScanController(Modeler modeler) {
        this.modeler = modeler;
    }
    
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }
    
    /**
     * モデルのスキャンを実行します。
     */
    public void scanModel() {
        // TODO: モデルスキャン処理を実装
    }
    
    /**
     * 未使用のテーブルを検出します。
     */
    public void findUnusedTables() {
        // TODO: 未使用テーブル検出処理を実装
    }
    
    /**
     * 未使用の機能を検出します。
     */
    public void findUnusedFunctions() {
        // TODO: 未使用機能検出処理を実装
    }
    
    /**
     * 未使用のフィールドを検出します。
     */
    public void findUnusedFields() {
        // TODO: 未使用フィールド検出処理を実装
    }
    
    /**
     * 循環参照を検出します。
     */
    public void findCircularReferences() {
        // TODO: 循環参照検出処理を実装
    }
    
    /**
     * 整合性チェックを実行します。
     */
    public void checkConsistency() {
        // TODO: 整合性チェック処理を実装
    }
}
