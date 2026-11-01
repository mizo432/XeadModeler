package xeadModeler.service;

import xeadModeler.model.ChangeState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * XeadUndoManagerのテスト
 */
@DisplayName("XeadUndoManagerテスト")
public class XeadUndoManagerTest {
    
    private XeadUndoManager undoManager;
    private ChangeState changeState;
    
    @BeforeEach
    public void setUp() {
        undoManager = new XeadUndoManager();
        changeState = new ChangeState();
        undoManager.setChangeState(changeState);
    }
    
    @Test
    @DisplayName("初期状態はアンドゥ/リドゥ不可")
    public void testInitialState() {
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
        assertFalse(changeState.isChanged());
    }
    
    @Test
    @DisplayName("追加アクションのログ記録")
    public void testAddLogOfAdd() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node, owner, true);
        
        assertTrue(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
        assertTrue(changeState.isChanged());
        assertEquals(1, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("undoable=falseの場合はログ記録されない")
    public void testAddLogOfAddNotUndoable() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node, owner, false);
        
        assertFalse(undoManager.canUndo());
        assertEquals(0, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("削除アクションのログ記録")
    public void testAddLogOfRemove() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfRemove(node, owner, true);
        
        assertTrue(undoManager.canUndo());
        assertEquals(1, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("修正アクションのログ記録")
    public void testAddLogAfterModified() {
        Object node = new Object();
        Object oldData = "old";
        Object newData = "new";
        Object owner = new Object();
        
        undoManager.saveNodeBeforeModified(node, oldData, owner);
        undoManager.addLogAfterModified(node, newData, true);
        
        assertTrue(undoManager.canUndo());
        assertEquals(1, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("アンドゥ実行")
    public void testUndo() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node, owner, true);
        assertTrue(undoManager.canUndo());
        
        boolean result = undoManager.undo();
        
        assertTrue(result);
        assertTrue(undoManager.canRedo());
        assertEquals(1, undoManager.getRedoStackSize());
    }
    
    @Test
    @DisplayName("アンドゥ不可時に実行するとfalse")
    public void testUndoWhenCannotUndo() {
        boolean result = undoManager.undo();
        assertFalse(result);
    }
    
    @Test
    @DisplayName("リドゥ実行")
    public void testRedo() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node, owner, true);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
        
        boolean result = undoManager.redo();
        
        assertTrue(result);
        assertFalse(undoManager.canRedo());
    }
    
    @Test
    @DisplayName("リドゥ不可時に実行するとfalse")
    public void testRedoWhenCannotRedo() {
        boolean result = undoManager.redo();
        assertFalse(result);
    }
    
    @Test
    @DisplayName("複数のアンドゥ/リドゥ")
    public void testMultipleUndoRedo() {
        Object node1 = new Object();
        Object node2 = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node1, owner, true);
        undoManager.addLogOfAdd(node2, owner, true);
        
        assertEquals(2, undoManager.getUndoStackSize());
        
        undoManager.undo();
        assertEquals(1, undoManager.getRedoStackSize());
        // getUndoStackSize()はスタックの総数を返す（2のまま）
        assertEquals(2, undoManager.getUndoStackSize());
        
        undoManager.undo();
        assertEquals(2, undoManager.getRedoStackSize());
        assertEquals(2, undoManager.getUndoStackSize());
        
        // 全てアンドゥすると変更状態はfalse
        assertFalse(changeState.isChanged());
    }
    
    @Test
    @DisplayName("新しいアクションでリドゥスタックがクリアされる")
    public void testRedoStackClearedOnNewAction() {
        Object node1 = new Object();
        Object node2 = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node1, owner, true);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
        
        undoManager.addLogOfAdd(node2, owner, true);
        assertFalse(undoManager.canRedo());
    }
    
    @Test
    @DisplayName("スタッククリア")
    public void testClear() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node, owner, true);
        undoManager.undo();
        
        undoManager.clear();
        
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
        assertEquals(0, undoManager.getUndoStackSize());
        assertEquals(0, undoManager.getRedoStackSize());
    }
    
    @Test
    @DisplayName("リスナー通知")
    public void testListenerNotification() {
        final boolean[] canUndoValue = {false};
        final boolean[] canRedoValue = {false};
        
        undoManager.setListener((canUndo, canRedo) -> {
            canUndoValue[0] = canUndo;
            canRedoValue[0] = canRedo;
        });
        
        Object node = new Object();
        Object owner = new Object();
        undoManager.addLogOfAdd(node, owner, true);
        
        assertTrue(canUndoValue[0]);
        assertFalse(canRedoValue[0]);
    }
    
    @Test
    @DisplayName("アクションタイプ確認")
    public void testActionType() {
        assertEquals(XeadUndoManager.ActionType.ADD, XeadUndoManager.ActionType.ADD);
        assertEquals(XeadUndoManager.ActionType.REMOVE, XeadUndoManager.ActionType.REMOVE);
        assertEquals(XeadUndoManager.ActionType.MODIFY, XeadUndoManager.ActionType.MODIFY);
    }
    
    @Test
    @DisplayName("削除アクションのundoable=false")
    public void testAddLogOfRemoveNotUndoable() {
        Object node = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfRemove(node, owner, false);
        
        assertFalse(undoManager.canUndo());
        assertEquals(0, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("修正アクションのundoable=false")
    public void testAddLogAfterModifiedNotUndoable() {
        Object node = new Object();
        Object oldData = "old";
        Object newData = "new";
        Object owner = new Object();
        
        undoManager.saveNodeBeforeModified(node, oldData, owner);
        undoManager.addLogAfterModified(node, newData, false);
        
        assertFalse(undoManager.canUndo());
        assertEquals(0, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("アンドゥ後に新しいアクションでリドゥスタックがクリア")
    public void testRedoStackClearedOnRemoveAction() {
        Object node1 = new Object();
        Object node2 = new Object();
        Object owner = new Object();
        
        undoManager.addLogOfAdd(node1, owner, true);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
        
        undoManager.addLogOfRemove(node2, owner, true);
        assertFalse(undoManager.canRedo());
    }
    
    @Test
    @DisplayName("変更状態がnullの場合")
    public void testNullChangeState() {
        XeadUndoManager managerWithoutChangeState = new XeadUndoManager();
        // changeStateを設定しない
        
        Object node = new Object();
        Object owner = new Object();
        managerWithoutChangeState.addLogOfAdd(node, owner, true);
        
        // 例外が発生しない
        assertTrue(managerWithoutChangeState.canUndo());
    }
    
    @Test
    @DisplayName("リスナーがnullの場合")
    public void testNullListener() {
        XeadUndoManager managerWithoutListener = new XeadUndoManager();
        // リスナーを設定しない
        
        Object node = new Object();
        Object owner = new Object();
        managerWithoutListener.addLogOfAdd(node, owner, true);
        
        // 例外が発生しない
        assertTrue(managerWithoutListener.canUndo());
    }
    
    @Test
    @DisplayName("アンドゥスタックサイズの確認")
    public void testUndoStackSize() {
        Object owner = new Object();
        
        assertEquals(0, undoManager.getUndoStackSize());
        
        undoManager.addLogOfAdd(new Object(), owner, true);
        assertEquals(1, undoManager.getUndoStackSize());
        
        undoManager.addLogOfAdd(new Object(), owner, true);
        assertEquals(2, undoManager.getUndoStackSize());
        
        undoManager.addLogOfAdd(new Object(), owner, true);
        assertEquals(3, undoManager.getUndoStackSize());
    }
    
    @Test
    @DisplayName("リドゥスタックサイズの確認")
    public void testRedoStackSize() {
        Object owner = new Object();
        
        assertEquals(0, undoManager.getRedoStackSize());
        
        undoManager.addLogOfAdd(new Object(), owner, true);
        undoManager.undo();
        assertEquals(1, undoManager.getRedoStackSize());
        
        // redoを実行するとredoStackからエントリが削除される
        undoManager.redo();
        assertEquals(0, undoManager.getRedoStackSize());
        
        // 再度undoしてredoStackにエントリを追加
        undoManager.undo();
        assertEquals(1, undoManager.getRedoStackSize());
    }
}
