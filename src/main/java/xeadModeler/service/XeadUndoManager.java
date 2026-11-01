package xeadModeler.service;

import xeadModeler.model.ChangeState;

import java.util.ArrayList;
import java.util.List;

/**
 * XEADアンドゥマネージャー
 * UI依存を除去した純粋なアンドゥ/リドゥ機能を提供します。
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadUndoManager {
    
    /**
     * アクションタイプ
     */
    public enum ActionType {
        ADD, REMOVE, MODIFY
    }
    
    /**
     * アンドゥエントリ
     */
    public static class UndoEntry {
        private final ActionType actionType;
        private final Object node;
        private final Object ownerNode;
        private final Object oldData;
        private final Object newData;
        
        public UndoEntry(ActionType actionType, Object node, Object ownerNode, Object oldData, Object newData) {
            this.actionType = actionType;
            this.node = node;
            this.ownerNode = ownerNode;
            this.oldData = oldData;
            this.newData = newData;
        }
        
        public ActionType getActionType() {
            return actionType;
        }
        
        public Object getNode() {
            return node;
        }
        
        public Object getOwnerNode() {
            return ownerNode;
        }
        
        public Object getOldData() {
            return oldData;
        }
        
        public Object getNewData() {
            return newData;
        }
    }
    
    /**
     * アンドゥマネージャーリスナー
     */
    public interface UndoManagerListener {
        void onUndoStateChanged(boolean canUndo, boolean canRedo);
    }
    
    private final List<UndoEntry> undoStack = new ArrayList<>();
    private final List<UndoEntry> redoStack = new ArrayList<>();
    private int indexOfLastUndo = -1;
    private boolean undoRedoActionBeingExecuted = false;
    private Object savedData;
    private Object savedOwnerNode;
    
    private ChangeState changeState;
    private UndoManagerListener listener;
    
    /**
     * アンドゥマネージャーを作成します
     */
    public XeadUndoManager() {
    }
    
    /**
     * 変更状態を設定します
     * @param changeState 変更状態
     */
    public void setChangeState(ChangeState changeState) {
        this.changeState = changeState;
    }
    
    /**
     * リスナーを設定します
     * @param listener リスナー
     */
    public void setListener(UndoManagerListener listener) {
        this.listener = listener;
    }
    
    /**
     * 追加アクションをログに記録します
     * @param node ノード
     * @param ownerNode 親ノード
     * @param undoable アンドゥ可能かどうか
     */
    public void addLogOfAdd(Object node, Object ownerNode, boolean undoable) {
        if (undoable && !undoRedoActionBeingExecuted) {
            undoStack.add(new UndoEntry(ActionType.ADD, node, ownerNode, null, null));
            redoStack.clear();
            indexOfLastUndo = undoStack.size() - 1;
            updateState();
        }
    }
    
    /**
     * 追加アクションをログに記録します（1引数オーバーロード）
     * @param node ノード
     */
    public void addLogOfAdd(Object node) {
        addLogOfAdd(node, null, true);
    }
    
    /**
     * 削除アクションをログに記録します
     * @param node ノード
     * @param ownerNode 親ノード
     * @param undoable アンドゥ可能かどうか
     */
    public void addLogOfRemove(Object node, Object ownerNode, boolean undoable) {
        if (undoable && !undoRedoActionBeingExecuted) {
            undoStack.add(new UndoEntry(ActionType.REMOVE, node, ownerNode, null, null));
            redoStack.clear();
            indexOfLastUndo = undoStack.size() - 1;
            updateState();
        }
    }
    
    /**
     * 削除アクションをログに記録します（1引数オーバーロード）
     * @param node ノード
     */
    public void addLogOfRemove(Object node) {
        addLogOfRemove(node, null, true);
    }
    
    /**
     * 修正前のデータを保存します
     * @param node ノード
     * @param data 修正前のデータ
     * @param ownerNode 親ノード
     */
    public void saveNodeBeforeModified(Object node, Object data, Object ownerNode) {
        this.savedData = data;
        this.savedOwnerNode = ownerNode;
    }
    
    /**
     * 修正前のデータを保存します（1引数オーバーロード）
     * @param node ノード
     */
    public void saveNodeBeforeModified(Object node) {
        this.savedData = null;
        this.savedOwnerNode = null;
    }
    
    /**
     * 修正後のアクションをログに記録します
     * @param node ノード
     * @param newData 修正後のデータ
     * @param undoable アンドゥ可能かどうか
     */
    public void addLogAfterModified(Object node, Object newData, boolean undoable) {
        if (undoable && !undoRedoActionBeingExecuted) {
            undoStack.add(new UndoEntry(ActionType.MODIFY, node, savedOwnerNode, savedData, newData));
            redoStack.clear();
            indexOfLastUndo = undoStack.size() - 1;
            updateState();
        }
    }
    
    /**
     * 修正後のアクションをログに記録します（1引数オーバーロード）
     * @param node ノード
     */
    public void addLogAfterModified(Object node) {
        addLogAfterModified(node, null, true);
    }
    
    /**
     * アンドゥを実行します
     * @return アンドゥを実行したかどうか
     */
    public boolean undo() {
        if (indexOfLastUndo < 0 || undoStack.isEmpty()) {
            return false;
        }
        
        undoRedoActionBeingExecuted = true;
        try {
            UndoEntry entry = undoStack.get(indexOfLastUndo);
            redoStack.add(entry);
            indexOfLastUndo--;
            
            // ここで実際のアンドゥ処理を呼び出す（サブクラスで実装）
            performUndo(entry);
            
            updateState();
            return true;
        } finally {
            undoRedoActionBeingExecuted = false;
        }
    }
    
    /**
     * リドゥを実行します
     * @return リドゥを実行したかどうか
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        
        undoRedoActionBeingExecuted = true;
        try {
            int redoIndex = redoStack.size() - 1;
            UndoEntry entry = redoStack.get(redoIndex);
            redoStack.remove(redoIndex);
            
            // ここで実際のリドゥ処理を呼び出す（サブクラスで実装）
            performRedo(entry);
            
            if (indexOfLastUndo < undoStack.size() - 1) {
                indexOfLastUndo++;
            }
            
            updateState();
            return true;
        } finally {
            undoRedoActionBeingExecuted = false;
        }
    }
    
    /**
     * アンドゥ処理を実行します（サブクラスでオーバーライド）
     * @param entry アンドゥエントリ
     */
    protected void performUndo(UndoEntry entry) {
        // デフォルト実装は空
    }
    
    /**
     * リドゥ処理を実行します（サブクラスでオーバーライド）
     * @param entry リドゥエントリ
     */
    protected void performRedo(UndoEntry entry) {
        // デフォルト実装は空
    }
    
    /**
     * 状態を更新します
     */
    private void updateState() {
        boolean canUndo = indexOfLastUndo >= 0 && !undoStack.isEmpty();
        boolean canRedo = !redoStack.isEmpty();
        
        if (changeState != null) {
            changeState.setChanged(canUndo);
        }
        
        if (listener != null) {
            listener.onUndoStateChanged(canUndo, canRedo);
        }
    }
    
    /**
     * アンドゥ可能かどうかを返します
     * @return アンドゥ可能の場合true
     */
    public boolean canUndo() {
        return indexOfLastUndo >= 0 && !undoStack.isEmpty();
    }
    
    /**
     * リドゥ可能かどうかを返します
     * @return リドゥ可能の場合true
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * アンドゥスタックのサイズを返します
     * @return サイズ
     */
    public int getUndoStackSize() {
        return undoStack.size();
    }
    
    /**
     * リドゥスタックのサイズを返します
     * @return サイズ
     */
    public int getRedoStackSize() {
        return redoStack.size();
    }
    
    /**
     * スタックをクリアします
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
        indexOfLastUndo = -1;
        updateState();
    }
    
    /**
     * アンドゥログをリセットします
     */
    public void resetLog() {
        undoStack.clear();
        redoStack.clear();
        indexOfLastUndo = -1;
        updateState();
    }
    
    /**
     * 修正内容を取得します
     * @return 修正内容のテキスト
     */
    public String getTextForAmendment() {
        return "";
    }
}
