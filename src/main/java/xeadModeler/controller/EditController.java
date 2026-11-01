package xeadModeler.controller;

import xeadModeler.Modeler;
import xeadModeler.service.XeadUndoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 編集コントローラー
 * Undo/Redoなどの編集操作のビジネスロジックを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Component
public class EditController {
    
    @Autowired
    private XeadUndoManager undoManager;
    
    private Modeler modeler;
    
    public EditController(Modeler modeler) {
        this.modeler = modeler;
    }
    
    public void setUndoManager(XeadUndoManager undoManager) {
        this.undoManager = undoManager;
    }
    
    /**
     * Undo操作を実行します。
     */
    public void undo() {
        if (undoManager != null && undoManager.canUndo()) {
            undoManager.undo();
        }
    }
    
    /**
     * Redo操作を実行します。
     */
    public void redo() {
        if (undoManager != null && undoManager.canRedo()) {
            undoManager.redo();
        }
    }
    
    /**
     * Undo可能かどうかを確認します。
     */
    public boolean canUndo() {
        return undoManager != null && undoManager.canUndo();
    }
    
    /**
     * Redo可能かどうかを確認します。
     */
    public boolean canRedo() {
        return undoManager != null && undoManager.canRedo();
    }
    
    /**
     * 選択範囲のコピー
     */
    public void copy() {
        // TODO: コピー処理を実装
    }
    
    /**
     * 選択範囲の切り取り
     */
    public void cut() {
        // TODO: 切り取り処理を実装
    }
    
    /**
     * クリップボードからのペースト
     */
    public void paste() {
        // TODO: ペースト処理を実装
    }
    
    /**
     * 全選択
     */
    public void selectAll() {
        // TODO: 全選択処理を実装
    }
    
    /**
     * 選択解除
     */
    public void deselectAll() {
        // TODO: 選択解除処理を実装
    }
}
